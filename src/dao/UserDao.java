package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.User;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import kasudb.KasuDB;

/**
 * @author Anagbla Jean, Daniel RADEAU
 */

public class UserDao {
	/**
	 * METHODE NAME 	: userExists
	 * DESCRIPTION 		: test l'existence du login dans la base de donnees des utilisateurs 	 
	 * @param login
	 * @return boolean
	 * @throws DbException
	 */
	public static boolean userExists(String email) throws SQLException {
		boolean answer=false;
		String sql = "SELECT * FROM USERS WHERE email= '"+email+"' ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sql); 
		answer=rs.next();	
		rs.close();
		s.close();
		c.close();	
		return answer;
	}

	/**
	 * EN : getUser(email) : Returns the object representation of the user designed by the email passed in parameters.
	 * FR : getUser(email) : Retourne un objet représentant l'utilisateur dont l'email correspond à celui passer en paramètre.
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static User getUser(String email)
		throws SQLException,
		UserNotFoundException,
		UserNotUniqueException
	{
		String sqlQuery = "SELECT * FROM USERS WHERE email='" + email +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);

		if (!rs.next())
			throw new UserNotFoundException();

		User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numero"));

		if (rs.next())
			throw new UserNotUniqueException();

		rs.close();
		s.close();
		c.close();	

		return user;
	}

	/**
	 * EN : getUser(email) : Returns the object representation of the user designed by the id passed in parameters.
	 * FR : getUser(email) : Retourne un objet représentant l'utilisateur dont l'id correspond à celui passer en paramètre.
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static User getUser(int id)
		throws SQLException,
		UserNotFoundException,
		UserNotUniqueException
	{	
		String sqlQuery = "SELECT * FROM USERS WHERE id='" + id +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);

		if (!rs.next())
			throw new UserNotFoundException();

		User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numero"));

		if (rs.next())
			throw new UserNotUniqueException();

		rs.close();
		s.close();
		c.close();	

		return user;
	}

	/**
	 * FR updateUser : Remplace la valeur des champs de l'utilisateur par de nouvelles.
	 * @param oldUser
	 * @param newUser
	 * @throws SQLException
	 * @throws Exception
	 */

	public static void updateUser(User oldUser, User newUser) 
		throws SQLException,
		UserNotFoundException,
		UserNotUniqueException
	{
		String sqlQuery = "SELECT * FROM USERS WHERE email='" + oldUser.getEmail() +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);

		if (!rs.next())
			throw new UserNotFoundException();

		sqlQuery = "UPDATE USERS SET " + 
				"email = '" + newUser.getEmail() + 
				"', mdp = '" + newUser.getPassword() + 
				"', nom = '" + newUser.getName() + 
				"', prenom = '"	+ newUser.getFirstname() + 
				"', numero = '"	+ newUser.getPhone() + 
				"' WHERE email = '" + oldUser.getEmail() + "';";

		if (rs.next())
			throw new UserNotUniqueException();

		s.executeUpdate(sqlQuery);

		rs.close();
		s.close();
		c.close();
	}


	/**
	 * METHODE NAME 		: addUser
	 * DESCRIPTION 			: Ajoute un nouvel utilisateur dans la base de donnees des utilisateurs   
	 * @param email
	 * @param mdp
	 * @param nom
	 * @param prenom
	 * @param numero
	 * @throws SQLException
	 */
	public static void addUser(String email,String mdp,String nom,String prenom,String numero) 
		throws SQLException
	{ 
		String sql = "INSERT INTO USERS(email,mdp,nom,prenom,numero) VALUES ("
				+ "'"+email+"' , '"+mdp+"' , '"+nom+"' , '"+prenom+"' , '"+numero+"'"
				+ ") ;";
		System.out.println(sql); //sql debug
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql);
		s.close();
		c.close();
	}

	public static void main(String[] args) throws SQLException {
		addUser("email", "mdp", "nom", "prenom", "1");}

}