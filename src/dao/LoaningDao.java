package dao;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, Celien Creminon, Wafae Cheglal */
public class LoaningDao {

	public static DBCollection collection = KasuDB.getMongoCollection("lr");//LOANING_REQUESTS

	public static void  requestItem(String idApplicant,String idItem){
		collection.insert(
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


	public static DBCursor applicantRequests(String idApplicant){
		return collection.find(
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
		return collection.find(
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
