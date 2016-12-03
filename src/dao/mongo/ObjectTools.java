package dao.mongo;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import kasudb.KasuDB;

/**
 * Contient les methodes pour gérer les objets dans la base de données.
 */
public class ObjectTools {
	// Instance de la collection
	public static final DBCollection collection = ItemsDB.collection;
	
	/**
	 * Ajoute un objet à la base mongo
	 * @param authorid
	 * @param text
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public static void addObject(JSONObject object) throws UnknownHostException, MongoException{

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
