package dao.places;

import java.util.Date;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, YAHI Lina, Giuseppe FEDERICO */
public class ExchangePointsDB {

	public static DBCollection collection = KasuDB.getMongoCollection("expts");

	public static void addExchangePoint(
			double lat,double lon,int radius,String id_user,String name){ 
		collection.insert(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				.append("radius",radius)
				.append("subscribers",
						new BasicDBList().add(
								new BasicDBObject()
								.append("id_user", id_user)
								.append("name", name)
								.append("date", new Date())
								)
						)
				);
		/*String sql = "INSERT INTO POINT_PRET(id_user,nom,lat,lon,radius) VALUES ("
				+ "'"+id_user+"' , '"+nom+"' , '"+lat+"' , '"+lon+"' , '"+radius+ "' ) ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql);
		s.close();
		c.close();*/
	}

	
	public static DBCursor userExchangePoints(String userID){
		return collection.find(
				new BasicDBObject()
				.append("subscribers.id_user", userID)
				);
		
		/*JSONArray myPointsPret = new JSONArray();
		String sql = "SELECT nom FROM POINT_PRET WHERE id_user='"+user+"';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next())
		{
			myPointsPret.put(new JSONObject()
					.put("nom",resultSet.getString(1)));
		}	

		resultSet.close();
		statement.close();
		connection.close();

		return new JSONObject().put("points",myPointsPret);*/
	}
	
	
	/*TODO
	 * ajouter des objets, s abonner, lister objets dispo sur un point, listr points des amis ,    
	 */
	
	
	
	
	
	
	
	/*public static void addPointEmprunt(int id_user,String nom,Double lat,Double lon,int radius)
			throws SQLException	
	{ 
		String sql = "INSERT INTO POINT_EMPRUNT(id_user,nom,lat,lon,radius) VALUES ("
				+ "'"+id_user+"' , '"+nom+"' , '"+lat+"' , '"+lon+"' , '"+radius+ "' ) ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql);
		s.close();
		c.close();
	}*/

	/*
	 * Ajoute un objet à la base mongo
	 * @param authorid
	 * @param text
	 * @throws UnknownHostException
	 * @throws MongoException
	 *
	public static void addPoint(JSONObject point) throws UnknownHostException, MongoException{
		// Instance de la collection
		DBCollection collection = KasuDB.getMongoCollection(MDB_POINTS_COLLECTION);

		// Parsing de l'objet
		DBObject dbObj = (DBObject) com.mongodb.util.JSON.parse(point.toString());


		// Ajout dans la base de données
		collection.insert( dbObj ).toString();
	}*/
}
