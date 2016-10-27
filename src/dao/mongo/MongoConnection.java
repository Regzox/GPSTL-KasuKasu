package dao.mongo;

import java.net.UnknownHostException;


import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * 
 * Gère des fonctions statiques pour la connexion à Mongo
 *
 */
public class MongoConnection {
	
	
	// Configuration à utiliser que en LOCALE 
	
	//  ################## [ conf locale   
	
	private static final String MONGO_ADR = "127.0.0.1";
	private static final Integer MONGO_PORT = 27017;
	public static final String MONGO_DB_NAME = "kasukasu";
	
	//   					 conf locale ] ##################
	
	/*
	 * Retourne une connexion Mongo
	 */
	public static Mongo getConnection() throws UnknownHostException, MongoException{

		System.out.println("-------- MONGO Connection  ------------\n");
		
		Mongo m = new Mongo(MONGO_ADR, MONGO_PORT);	
		System.out.println("Connecté a MongoDB\n" + MONGO_ADR +":"+MONGO_PORT);
		return m;
			
	}
	
}
	
