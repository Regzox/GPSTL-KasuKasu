package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.ExchangePointsDB;
import dao.items.ItemsDB;
import dao.users.UserDao;
import exceptions.DatabaseException;
import utils.Tools;

/**
 * @author ANAGBLA Joan, YAHI Lina */
public class ExchangePoints {

	/**
	 * Add a new exchange pint in db 
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param userID
	 * @param nom
	 * @return
	 * @throws JSONException */
	public static JSONObject addExchangePoint(
			double lat,double lon,int radius,String userID,String name) 
					throws JSONException {		
		if(ExchangePointsDB.exists(lat, lon, userID)){
			if(!ExchangePointsDB.existsForUser(lat, lon, userID))
				ExchangePointsDB.subscribeToExchangePoint(
						ExchangePointsDB.getExchangePointByLatLon(lat,lon)
						.get("_id").toString()
						, userID, name);
		}else ExchangePointsDB.addExchangePoint(lat,lon,radius,userID,name);
		return Tools.serviceMessage(1);
	}


	/**
	 * Subscribe to an existing exchange point
	 * @param id
	 * @param userID
	 * @param name
	 * @return
	 * @throws JSONException */
	public static JSONObject subscribeToExchangePoint(String id,String userID,String name) 
			throws JSONException {			
		ExchangePointsDB.subscribeToExchangePoint(id, userID, name);		
		return Tools.serviceMessage(1);
	}


	/**
	 * Update exchange point's preferences for an specific user 
	 * @param id
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param name 
	 * @throws JSONException */
	public static JSONObject updateExchangePoint(
			String id,String userID,int radius,String name) throws JSONException{ 
		if(!ExchangePointsDB.isMember(id ,userID))
			return Tools.serviceRefused
					("Vous n'avez pas le droit de modifier ce point d'echange!", -1);
		ExchangePointsDB.updateExchangePoint(id, userID, radius, name);
		return Tools.serviceMessage(1);
	}


	/**TODO FINISH IT
	 * Return user exchange points according to his userID
	 * @param userID
	 * @return
	 * @throws JSONException */
	public static JSONObject accessibleExchangePoints(String userID) throws JSONException {			
		JSONArray jar=new JSONArray();
		DBCursor cursor = ExchangePointsDB.accessibleExchangePoints(userID); 
		while(cursor.hasNext()){
			DBObject dbo=cursor.next();
			jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("type","expoint")
					.put("lat",dbo.get("lat"))
					.put("lon",dbo.get("lon"))
					.put("radius",dbo.get("rad")));}
		return new JSONObject().put("expts",jar);
	}


	/**
	 * Add a list of user's items to an exchange point in db
	 * @param id
	 * @param userID
	 * @param items 
	 * @throws JSONException */
	public static JSONObject addBulkUserItemsToExchangePoint(String id,String userID, String[] items) throws JSONException{
		ExchangePointsDB.addBulkUserItemsToExchangePoint(id, userID, items);
		return Tools.serviceMessage(1);
	}

	/**
	 * Return the list of user's subscribe exchange points
	 * @param userID
	 * @return */
	public static JSONObject userPoints(String userID) throws DatabaseException, JSONException{
		JSONArray jar=new JSONArray();
		DBCursor cursor = ExchangePointsDB.userPoints(userID);
		cursor.sort(new BasicDBObject("date",-1)); 
		while (cursor.hasNext()){
			DBObject dbo=cursor.next();
			BasicDBList bl = (BasicDBList) dbo.get("subscribers");
			String name = (String) ((DBObject)bl.get(0)).get("name"); 
			jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("lat",dbo.get("lat"))
					.put("name", name)
					.put("lon",dbo.get("lon"))
					.put("radius",dbo.get("rad")));}
		return new JSONObject().put("expts",jar);
	}

	public static void addItemToExPoint(String itemID,String exPointID,String userID){
		if(!ItemsDB.checkAthorization(userID,itemID))
			return ;
		//ExchangePointsDB.addItemToExPoint(itemID, exPointID);
	}


	public static void removeItemFromExPoint(String itemID,String exPointID,String userID){
		if(!ItemsDB.checkAthorization(userID,itemID))
			return ;
		ExchangePointsDB.removeItemFromExPoint(itemID, exPointID);
	}


	public static JSONArray getItemExchangePoints(String itemID,String userID) throws JSONException{
		/* o => BasicDBList */
		if(!ItemsDB.checkAthorization(userID,itemID))
			return null;
		//BasicDBList o= (BasicDBList)ExchangePointsDB.getItemExchangePoints(itemID);
		JSONArray js;
		//if(o==null)
		js=new JSONArray();
		//else
		//js=new JSONArray(o.toString());
		return js;
	}



	/**
	 * Return the list of user friend's exchange points
	 * @param userID
	 * @return */
	public static JSONObject friendsExchangePoints(String userID) throws DatabaseException, JSONException{
		JSONArray jar=new JSONArray();
		DBCursor cursor = ExchangePointsDB.friendsExchangePoints(userID);
		cursor.sort(new BasicDBObject("date",-1)); 
		while (cursor.hasNext())
		{
			DBObject dbo=cursor.next();
//			if (!ExchangePointsDB.isMember(dbo.get("_id").toString() ,userID))
//			{

			BasicDBList bl = new BasicDBList();
			bl = (BasicDBList) dbo.get("subscribers");
			String name="";
			for (int i=0;i< bl.size(); i++)				
			{
				String user_firstname = UserDao.getUserById((String)((DBObject)bl.get(i)).get("id_user")).getFirstname();
				String user_name = UserDao.getUserById((String)((DBObject)bl.get(i)).get("id_user")).getName();

				if (i==bl.size()-1) name = name+ user_firstname+" "+ user_name+ "."; 
				else name = name+ user_firstname+" "+ user_name+", ";
			}
			
				JSONArray put = jar.put(new JSONObject()
						.put("id",dbo.get("_id"))
						.put("lat",dbo.get("lat"))
						.put("name", name)
						.put("lon",dbo.get("lon"))
						.put("radius",dbo.get("rad")));
			//}

	     }
		return new JSONObject().put("expts",jar);
	}

	/**
	 * Return the list of user friend's exchange points
	 * @param userID
	 * @return */
	public static JSONObject pointUsersName(String lat, String lon) throws DatabaseException, JSONException{
		JSONArray jar=new JSONArray();
		DBCursor cursor = ExchangePointsDB.pointUsersName(lat,lon);
		cursor.sort(new BasicDBObject("date",-1)); 
		while (cursor.hasNext()){
			DBObject dbo=cursor.next();
			BasicDBList bl = new BasicDBList();
			bl = (BasicDBList) dbo.get("subscribers");
			String name = (String) ((DBObject)bl.get(0)).get("id_user"); 
			JSONArray put = jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("lat",dbo.get("lat"))
					.put("id_user", name)
					.put("lon",dbo.get("lon"))
					.put("radius",dbo.get("rad")));}
		return new JSONObject().put("expts",jar);
	}

	public static void main(String[] args) throws DatabaseException, JSONException 
	{
		//System.out.println(friendsExchangePoints("58496e19273633e062a41acc"));
		System.out.println(ExchangePointsDB.isMember("584c70342736ced6f8f4fa61","58496e8c273633e062a41acd"));
		//System.out.println(ExchangePointsDB.isMember("584c5a2c2736f0f356b0cfe9","58496e19273633e062a41acc"));

	}
}
