package services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.mongo.ItemsDB;
import dao.mongo.ItemsMR;
import dao.mongo.ObjetRSV;
import exceptions.DatabaseException;

/**
 *@author ANAGBLA Joan */
public class Items {

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