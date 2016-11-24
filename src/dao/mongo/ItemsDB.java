package dao.mongo;

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
		return collection.find(
				new BasicDBObject()
				.append("owner",userID)
				.append("title",//"description"
						new BasicDBObject()
						.append("$regex","/"+query+"/")
						.append("$options","i")));}

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
		return collection.find(
				new BasicDBObject()
				.append("description",
						new BasicDBObject()
						.append("$regex","/"+query+"/")
						.append("$options","i"))
				.append("owner",
						new BasicDBObject()
						.append("$ne",userID)));}	
	
	
	public static void main(String[] args) {
		
		//TODO https://docs.mongodb.com/v3.2/reference/operator/query/or/
		//DBCursor dbc =userItems("6");
		DBCursor dbc =userItemsSQLMODO("S5","6");
		//DBCursor dbc =utherItems("6");
		//DBCursor dbc =utherItems("1");
		//DBCursor dbc =utherItemsSQLMODO("S5","1");
		while(dbc.hasNext())
			System.out.println(dbc.next());	}
	
}
