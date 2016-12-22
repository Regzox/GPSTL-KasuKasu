package dao.items;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import dao.GroupsDB;
import dao.search.PatternsHolder;
import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, Giuseppe FEDERICO, Cedric Ribeiro*/
public class ItemsDB {

	public static DBCollection collection = KasuDB.getMongoCollection("items");

	/**
	 * Ajoute un objet a la base mongo
	 * @param authorid
	 * @param text
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public static void addItem(JSONObject object) {
		// Parsing de l'objet
		DBObject dbObj = (DBObject) com.mongodb.util.JSON.parse(object.toString());

		// Ajout de la date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		dbObj .put("date", dateFormat.format(today));

		// Ajout dans la base de donnees
		collection.insert( dbObj ).toString();		
	}


	/**
	 * Check if user's right on the object   
	 * @param userId
	 * @param id
	 * @return */
	public static boolean checkAthorization(String userId,String id) {
		return collection.find(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				.append("owner",userId)).hasNext();
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
	 * Remove an item
	 * @param id
	 * @param title
	 * @param description */
	public static void removeItem(String id) {
		collection.remove(
				new BasicDBObject()
				.append("_id",new ObjectId(id)));
	}	


	/**
	 * Set an item'status 
	 * @param id
	 * @param title
	 * @param description */
	public static void setItemStatus(String id,String status) {
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				,new BasicDBObject()
				.append("$set",
						new BasicDBObject()						
						.append("status",status)));
	}


	/**
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
	 * return item's status 
	 * @param id
	 * @param title
	 * @param description 
	 * @return */
	public static String itemStatus(String id) {
		return (String)collection.findOne(
				new BasicDBObject()
				.append("_id",new ObjectId(id))).get("status");
	}	


	/**
	 * Find all items owned by the user
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static DBCursor userItems(String userID)  {  
		return collection.find(
				new BasicDBObject()
				.append("owner",userID));
		}
	
	
	/**zone-telechargement search engine's style
	 * ADMIN FUNCTION : sub common function of (SQLMODO methods)
	 * @param userID
	 * @return */
	private static BasicDBList itemsSQLMODOCore(String query){
		Set<String> wordsSet= new HashSet<>(Arrays.asList(
				query.trim().split(PatternsHolder.blank)));
		List<Pattern> patterns = new ArrayList<>();
		for(String word : wordsSet)	
			patterns.add(Pattern.compile(
					PatternsHolder.stain(word)
					//PatternsHolder.fuzzyfy(word,2)//if you want to fuzzy search instead of pattern-matching
			, Pattern.CASE_INSENSITIVE));
		BasicDBList orlist = new BasicDBList(); 
		for(String field : Arrays.asList("title","description"))
			orlist.add(new BasicDBObject(field, new BasicDBObject("$in", patterns)));
		System.out.println("ItemsDB/itemsSQLMODOCore::orlist="+orlist);//debug
		return orlist;
	}
	
	
	/**
	 * Find all items owned by the user using mongo's SQl way
	 * @param query
	 * @param userID
	 * @return */
	public static DBCursor userItemsSQLMODO(String query,String userID) {  
 		return collection.find(new BasicDBObject()
				.append("owner",userID)
				.append("$or",itemsSQLMODOCore(query)));
 		}


	/**
	 * Find all items not owned by user
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static DBCursor utherItems(String userID) {  
		return collection.find(utherItemCore(userID));}	
	/**
	 * Find all items not owned by user
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static DBCursor utherItemsSQLMODO(String query, String userID) {  
		return collection.find(
				utherItemCore(userID)
				.append("status","available")
				.append("$or",itemsSQLMODOCore(query))
				.append("owner",
						new BasicDBObject()
						.append("$ne",userID)));}

	
	/**
	 * ADMIN FUNCTION : sub common function of (utherItems methods)
	 * @param userID
	 * @return */
	private static BasicDBObject utherItemCore(String userID){
		DBCursor dbc = GroupsDB.userGroupsMembership(userID);
		BasicDBList bdbl = new BasicDBList();
		while(dbc.hasNext())
			bdbl.add(dbc.next().get("_id").toString());
		//System.out.println("ItemDB/utherItems::userGroupsMembership="+bdbl);//debug
		
		BasicDBList exprs = new BasicDBList();
		exprs.add(
				new BasicDBObject()
				.append("groups", 
						new BasicDBObject()
						.append("$in",bdbl)));
		exprs.add(
				new BasicDBObject()
				.append("groups",
						new BasicDBObject()
						.append("$size",0)));
		
		return new BasicDBObject()
		.append("status","available")
		//.append("$or", exprs)
		.append("owner",
				new BasicDBObject()
				.append("$ne",userID));
		}



	/**
	 * 
	 * 				ITEMS GROUPS (& VISIBILITY ) MANAGEMENT
	 * 
	 * */


	public static void addGroupToItem(String itemID, String groupID){
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.put("_id", new ObjectId(itemID));
		BasicDBObject updateCommand = new BasicDBObject();
		updateCommand.put("$addToSet", new BasicDBObject("groups",groupID));
		collection.update(updateQuery,updateCommand);
	}


	public static void removeGroupFromItem(String itemID, String groupID){
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.put("_id", new ObjectId(itemID));
		BasicDBObject updateCommand = new BasicDBObject();
		updateCommand.put("$pull", new BasicDBObject("groups",groupID));
		collection.update(updateQuery,updateCommand);
	}


	public static BasicDBList getGroupsFromItem(String itemID){
		DBObject item = getItem(itemID);
		BasicDBList groups = (BasicDBList) item.get("groups");
		return groups;
	}


	public static void main(String[] args) {
		System.out.println("Results...\n%");
		//DBCursor dbc =userItems("6");//Ok
		//DBCursor dbc =utherItems("6");//Ok
		//DBCursor dbc =utherItems("1");//OK
		//DBCursor dbc =userItemsSQLMODO("    S5 noir  ","6"); //OK
		//DBCursor dbc =userItemsSQLMODO("    S5  noir  ","6"); //NOK : not mr (pattern is strict : too much space between 2 words fails to match pattern)
		//DBCursor dbc =utherItemsSQLMODO("S5","6");//OK
		DBCursor dbc =utherItemsSQLMODO("vélo","1");//OK
		while(dbc.hasNext())
			System.out.println(dbc.next());
		System.out.println("%\n");
		/*System.out.print("Permission : ");
		if(checkAthorization("6","581c70b04c1471dd003afb61")){
			System.out.println("Granted");
			updateItem("581c70b04c1471dd003afb61","galaxy S5 neuf",
					"galaxy S5 noir neuf, tres peu servi");
		}else System.out.println("Denied");*/
	}

}
