package dao.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Représente une connexion à la BD Mongo 
 * Fournit des fonctions statique pour avoir accès à la base.
 * 
 * @author Giuseppe FEDERICO
 */
public class MongoConnection {
	private static Mongo MongoConnection = null;
	private static DB db = null;
	
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
	private static void initClient() {
		if (MongoConnection == null) {
			try {
				MongoConnection = getConnection();
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
	private static Mongo getConnection() throws UnknownHostException, MongoException{
		Mongo m = new Mongo(MONGO_ADR, MONGO_PORT);	
		return m;
			
	}
	
	/**
	 * Retourne une DB Mongo 
	 */
	public static DB getDatabase() throws UnknownHostException, MongoException{
		if (MongoConnection == null) {
			initClient();
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
	public static DBCollection getCollection(String collection) throws UnknownHostException, MongoException{
		return getDatabase().getCollection(collection);
			
	}
	
	/**
	 * Termine une connexion MongoDB
	 */
	public static void closeConnection(){
		if( MongoConnection != null){
			MongoConnection.close();
			MongoConnection = null;
		}else{
			throw new NullPointerException("Instance to the DB is null");
			
		}
		
		
	}
	
}
	
