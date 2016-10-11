package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.User;
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
		ResultSet rs = s.executeQuery(sql); //Execution de la requete
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
	public static User getUser(String email) throws SQLException, Exception {		
		String sqlQuery = "SELECT * FROM USERS WHERE email='" + email +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);
		
		if (!rs.next())
			throw new Exception("No users for this email");
		
		User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numero"));
		
		if (rs.next())
			throw new Exception("Duplicate email exists in the database");
		
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
	public static User getUser(int id) throws SQLException, Exception {		
		String sqlQuery = "SELECT * FROM USERS WHERE id='" + id +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);
		
		if (!rs.next())
			throw new Exception("No users for this email");
		
		User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numero"));
		
		if (rs.next())
			throw new Exception("Duplicate email exists in the database");
		
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
	
	public static void updateUser(User oldUser, User newUser) throws SQLException, Exception {
		
		String sqlQuery = "SELECT * FROM USERS WHERE email='" + oldUser.getEmail() +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);
		
		if (!rs.next())
			throw new Exception("No users for this email");
		
		sqlQuery = "UPDATE USERS SET " + 
				"email = '" + newUser.getEmail() + 
				"', mdp = '" + newUser.getPassword() + 
				"', nom = '" + newUser.getName() + 
				"', prenom = '"	+ newUser.getFirstname() + 
				"', numero = '"	+ newUser.getPhone() + 
				"' WHERE email = '" + oldUser.getEmail() + "';";
		
		if (rs.next())
			throw new Exception("Duplicate email exists in the database");
		
		s.executeUpdate(sqlQuery);
		
		rs.close();
		s.close();
		c.close();
	}


	/**
	 * METHODE NAME 		: addUser
	 * DESCRIPTION 			: Ajoute un nouvel utilisateur dans la base de donnees des utilasteurs   
	 * @param login
	 * @param passwd
	 * @param nom
	 * @param prenom
	 * @throws DbException
	 * @return void 
	 */
	public static void addUser(String email,String mdp,String nom,String prenom,int numero) throws SQLException{ 
		String sql = "INSERT INTO USERS(email,mdp,nom,prenom,numero) VALUES ('"+email+"' , '"+mdp+"' , '"+nom+"' , '"+prenom+"' , '"+numero+"') ;";
		System.out.println(sql);
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql); 	//Execution de la requete
		s.close();
		c.close();
	}
	
	public static void main(String[] args) throws SQLException {
		addUser("email", "mdp", "nom", "prenom", 1);
	}
	
}