package services;
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
import dao.users.UserDao;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import json.Success;
import json.Warning;
import kasudb.KasuDB;
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
	 * @throws  */
	public static JSONObject requestItem(
			String lang, 
			String idApplicant,
			String idItem, 
			Date debut, 
			Date fin) 
					throws Exception{

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



		// Traduction stuff
		if(lang.equals("fr"))
			SendEmail.sendMail(to, 
					"[kasukasu] Demande d'emprunt pour l'objet : "+item.get("title"),
					"Vous avez une demande d'emprunt pour "
							+ "l'objet "+item.get("title")+" "
							+"venant de "+applicant.getFirstname()+" "+applicant.getName()+".\n"
							+ "Votre ami "+applicant.getFirstname() 
							+ " voudrait vous emprunter cet objet du "+ Tools.reshapeDateShort(debut) +" jusqu'au "+ Tools.reshapeDateShort(fin) +".\n"
							+ "\nMerci de consulter vos demandes d'emprunt."
							+ "\n\nL'équipe KasuKasu");

		if(lang.equals("en"))
			SendEmail.sendMail(to, 
					"[kasukasu] Loan request for the item : "+item.get("title"),
					"You have got a loan request  "
							+ "for the item : "+item.get("title")+" "
							+"coming from "+applicant.getFirstname()+" "+applicant.getName()+"."
							+ applicant.getFirstname() + " would like to borrow this object from "+ Tools.reshapeDateShort(debut) +" to "+ Tools.reshapeDateShort(fin) +".\n"
							+ "\nPlease, check your  loan requests."
							+ "\n\nTeam KasuKasu");

		//Response
		return Tools.serviceMessage(1);
	}



	/**
	 * Zannen means "dommage" (pity) in japanese ;) */
	public static void zannen(String toEmail, String idItem, String lang){
		DBObject item = ItemsDB.getItem(idItem);

		if(lang.equals("fr"))
			SendEmail.sendMail(toEmail, 
					"[kasukasu] Demande d'emprunt pour l'objet : "+item.get("title"),
					"Vous avez fait une demande d'emprunt pour "
							+ "l'objet "+item.get("title")+".\n"
							+"Malheureusement, cette demande vient d'être refusée "
							+ "ou l'objet a déjà été prêté à autrui."
							+ "\n\nL'équipe KasuKasu.");

		if(lang.equals("en"))
			SendEmail.sendMail(toEmail, 
					"[kasukasu] Loan request for the item : "+item.get("title"),
					"You have applied for a loan "
							+ "for this item : "+item.get("title")+".\n"
							+ "Unfortunately, this request has been refused "
							+ "or the object has already been lent to others."
							+ "\n\nThe KasuKasu team.");
	}
	
	
	
	/**
	 * Yukata means "Dieu merci" (thank god) in japanese ;) */
	private static void yukata(String toEmail, String idItem, String lang){
		DBObject item = ItemsDB.getItem(idItem);

		if(lang.equals("fr"))
			SendEmail.sendMail(toEmail, 
					"[kasukasu] Demande d'emprunt pour l'objet : "+item.get("title"),
					"Vous avez fait une demande d'emprunt pour "
							+ "l'objet "+item.get("title")+".\n"
							+"Cette demande vient d'être acceptée."
							+ "\n\nL'équipe KasuKasu.");

		if(lang.equals("en"))
			SendEmail.sendMail(toEmail, 
					"[kasukasu] Loan request for the item : "+item.get("title"),
					"You have applied for a loan "
							+ "for this item : "+item.get("title")+".\n"
							+ "This request has been accepted."
							+ "\n\nThe KasuKasu team.");
	}



	/**
	 * Accept an applicant's request for an item
	 * @param idRequest 
	 * @throws Exception */
	public static JSONObject acceptRequests(String idApplicant, String idItem, String lang) 
			throws Exception{
		LoaningDB.acceptRequests(idApplicant,idItem,lang);
		yukata(UserDao.getUserById(idApplicant).getEmail(),idItem,lang);
		return Tools.serviceMessage(1);
	}


	/**
	 * Refuse an applicant's request for an item
	 * @param idRequest 
	 * @return 
	 * @throws Exception */
	public static JSONObject refuseRequests(String idApplicant, String idItem, String lang) 
			throws Exception{
		LoaningDB.refuseRequests(idApplicant,idItem);
		zannen(UserDao.getUserById(idApplicant).getEmail(),idItem,lang);
		return Tools.serviceMessage(1);
	}


	/**
	 * List all the current applicant's requests
	 * @param idApplicant
	 * @return 
	 * @throws Exception */
	public static JSONObject applicantRequests(String idApplicant)throws Exception {
		JSONArray jar = new JSONArray();
		DBCursor dbc = LoaningDB.applicantRequests(idApplicant);
		while(dbc.hasNext()){
			DBObject dbo = dbc.next();
			entities.User owner = User.getUserById(
					((String)ItemsDB.getItem((String)dbo.get("id_item")).get("owner"))
					);
			jar.put(
					new JSONObject()
					.put("loan_request_id", dbo.get("_id"))
					.put("item",  dbo.get("id_item"))
					.put("title", ItemsDB.getItem(dbo.get("id_item").toString())
							.get("title"))
					.put("type", "loan_request")
					.put("when", Tools.reshapeDateShort((Date)dbo.get("when")))
					.put("debut", Tools.reshapeDateShort((Date)dbo.get("debut")))
					.put("fin", Tools.reshapeDateShort((Date)dbo.get("fin")))
					.put("owner", owner.getId())
					.put("ownername", owner.getName()+" "+ owner.getFirstname())
					);
		}
		return new JSONObject().put("requests",jar);
	}


	/**
	 * Return a json object containing an applicant's loan which has been validated
	 * @param idApplicant
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static JSONObject applicantLoanings(String idApplicant)throws Exception {
		JSONArray jar = new JSONArray();
		DBCursor dbc = LoaningDB.applicantLoanings(idApplicant);
		while(dbc.hasNext()){
			DBObject dbo = dbc.next();
			entities.User owner = User.getUserById(
					((String)ItemsDB.getItem((String)dbo.get("id_item")).get("owner"))
					);
			jar.put(
					new JSONObject()
					.put("loan_id", dbo.get("_id"))
					.put("item",  dbo.get("id_item"))
					.put("type", "loan")
					.put("title", ItemsDB.getItem(dbo.get("id_item").toString())
							.get("title"))
					.put("debut", Tools.reshapeDateShort((Date)dbo.get("debut"))) 
					.put("fin", Tools.reshapeDateShort((Date)dbo.get("fin"))) 
					.put("owner", owner.getId())
					.put("ownername", owner.getName()+" "+ owner.getFirstname())
					);
		}
		return new JSONObject().put("loans",jar);
	}


	/**
	 * Return a json object containing item's applicants list
	 * @param idItem
	 * @return
	 * @throws UserNotUniqueException 
	 * @throws UserNotFoundException 
	 * @throws JSONException */
	public static JSONObject itemApplicants(String idItem) throws UserNotFoundException, UserNotUniqueException, JSONException{
		DBCursor dbc = LoaningDB.itemApplicants(idItem);
		JSONObject applicants = new JSONObject();
		JSONArray jar = new JSONArray();
		DBObject item = ItemsDB.getItem(idItem);
		while(dbc.hasNext()){
			DBObject dbo =dbc.next();
			entities.User applicant = UserDao.getUserById((String)dbo.get("id_applicant"));
			jar.put(new JSONObject()
					.put("id",applicant.getId())
					.put("debut",Tools.reshapeDateShort((Date)dbo.get("debut")))
					.put("fin",Tools.reshapeDateShort((Date)dbo.get("fin")))
					.put("when",Tools.reshapeDateLong((Date)dbo.get("when")))
					.put("name", applicant.getName())
					.put("firstname", applicant.getFirstname())
					.put("itemid",idItem)
					.put("itemtitle",item.get("title"))
					);

			applicants.put("users", jar).put("printobject", false);

			if (jar.length() ==0)
				return new Warning("No requests found.");
		}
		return applicants;
	}


	/**
	 * list all user's items applicants
	 * @param userID
	 * @return 
	 * @throws UserNotUniqueException 
	 * @throws UserNotFoundException 
	 * @throws JSONException */
	public static JSONObject userItemsApplicants(String userID) throws UserNotFoundException, UserNotUniqueException, JSONException{
		DBCursor dbc = LoaningDB.userItemsApplicants(userID);
		JSONObject applicants = new JSONObject();
		JSONArray jar = new JSONArray();

		while(dbc.hasNext()){
			DBObject dbo =dbc.next();
			DBObject item = ItemsDB.getItem((String)dbo.get("id_item"));
			entities.User applicant = UserDao.getUserById((String)dbo.get("id_applicant"));
			jar.put(new JSONObject()
					.put("id",applicant.getId())
					.put("debut",Tools.reshapeDateShort((Date)dbo.get("debut")))
					.put("fin",Tools.reshapeDateShort((Date)dbo.get("fin")))
					.put("when",Tools.reshapeDateLong((Date)dbo.get("when")))
					.put("name", applicant.getName())
					.put("firstname", applicant.getFirstname())
					.put("itemid", (String)dbo.get("id_item"))
					.put("itemtitle",item.get("title"))
					);

			applicants.put("users", jar).put("printobject", true);

			if (jar.length() ==0)
				return new Warning("No requests found.");
		}
		return applicants;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(applicantLoanings("5849a585641a80878d717279"));
		System.out.println(userItemsApplicants("588610d8ed0a2422703f1ad4"));
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
