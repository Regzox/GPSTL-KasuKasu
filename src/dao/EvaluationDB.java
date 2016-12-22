package dao;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import kasudb.KasuDB;

public class EvaluationDB {

	protected static DBCollection evaluations = KasuDB.getMongoCollection("evaluations");
	
	public static WriteResult insert(DBObject evaluation) {
		return evaluations.insert(evaluation);
	}
	
	public static WriteResult update(DBObject evaluationOld, DBObject evaluation) {
		return evaluations.update(evaluationOld, evaluation);
	}
	
	public static WriteResult remove(DBObject evaluation) {
		return evaluations.remove(evaluation);
	}
	
	public static DBCursor find(DBObject evaluation) {
		return evaluations.find(evaluation);
	}
	
}
