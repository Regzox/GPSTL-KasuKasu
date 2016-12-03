package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan */
public class LoaningDao {

	public static DBCollection collection = KasuDB.getMongoCollection("lr");//LOANING_REQUESTS

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
}
