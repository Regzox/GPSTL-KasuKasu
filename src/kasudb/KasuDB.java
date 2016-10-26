package kasudb;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class KasuDB {

	public static Connection SQLConnection() throws SQLException {
			try {Class.forName("com.mysql.jdbc.Driver");
				return (DriverManager.getConnection("jdbc:mysql://localhost/KASUDB","root",""));}
			catch (ClassNotFoundException e) {e.printStackTrace();return null;}
	}
	
	public static Connection SQLConnection(String password) throws SQLException {
		try {Class.forName("com.mysql.jdbc.Driver");
			return (DriverManager.getConnection("jdbc:mysql://localhost/KASUDB","root",password));}
		catch (ClassNotFoundException e) {e.printStackTrace();return null;}
	}

	
	public static DBCollection getCollection(String coll) {
		try {return new Mongo("127.0.0.1",27017).getDB("KASUDB").getCollection(coll);}
		catch (UnknownHostException e) {e.printStackTrace();return null;}
	}	
}
 
