package services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.items.ItemsDB;
import dao.items.mapreduce.ItemsMR;
import dao.items.mapreduce.ObjetRSV;
import exceptions.DatabaseException;
import utils.Tools;

/**
 *@author ANAGBLA Joan */
public class Items {



	/**
	 * Update an Item
	 * @param id
	 * @param title
	 * @param description
	 * @return
	 * @throws JSONException
	 * @throws DatabaseException */
	public static JSONObject updateItem(String userId,String id, String title, String description) 
			throws JSONException, DatabaseException{
		if(ItemsDB.checkAthorization(userId,id))
			ItemsDB.updateItem(id,title,description);
		else return Tools.serviceRefused(
				"Vous n'avez pas le droit de modifier cet objet", -1);
		return Tools.serviceMessage(1);
	}


	/**
	 * Return the list of applicants for item identified by id
	 * @param id
	 * @return	 */
	public static JSONObject itemApplicants(String id){
		
		return null;
	}


	/**
	 * return a list of items owned by an user 
	 * @param userID
	 * @return
	 * @throws JSONException
	 * @throws DatabaseException */
	public static JSONObject userItems(String query, String userID) throws JSONException, DatabaseException{
		JSONArray jar =new JSONArray();
		List<ObjetRSV> results=new ArrayList<ObjetRSV>();

		DBCursor cursor = ItemsDB.userItems(userID);

		if(!query.equals("")){//with QueryFilter
			results=ItemsMR.pertinence(query,cursor);
			if(results.isEmpty()){
				System.out.println(
						"userItems [with queryfilter]  : No pertinent results switching to SQLMODO");
				cursor=ItemsDB.userItemsSQLMODO(query, userID);}}

		while(cursor.hasNext()){
			DBObject doc = cursor.next();
			results.add(new ObjetRSV(doc,1));}

		for(ObjetRSV orsv : results )
			jar.put(new JSONObject()
					.put("id",orsv.getDbo().get("_id"))
					.put("type","item")
					.put("owner",orsv.getDbo().get("owner"))
					.put("title",orsv.getDbo().get("title"))
					.put("group",orsv.getDbo().get("group"))
					.put("longitude",orsv.getDbo().get("longitude"))
					.put("latitude",orsv.getDbo().get("latitude"))
					.put("date",orsv.getDbo().get("date"))
					.put("description",orsv.getDbo().get("description")));
		return new JSONObject().put("items",jar);}

	/**
	 * Search Items by query using ItemsMR's pertinence
	 * @param query
	 * @return
	 * @throws JSONException
	 * @throws DatabaseException */
	public static JSONObject searchItems(String query,String userID) throws JSONException, DatabaseException{		
		JSONArray jar =new JSONArray();

		List<ObjetRSV> results=ItemsMR.pertinence(query,ItemsDB.utherItems(userID));

		if(results.isEmpty()){
			System.out.println(
					"searchItems  : No pertinent results switching to SQLMODO");
			DBCursor cursor=ItemsDB.utherItemsSQLMODO(query, userID);
			while(cursor.hasNext()){
				DBObject doc = cursor.next();
				results.add(new ObjetRSV(doc,1));}}

		for(ObjetRSV orsv : results )
			jar.put(new JSONObject()
					.put("id",orsv.getDbo().get("_id"))
					.put("type","item")
					.put("owner",orsv.getDbo().get("owner"))
					.put("title",orsv.getDbo().get("title"))
					.put("group",orsv.getDbo().get("group"))
					.put("longitude",orsv.getDbo().get("longitude"))
					.put("latitude",orsv.getDbo().get("latitude"))
					.put("date",orsv.getDbo().get("date"))
					.put("description", orsv.getDbo().get("description")));
		return new JSONObject().put("items",jar);}
}