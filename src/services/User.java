package services;

import java.sql.SQLException;

import dao.UserDao;

/**
 * * @author Anagbla Jean
 * */

public class User {

	/**
	 * Permet d'ajouter un utilisateur dans la bas des utilisateurs
	 * @param email
	 * @param mdp
	 * @param nom
	 * @param prenom
	 * @param numero
	 * @return
	 * @throws SQLException
	 */
	public static boolean createUser(String email,String mdp,String nom,String prenom, int numero) throws SQLException{		
		//Verification de la non existance de l'email utilisateur  
		if (UserDao.userExists(email))
			return false;
		UserDao.addUser(email,mdp,nom,prenom,numero);
		return true;
	}
}
