package dao.items;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import dao.search.ObjetRSV;
import dao.search.PatternsHolder;
import exceptions.DatabaseException;
import kasudb.KasuDB;

/**Leboncoin/Zone-telechargement search engines styles (mr on tf-idf)/(pattern-matching)
 * @author ANAGBLA Joan
 * map-reduce [with one level of optimization : accents ]   		
 * //TODO opti 2nd level : unification des racines communes de mots, pluriels , etc */
public class ItemsMR {

	private static DBCollection collection = ItemsDB.collection;
	private static DBCollection tfcoll = KasuDB.getMongoCollection("tf");
	private static DBCollection dfcoll = KasuDB.getMongoCollection("df");

	private static String mrpattern=PatternsHolder.notWord;   

	/**
	 * @param query
	 * @return
	 * @throws DatabaseException
	 * @throws JSONException */
	public static List<ObjetRSV> pertinence(String query,DBCursor cursor) throws JSONException, DatabaseException{
		Set<String> querywords =PatternsHolder.wordSet(query,ItemsMR.mrpattern);	
		List<ObjetRSV> results = new ArrayList<ObjetRSV>();

		while(cursor.hasNext()){
			DBObject doc = cursor.next();
			String docID = ((ObjectId)doc.get("_id")).toString();
			//calculate document's score
			Double score = 0.0;
			for(String word : querywords){
				//TF
				DBCursor dbc = tfcoll.find(
						new BasicDBObject()
						.append("word",word)
						.append("docID",docID)
						);
				if(!dbc.hasNext()) continue;
				if(dbc.count()>1) 
					throw new DatabaseException("TF Base is inconsistent!");

				double tf =(double)dbc.next().get("tf");

				//DF
				dbc = dfcoll.find(
						new BasicDBObject()
						.append("word",word)
						);
				if(!dbc.hasNext()) 
					throw new DatabaseException("DF Base is inconsistent!");
				if(dbc.count()>1) 
					throw new DatabaseException("DF Base is inconsistent!");

				double df =(double)dbc.next().get("df");

				/** Be aware : 
				 * DBCursor.count(): Counts the number of objects matching the query.
				 * This does not take limit/skip into consideration.
				 * DBCursor.size(): Counts the number of objects matching the query.
				 * This does take limit/skip into consideration*/
				System.out.println("Pertinence of "+word+" in doc-"+docID+":"
						+ " tf="+tf+" df="+df+" score="+score);//Debug formula is respected
				score = score + tf * Math.log(cursor.count()/df); }
			/** This suppose cursor contains all the results without limit/skip
			 * Limit will be in the display function (client side) */
			results.add(new ObjetRSV(doc,score));}

		Collections.sort(results,Collections.reverseOrder());
		for(ObjetRSV o: results)	System.out.println(o);//Debug
		List<ObjetRSV> pertinentResults=new ArrayList<ObjetRSV>();
		for(ObjetRSV orsv : results)
			if(orsv.getRsv()>0)  
				pertinentResults.add(orsv);
		return pertinentResults;}

	

	/**
	 * Filling both TF & DF tables
	 * @throws JSONException
	 * @throws DatabaseException */
	public static void updateTFDF() throws JSONException{
		updateTF();updateDF();}

	/**
	 * local test
	 * @param args
	 * @throws JSONException */
	public static void main(String[] args) throws JSONException {
		updateTFDF();
	}


	/**
	 * Filling of the SQL TF table 
	 * @throws JSONException
	 * @throws DatabaseException */
	public static void updateTF() throws  JSONException {
		List<JSONObject> l = TFList(); 

		tfcoll.remove(new BasicDBObject());
		for (int i=0;i<l.size();i++)
			tfcoll.insert(
					new BasicDBObject()
					.append("word",(
							(BasicDBObject)l.get(i).get("key"))
							.getString("word")
							)
					.append("docID", ( (ObjectId)
							((BasicDBObject)l.get(i).get("key"))
							.get("mongodocid") ).toString()
							)
					.append("tf", l.get(i).getDouble("tf")	)
					.append("defdate", new Date())
					); 
	}	


	/**
	 * Filling of the SQL DF table 
	 * @throws JSONException
	 * @throws DatabaseException */
	public static void updateDF() throws JSONException {
		List<JSONObject> l = DFList(); 

		dfcoll.remove(new BasicDBObject());
		for (int i=0;i<l.size();i++)
			dfcoll.insert(
					new BasicDBObject()
					.append("word", l.get(i).getString("word"))
					.append("df", l.get(i).getDouble("df"))
					.append("defdate", new Date())
					);
	}	


	/**
	 * Term Frequency BackEnd work
	 * @return
	 * @throws JSONException */
	private static List<JSONObject> TFList() throws JSONException {
		String map ="function(){var cont=this.title + \" \"+this.description;"
				+ "cont=cont.toLowerCase();"
				+ "cont=cont.replace(/[����]/gi,\"e\");"
				+ "cont=cont.replace(/[���]/gi,\"a\");"
				+ "cont=cont.replace(/[���]/gi,\"u\");"
				+ "cont=cont.replace(/[��]/gi,\"o\");"
				+ "cont=cont.replace(/[��]/gi,\"e\");"
				+ "cont=cont.replace(/[�]/gi,\"y\");"
				+ "cont=cont.replace(/[�]/gi,\"c\");"
				+ "var Words=cont.split(/"+mrpattern+"/);"
				+ "for(var i in Words){if(Words[i]!=\"\")"
				+ "emit({mongodocid:this._id,word:Words[i]},1);}}";

		String reduce = "function(key,values){total=0;"
				+ "for(var i in values){total+=values[i];}return total}";

		MapReduceOutput out = collection.mapReduce(
				new MapReduceCommand(collection, map, reduce,null
						, MapReduceCommand.OutputType.INLINE, null));

		System.out.println("TFList : "+out.results());//Debug
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
				+ "cont=cont.replace(/[����]/gi,\"e\");"
				+ "cont=cont.replace(/[���]/gi,\"a\");"
				+ "cont=cont.replace(/[���]/gi,\"u\");"
				+ "cont=cont.replace(/[��]/gi,\"o\");"
				+ "cont=cont.replace(/[��]/gi,\"e\");"
				+ "cont=cont.replace(/[�]/gi,\"y\");"
				+ "cont=cont.replace(/[�]/gi,\"c\");"
				+ "var Words = cont.split(/"+mrpattern+"/);"
				+ "dictionary = Words.filter(function(elem,pos) "
				+ "{return Words.indexOf(elem)==pos;});"
				+ "for(var i in dictionary){if(dictionary[i]!=\"\")"
				+ "emit(dictionary[i],1);}}";

		String reduce = "function(key,values){total=0;"
				+ "for(var i in values){total+=values[i];}return total}";

		MapReduceOutput out = collection.mapReduce(
				new MapReduceCommand( collection, map, reduce,null,
						MapReduceCommand.OutputType.INLINE, null ));

		System.out.println("DFList : "+out.results());//Debug
		List<JSONObject> l = new ArrayList<JSONObject>();
		for (DBObject o:out.results())
			l.add(new JSONObject().put("df",o.get("value")).put("word", o.get("_id")));
		return l;}
}