package dao.mongo;

import java.util.regex.Pattern;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan*/
public class ItemsDB {

	public static DBCollection collection = KasuDB.getMongoCollection("Items");

	/**
	 * Find all items owned by the user
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static DBCursor userItems(String userID)  {  
		return collection.find(
				new BasicDBObject()
				.append("owner",userID));}

	/**
	 * Find all items owned by the user using mongo's SQl way
	 * @param query
	 * @param userID
	 * @return */
	public static DBCursor userItemsSQLMODO(String query,String userID) {  
		Pattern pquery = Pattern.compile(query.trim(), Pattern.CASE_INSENSITIVE );
		
		BasicDBList bdbl =new BasicDBList();
		bdbl.add(new BasicDBObject()
				.append("title",pquery));
		bdbl.add(new BasicDBObject()
				.append("description",pquery));
		
		//System.out.println("bdbl="+bdbl);//debug
		return collection.find(new BasicDBObject()
				.append("owner",userID)
				.append("$or",bdbl));
	}



	/**
	 * Find all items not owned by user
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static DBCursor utherItems(String userID) {  
		return collection.find(
				new BasicDBObject()
				.append("owner",
						new BasicDBObject()
						.append("$ne",userID)));}	
	/**
	 * Find all items not owned by user
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static DBCursor utherItemsSQLMODO(String query, String userID) {  
Pattern pquery = Pattern.compile(query.trim(), Pattern.CASE_INSENSITIVE );
		
		BasicDBList bdbl =new BasicDBList();
		bdbl.add(new BasicDBObject()
				.append("title",pquery));
		bdbl.add(new BasicDBObject()
				.append("description",pquery));
		
		//System.out.println("bdbl="+bdbl);//debug
		return collection.find(
				new BasicDBObject()
				.append("$or",bdbl)
				.append("owner",
						new BasicDBObject()
						.append("$ne",userID)));}	


	public static void main(String[] args) {
		System.out.println("Results...\n%");
		//TODO https://docs.mongodb.com/v3.2/reference/operator/query/or/
		//DBCursor dbc =userItems("6");//Ok
		//DBCursor dbc =utherItems("6");//Ok
		//DBCursor dbc =utherItems("1");//OK
		//DBCursor dbc =userItemsSQLMODO("    S5 noir  ","6"); //OK
		//DBCursor dbc =userItemsSQLMODO("    S5  noir  ","6"); //NOK : not mr (pattern is strict : too much space between 2 words fails to match pattern)
		//DBCursor dbc =utherItemsSQLMODO("S5","6");//OK
		DBCursor dbc =utherItemsSQLMODO("S5","1");//OK
		while(dbc.hasNext())
			System.out.println(dbc.next());
		System.out.println("%");
	}


}
