package services;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.LoaningDB;
import dao.items.ItemsDB;
import exceptions.DatabaseException;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import json.Success;
import kasudb.KasuDB;
import lingua.Lingua;
import utils.SendEmail;
import utils.Tools;

/**
 * @author Anagbla Joan, Giuseppe Federico, Ouiza Bouyahiaoui*/
public class Loaning {

	/**
	 * Add an applicant's request for an item 
	 * 
	 * @param idApplicant
	 * @param idItem 
	 * @param debut - Start date of the desired loaning.
	 * @param fin  - End date of the desired loaning.
	 * @throws Exception 
	 * @throws SQLException */
	public static JSONObject requestItem(	String value, 
											String idApplicant,
											String idItem, 
											Date debut, 
											Date fin) 
			throws SQLException, Exception{
		
		// Test if the request already exists
		if(LoaningDB.requestExists(idApplicant, idItem))
			return Tools.serviceRefused
					("Vous avez deja une demande en cours pour cet objet!", -1);
		
		// Save request
		LoaningDB.requestItem(idApplicant, idItem, debut, fin);	
		
		DBObject item = ItemsDB.getItem(idItem);
		
		// The applicant User
		entities.User applicant = User.getUserById(idApplicant);
		
		// The recipient user
		String to = User.getUserById(
				item.get("owner").toString())
				.getEmail();
		
		
		// Treduction stuff
		if(value.equals("fr"))
			SendEmail.sendMail(to, 
							"[kasukasu] Demande d'emprunt pour l'objet : "+item.get("title"),
							"Vous avez une demande d'emprunt pour "
							+ "l'objet "+item.get("title")+" "
							+"venant de "+applicant.getFirstname()+" "+applicant.getName()+".\n"
							+ applicant.getFirstname() + " voudrait l'objet du "+ debut +" jusqu'au "+ fin +".\n"
							+ "\nMerci de consulter votre compte."
							+ "L'équipe KasuKasu");
		
		if(value.equals("en"))
			SendEmail.sendMail(to, 
					"[kasukasu] Loan request for the item : "+item.get("title"),
					"You have got a loan request  "
					+ "for the item : "+item.get("title")+" "
					+"coming from "+applicant.getFirstname()+" "+applicant.getName()+"."
					+ applicant.getFirstname() + " would like the object from the "+ debut +" to the"+ fin +".\n"
					+ "\nPlease, check your account."
					+ "Team KasuKasu");
		
		//Response
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
					.put("debut", dbc.next().get("debut"))
					.put("fin", dbc.next().get("fin"))
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
					.put("debut", dbo.get("debut")) 
					.put("fin", dbo.get("fin")) 
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
	
	public static JSONObject returnItem(String loanId) {
						
		DBCollection loanings = KasuDB.getMongoCollection("loaning");
		DBCursor cl = loanings.find(new BasicDBObject().append("_id", new ObjectId(loanId)));
		DBObject loan = cl.next();
		String itemId = (String) loan.get("id_item");
		String applicantId = (String) loan.get("id_applicant");
		cl.close();
		
		DBCollection items = KasuDB.getMongoCollection("items");
		DBCursor ci = items.find(new BasicDBObject().append("_id", new ObjectId(itemId)));
		DBObject item = ci.next();
		String ownerId = (String) item.get("owner");
		ci.close();
	
		Evaluation.insertRequest(applicantId, ownerId, loanId);
		
		LoaningDB.removeLoan(loanId);
		
		return new Success("Item returned to this owner");
	}
	
}
