package services;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.FriendsDao;


public class Friends {
	public static void  addFriend(String id1,String id2) 
	{
		FriendsDao.addFriend(id1, id2);
	}

	public static void  removeFriend(String user1,String user2)  
	{
		FriendsDao.removeFriend(user1, user2);
	}

	public static boolean areFriends(String user1,String user2)  
	{
		return FriendsDao.areFriends(user1, user2);
	}

	public static JSONObject myFriends(String user)throws JSONException
	{				
		JSONObject myFriendsJSON=new JSONObject();
		JSONArray ids = new JSONArray();
		ArrayList<String> myFriends=FriendsDao.myFriends(user);
		for(String i : myFriends){
			ids.put(i);
		}
		myFriendsJSON.put("friends",ids);
		return myFriendsJSON;
	}

	public static ArrayList<String> myFriendsArray(String user)throws JSONException
	{
		return FriendsDao.myFriends(user);
	}

	public static void  addRequest(String idfrom,String idto)  
	{
		if(isPending(idfrom,idto))
			return;/* Tools.serviceRefused
					("Vous avez deja une demande en cours pour cet objet!", -1);*/
		FriendsDao.addRequest(idfrom, idto);
	}

	public static void  removeRequest(String idfrom,String idto)  
	{
		FriendsDao.removeRequest(idfrom, idto);
	}
	
	public static boolean isPending(String idfrom,String idto)  
	{
		return FriendsDao.isPending(idfrom,idto);
	}
	
	public static JSONObject pendingRequests(String user)throws JSONException
	{
		JSONObject pendingRequestsJSON=new JSONObject();
		JSONArray ids = new JSONArray();
		ArrayList<String> pending=FriendsDao.pendingRequests(user);
		for(String i : pending){
			ids.put(i);
		}
		pendingRequestsJSON.put("requests",ids);
		return pendingRequestsJSON;
	}

	public static ArrayList<String> pendingRequestsArray(String user)throws JSONException
	{
		return FriendsDao.pendingRequests(user);
	}

}
