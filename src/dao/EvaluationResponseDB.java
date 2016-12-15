package dao;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import kasudb.KasuDB;

public class EvaluationResponseDB {

	protected static DBCollection evaluationResponses = KasuDB.getMongoCollection("evaluation-responses");
	
	public static WriteResult insert(DBObject evaluationResponse) {
		return evaluationResponses.insert(evaluationResponse);
	}
	
	public static WriteResult update(DBObject evaluationResponseOld, DBObject evaluationResponse) {
		return evaluationResponses.update(evaluationResponseOld, evaluationResponse);
	}
	
	public static WriteResult remove(DBObject evaluationResponse) {
		return evaluationResponses.remove(evaluationResponse);
	}
	
	public static DBCursor find(DBObject evaluationResponse) {
		return evaluationResponses.find(evaluationResponse);
	}
}
