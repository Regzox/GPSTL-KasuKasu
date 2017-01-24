package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.LoaningDB;
import dao.items.ItemsDB;
import dao.items.ItemsMR;
import dao.search.ObjetRSV;
import dao.search.PatternsHolder;
import dao.users.UserDao;
import exceptions.DatabaseException;
import utils.Tools;

/**
 *@author ANAGBLA Joan, Cedric Ribeiro */
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
	 * return an item (all fields)
	 * @param id
	 * @param title
	 * @param description 
	 * @return 
	 * @throws JSONException */
	public static JSONObject getItem(String id,String userID) throws JSONException {
		DBObject dbo = ItemsDB.getItem(id);
		JSONObject item= new JSONObject()
				.put("id",dbo.get("_id"))
				.put("type","item")
				.put("owner",dbo.get("owner"))
				.put("title",dbo.get("title"))
				.put("date",dbo.get("date"))
				.put("description",dbo.get("description"));

		if(dbo.get("owner").equals(userID))
			item.put("permission",1);
		else
			item.put("permission",0);

		return new JSONObject().put(
				"items",new JSONArray().put(item));
	}


	/**
	 * Remove an item
	 * @param id
	 * @param title
	 * @param description 
	 * @throws JSONException */
	public static JSONObject removeItem(String userId,String id) throws JSONException {
		if(!ItemsDB.checkAthorization(userId,id))
			return Tools.serviceRefused(
					"Vous n'avez pas le droit de supprimer cet objet", -1);
		ItemsDB.removeItem(id);
		return Tools.serviceMessage(1);
	}	


	/**
	 * Return item's status (availability) 
	 * this method is a service only to know if yes or not an item is busy  
	 * true if item is busy (or borrowed), else false.
	 * if an item doesn't have status it means it's available (by default)
	 * 
	 * @param id
	 * @param title
	 * @param description 
	 * @return 
	 * @throws JSONException */
	public static JSONObject isBusy(String id) throws JSONException {
		if(ItemsDB.itemStatus(id).equals("borrowed"))
			return new JSONObject().put("vacationstatus", true);
		if(ItemsDB.itemStatus(id).equals("busy"))
			return new JSONObject().put("vacationstatus", true);
		return new JSONObject().put("vacationstatus", false);
	}	


	/**
	 * Set an item's status 
	 * status are borrowed|available|busy
	 * @param id
	 * @param title
	 * @param description 
	 * @throws JSONException */
	public static JSONObject setItemBusyStatus(String id,boolean status) throws JSONException{
		if(ItemsDB.itemStatus(id).equals("borrowed"))
			return Tools.serviceMessage("Cet objet est en pret."); 
		if(status){
			ItemsDB.setItemStatus(id,"busy");
			return Tools.serviceMessage("Cet objet n'est plus visible de vos amis");
		}
		else{
			ItemsDB.setItemStatus(id,"available");	
			return Tools.serviceMessage("Cet objet est de nouveau visible de vos amis");
		}
	}



	/**
	 * return a list of items owned by an user 
	 * @param userID
	 * @return
	 * @throws JSONException
	 * @throws DatabaseException */
	public static JSONObject userItems(String query, String userID) throws JSONException, DatabaseException{
		query=PatternsHolder.refine(query); //important to early refine
		System.out.println("userItems/refined query = "+query);

		JSONArray jar =new JSONArray();
		for(ObjetRSV orsv : ItemsMR.pertinence(query,ItemsDB.userItems(userID,query)))
			jar.put(new JSONObject()
					.put("id",orsv.getDbo().get("_id"))
					.put("type","item")
					.put("owner",orsv.getDbo().get("owner"))
					.put("permission",1)
					.put("title",orsv.getDbo().get("title"))
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
		query=PatternsHolder.refine(query); //important to early refine 
		System.out.println("searchItems/refined query = "+query);

		JSONArray jar =new JSONArray();
		for(ObjetRSV orsv : ItemsMR.pertinence(query,ItemsDB.utherItems(userID,query)))
			if(LoaningDB.requestExists(userID,orsv.getDbo().get("_id").toString()))
				continue;
			else if (UserDao.getVacationStatus((String) orsv.getDbo().get("owner")))
				continue;
			else if(orsv.getDbo().get("status").equals("borrowed"))
				continue;
			else if(orsv.getDbo().get("status").equals("busy"))
				continue;
			else
				jar.put(new JSONObject()
						.put("id",orsv.getDbo().get("_id"))
						.put("type","item")
						.put("owner",orsv.getDbo().get("owner"))
						.put("permission",0)
						.put("title",orsv.getDbo().get("title"))
						.put("date",orsv.getDbo().get("date"))
						.put("description", orsv.getDbo().get("description")));
		return new JSONObject().put("items",jar);
	}





	public static void main(String[] args) throws JSONException, DatabaseException {
		searchItems("Vélo rouge","1");
	}

	/**
	 * Add a groupId to an item
	 * @param itemID
	 * @param groupID
	 * @param userID */
	public static void addGroupToItem(String itemID, String groupID,String userID){
		if(!ItemsDB.checkAthorization(userID,itemID)) 
			return ;
		ItemsDB.addGroupToItem(itemID, groupID);
	}


	/**
	 * Remove a groupId from an item
	 * @param itemID
	 * @param groupID
	 * @param userID */
	public static void removeGroupFromItem(String itemID, String groupID,String userID){
		if(!ItemsDB.checkAthorization(userID,itemID)) 
			return ;
		ItemsDB.removeGroupFromItem(itemID, groupID);
	}


	/**
	 * Return item's groups
	 * @param itemID
	 * @param userID
	 * @return
	 * @throws JSONException */
	public static JSONArray getGroupsFromItem(String itemID,String userID) throws JSONException{
		/* => BasicDBList */
		if(!ItemsDB.checkAthorization(userID,itemID)) 
			return null;
		BasicDBList o= ItemsDB.getGroupsFromItem(itemID);
		JSONArray js;
		if(o==null)
			js=new JSONArray();
		else
			js=new JSONArray(o.toString());
		return js;
	}


	public static JSONObject userInfos(String userID) throws Exception {
		JSONObject jar=new JSONObject();

		JSONObject objects = Items.userItems("", userID);
		int pret = objects.getJSONArray("items").length();

		JSONObject loaning = Loaning.applicantLoanings(userID);
		int emprunt = loaning.getJSONArray("loans").length();

		DBCursor dbc = ItemsDB.userItemsLoaned(userID);
		int loaned = dbc.count();

		DBCursor pend = LoaningDB.pendingRequests(userID);
		int pendR = pend.count();


		jar.put("pret",pret);
		jar.put("emprunt",emprunt);
		jar.put("loaned",loaned);
		jar.put("pending",pendR);



		return jar;
	}
}