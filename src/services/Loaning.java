package services;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.LoaningDB;
import dao.items.ItemsDB;
import exceptions.DatabaseException;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import utils.SendEmail;
import utils.Tools;

/**
 * @author Anagbla Joan, Ouiza Bouyahiaoui*/
public class Loaning {

	/**
	 * Add an applicant's request for an item 
	 * @param idApplicant
	 * @param idItem 
	 * @throws Exception 
	 * @throws SQLException */
	
	
	public static JSONObject requestItem(String idApplicant,String idItem) throws SQLException, Exception{
		if(LoaningDB.requestExists(idApplicant, idItem))
			return Tools.serviceRefused
					("Vous avez deja une demande en cours pour cet objet!", -1);
		LoaningDB.requestItem(idApplicant, idItem);	
		DBObject item = ItemsDB.getItem(idItem);
		entities.User applicant =User.getUserById(idApplicant);
		String to = User.getUserById(
				item.get("owner").toString())
				.getEmail();
		String subject ="Demande d'emprunt pour l'objet "+item.get("title");
		String contenu ="Vous avez une demande d'emprunt pour "
				+ "l'objet "+item.get("title")+" "
				+"venant de "+applicant.getFirstname()+" "+applicant.getName()+"."
				+ "\nMerci de consulter votre compte.";
		SendEmail.sendMail(to, subject, contenu);

		return Tools.serviceMessage(1);
	}
	
	
	/**
	 * Accept an applicant's request for an item
	 * @param idRequest 
	 * @throws JSONException 
	 * @throws DatabaseException */
	public static JSONObject acceptRequests(String idApplicant, String idItem) 
			throws JSONException, DatabaseException{
		LoaningDB.acceptRequests(idApplicant,idItem);
		return Tools.serviceMessage(1);
	}
	
	
	/**
	 * Refuse an applicant's request for an item
	 * @param idRequest 
	 * @return 
	 * @throws JSONException */
	public static JSONObject refuseRequests(String idApplicant, String idItem) 
			throws JSONException{
		LoaningDB.refuseRequests(idApplicant,idItem);
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
	 * Return a json object containing an applicant's loan which has been validated
	 * @param idApplicant
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject applicantLoanings(String idApplicant)throws JSONException {
		JSONArray jar = new JSONArray();
		DBCursor dbc = LoaningDB.applicantLoanings(idApplicant);
		while(dbc.hasNext()){
			DBObject dbo = dbc.next();
			jar.put(
					new JSONObject()
					.put("loan_id", dbo.get("_id"))
					.put("item",  dbo.get("id_item"))
					.put("type", "loan")
					.put("title", ItemsDB.getItem(dbo.get("id_item").toString())
							.get("title"))
					//.put("debut", dbo.get("debut")) //TODO 
					//.put("fin", dbo.get("fin")) //TODO
					);
			}
		return new JSONObject().put("loans",jar);
	}
	

	public static void main(String[] args) throws JSONException {
		System.out.println(applicantLoanings("5849a585641a80878d717279"));
	}
	
	/**
	 * Return a json object containing item's applicants list
	 * @param idItem
	 * @return
	 * @throws UserNotUniqueException 
	 * @throws UserNotFoundException */
	public static JSONObject itemApplicants(String idItem) throws UserNotFoundException, UserNotUniqueException{
		DBCursor dbc = LoaningDB.itemApplicants(idItem);
		ArrayList<String> applicantIDs = new ArrayList<String>();
		while(dbc.hasNext())
			applicantIDs.add((String)dbc.next().get("id_applicant"));
		return User.getUsersJSONProfileFromIds(applicantIDs);
	}
	
}
