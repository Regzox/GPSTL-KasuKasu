package dao;

import java.util.Arrays;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, YAHI Lina, Giuseppe FEDERICO, Cedric Ribeiro */
public class ExchangePointsDB {

	public static DBCollection collection = KasuDB.getMongoCollection("expts");

	/**
	 * Add an exchange point in db
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param userID
	 * @param name */
	public static void addExchangePoint(
			double lat,double lon,int radius,String userID,String name){ 
		BasicDBList bdbl = new BasicDBList();
		bdbl.add(
				new BasicDBObject()
				.append("id_user", userID)
				.append("name", name)
				.append("radius",radius)
				.append("date", new Date())
				.append("useritems",new BasicDBList())
				);
		collection.insert(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				.append("subscribers",bdbl)
				);
		/*String sql = "INSERT INTO POINT_PRET(id_user,nom,lat,lon,radius) VALUES ("
				+ "'"+id_user+"' , '"+nom+"' , '"+lat+"' , '"+lon+"' , '"+radius+ "' ) ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql);
		s.close();
		c.close();*/
	}
	
	/**
	 * ADMIN FUNCTION  : check if user have already created such an exchange point
	 * @param lat
	 * @param lon
	 * @param userID
	 * @return */
	public static boolean exchangePointExists(
			double lat,double lon,String userID){ 
		return collection.find(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon",lon)
				).hasNext();
	}


	/**
	 * Add a list of user's items to an exchange point in db
	 * @param id
	 * @param userID
	 * @param items */
	public static void subscribeToExchangePoint(String id,String userID,String name){ 
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id)),
				new BasicDBObject()
				.append("$addToSet", 
						new BasicDBObject()
						.append("subscribers",
								new BasicDBObject()
								.append("id_user", userID)
								.append("name", name)
								.append("date", new Date())
								.append("useritems",new BasicDBList())
								)
						)
				);
	}	

	
	/**
	 * Update exchange point's preferences for an specific user 
	 * @param id
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param name */
	public static void updateExchangePoint(
			String id,String userID,int radius,String name){ 
		collection.update(
				new BasicDBObject()
				.append("_id",id)
				.append("subscribers.id_user",userID),
				new BasicDBObject()
				.append("$set",
						new BasicDBObject()
						.append("subscribers.name",name)
						.append("subscribers.radius",radius)
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
	
	
	/**
	 * Check if an user is a subscriber to an exchange point
	 * @param id
	 * @param userID
	 * @return */
	public static Boolean isMember(String id , String userID){
		return collection.find(
				new BasicDBObject()
				.append("_id",id)
				.append("subscribers.id_user",userID)).hasNext();
	}
	

	/**
	 * Return all the accessible exchange points of an user 
	 * (his subscribed points and visible friends points)
	 * @param userID
	 * @return */
	public static DBCursor accessibleExchangePoints(String userID){
		BasicDBList exprs = new BasicDBList();
		BasicDBList usergroupsmembership = new BasicDBList();
		DBCursor dbc = GroupsDB.userGroupsMembership(userID);

		while(dbc.hasNext())
			usergroupsmembership.add(dbc.next().get("_id").toString());

		exprs.add(new BasicDBObject()
				.append("subscribers.id_user", userID));
		exprs.add(
				new BasicDBObject()
				.append("subscribers.id_user",
						new BasicDBObject()
						.append("$in",FriendsDao.myFriends(userID)))
				//TODO FILTER BY GROUP APPARTENANCE according to items's groups
				);
		return collection.find(new BasicDBObject().append("$or", exprs));

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


	/**
	 * Add a list of user's items to an exchange point in db
	 * @param id
	 * @param userID
	 * @param items */
	public static void addBulkUserItemsToExchangePoint(String id,String userID, String[] items){ 
		BasicDBList bdbl = new BasicDBList();
		bdbl.addAll(Arrays.asList(items));
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				.append("subscribers.id_user", userID),
				new BasicDBObject()
				.append("$addToSet", 
						new BasicDBObject()
						.append("subscribers.useritems",bdbl) //TODO find how it work
						)
				);
	}
	
	/**
	 * Return the list of user's subscribed exchange points
	 * @param userID
	 * @return */
	public static DBCursor userPoints(String userID) {  
		return collection.find(
				new BasicDBObject()
				.append("subscribers.id_user",userID));}
	
	/**
	 * Return the list of user friend's exchange points
	 * @param userID
	 * @return */
	public static DBCursor friendsExchangePoints(String userID){
		BasicDBList exprs = new BasicDBList();
		exprs.add(
				new BasicDBObject()
				.append("subscribers.id_user",
						new BasicDBObject()
						.append("$in",FriendsDao.myFriends(userID)))
				);
		return collection.find(new BasicDBObject().append("$or", exprs));	
	}
	
	/**
	 * Return the list of users subscribed in an exchange point
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static DBCursor pointUsersName(String lat, String lon){
		return collection.find(
				new BasicDBObject()
				.append("lat",lat)
				.append("lon", lon));	
	}

	/*public static void addItemToExPoint(String itemID,String exPointID,String userID){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id))
				.append("subscribers.id_user", userID),
				new BasicDBObject()
				.append("$addToSet", 
						new BasicDBObject()
						.append("subscribers.useritems",bdbl) //TODO find how it work
						)
				);
	}*/


	public static void removeItemExPointFrom(String itemID,String exPointID){
		BasicDBObject updateQuery = new BasicDBObject();
	    updateQuery.put("_id", new ObjectId(itemID));
	    BasicDBObject updateCommand = new BasicDBObject();
	    updateCommand.put("$pull", new BasicDBObject("exchangepoints",exPointID));
		collection.update(updateQuery,updateCommand);
	}
	
	/*public static DBObject getItemExchangePoints(String itemID){
		BasicDBList groups = (BasicDBList) item.get("exchangepoints");
		return groups;
	}*/


	
	public static void main(String[] args) {
		/*collection.remove(new BasicDBObject());
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg5","fac");
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg5","maison");
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg5","upmc");
		addExchangePoint(2.0,3.0,200,"5jhjy62hghfj5874gtg6","upmc2");
		addExchangePoint(2.0, 3.0, 200, "58429ac00feb9052f1da4be3", "UPMC");
		String excpt_id=accessibleExchangePoints("5jhjy62hghfj5874gtg5").next().get("_id").toString();
		subscribeToExchangePoint(excpt_id, "new_user_id", "la prison");
		subscribeToExchangePoint(excpt_id, "new_user_id2", "upmc");
		subscribeToExchangePoint(excpt_id, "new_user_id3", "l'enfer");
		addBulkUserItemsToExchangePoint(excpt_id,"5jhjy62hghfj5874gtg5",
				new String[]{"itemid1","itemid2","itemid3"});*/
		System.out.println(userPoints("58496e8c273633e062a41acd").next());
		System.out.println(userPoints("58496e19273633e062a41acc").next());
		System.out.println(friendsExchangePoints("58496e19273633e062a41acc").next());
	
	}

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
