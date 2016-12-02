package dao;

import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import entities.User;
import kasudb.KasuDB;

/**
 * Allows to manipulate users personal picture references using user id and picture path. 
 * 
 * @author Giuseppe FEDERICO
 * @author Daniel RADEAU
 *
 */

public class UsersImagesDao {
	// Logger
	private static Logger logger = Logger.getLogger( UsersImagesDao.class.getName() ); 
	
	// Instance de la collection
	private static DBCollection collection = 
			KasuDB.getMongoCollection(KasuDB.MDB_IMAGES_COLLECTION);
	
	// Clefs des données
	private static String USER_URI = "user";
	private static String URL_URI = "url";

	/**
	 * Add an user picture into database
	 * 
	 * @param user
	 * @param url
	 */
	public static void addUserImage(User user, String url){
/*		String sql = "INSERT INTO IMAGES (user_id, path) VALUES('" + user.getId() + "', '" + url + "');";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();*/
	
		// Création objet JSON qui va être sauvegardé dans la BD.
		BasicDBObject image_object = new BasicDBObject();          
		image_object.put(	USER_URI	,	user.getId()	);
		image_object.put(	URL_URI		,	url				);
		
		// Ajout dans la base de données
		collection.insert( image_object  );
		logger.warning("Imaged added.");
		
	}
	
	/**
	 * Remove an user picture from database
	 * 
	 * @param user
	 * @param url
	 */
	public static void removeUserImage(User user) {
/*		String sql = "DELETE FROM IMAGES WHERE user_id='" + user.getId() + "';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();*/
		
		// Query
		DBObject query = new BasicDBObject( USER_URI,  user.getId() );
		
		// Remove
		collection.remove(query);
		
	}
	
	/**
	 * Update an user picture into database
	 * 
	 * @param user
	 * @param url
	 */
	
	public static void updateUserImage(User user, String url) {
		// Query
		DBObject query	= new BasicDBObject( 	USER_URI	,	user.getId()	);
		
		// Modifier le champs URL
		DBObject object	= new BasicDBObject(	"$set"		,	new BasicDBObject(	URL_URI	,	url	) );
		
		// Mettre à jour la base
		collection.update(	query	,	object	, true,	false);
		
	/*	String sql = "UPDATE IMAGES SET path= '" + url + "' WHERE user_id='" + user.getId() + "';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();*/
		
	}
	
	/**
	 * Get an user picture url from database
	 * 
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public static String getUserImage(User user) throws Exception {
		String res_url = "";
		
		
		// Query
		DBObject query	= new BasicDBObject( 	USER_URI	,	user.getId()	);

		// Cursor pour parcourir les résultats
		DBCursor cursor = collection.find(query);
		
		if( cursor.size() > 1)
			logger.warning("Incoherent ImageObject structure, more url field than expected");
		
		else if( cursor.size() == 0)
			logger.warning("Incoherent ImageObject structure, no url field");

		else
			// On sauvegarde le contenu de l'url
			res_url = ( String )cursor.next().get(URL_URI);
	
		
		// Si l'url est vide
		if ( res_url.isEmpty() ){
			logger.warning("The returned image url is empty.");
			throw new Exception("User havn't picture");
			
		}
		
		
		return res_url;

	/*	
		String sql = "SELECT * FROM IMAGES WHERE user_id='" + user.getId() + "';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		
		if (!resultSet.next())
			throw new Exception("User havn't picture");
		
		String url = resultSet.getString(2);
		
		statement.close();
		connection.close();*/
		
	}
	
	
}
