package dao;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
	 * Accept an applicant's request for an item
	 * @param idRequest */
	public static void acceptRequests(String idRequest){
		DBObject dbo = requests.findOne(
				new BasicDBObject()
				.append("_id", idRequest));
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
				.append("_id", idRequest)
				);
	}
	
	
	/**
	 * Refuse an applicant's request for an item
	 * @param idRequest */
	public static void refuseRequests(String idRequest){
		requests.remove(
				new BasicDBObject()
				.append("_id", idRequest)
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
