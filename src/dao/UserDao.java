package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kasudb.KasuDB;

/**
 * @author Anagbla Jean
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