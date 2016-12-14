package dao;

import java.util.Arrays;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, YAHI Lina, Giuseppe FEDERICO, Cedric Ribeiro */
public class ExchangePointsDB {

	public static DBCollection collection = KasuDB.getMongoCollection("expts");

	/**
	 * Add an exchange point in db
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param userID
	 * @param name */
	public static void addExchangePoint(
			double lat,double lon,int radius,String userID,String name){ 
		BasicDBList bdbl = new BasicDBList();
		bdbl.add(
				new BasicDBObject()
				.append("id_user", userID)
				.append("name", name)
				.append("radius",radius)
				.append("date", new Date())
				.append("useritems",new BasicDBList())
				);
		collection.insert(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				.append("subscribers",bdbl)
				);
	}

	/**
	 * ADMIN FUNCTION  : check if user have already created such an exchange point
	 * @param lat
	 * @param lon
	 * @param userID
	 * @return */
	public static boolean exists(
			double lat,double lon,String userID){ 
		return collection.find(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				).hasNext();
	}


	
	/**
	 * ADMIN FUNCTION  : check if user have already created or subscribed such an exchange point
	 * @param lat
	 * @param lon
	 * @param userID
	 * @return */
	public static boolean existsForUser(
			double lat,double lon,String userID){ 
		return collection.find(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				.append("subscribers.id_user",userID)
				).hasNext();
	}

	/**
	 * ADMIN FUNCTION  : get an exchange point by latitude longitude 
	 * @param lat
	 * @param lon
	 * @param userID
	 * @return */
	public static DBObject getExchangePointByLatLon(
			double lat,double lon){ 
		return collection.findOne(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				);
	}
	
	/**
	 * ADMIN FUNCTION  : get an exchange point by his id  
	 * @param lat
	 * @param lon
	 * @param userID
	 * @return */
	public static DBObject getExchangePoint(String id){ 
		return collection.findOne(
				new BasicDBObject()
				.append("_id",new ObjectId(id)));
	}
	
	
	/**
	 * Add a list of user's items to an exchange point in db
	 * @param id
	 * @param userID
	 * @param items */
	public static void subscribeToExchangePoint(String id,String userID,String name){ 
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id)),
				new BasicDBObject()
				.append("$addToSet", 
						new BasicDBObject()
						.append("subscribers",
								new BasicDBObject()
								.append("id_user", userID)
								.append("name", name)
								.append("date", new Date())
								.append("useritems",new BasicDBList())
								)
						)
				);
	}	


	/**
	 * Update exchange point's preferences for an specific user 
	 * @param id
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param name */
	public static void updateExchangePoint(
			String id,String userID,int radius,String name){ 
		collection.update(
				new BasicDBObject()
				.append("_id",id)
				.append("subscribers.id_user",userID),
				new BasicDBObject()
				.append("$set",
						new BasicDBObject()
						.append("subscribers.name",name)
						.append("subscribers.radius",radius)
						)
				);
	}


	/**
	 * Check if an user is a subscriber to an exchange point
	 * @param id
	 * @param userID
	 * @return */
	public static Boolean isMember(String id , String userID){
		return collection.find(
				new BasicDBObject()
				.append("_id",id)
				.append("subscribers.id_user",userID)).hasNext();
	}


	/**
	 * Return all the accessible exchange points of an user 
	 * (his subscribed points and visible friends points)
	 * @param userID
	 * @return */
	public static DBCursor accessibleExchangePoints(String userID){
		BasicDBList exprs = new BasicDBList();
		BasicDBList usergroupsmembership = new BasicDBList();
		DBCursor dbc = GroupsDB.userGroupsMembership(userID);

		while(dbc.hasNext())
			usergroupsmembership.add(dbc.next().get("_id").toString());
		
		exprs.add(new BasicDBObject()
				.append("subscribers.id_user", userID));
		exprs.add(
				new BasicDBObject()
				.append("subscribers.id_user",
						new BasicDBObject()
						.append("$in",FriendsDao.myFriends(userID)))
				//TODO FILTER BY GROUP APPARTENANCE according to items's groups
				);
		return collection.find(new BasicDBObject().append("$or", exprs));
	}


	/**
	 * Add a list of user's items to an exchange point in db
	 * @param id
	 * @param userID
	 * @param items */
	public static void addBulkUserItemsToExchangePoint(String id,String userID, String[] items){ 
		BasicDBList bdbl = new BasicDBList();
		bdbl.addAll(Arrays.asList(items));
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				.append("subscribers.id_user", userID),
				new BasicDBObject()
				.append("$addToSet", 
						new BasicDBObject()
						.append("subscribers.useritems",bdbl) //TODO find how it work
						)
				);
	}

	/**
	 * Return the list of user's subscribed exchange points
	 * @param userID
	 * @return */
	public static DBCursor userPoints(String userID) {  
		return collection.find(
				new BasicDBObject()
				.append("subscribers.id_user",userID));}

	/**
	 * Return the list of user friend's exchange points
	 * @param userID
	 * @return */
	public static DBCursor friendsExchangePoints(String userID){
		BasicDBList exprs = new BasicDBList();
		exprs.add(
				new BasicDBObject()
				.append("subscribers.id_user",
						new BasicDBObject()
						.append("$in",FriendsDao.myFriends(userID))
						.append("$ne", userID)
						)
				);
		return collection.find(new BasicDBObject().append("$or", exprs));	
	}

	/**
	 * Return the list of users subscribed in an exchange point
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static DBCursor pointUsersName(String lat, String lon){
		return collection.find(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon", lon));	
	}

	/*public static void addItemToExPoint(String itemID,String exPointID,String userID){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				.append("subscribers.id_user", userID),
				new BasicDBObject()
				.append("$addToSet", 
						new BasicDBObject()
						.append("subscribers.useritems",bdbl) //TODO find how it work
						)
				);
	}*/


	public static void removeItemFromExPoint(String itemID,String exPointID){
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.put("_id", new ObjectId(itemID));
		BasicDBObject updateCommand = new BasicDBObject();
		updateCommand.put("$pull", new BasicDBObject("exchangepoints",exPointID));
		collection.update(updateQuery,updateCommand);
	}

	/*public static DBObject getItemExchangePoints(String itemID){
		BasicDBList groups = (BasicDBList) item.get("exchangepoints");
		return groups;
	}*/



	public static void main(String[] args) {
		/*collection.remove(new BasicDBObject());
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg5","fac");
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg5","maison");
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg5","upmc");
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg6","upmc2");
		addExchangePoint(2.0, 3.0, 200, "58429ac00feb9052f1da4be3", "UPMC");
		String excpt_id=accessibleExchangePoints("5jhjy62hghfj5874gtg5").next().get("_id").toString();
		subscribeToExchangePoint(excpt_id, "new_user_id", "la prison");
		subscribeToExchangePoint(excpt_id, "new_user_id2", "upmc");
		subscribeToExchangePoint(excpt_id, "new_user_id3", "l'enfer");
		addBulkUserItemsToExchangePoint(excpt_id,"5jhjy62hghfj5874gtg5",
				new String[]{"itemid1","itemid2","itemid3"});*/
		//System.out.println(userPoints("58496e8c273633e062a41acd").next());
		//System.out.println(userPoints("58496e19273633e062a41acc").next());
		System.out.println(friendsExchangePoints("58496e19273633e062a41acc").next());

	}
}
