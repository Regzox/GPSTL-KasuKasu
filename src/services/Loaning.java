package services;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBCursor;

import dao.LoaningDB;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import utils.Tools;

/**
 * @author Anagbla Joan */
public class Loaning {

	/**
	 * Add an applicant's request for an item 
	 * @param idApplicant
	 * @param idItem */
	public static JSONObject requestItem(String idApplicant,String idItem) throws JSONException{
		LoaningDB.requestItem(idApplicant, idItem);
		return Tools.serviceMessage(1);
	}
	
	
	/**
	 * Accept an applicant's request for an item
	 * @param idRequest 
	 * @throws JSONException */
	public static JSONObject acceptRequests(String idRequest) throws JSONException{
		LoaningDB.acceptRequests(idRequest);
		return Tools.serviceMessage(1);
	}
	
	
	/**
	 * Refuse an applicant's request for an item
	 * @param idRequest 
	 * @return 
	 * @throws JSONException */
	public static JSONObject refuseRequests(String idRequest) throws JSONException{
		LoaningDB.refuseRequests(idRequest);
		return Tools.serviceMessage(1);
	}
	
	
	/**
	 * List all the current applicant's requests
	 * @param idApplicant
	 * @return */
	public static JSONObject applicantRequests(String idApplicant)throws JSONException {
		JSONArray jar = new JSONArray();
		DBCursor dbc = LoaningDB.applicantRequests(idApplicant);
		
		while(dbc.hasNext())
			jar.put(
					new JSONObject()
					.put("loan_request", dbc.next().get("_id"))
					.put("applicant", dbc.next().get("id_applicant"))
					.put("item",  dbc.next().get("id_item"))
					.put("when",dbc.next().get("when"))
					.put("debut", dbc.next().get("debut")) //TODO
					.put("fin", dbc.next().get("fin"))//TODO
					);
		return new JSONObject().put("requests",jar);
	}
	
	
	/**
	 * Return a json object containing item's applicants list
	 * @param id_objet
	 * @return
	 * @throws UserNotUniqueException 
	 * @throws UserNotFoundException */
	public static JSONObject itemApplicants(String id_objet) throws UserNotFoundException, UserNotUniqueException{
		DBCursor dbc = LoaningDB.itemApplicants(id_objet);
		ArrayList<String> applicantIDs = new ArrayList<String>();
		while(dbc.hasNext())
			applicantIDs.add((String)dbc.next().get("id_applicant"));
		return User.getUsersJSONProfileFromIds(applicantIDs);
	}
	
	
	
}
