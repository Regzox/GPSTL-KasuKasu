package kasudb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class KasuDB {
	private static Mongo MongoConnection = null;
	private static DB db = null;
	
	// Nom collection mongo qui contient les URL des images
	public static final String MDB_IMAGES_COLLECTION = "Images";


	/*public static DBCollection getCollection(String coll) {
		try {return new Mongo("127.0.0.1",27017).getDB("KASUDB").getCollection(coll);}
		catch (UnknownHostException e) {e.printStackTrace();return null;}
	}*/

	

	// Configuration à utiliser que en LOCALE 

	//  ################## [ conf locale   

	private static final String MONGO_ADR = "127.0.0.1";
	private static final Integer MONGO_PORT = 27017;
	public static final String MONGO_DB_NAME = "KASUDB";

	//   					 conf locale ] ##################

	/**
	 * Initialiser la connexion avec Mongo dans une variable statique, 
	 * pas besoin de faire plusieurs connexions.
	 * une suffit.
	 */
	private static void initMongoClient() {
		if (MongoConnection == null) {
			try {
				MongoConnection = getMongoConnection();
			} catch (UnknownHostException e) {			
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Effectue une connexion Mongo 
	 */
	private static Mongo getMongoConnection() throws UnknownHostException, MongoException{
		Mongo m = new Mongo(MONGO_ADR, MONGO_PORT);	
		return m;
	}

	/**
	 * Retourne une DB Mongo 
	 */
	public static DB getMongoDatabase() throws UnknownHostException, MongoException{
		if (MongoConnection == null) {
			initMongoClient();
		}

		if (db == null) {
			try {
				db =  MongoConnection.getDB(MONGO_DB_NAME);
			} catch (Exception e) {
				throw new RuntimeException("DB Not found", e);
			}
		}
		return db;
	}

	/**
	 * Retourne une collection Mongo 
	 */
	public static DBCollection getMongoCollection(String collection) {
		try {
			return getMongoDatabase().getCollection(collection);
		} catch (Exception e) {
			e.printStackTrace();return null;
		}
	}

	/**
	 * Termine une connexion MongoDB
	 */
	public static void closeMongoConnection(){
		if( MongoConnection != null){
			MongoConnection.close();
			MongoConnection = null;
		}else{
			throw new NullPointerException("Instance to the DB is null");

		}
	}
}