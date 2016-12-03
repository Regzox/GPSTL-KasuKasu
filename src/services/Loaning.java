package services;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mongodb.DBCursor;
import dao.LoaningDao;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;

/**
 * @author Anagbla Joan */
public class Loaning {

	public static void  requestItem(String idApplicant,String idItem){
		LoaningDao.requestItem(idApplicant, idItem);
	}
	
	public static JSONObject applicantRequests(String idApplicant)throws JSONException {
		JSONArray jar = new JSONArray();
		DBCursor dbc = LoaningDao.applicantRequests(idApplicant);
		
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
		DBCursor dbc = LoaningDao.itemApplicants(id_objet);
		ArrayList<String> applicantIDs = new ArrayList<String>();
		while(dbc.hasNext())
			applicantIDs.add((String)dbc.next().get("id_applicant"));
		return User.getUsersJSONProfileFromIds(applicantIDs);
	}
	
}
