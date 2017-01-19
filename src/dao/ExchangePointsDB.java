package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.items.ItemsDB;
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
	 * ADMIN FUNCTION  : check if an exchange point at this position already exists
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
	 * ADMIN FUNCTION  : check if user have subscribed such an exchange point
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
	 * Return the list of user's subscribed exchange points
	 * @param userID
	 * @return */
	public static DBCursor userPoints(String userID) {  
		return collection.find(
				new BasicDBObject()
				.append("subscribers.id_user",userID));}

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
	public static void subscribeToExchangePoint(String id,String userID,String name, int radius){ 
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
								.append("radius", radius)
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
				.append("_id", new ObjectId(id))
				.append("subscribers.id_user",userID),
				new BasicDBObject()
				.append("$set",
						new BasicDBObject()
						.append("subscribers.$.name",name)
						.append("subscribers.$.radius",radius)
						)
				);
	}


	/**  TODO pas optimal boucle d'acces a la base
	 * Add an item to the <i>useritems</i> array of the given user for every
	 * exchange point in the given list.
	 * 
	 * @param itemID the item object that should be added to <i>useritems</i>..
	 * @param userID the ID of the user that contains the array <i>useritems</i>.
	 * @param exPoints the list of exchange points where the item should be added.
	 * @return void  */
	public static void addItemToUserExPoints(
			String itemID, 
			String userID, 
			ArrayList<String> exPoints){
		for (String exPoint : exPoints) { //TODO Il faut eviter les boucles 
			collection.update(
					new BasicDBObject()
					.append("_id", new ObjectId(exPoint))
					.append("subscribers.id_user",userID),
					new BasicDBObject("$addToSet",
							new BasicDBObject("subscribers.$.useritems",
									itemID)
							)
					);

		}
	}

	
	
	
	/**
	 * Add a list of items IDs to an exchange point for a specific user  
	 * @param id
	 * @param userID
	 * @param itemIDs */
	public static void addItemsToExPoint(String id,String userID,Set<String> itemIDs){
		collection.update(
				new BasicDBObject()
				.append("_id", new ObjectId(id))
				.append("subscribers.id_user",userID),
				new BasicDBObject("$addToSet",
						new BasicDBObject("subscribers.$.useritems",
								new BasicDBObject("$each",itemIDs))
						)
				);
	}


	/**
	 * Return the list of ExchangePoints where the item
	 * @param itemID
	 * @return */
	public static DBCursor itemExchangePoints(String itemID){
		return collection.find( 
				new BasicDBObject("subscribers.useritems",itemID)
						
				);
	}



	/**
	 * remove a list of items IDs from an exchange point for a specific user
	 * @param id
	 * @param userID
	 * @param itemIDs */
	public static void removeItemsFromExPoint(String id, String userID,Set<String> itemIDs){
		collection.update(
				new BasicDBObject()
				.append("_id", new ObjectId(id))
				.append("subscribers.id_user",userID),
				new BasicDBObject("$pull", 
						new BasicDBObject("subscribers.$.useritems",
								new BasicDBObject("$in",itemIDs))
						)
				);
	}


	
	/**
	 * Delete an exchange point subscribed by the user
	 * @param id
	 * @param ownerID */
	public static void deleteExchangePoint(String id,String ownerID){
		collection.update(new BasicDBObject("_id", new ObjectId(id)), 
				new BasicDBObject("$pull",
						new BasicDBObject("subscribers", 
								new BasicDBObject("id_user", ownerID))));		
	}


	public static void main(String[] args) {
		collection.remove(new BasicDBObject());
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg5","fac");
		String excpt_id=accessibleExchangePoints("5jhjy62hghfj5874gtg5").next().get("_id").toString();
		subscribeToExchangePoint(excpt_id, "new_user_id", "la prison",2);
		subscribeToExchangePoint(excpt_id, "new_user_id2", "upmc",50);
		subscribeToExchangePoint(excpt_id, "new_user_id3", "l'enfer",1000);
		updateExchangePoint(excpt_id, "5jhjy62hghfj5874gtg5", 10, "UPMC");
		addItemsToExPoint(excpt_id,"5jhjy62hghfj5874gtg5",
				new HashSet<String>(Arrays.asList(new String[]
						{"LOLitemID0","LOLitemID1","LOLitemID2","LOLitemID1","LOLitemID7"}
						)));
		System.out.println("itemExchangePoints : "+itemExchangePoints("LOLitemID0").next());
		removeItemsFromExPoint(excpt_id,"5jhjy62hghfj5874gtg5",
				new HashSet<String>(Arrays.asList(new String[]
						{"LOLitemID7","LOLitemID8","LOLitemID0"}
						)));
		System.out.println("userPoints : "+userPoints("new_user_id").next());
		try{System.out.println("itemExchangePoints : "+itemExchangePoints("LOLitemID0").next());//Must throw NoSuchElementException
		}catch(NoSuchElementException nsee){System.out.println("NoSuchElementException");}
		System.out.println("itemExchangePoints id1: "+itemExchangePoints("LOLitemID1").next());
		//				System.out.println(friendsLargeExchangePoints("58496e19273633e062a41acc").next());
		deleteExchangePoint(excpt_id,"new_user_id3");
		System.out.println("userPoints : "+userPoints("new_user_id").next());
	}




	/**TODO REVOIR SI UTILE A GARDER AVEC LINA : SEMBLE OBSOLETE
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

		exprs.add(new BasicDBObject("subscribers.id_user", userID));
		exprs.add(new BasicDBObject("subscribers.id_user",
						new BasicDBObject("$in",FriendsDao.myFriends(userID)))
				//TODO FILTER BY GROUP APPARTENANCE according to items's groups
				);
		return collection.find(new BasicDBObject().append("$or", exprs));
	}





	/** TODO mod add groups visibility constraints 
	 * Return the list of user friend's exchange points (including subscribed user's points)
	 * @param userID
	 * @return */
	public static DBCursor friendsLargeExchangePoints(String userID){

				BasicDBList useritemsvisible = new BasicDBList();
				DBCursor dbc = ItemsDB.accessibleItems(userID);
		
				while(dbc.hasNext())
					useritemsvisible.add(dbc.next().get("_id").toString());

		BasicDBList exprs = new BasicDBList();
		exprs.add(
				new BasicDBObject()
				.append("subscribers.id_user",
						new BasicDBObject()
						.append("$in",FriendsDao.myFriends(userID))
						.append("$ne", userID)
						)
								.append("subscribers.useritems",
										new BasicDBObject()
										.append("$in",useritemsvisible)
										)
				);
		return collection.find(new BasicDBObject().append("$or", exprs));	
	}


	/** TODO mod add groups visibility constraints
	 * Return the list of user friend's exchange points 
	 * @param userID
	 * @return */
	public static DBCursor friendsStrictExchangePoints(String userID){
		BasicDBList exprs = new BasicDBList();
		exprs.add(
				new BasicDBObject()
				.append("subscribers.id_user",
						new BasicDBObject()
						.append("$in",FriendsDao.myFriends(userID))
						.append("subscribers.id_user",
								new BasicDBObject()
								.append("$nin", Arrays.asList(new String[]{userID}))))
				);
		return collection.find(new BasicDBObject().append("$or", exprs));	
	}
	
	
	public static DBObject userPointDetail(String userID, double lat,double lon) { 
		return collection.findOne(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				.append("subscribers.id_user",userID)
				);}






}