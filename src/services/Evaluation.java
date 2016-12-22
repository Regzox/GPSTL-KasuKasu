package services;

import java.util.Map;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import dao.EvaluationDB;
import dao.EvaluationRequestDB;
import dao.EvaluationResponseDB;
import entities.EvaluationRequest;
import entities.EvaluationResponse;
import json.Error;
import json.Success;
import json.Warning;
import kasudb.KasuDB;

/**
 * Service de gestion des évaluations.
 * 
 * @author Daniel RADEAU
 *
 */

public class Evaluation {

	public enum Mode {GIVEN, RECEIVED};

	public static String DEFAULT_IMAGE = "/KasuKasu/data/profile-icon.png";

	/**
	 * Insert en base de données une requête d'évaluation
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param loanId
	 * @return
	 */

	public static JSONObject insertRequest(String senderId, String receiverId, String loanId) {
		EvaluationRequest evr = new EvaluationRequest(senderId, receiverId, loanId);
		EvaluationRequestDB.insert(evr.toDBObject());
		return new Success("La requête d'évaluation à bien été mise en base de données");
	}

	/**
	 * Insert en base de données une évaluation
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param loanId
	 * @param comment
	 * @param mark
	 * @return
	 */

	public static JSONObject insertEvaluation(String senderId, String receiverId, String loanId, String comment, String mark) {
		EvaluationRequest evr = new EvaluationRequest(senderId, receiverId, loanId);
		entities.Evaluation ev = new entities.Evaluation(evr, comment, mark);
		EvaluationDB.insert(ev.toDBObject());
		return new Success("L'évaluation à bien été mise en base de données");
	}

	/**
	 * Insert en base de données une response d'évaluation
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param loanId
	 * @return
	 */

	public static JSONObject insertResponse(String senderId, String receiverId, String loanId) {

		System.out.println(senderId);
		System.out.println(receiverId);
		System.out.println(loanId);

		EvaluationResponse evr = new EvaluationResponse(senderId, receiverId, loanId);
		EvaluationResponseDB.insert(evr.toDBObject());
		return new Success("La réponse d'évaluation à bien été mise en base de données");
	}

	/**
	 * Liste l'ensemble des requêtes d'évaluations destinées à l'utilisateur
	 * 
	 * @param userId
	 * @return
	 */

