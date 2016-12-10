package dao;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, Cedric Ribeiro */
public class FriendsDao {

	public static DBCollection collection = KasuDB.getMongoCollection("friends");
	public static DBCollection requests= KasuDB.getMongoCollection("frqst");//FRIENDS_REQUESTS

	/**
	 * @rebasetested
	 * @param id1
	 * @param id2
	 */
	public static void  addFriend(String id1,String id2){
		collection.insert(
				new BasicDBObject()
				.append("id1",id1)
				.append("id2",id2)
				);	
	}

	/**
	 * @rebasetested
	 * @param user1
	 * @param user2
	 */
	public static void  removeFriend(String user1,String user2)	{
		BasicDBList bdbl=new BasicDBList();
		bdbl.add(new BasicDBObject()
				.append("id1",user1)
				.append("id2",user2)
				);
		bdbl.add(new BasicDBObject()
				.append("id1",user2)
				.append("id2",user1)
				);
		collection.remove(
				new BasicDBObject().append("$or", bdbl)
				);
	}

	/**
	 * @rebasetested
	 * @param user1
	 * @param user2
	 * @return
	 */
	public static boolean areFriends(String user1,String user2)	{
		BasicDBList bdbl=new BasicDBList();
		bdbl.add(new BasicDBObject()
				.append("id1",user1)
				.append("id2",user2)
				);
		bdbl.add(new BasicDBObject()
				.append("id1",user2)
				.append("id2",user1)
				);
		return collection.find(
				new BasicDBObject().append("$or", bdbl)
				).hasNext();
	}

	/**
	 * @rebasetested
	 * @param user
	 * @return
	 */
	public static ArrayList<String> myFriends(String user){
		ArrayList<String> ids=new ArrayList<String>();

		DBCursor dbc = collection.find(
				new BasicDBObject()
				.append("id1",user)
				);
		while(dbc.hasNext())
			ids.add((String) dbc.next().get("id2"));

		dbc = collection.find(
				new BasicDBObject()
				.append("id2",user)
				);
		while(dbc.hasNext())
			ids.add((String) dbc.next().get("id1"));

		return ids;
	}

	/**
	 * @rebasetested
	 * @param idfrom
	 * @param idto  */
	public static void  addRequest(String idfrom,String idto){
		requests.insert(
				new BasicDBObject()
				.append("idfrom",idfrom)
				.append("idto",idto)
				);
	}

	/**
	 * @rebasetested
	 * @param idfrom
	 * @param idto
	 */
	public static void  removeRequest(String idfrom,String idto){
		BasicDBList bdbl=new BasicDBList();
		bdbl.add(new BasicDBObject()
				.append("idfrom",idfrom)
				.append("idto",idto)
				);
		bdbl.add(new BasicDBObject()
				.append("idto",idfrom)
				.append("idfrom",idto)
				);
		requests.remove(
				new BasicDBObject().append("$or", bdbl)
				);
	}

	/**
	 * @rebasetested
	 * @param user
	 * @return
	 */
	public static ArrayList<String> pendingRequests(String user){
		ArrayList<String> ids=new ArrayList<String>();
		DBCursor dbc = requests.find(
				new BasicDBObject()
				.append("idto",user)
				);
		while(dbc.hasNext())
			ids.add((String) dbc.next().get("idfrom"));

		return ids;
	}

	/**
	 * @rebasetested
	 * @param idfrom
	 * @param idto
	 * @return
	 */
	public static boolean isPending(String idfrom, String idto){
		return requests.find(
				new BasicDBObject()
				.append("idfrom",idfrom)
				.append("idto",idto)
				).hasNext();
	}
}
