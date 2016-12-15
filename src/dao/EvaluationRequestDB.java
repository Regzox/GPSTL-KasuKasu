package dao;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import kasudb.KasuDB;

public class EvaluationRequestDB {

	protected static DBCollection evaluationRequests = KasuDB.getMongoCollection("evaluation-requests");
	
	public static WriteResult insert(DBObject evaluationRequest) {
		return evaluationRequests.insert(evaluationRequest);
	}
	
	public static WriteResult update(DBObject evaluationRequestOld, DBObject evaluationRequest) {
		return evaluationRequests.update(evaluationRequestOld, evaluationRequest);
	}
	
	public static WriteResult remove(DBObject evaluationRequest) {
		return evaluationRequests.remove(evaluationRequest);
	}
	
	public static DBCursor find(DBObject evaluationRequest) {
		return evaluationRequests.find(evaluationRequest);
	}
	
}
