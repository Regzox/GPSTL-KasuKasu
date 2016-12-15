package services;

import java.util.Map;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.EvaluationDB;
import dao.EvaluationRequestDB;
import dao.EvaluationResponseDB;
import entities.EvaluationRequest;
import entities.EvaluationResponse;
import json.Error;
import json.Success;

/**
 * Service de gestion des évaluations.
 * 
 * @author Daniel RADEAU
 *
 */

public class Evaluation {
	
	public enum Mode {GIVEN, RECEIVED};
	
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
		EvaluationResponseDB.insert(ev.toDBObject());
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
	
	public static JSONObject insertReply(String senderId, String receiverId, String loanId) {
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
	
	public static JSONObject listRequests(String userId) {
		EvaluationRequest evaluationRequest = new EvaluationRequest(null, userId, null);
		DBCursor cursor = EvaluationRequestDB.find(evaluationRequest.toDBObject());
		DBObject object = cursor.next();
		JSONArray requests = new JSONArray();
		
		do {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) object.toMap();
			JSONObject json = new JSONObject();
			
			for (String key : map.keySet()) {
				if (!key.equals("_id")) {
					try {
						json.append(key, map.get(key));
					} catch (JSONException e) {
						return new Error("DBObject convertion fails for key " + key + " : " + map.get(key));
					}
				}
			}
			requests.put(json);
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
		DBObject object = cursor.next();
		
		do {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) object.toMap();
			JSONObject json = new JSONObject();
			
			for (String key : map.keySet()) {
				if (!key.equals("_id")) {
					try {
						json.append(key, map.get(key));
					} catch (JSONException e) {
						return new Error("DBObject convertion fails for key " + key + " : " + map.get(key));
					}
				}
			}
			evaluations.put(json);
		} while (object != null);
		
		return new Success(evaluations);
	}
	
	/**
	 * Liste des responses d'évaluations destinées à l'utilisateur
	 * 
	 * @param userId
	 * @return
	 */
	
	public static JSONObject listReplies(String userId) {
		EvaluationResponse evaluationResponse = new EvaluationResponse(null, userId, null);
		DBCursor cursor = EvaluationResponseDB.find(evaluationResponse.toDBObject());
		DBObject object = cursor.next();
		JSONArray responses = new JSONArray();
		
		do {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) object.toMap();
			JSONObject json = new JSONObject();
			
			for (String key : map.keySet()) {
				if (!key.equals("_id")) {
					try {
						json.append(key, map.get(key));
					} catch (JSONException e) {
						return new Error("DBObject convertion fails for key " + key + " : " + map.get(key));
					}
				}
			}
			responses.put(json);
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
		return new Success(EvaluationRequestDB.remove(new BasicDBObject().append("_id", new ObjectId(evaluationRequestId))).toString());
	}
	
	/**
	 * Supprime une évaluation
	 * 
	 * @param evaluationId
	 * @return
	 */
	
	public static JSONObject removeEvaluation(String evaluationId) {
		return new Success(EvaluationDB.remove(new BasicDBObject().append("_id", new ObjectId(evaluationId))).toString());
	}
	
	/**
	 * Supprime une réponse d'évaluation
	 * 
	 * @param evaluationResponseId
	 * @return
	 */
	
	public static JSONObject removeReply(String evaluationResponseId) {
		return new Success(EvaluationResponseDB.remove(new BasicDBObject().append("_id", new ObjectId(evaluationResponseId))).toString());
	}
	
	/**
	 * Trouve une requête d'évaluation
	 * 
	 * @param evaluationRequestId
	 * @return
	 */
	
	public static JSONObject findRequest(String evaluationRequestId) {
		return new Success(EvaluationRequestDB.find(new BasicDBObject().append("_id", new ObjectId(evaluationRequestId))).toString());
	}
	
	/**
	 * Trouve une évaluation
	 * 
	 * @param evaluationId
	 * @return
	 */
	
	public static JSONObject findEvaluation(String evaluationId) {
		return new Success(EvaluationDB.find(new BasicDBObject().append("_id", new ObjectId(evaluationId))).toString());
	}
	
	/**
	 * Trouve une réponse d'évaluation
	 * 
	 * @param evaluationResponseId
	 * @return
	 */
	
	public static JSONObject findReply(String evaluationResponseId) {
		return new Success(EvaluationResponseDB.find(new BasicDBObject().append("_id", new ObjectId(evaluationResponseId))).toString());
	}
}