	public static JSONObject listRequests(String userId) throws NoSuchElementException {
		EvaluationRequest evaluationRequest = new EvaluationRequest(null, userId, null);
		DBCursor cursor = EvaluationRequestDB.find(evaluationRequest.toDBObject());
		DBObject object = cursor.next();
		JSONArray requests = new JSONArray();

		do {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) object.toMap();
			JSONObject json = new JSONObject();

			for (String key : map.keySet()) {
				//if (!key.equals("_id")) {
				try {
					json.put(key, map.get(key));
				} catch (JSONException e) {
					return new Error("DBObject convertion fails for key " + key + " : " + map.get(key));
				}
				//}
			}
			requests.put(json);
			if (cursor.hasNext())
				object = cursor.next();
			else
				break;
		} while (object != null);
		return new Success(requests);
	}

	/**
	 * Liste des évaluations soumises ou reçues par un utilisateur
	 * 
	 * @param mode
	 * @param userId
	 * @return
	 */

	public static JSONObject listEvaluations(Mode mode, String userId) {
		JSONArray evaluations = new JSONArray();
		entities.EvaluationRequest evr = null;
		entities.Evaluation ev = null;

		switch (mode) {
		case GIVEN :
			evr = new EvaluationRequest(userId, null, null);
			ev = new entities.Evaluation(evr, null, null);
			break;
		case RECEIVED :
			evr = new EvaluationRequest(null, userId, null);
			ev = new entities.Evaluation(evr, null, null);
			break;
		default :
			return new json.Error("Mode non reconnu");
		}

		DBCursor cursor = EvaluationDB.find(ev.toDBObject());
		DBObject object = cursor.next();;

		do {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) object.toMap();
			JSONObject json = new JSONObject();

			for (String key : map.keySet()) {
				try {
					json.put(key, map.get(key));
				} catch (JSONException e) {
					return new Error("DBObject convertion fails for key " + key + " : " + map.get(key));
				}
			}
			evaluations.put(json);
			if (cursor.hasNext())
				object = cursor.next();
			else
				break;
		} while (object != null);

		return new Success(evaluations);
	}

	/**
	 * Liste des responses d'évaluations destinées à l'utilisateur
	 * 
	 * @param userId
	 * @return
	 */

	public static JSONObject listResponses(String userId) throws NoSuchElementException {
		EvaluationResponse evaluationResponse = new EvaluationResponse(null, userId, null);
		DBCursor cursor = EvaluationResponseDB.find(evaluationResponse.toDBObject());
		DBObject object = cursor.next();
		JSONArray responses = new JSONArray();

		do {			
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) object.toMap();
			JSONObject json = new JSONObject();

			for (String key : map.keySet()) {
				try {
					json.put(key, map.get(key));
				} catch (JSONException e) {
					return new Error("DBObject convertion fails for key " + key + " : " + map.get(key));
				}
			}
			responses.put(json);
			if (cursor.hasNext())
				object = cursor.next();
			else
				break;
		} while (object != null);
		return new Success(responses);
	}

	/**
	 * Supprime une requête d'évaluation
	 * 
	 * @param evaluationRequestId
	 * @return
	 */

	public static JSONObject removeRequest(String evaluationRequestId) {
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(evaluationRequestId));
		return new Success(EvaluationRequestDB.remove(search).toString());
	}

	/**
	 * Supprime une évaluation
	 * 
	 * @param evaluationId
	 * @return
	 */

	public static JSONObject removeEvaluation(String evaluationId) {
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(evaluationId));
		return new Success(EvaluationDB.remove(search).toString());
	}

	/**
	 * Supprime une réponse d'évaluation
	 * 
	 * @param evaluationResponseId
	 * @return
	 */

	public static JSONObject removeResponse(String evaluationResponseId) {
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(evaluationResponseId));
		return new Success(EvaluationResponseDB.remove(search).toString());
	}

	/**
	 * Trouve une requête d'évaluation
	 * 
	 * @param evaluationRequestId
	 * @return
	 */

	public static JSONObject findRequest(String evaluationRequestId) {
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(evaluationRequestId));
		return new Success(EvaluationRequestDB.find(search).toString());
	}

	/**
	 * Trouve une évaluation
	 * 
	 * @param evaluationId
	 * @return
	 */

	public static JSONObject findEvaluation(String evaluationId) {
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(evaluationId));
		return new Success(EvaluationDB.find(search).toString());
	}

	/**
	 * Trouve une réponse d'évaluation
	 * 
	 * @param evaluationResponseId
	 * @return
	 */

	public static JSONObject findResponse(String evaluationResponseId) {
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(evaluationResponseId));
		return new Success(EvaluationResponseDB.find(search).toString());
	}

	/**
	 * Prépare une liste de notifications disponibles à partir des requêtes 
	 * d'évaluation trouvées pour l'utilisateur destinataire passé en paramètre.
	 * 
	 * @param userId
	 * @return
	 */

	public static JSONObject listRequestNotifications(String userId) {
		JSONObject jsonSuccess = null;
		try {
			jsonSuccess = listRequests(userId);
		} catch (NoSuchElementException e) {
			return new Warning("No request for the user " + userId);
		}
		JSONArray notifications = new JSONArray();

		try {
			JSONArray requestsArray = (JSONArray) jsonSuccess.get("success");

			for (int i = 0; i < requestsArray.length(); i++) {

				JSONObject request = (JSONObject) requestsArray.get(i);

				String requestId = request.getString("_id");
				String senderId = request.getString("senderId");
				String receiverId = request.getString("receiverId");
				String loanId = request.getString("loanId");

				DBObject sender = findUserById(senderId);
				DBObject receiver = findUserById(receiverId);
				DBObject image = findUserImage(senderId);
				DBObject loan = findLoanById(loanId);
				DBObject item = findItemOfLoan(loanId);

				JSONObject notification = notificationToJSON(sender, receiver, image, loan, item);
				notification.put("requestId", requestId);

				notifications.put(notification);
			}

		} catch (JSONException e) {
			return new Error("Erreur dans la déballage de la liste des requêtes d'évaluation json");
		}

		return new Success(notifications);
	}

	/**
	 * Prépare une liste de notifications disponibles à partir des responses 
	 * d'évaluation trouvées pour l'utilisateur destinataire passé en paramètre.
	 * 
	 * @param userId
	 * @return
	 */

	public static JSONObject listResponseNotifications(String userId) {
		JSONObject jsonSuccess = null;
		try {
			jsonSuccess = listResponses(userId);
		} catch (NoSuchElementException e) {
			return new Warning("No responses for the user " + userId);
		}
		JSONArray notifications = new JSONArray();

		try {
			JSONArray responsesArray = (JSONArray) jsonSuccess.get("success");

			for (int i = 0; i < responsesArray.length(); i++) {

				JSONObject response = (JSONObject) responsesArray.get(i);

				String responseId = response.getString("_id");
				String senderId = response.getString("senderId");
				String receiverId = response.getString("receiverId");
				String loanId = response.getString("loanId");

				DBObject sender = findUserById(senderId);
				DBObject receiver = findUserById(receiverId);
				DBObject image = findUserImage(senderId);
				DBObject loan = findLoanById(loanId);
				DBObject item = findItemOfLoan(loanId);
				DBObject evaluation = findEvaluationOfLoan(loanId);

				JSONObject notification = notificationToJSON(sender, receiver, image, loan, item);
				notification.put("responseId", responseId);
				notification.put("comment", evaluation.get("comment"));
				notification.put("mark", evaluation.get("mark"));
				notifications.put(notification);
			}

		} catch (JSONException e) {
			return new Error("Erreur dans la déballage de la liste des réponses d'évaluation json");
		}

		return new Success(notifications);
	}

	/******************************************************************************************************************
	 * 
	 * 		Factorisation
	 * 
	 ******************************************************************************************************************/

	/**
	 * Trouve l'utilisateur en fonction de son id
	 * 
	 * @param userId
	 * @return
	 * @throws MongoException
	 */

	private static DBObject findUserById(String userId) throws MongoException {

		DBCollection users = KasuDB.getMongoCollection("users");
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(userId));
		DBCursor cus = users.find(search);
		DBObject user = cus.next();
		cus.close();

		return user;
	}

	/**
	 * Trouve l'image d'un utilisateur
	 * 
	 * @param userId
	 * @return
	 * @throws MongoException
	 */

	private static DBObject findUserImage(String userId) throws MongoException {

		DBCollection images = KasuDB.getMongoCollection("Images");
		DBObject user = findUserById(userId);
		DBObject search = new BasicDBObject();
		search.put("user", user.get("_id"));
		DBCursor cim = images.find(search);
		DBObject image = null;
		if (cim.hasNext())
			image = cim.next();
		cim.close();

		return image;
	}

	/**
	 * Trouve un emprunt en fonction de son id
	 * 
	 * @param loanId
	 * @return
	 * @throws MongoException
	 */

	private static DBObject findLoanById(String loanId) throws MongoException {

		DBCollection loans = KasuDB.getMongoCollection("lhistory");
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId(loanId));
		DBCursor clo = loans.find(search);
		DBObject loan = clo.next();
		clo.close();

		return loan;
	}

	/**
	 * Trouve l'objet emprunté
	 * 
	 * @param loanId
	 * @return
	 */

	private static DBObject findItemOfLoan(String loanId) throws MongoException {

		DBCollection items = KasuDB.getMongoCollection("items");
		DBObject loan = findLoanById(loanId);
		DBObject search = new BasicDBObject();
		search.put("_id", new ObjectId((String) loan.get("id_item")));
		DBCursor cit = items.find(search);
		DBObject item = cit.next();
		cit.close();

		return item;
	}

	/**
	 * Construit un objet JSON pour la notification à partir d'informations
	 * 
	 * @param sender
	 * @param image
	 * @param loan
	 * @param item
	 * @return
	 * @throws JSONException
	 */

	private static JSONObject notificationToJSON(
			DBObject sender, 
			DBObject receiver,
			DBObject image, 
			DBObject loan, 
			DBObject item) throws JSONException 
	{
		JSONObject notification = new JSONObject();

		notification.put("senderId", sender.get("_id"));
		notification.put("senderFirstname", sender.get("prenom"));
		notification.put("senderLastname", sender.get("nom"));
		if (image != null)
			notification.put("senderImage", image.get("url"));
		else
			notification.put("senderImage", DEFAULT_IMAGE);
		notification.put("receiverId", receiver.get("_id"));
		notification.put("receiverFirstname", receiver.get("prenom"));
		notification.put("receiverLastname", receiver.get("nom"));
		notification.put("loanId", loan.get("_id"));
		notification.put("itemId", item.get("_id"));
		notification.put("itemTitle", item.get("title"));
		notification.put("itemDescription", item.get("description"));

		return notification;
	}

	private static DBObject findEvaluationOfLoan(String loanId) {

		DBCollection evaluations = KasuDB.getMongoCollection("evaluations");
		DBObject search = new BasicDBObject();
		search.put("er.loanId", loanId);
		DBCursor cev = evaluations.find(search);
		DBObject evaluation = cev.next();
		cev.close();

		return evaluation;
	}
}
