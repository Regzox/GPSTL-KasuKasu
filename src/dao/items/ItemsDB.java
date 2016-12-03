package dao.items;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan*/
public class ItemsDB {

	public static DBCollection collection = KasuDB.getMongoCollection("items");
	
	
	/**
	 * Check if user's right on the object   
	 * @param userId
	 * @param id
	 * @return */
	public static boolean checkAthorization(String userId,String id) {
		return (collection.find(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				.append("owner",userId))).hasNext();
	}
	

	/**
	 * Update an item
	 * @param id
	 * @param title
	 * @param description */
	public static void updateItem(String id, String title, String description) {
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				,new BasicDBObject()
				.append("$set",
						new BasicDBObject()
						.append("title",title)
						.append("description",description)));
	}	
	
	
	/**
	 * ADMIN FUNCTION (internal use)
	 * return an item (all fields)
	 * @param id
	 * @param title
	 * @param description 
	 * @return */
	public static DBObject getItem(String id) {
		return collection.findOne(
				new BasicDBObject()
				.append("_id",new ObjectId(id)));
	}	
	
	

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
		System.out.println("%\n");
		System.out.print("Permission : ");
		if(checkAthorization("6","581c70b04c1471dd003afb61")){
			System.out.println("Granted");
		updateItem("581c70b04c1471dd003afb61","galaxy S5 neuf",
				"galaxy S5 noir neuf, tres peu servi");
		}else System.out.println("Denied");
	}

}
