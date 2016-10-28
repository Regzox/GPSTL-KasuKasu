package services;

import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.FriendsDao;

public class Friends {
	public static void  addFriend(String id1,String id2)throws SQLException
	{
		FriendsDao.addFriend(id1, id2);
	}

	public static void  removeFriend(String user1,String user2)throws SQLException
	{
		FriendsDao.removeFriend(user1, user2);
	}

	public static boolean areFriends(String user1,String user2)throws SQLException
	{
		return FriendsDao.areFriends(user1, user2);
	}

	public static JSONObject myFriends(String user)throws SQLException, JSONException
	{
		JSONObject myFriendsJSON=new JSONObject();
		JSONArray ids = new JSONArray();
		ArrayList<Integer> myFriends=FriendsDao.myFriends(user);
		for(Integer i : myFriends){
			ids.put(i);
		}
		myFriendsJSON.put("friends",ids);
		return myFriendsJSON;
	}

	public static ArrayList<Integer> myFriendsArray(String user)throws SQLException, JSONException
	{
		return FriendsDao.myFriends(user);
	}

	public static void  addRequest(String idfrom,String idto)throws SQLException
	{
		FriendsDao.addFriend(idfrom, idto);
	}

	public static void  removeRequest(String idfrom,String idto)throws SQLException
	{
		FriendsDao.removeRequest(idfrom, idto);
	}

	public static JSONObject pendingRequests(String user)throws SQLException, JSONException
	{
		JSONObject pendingRequestsJSON=new JSONObject();
		JSONArray ids = new JSONArray();
		ArrayList<Integer> pending=FriendsDao.pendingRequests(user);
		for(Integer i : pending){
			ids.put(i);
		}
		pendingRequestsJSON.put("requests",ids);
		return pendingRequestsJSON;
	}

	public static ArrayList<Integer> pendingRequestsArray(String user)throws SQLException, JSONException
	{
		return FriendsDao.pendingRequests(user);
	}

}
