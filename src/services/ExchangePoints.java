package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.ExchangePointsDB;
import dao.GroupsDB;
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
		if(!ExchangePointsDB.exchangePointExistsForUser(lat, lon, userID))
		ExchangePointsDB.addExchangePoint(lat,lon,radius,userID,name);		
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
			BasicDBList bl = new BasicDBList();
			bl = (BasicDBList) dbo.get("subscribers");
			String name = (String) ((DBObject)bl.get(0)).get("name"); 
			JSONArray put = jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("lat",dbo.get("lat"))
					.put("name", name)
					.put("lon",dbo.get("lon"))
					.put("radius",dbo.get("rad")));}
		return new JSONObject().put("expts",jar);
	}
	
	public static void main(String[] args) 
	{

		try {
			System.out.println(userPoints("5843fafc27360eacbbde0e9f"));
			//System.out.println(userPoints("Coucou"));

			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	

	


	/*public static JSONObject createPointEmprunt(int id_user,String nom,Double lat,Double lon,int radius) 
			throws SQLException, JSONException
	{			
		PointEmpruntDao.addPointEmprunt(id_user,nom,lat,lon,radius);
		return Tools.serviceMessage(1);
	}*/
}
