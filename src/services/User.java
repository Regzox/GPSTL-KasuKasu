package services;

import java.sql.SQLException;

import dao.UserDao;

/**
 * * @author Anagbla Jean, Daniel RADEAU
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
	public static boolean createUser(String email,String mdp,String nom,String prenom, String numero) throws SQLException{		
		//Verification de la non existance de l'email utilisateur  
		if (UserDao.userExists(email))
			return false;
		UserDao.addUser(email,mdp,nom,prenom,numero);
		return true;
	}
	
	/**
	 * Met à jour les informations de l'utilisateur correspondant à l'email et au mot de passe par celle contenues
	 * dans l'instance d'entities.User
	 * @param email
	 * @param password
	 * @param newUser
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	
	public static boolean updateUser(String email, String password, entities.User newUser) throws SQLException, Exception {
		entities.User oldUser = UserDao.getUser(email);
		if (!oldUser.getPassword().equals(password))
			return false;
		UserDao.updateUser(oldUser, newUser);
		return true;
	}
	
	/**
	 * Récupère l'utilisateur correspondant à l'email passé en paramètre
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	
	public static entities.User getUser(String email) throws SQLException, Exception {
		entities.User user = UserDao.getUser(email);
		if (user != null)
			return user;
		return null;
	}
}
