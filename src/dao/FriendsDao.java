package dao;

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import kasudb.KasuDB;

public class FriendsDao {

	public static DBCollection collection = KasuDB.getMongoCollection("friends");
	public static DBCollection requests= KasuDB.getMongoCollection("fr");//FRIENDS_REQUESTS

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

		/*String sqlQuery = "INSERT INTO FRIENDS values ('"+id1+"','"+id2+"');";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();*/	
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

		/*String sqlQuery = "DELETE FROM FRIENDS WHERE ((id1='"+user1+"' AND id2='"+user2+"') OR (id1='"+user2+"' AND id2='"+user1+"'));";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();*/	
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

		/*String sql = "SELECT * FROM FRIENDS WHERE ((id1='"+user1+"' AND id2='"+user2+"') OR (id1='"+user2+"' AND id2='"+user1+"'));";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		boolean areFriends=resultSet.next();

		resultSet.close();
		statement.close();
		connection.close();

		return areFriends;*/
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

		/*ArrayList<Integer> myFriends = new ArrayList<Integer>();
		String sql = "SELECT id2 FROM FRIENDS WHERE (id1='"+user+"') UNION SELECT id1 FROM FRIENDS WHERE (id2='"+user+"');";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next())
			myFriends.add(resultSet.getInt(1));

		resultSet.close();
		statement.close();
		connection.close();

		return myFriends;*/
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

		/*String sqlQuery = "INSERT INTO FRIENDREQUESTS values ('"+idfrom+"','"+idto+"');";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	*/
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

		/*String sqlQuery = "DELETE FROM FRIENDREQUESTS WHERE ((idto='"+idto+"'AND idfrom='"+idfrom+"') OR (idto='"+idfrom+"'AND idfrom='"+idto+"'));";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();*/	
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

		/*ArrayList<Integer> pendingRequests=new ArrayList<Integer>();
		String sqlQuery = "SELECT idfrom FROM FRIENDREQUESTS WHERE idto='"+user+"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet resultSet = s.executeQuery(sqlQuery);

		while (resultSet.next())
			pendingRequests.add(resultSet.getInt(1));

		resultSet.close();
		s.close();
		c.close();

		return pendingRequests;*/	
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

		/*String sql = "SELECT * FROM FRIENDREQUESTS WHERE (idfrom='"+idfrom+"' AND idto='"+idto+"');";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		boolean isPending=resultSet.next();

		resultSet.close();
		statement.close();
		connection.close();

		return isPending;*/

	}
}
