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
	
	public static DBCursor userItems(int userID) throws DatabaseException {  
		return collection.find(
				new BasicDBObject()
				.append("owner",userID));}

}
