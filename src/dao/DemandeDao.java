package dao;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import kasudb.KasuDB;

public class DemandeDao {

	public static DBCollection collection = KasuDB.getMongoCollection("lr");//LOANING_REQUESTS

	public static void  AjouterDemande(String idApplicant,String idItem){
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

	public static void main(String[] args) {
		AjouterDemande("1","idObject");
		System.out.println(myDemande("1").next());
	}



	public static DBCursor myDemande(String idApplicant){
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
}
