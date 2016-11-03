package dao.mongo;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * Contient les methodes pour gérer les objets dans la base de données.
 */
public class ObjectTools {
	public static final String MDB_OBJECTS_COLLECTION = "objets";
	
	/**
	 * Ajoute un objet à la base mongo
	 * @param authorid
	 * @param text
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public static void addObject(JSONObject object) throws UnknownHostException, MongoException{
		// Instance de la collection
		DBCollection collection = MongoConnection.getCollection(MDB_OBJECTS_COLLECTION);

		// Parsing de l'objet
		DBObject dbObj = (DBObject) com.mongodb.util.JSON.parse(object.toString());
		
		// Ajout de la date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		dbObj .put("date", dateFormat.format(today));
		
		// Ajout dans la base de données
		collection.insert( dbObj ).toString();

		
	}

}
