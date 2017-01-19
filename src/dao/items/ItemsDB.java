package dao.items;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.ExchangePointsDB;
import dao.GroupsDB;
import dao.search.FuzzyFinder;
import dao.search.PatternsHolder;
import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, Giuseppe FEDERICO, Cedric Ribeiro*/
public class ItemsDB {

	public static DBCollection collection = KasuDB.getMongoCollection("items");
	//According to the customer demand fuzzyness is 2(omission|substitution|addition)
	private static int fuzzyness = 2; 

	/**
	 * Ajoute un objet à la base mongo.
	 * @param object
	 */
	public static void addItem(JSONObject object, JSONArray exPoints) {
		// Parsing de l'objet
		DBObject dbObj = (DBObject) com.mongodb.util.JSON.parse(object.toString());

		// Ajout de la date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		dbObj .put("date", dateFormat.format(today));

		// Ajout dans la base de donnees des objets
		collection.insert( dbObj ).toString();	
		
		// Récupère l'id et l'utilisateur de l'objet qui vient d'être ajouté
		String itemID = ((ObjectId)dbObj.get( "_id" )).toString();
		String userID = (String) dbObj.get( "owner" );
		
		// Conversion JSONArray to ArrayList
		ArrayList<String> exPointsList = new ArrayList<String>();     
		JSONArray jsonArray = exPoints; 
		for (int i=0; i<jsonArray.length(); i++)
			try {
				exPointsList.add(jsonArray.get(i).toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		   
		// Ajout l'objet dans les points d'échange de l'utilisateur
		ExchangePointsDB.addItemToUserExPoints(itemID, userID, exPointsList);
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
	public static Iterable<DBObject> userItems(String userID,String query)  {  
		BasicDBObject constraints = new BasicDBObject("owner",userID);
		if(query.trim().length()==0) 
			return collection.aggregate(Arrays.asList(
					new BasicDBObject("$match", constraints)
					,new BasicDBObject("$sort",new BasicDBObject("date", -1))
					//,new BasicDBObject("$project",new BasicDBObject("nom",1).append("prenom",1).append("_id", 0))//debug
					)).results();
		return FuzzyFinder.find(collection,	
				PatternsHolder.wordSet(query,PatternsHolder.blank),
				fuzzyness,
				Arrays.asList("title","description"),constraints,"");
		
	}


	/**
	 * Find all items not owned by user (uther = other user)
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static Iterable<DBObject> utherItems(String userID,String query) { 
		DBCursor dbc = GroupsDB.userGroupsMembership(userID);
		BasicDBList groupsIDList = new BasicDBList();
		while(dbc.hasNext())
			groupsIDList.add(dbc.next().get("_id").toString());
		System.out.println("ItemDB/utherItems::userGroupsMembership="+groupsIDList);//debug

		BasicDBList orList = new BasicDBList();
		orList.add(new BasicDBObject("groups", new BasicDBObject("$in",groupsIDList))); //TODO plutot $elemMatch
		orList.add(new BasicDBObject("groups",new BasicDBObject("$size",0)));

		BasicDBObject constraints = new BasicDBObject("status","available")
				//.append("$or", orList)//TODO visibilite tester
				.append("owner",new BasicDBObject("$ne",userID));

		if(query.trim().length()==0) 
			return collection.aggregate(Arrays.asList(
					new BasicDBObject("$match", constraints)
					,new BasicDBObject("$sort",new BasicDBObject("date", -1))
					//,new BasicDBObject("$project",new BasicDBObject("nom",1).append("prenom",1).append("_id", 0))//debug
					)).results();
		return FuzzyFinder.find(collection,
				PatternsHolder.wordSet(query,PatternsHolder.blank),
				fuzzyness,
				Arrays.asList("title","description"),constraints,"");
	}	



	
	/***************** ITEMS GROUPS (VISIBILITY ) MANAGEMENT *****************/


	/** TODO VERIFIER QUE TOUT MARCHE BIEN LORS 
	 * Add to an item one more groupId 
	 * @param itemID
	 * @param groupID */
	public static void addGroupToItem(String itemID, String groupID){
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.put("_id", new ObjectId(itemID));
		BasicDBObject updateCommand = new BasicDBObject();
		updateCommand.put("$addToSet", 
				new BasicDBObject("groups",groupID)
				.append("nom", GroupsDB.getGroup(groupID).get("name")));
		collection.update(updateQuery,updateCommand);
	}


	/**
	 * remove from an item the specified groupId
	 * @param itemID
	 * @param groupID */
	public static void removeGroupFromItem(String itemID, String groupID){
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.put("_id", new ObjectId(itemID));
		BasicDBObject updateCommand = new BasicDBObject();
		updateCommand.put("$pull", new BasicDBObject("groups",groupID));
		collection.update(updateQuery,updateCommand);
	}


	/**
	 * return the list of groupIDs of an item 
	 * @param itemID
	 * @return */
	public static BasicDBList getGroupsFromItem(String itemID){
		DBObject item = getItem(itemID);
		BasicDBList groups = (BasicDBList) item.get("groups");
		return groups;
	}

	/**
	 * Get the list of items visible by the user
	 * @param itemID
	 * @param userID
	 * @return
	 */
		public static DBCursor accessibleItems(String userID) {
			
			BasicDBList usergroupsmembership = new BasicDBList();
			DBCursor dbc = GroupsDB.userGroupsMembership(userID);
			
			while(dbc.hasNext())
				usergroupsmembership.add(dbc.next().get("_id").toString());
			
			return  collection.find(
					new BasicDBObject()//TODO attention  groups.$ ou $elemMatch au lieu de groups.id(ne marchera pas)
					.append("groups.id", new BasicDBObject().append("$in",usergroupsmembership)));
		}

		

	public static void main(String[] args) {
		System.out.println("Results...\n%");
//		Iterable<DBObject> res =userItems("586f67636c7ec4b61187a196","");
//		Iterable<DBObject> res =userItems("586f67636c7ec4b61187a196","V");
//		Iterable<DBObject> res =utherItems("1","");
//		Iterable<DBObject> res =utherItems("6","    V�lo   noir  ");
//		for(DBObject o : res)System.out.println(o);
//		System.out.println("%\n");
//		System.out.print("Permission : ");
//		if(checkAthorization("6","581c70b04c1471dd003afb61")){
//			System.out.println("Granted");
//			updateItem("581c70b04c1471dd003afb61","galaxy S5 neuf",
//					"galaxy S5 noir neuf, tres peu servi");
//		}else System.out.println("Denied");
	}

}
