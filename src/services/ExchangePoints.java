package services;

import java.util.Arrays;
import java.util.HashSet;

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
import entities.User;
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
						, userID, name,radius);
		}else 
			ExchangePointsDB.addExchangePoint(lat,lon,radius,userID,name);
		return Tools.serviceMessage(1);
	}

	public static void main(String[] args) throws DatabaseException, JSONException {
		//System.out.println(addExchangePoint(2,3,200,"5jhjy62hghfj5874gtg5","fac"));
		//System.out.println(addExchangePoint(2,3,500,"7jhjy62hghfj5874gtg5","maison"));
		System.out.println(userPointDetail("586e8cd92736d4e126b99c07", 6250249.17563, 261632.91313));

		//System.out.println(userPoints("5843fafc27360eacbbde0e9f"));
		//System.out.println(userPoints("Coucou"));
	}		



	/**
	 * Subscribe to an existing exchange point
	 * @param id
	 * @param userID
	 * @param name
	 * @return
	 * @throws JSONException */
	public static JSONObject subscribeToExchangePoint(String id,String userID,String name, int radius) 
			throws JSONException {			
		ExchangePointsDB.subscribeToExchangePoint(id, userID, name, radius);		
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
		//		if(!ExchangePointsDB.isMember(id ,userID))
		//			return Tools.serviceRefused
		//					("Vous n'avez pas le droit de modifier ce point d'echange!", -1);
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
	 * Return the list of user's subscribed exchange points
	 * @param userID
	 * @return */
	public static JSONObject userPoints(String userID) throws DatabaseException, JSONException{
		JSONArray jar=new JSONArray();
		DBCursor cursor = ExchangePointsDB.userPoints(userID);
		cursor.sort(new BasicDBObject("date",-1)); 
		while (cursor.hasNext()){
			DBObject dbo=cursor.next();
			BasicDBList bl = (BasicDBList) dbo.get("subscribers");
			int i=0;			
			String name="";
			int radius=0;
			boolean trouve=false;
			String id_user;

			while (i< bl.size() && trouve==false)				
			{
				id_user= (String)((DBObject)bl.get(i)).get("id_user");
				if (id_user.equals(userID)) 
				{
					trouve=true;
					name = (String)((DBObject)bl.get(i)).get("name");
					radius = (int)((DBObject)bl.get(i)).get("radius");


				}
				else i++;

			}

			jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("lat",dbo.get("lat"))
					.put("name", name)
					.put("lon",dbo.get("lon"))
					.put("radius",radius));}
		return new JSONObject().put("expts",jar);
	}

	public static void addItemToExPoint(String itemID,String exPointID,String userID){
		if(!ItemsDB.checkAthorization(userID,itemID))
			return ;
		ExchangePointsDB.addItemsToExPoint(exPointID, userID, 
				new HashSet<>(Arrays.asList(itemID)));
	}


	public static void removeItemFromExPoint(String itemID,String exPointID,String userID){
		if(!ItemsDB.checkAthorization(userID,itemID))
			return ;
		ExchangePointsDB.removeItemsFromExPoint(exPointID,userID,
				new HashSet<String>(Arrays.asList(new String[]{itemID})));
	}


	/*TODO REVOIR ENTIEEMENT*/
	public static JSONArray getItemExchangePoints(String itemID,String userID) throws JSONException{
		/* o => BasicDBList */
		if(ItemsDB.checkAthorization(userID,itemID)){
			DBCursor c=ExchangePointsDB.itemExchangePointsLight(itemID);
			DBObject o=null;
			JSONArray js=new JSONArray();
			while(c.hasNext()){
				o=c.next();
				js.put(new JSONObject(o.toString()));
			}
			return js;
		}
		return new JSONArray();
	}



	/**TODO optimize
	 * Return the list of user friend's exchange points
	 * @param userID
	 * @return */
	public static JSONObject friendsExchangePoints(String userID) throws DatabaseException, JSONException{
		JSONArray jar=new JSONArray();
		DBCursor cursor = ExchangePointsDB.friendsLargeExchangePoints(userID);
		cursor.sort(new BasicDBObject("date",-1)); 
		while (cursor.hasNext())
		{
			DBObject dbo=cursor.next();
			/*Récupérer les noms des personnes abonnés à ce lieu */
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

			jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("lat",dbo.get("lat"))
					.put("name", name)
					.put("lon",dbo.get("lon"))
					.put("radius",dbo.get("rad")));

		}
		return new JSONObject().put("expts",jar);
	}

	public static JSONObject deleteExchangePoint(String id,String ownerID) throws JSONException 
	{
		ExchangePointsDB.deleteExchangePoint(id,ownerID);
		return Tools.serviceMessage(1);

	}
	
	public static JSONObject userPointDetail(String userID, double lat,double lon) throws JSONException 
	{
		DBObject dbc = ExchangePointsDB.userPointDetail(userID,lat,lon);
		BasicDBList bl = new BasicDBList();
		bl = (BasicDBList) dbc.get("subscribers");
		JSONObject jo = new JSONObject(); 
		jo.put("id", dbc.get("_id").toString());
		jo.put("name", ((DBObject)bl.get(0)).get("name"));
		jo.put("radius", ((DBObject)bl.get(0)).get("radius"));
		return jo;

	}
	



}
