package kasudb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
