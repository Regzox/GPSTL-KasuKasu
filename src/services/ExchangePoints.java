package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.places.ExchangePointsDB;
import utils.Tools;

/**
 * @author ANAGBLA Joan, YAHI Lina */
public class ExchangePoints {
	
	/**
	 * Add a new exchange pint in db 
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param id_user
	 * @param nom
	 * @return
	 * @throws JSONException */
	public static JSONObject addExchangePoint(
			double lat,double lon,int radius,String id_user,String name) 
			throws JSONException {			
		ExchangePointsDB.addExchangePoint(lat,lon,radius,id_user,name);		
		return Tools.serviceMessage(1);
	}

	/**
	 * Return user exchange points according to his userID
	 * @param userID
	 * @return
	 * @throws JSONException */
	public static JSONObject userExchangePoints(String userID) throws JSONException {			
		JSONArray jar=new JSONArray();
		DBCursor cursor = ExchangePointsDB.userExchangePoints(userID); 
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
	
	/*public static JSONObject createPointEmprunt(int id_user,String nom,Double lat,Double lon,int radius) 
			throws SQLException, JSONException
	{			
		PointEmpruntDao.addPointEmprunt(id_user,nom,lat,lon,radius);
		return Tools.serviceMessage(1);
	}*/
}
