package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import kasudb.KasuDB;

public class FriendsDao {

	public static void  addFriend(String id1,String id2)throws SQLException
	{
		String sqlQuery = "INSERT INTO FRIENDS values ('"+id1+"','"+id2+"');";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	
	}

	public static void  removeFriend(String user1,String user2)throws SQLException
	{
		String sqlQuery = "DELETE FROM FRIENDS WHERE ((id1='"+user1+"' AND id2='"+user2+"') OR (id1='"+user2+"' AND id2='"+user1+"'));";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	
	}

	public static boolean areFriends(String user1,String user2)throws SQLException
	{
		String sql = "SELECT * FROM FRIENDS WHERE ((id1='"+user1+"' AND id2='"+user2+"') OR (id1='"+user2+"' AND id2='"+user1+"'));";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		boolean areFriends=resultSet.next();

		resultSet.close();
		statement.close();
		connection.close();

		return areFriends;
	}

	public static ArrayList<Integer> myFriends(String user)throws SQLException
	{
		ArrayList<Integer> myFriends = new ArrayList<Integer>();
		String sql = "SELECT id2 FROM FRIENDS WHERE (id1='"+user+"') UNION SELECT id1 FROM FRIENDS WHERE (id2='"+user+"');";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next())
			myFriends.add(resultSet.getInt(1));

		resultSet.close();
		statement.close();
		connection.close();

		return myFriends;
	}
	
	public static void  addRequest(String idfrom,String idto)throws SQLException
	{
		String sqlQuery = "INSERT INTO FRIENDREQUESTS values ('"+idfrom+"','"+idto+"');";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	
	}
	
	public static void  removeRequest(String idfrom,String idto)throws SQLException
	{
		String sqlQuery = "DELETE FROM FRIENDREQUESTS WHERE ((idto='"+idto+"'AND idfrom='"+idfrom+"') OR (idto='"+idfrom+"'AND idfrom='"+idto+"'));";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	
	}
	
	public static ArrayList<Integer> pendingRequests(String user)throws SQLException
	{
		ArrayList<Integer> pendingRequests=new ArrayList<Integer>();
		String sqlQuery = "SELECT idfrom FROM FRIENDREQUESTS WHERE idto='"+user+"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet resultSet = s.executeQuery(sqlQuery);

		while (resultSet.next())
			pendingRequests.add(resultSet.getInt(1));
		
		resultSet.close();
		s.close();
		c.close();
		
		return pendingRequests;	
	}
}
