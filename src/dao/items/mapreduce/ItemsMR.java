package dao.items.mapreduce;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoException;

import dao.items.ItemsDB;
import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan
 * Unoptimized MapReduce management  		
 * //TODO opti : Gerer les mots accentue et et ameliorer le filtre de separation
 *  (ne va pas du tout) + d'autres opti de pattern & conversion , filtre , epuration,
 *  unification des racines communes de mots */
public class ItemsMR {
	
	public static DBCollection collection = ItemsDB.collection;
	static String mrpattern="/[^\\d\\w]+/";   
	
	/**
	 * @param query
	 * @return
	 * @throws DatabaseException
	 * @throws JSONException */
	public static List<ObjetRSV> pertinence(String query,DBCursor cursor) throws DatabaseException, JSONException{
		ItemsMR.updateTFDF();//TODO replace by an mrprog in future sprints
		Set<String> querywords =wordSet(query,ItemsMR.mrpattern);	
		List<ObjetRSV> results = new ArrayList<ObjetRSV>();
		try{
			Connection sqldbconect = KasuDB.SQLConnection();
			while(cursor.hasNext()){
				DBObject doc = cursor.next();
				String docID = ((ObjectId)doc.get("_id")).toString();
				//calculate document's score
				Double score = 0.0;
				for(String word : querywords){
					//TF
					Statement s = sqldbconect.createStatement();
					ResultSet rs = s.executeQuery("SELECT tf FROM TF WHERE"
							+ " word = \""+word+"\" AND docID=\""+ docID +"\"");
					if(!rs.next())   
						continue; 
					double tf =rs.getDouble("tf");
					s.close();

					//DF
					s = sqldbconect.createStatement();
					rs = s.executeQuery("SELECT df FROM DF WHERE word = \"" + word+"\"");
					rs.next();
					double df =rs.getDouble("df");
					s.close();
					/** Be aware : 
					 * DBCursor.count(): Counts the number of objects matching the query.
					 * This does not take limit/skip into consideration.
					 * DBCursor.size(): Counts the number of objects matching the query.
					 * This does take limit/skip into consideration*/
					score = score + tf * Math.log(cursor.count()/df); }
				/** This suppose cursor contains all the results without limit/skip
				 * Limit will be in the display function (client side) */
				results.add(new ObjetRSV(doc,score));}}
		catch(SQLException e){throw new DatabaseException(e.getMessage());}

		Collections.sort(results,Collections.reverseOrder());
		//for(ObjetRSV o: results)	System.out.println(o);//Debug
		List<ObjetRSV> pertinentResults=new ArrayList<ObjetRSV>();
		for(ObjetRSV orsv : results)
			if(orsv.getRsv()>0)  
				pertinentResults.add(orsv);
		return pertinentResults;}
	
	/**
	 * Return a set of words contained in a string (only one occurence of a word)
	 * @param string
	 * @param pattern
	 * @return */
	private static Set<String> wordSet(String string,String pattern) {
		return new HashSet<String>(
				Arrays.asList(string.toLowerCase().split(pattern)));}

	/**
	 * Filling both TF & DF tables
	 * @throws JSONException
	 * @throws DatabaseException */
	public static void updateTFDF() throws JSONException, DatabaseException{
		updateTF();updateDF();}


	/**
	 * Filling of the SQL TF table 
	 * @throws JSONException
	 * @throws DatabaseException */
	public static void updateTF() throws  JSONException, DatabaseException {
		List<JSONObject> l = TFList(); 
		try {
			Connection c = KasuDB.SQLConnection();
			Statement ds = c.createStatement();
			ds.executeUpdate("DELETE FROM TF ;");
			ds.close();
			for (int i=0;i<l.size();i++) {
				BasicDBObject key = (BasicDBObject)l.get(i).get("key");
				String query = "INSERT INTO TF VALUES ('"+key.getString("word")+"','"
						+((ObjectId)key.get("mongodocid")).toString()+"',"
						+l.get(i).getInt("tf")+",NOW());";
				Statement is = c.createStatement();
				is.executeUpdate(query);
				is.close();	
			}c.close();}
		catch (SQLException e){
			throw new DatabaseException("Error while filling Term Frequency table :" 
					+e.getMessage());}}	


	/**
	 * Filling of the SQL DF table 
	 * @throws JSONException
	 * @throws DatabaseException */
	public static void updateDF() throws   DatabaseException, JSONException {
		List<JSONObject> l = DFList(); 
		try {
			Connection c = KasuDB.SQLConnection();
			Statement ds = c.createStatement();
			ds.executeUpdate("DELETE FROM DF ;");
			ds.close();
			for (int i=0;i<l.size();i++){
				String query = "INSERT INTO DF VALUES ('"+l.get( i ).getString("word")+"',"
						+l.get( i ).getInt("df")+",NOW());";
				Statement is = c.createStatement();
				is.executeUpdate(query);
				is.close();	
			}c.close();}
		catch (SQLException e){
			throw new DatabaseException("Error while filling Document Frequency table : "
					+e.getMessage());}}	


	/**
	 * Term Frequency BackEnd work
	 * @return
	 * @throws JSONException */
	private static List<JSONObject> TFList() throws JSONException {
		String map ="function(){var cont=this.title + \" \"+this.description;"
				+ "cont=cont.toLowerCase();"
				+ "var Words=cont.split("+mrpattern+");"
				+ "for(var i in Words){if(Words[i]!=\"\")"
				+ "emit({mongodocid:this._id,word:Words[i]},1);}}";

		String reduce = "function(key,values){total=0;"
				+ "for(var i in values){total+=values[i];}return total}";

		MapReduceOutput out = collection.mapReduce(
				new MapReduceCommand(collection, map, reduce,null
						, MapReduceCommand.OutputType.INLINE, null));

		//System.out.println("TFList : "+out.results());
		List<JSONObject> l = new ArrayList<JSONObject>();
		for (DBObject o : out.results()) 	
			l.add(new JSONObject().put("tf",o.get("value")).put("key",o.get("_id")));
		return l;}


	/**
	 * Document Frequency BackEnd work
	 * @return
	 * @throws UnknownHostException
	 * @throws MongoException
	 * @throws JSONException */
	private static List<JSONObject> DFList() throws JSONException {  
		String map ="function () {var cont = this.title + \" \"+this.description;"
				+ "cont = cont.toLowerCase();"
				+ "var Words = cont.split("+mrpattern+");"
				+ "dictionary = Words.filter(function(elem,pos) "
				+ "{return Words.indexOf(elem)==pos;});"
				+ "for(var i in dictionary){if(dictionary[i]!=\"\")"
				+ "emit(dictionary[i],1);}}";

		String reduce = "function(key,values){total=0;"
				+ "for(var i in values){total+=values[i];}return total}";

		MapReduceOutput out = collection.mapReduce(
				new MapReduceCommand( collection, map, reduce,null,
						MapReduceCommand.OutputType.INLINE, null ));

		//System.out.println("DFList : "+out.results());
		List<JSONObject> l = new ArrayList<JSONObject>();
		for (DBObject o:out.results())
			l.add(new JSONObject().put("df",o.get("value")).put("word", o.get("_id")));
		return l;}
}