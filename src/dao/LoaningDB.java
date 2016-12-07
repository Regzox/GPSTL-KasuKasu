package dao;

import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, Celien Creminon, Wafae Cheglal */
public class LoaningDB {

	public static DBCollection collection = KasuDB.getMongoCollection("loaning");//LOANING_REQUESTS
	public static DBCollection requests = KasuDB.getMongoCollection("lrqst");

	/**
	 * Add an applicant's request for an item 
	 * @param idApplicant
	 * @param idItem */
	public static void requestItem(String idApplicant,String idItem){
		requests.insert(
				new BasicDBObject()
				.append("id_applicant", idApplicant)
				.append("id_item", idItem)
				.append("when", new Date())
				.append("debut", "") //TODO
				.append("fin", "")//TODO
				);

		/*String sqlQuery = "INSERT INTO "+table_name +" values ('"+idSend+"','"+idObject+"',null,null,NOW());";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sqlQuery);
		s.close();
		c.close();	*/
	}


	/**
	 * ADMIN FUNCTION
	 * Check if a request is already sent for an item by the same applicant 
	 * @param idApplicant
	 * @param idItem */
	public static boolean requestExists(String idApplicant,String idItem){
		return requests.find(
				new BasicDBObject()
				.append("id_applicant", idApplicant)
				.append("id_item", idItem)			
				).hasNext();
	}


	/**
	 * Accept an applicant's request for an item
	 * @param idRequest 
	 * @throws DatabaseException */
	public static void acceptRequests(String idApplicant, String idItem) throws DatabaseException{
		System.out.println("idApplicant : " + idApplicant+" idItem : "+idItem);
		DBCursor dbc = requests.find(
				new BasicDBObject()
				.append("id_item", new ObjectId(idItem))
				.append("id_applicant", new ObjectId(idApplicant))
				);
		if(dbc.count()!=1)
			throw new DatabaseException("LoaningDB : Database is inconsistent between : loaning <-> lrqst");
		DBObject dbo= dbc.next();
		collection.insert(
				new BasicDBObject()
				.append("id_applicant", dbo.get("id_applicant"))
				.append("id_item", dbo.get("id_item"))
				.append("when", new Date())
				.append("debut", "") //TODO
				.append("fin", "")//TODO
				);
		requests.remove(
				new BasicDBObject()
				.append("_id", new ObjectId((String)dbo.get("_id")))
				);
	}


	/**
	 * Refuse an applicant's request for an item
	 * @param idRequest */
	public static void refuseRequests(String idApplicant, String idItem){
		requests.remove(
				new BasicDBObject()
				.append("id_applicant", idApplicant)
				.append("id_item", idItem)
				);
	}


	/**
	 * List all the current applicant's requests
	 * @param idApplicant
	 * @return */
	public static DBCursor applicantRequests(String idApplicant){
		return requests.find(
				new BasicDBObject()
				.append("id_applicant", idApplicant));


		/*ArrayList<Integer> myDemandes = new ArrayList<Integer>();
		String sql = "SELECT idSend FROM "+table_name +" WHERE (idRecep='"+user+"');";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next())
			myDemandes.add(resultSet.getInt(1));

		resultSet.close();
		statement.close();
		connection.close();

		return myDemandes;*/
	}

	public static DBCursor itemApplicants(String id_item){
		return requests.find(
				new BasicDBObject()
				.append("id_item", id_item));

		/*String sql = "SELECT * FROM "+table_name +" WHERE id_objet= '"+id_objet+"' ;";
		try {
			Connection c = KasuDB.SQLConnection();
			Statement s = c.createStatement();
			return new CSRShuttleBus(c, s, s.executeQuery(sql));}
		catch (SQLException e) {
			throw new DatabaseException(DAOToolBox.getStackTrace(e));}*/
	}

	public static void main(String[] args) {
		requestItem("1","idObject");
		System.out.println(applicantRequests("1").next());
	}

}
