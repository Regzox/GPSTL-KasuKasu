package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.User;
import kasudb.KasuDB;

/**
 * Allows to manipulate users personal picture references using user id and picture path. 
 * 
 * @author Daniel RADEAU
 *
 */

public class UsersImagesDao {

	/**
	 * Add an user picture into database
	 * 
	 * @param user
	 * @param url
	 * @throws SQLException
	 */
	
	public static void addUserImage(User user, String url) throws SQLException {
		String sql = "INSERT INTO IMAGES (user_id, path) VALUES('" + user.getId() + "', '" + url + "');";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();
	}
	
	/**
	 * Remove an user picture from database
	 * 
	 * @param user
	 * @param url
	 * @throws SQLException
	 */
	
	public static void removeUserImage(User user) throws SQLException {
		String sql = "DELETE FROM IMAGES WHERE user_id='" + user.getId() + "';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();
	}
	
	/**
	 * Update an user picture into database
	 * 
	 * @param user
	 * @param url
	 * @throws SQLException
	 */
	
	public static void updateUserImage(User user, String url) throws SQLException {
		String sql = "UPDATE IMAGES SET path= '" + url + "' WHERE user_id='" + user.getId() + "';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();
	}
	
	/**
	 * Get an user picture url from database
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	
	public static String getUserImage(User user) throws SQLException, Exception {
		String sql = "SELECT * FROM IMAGES WHERE user_id='" + user.getId() + "';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		
		if (!resultSet.next())
			throw new Exception("User havn't picture");
		
		String url = resultSet.getString(2);
		
		statement.close();
		connection.close();
		
		return url;
	}
	
}
