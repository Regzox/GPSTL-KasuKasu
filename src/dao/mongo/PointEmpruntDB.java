package dao.mongo;

import java.net.UnknownHostException;
import org.json.JSONObject;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import kasudb.KasuDB;

/**
 * Contient les methodes pour gérer les objets dans la base de données.
 */
public class PointEmpruntDB
{
	public static final String MDB_POINTS_COLLECTION = "points";
	
	/**
	 * Ajoute un objet à la base mongo
	 * @param authorid
	 * @param text
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public static void addPoint(JSONObject point) throws UnknownHostException, MongoException{
		// Instance de la collection
		DBCollection collection = KasuDB.getMongoCollection(MDB_POINTS_COLLECTION);

		// Parsing de l'objet
		DBObject dbObj = (DBObject) com.mongodb.util.JSON.parse(point.toString());
		
		
		// Ajout dans la base de données
		collection.insert( dbObj ).toString();

		
	}

}
