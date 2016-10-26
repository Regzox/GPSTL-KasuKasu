package services;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDao;
import exceptions.StringNotFoundException;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import json.Error;
import json.Warning;
import lingua.Lingua;
import utils.SendEmail;
import utils.Tools;

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
	 * @throws JSONException 
	 * @throws UserNotUniqueException 
	 * @throws UserNotFoundException 
	 */
	public static JSONObject createUser(String email,String mdp,String nom,String prenom, String numero) 
			throws SQLException, JSONException, UserNotFoundException, UserNotUniqueException{		
		if (UserDao.userExists(email))
			return Tools.serviceMessage("User's email already exists.");

		UserDao.addUser(email,mdp,nom,prenom,numero);
		//TODO if we implements language choices , change fr-FR by dyn language selection
		try {
			SendEmail.sendMail(email,
					Lingua.get("welcomeMailSubject","fr-FR"),
					Lingua.get("welcomeMailMessage","fr-FR")
					+"http://localhost:8080/KasuKasu/confirm?id="+UserDao.getUser(email).getId()
					);
		} catch (StringNotFoundException e) { 
			System.out.println("Dictionary Error : Mail not send");
			e.printStackTrace(); 
		}
		return Tools.serviceMessage(1);
	}

	/**
	 * Met � jour les informations de l'utilisateur correspondant � l'email et au mot de passe par celle contenues
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
	 * Retourne un objet json repr�sentant une liste d'utilisateurs trouv�s pour le champ 'field' et sa valeur 'value'
	 * 
	 * Exemple : 	getUsersJSONProfileWhere("nom", "PIERRE") returns
	 * 				{ 	user0 : { 
	 * 						id : "42",
	 * 						email : "truc@gmail.com",
	 * 						password : "123456",
	 * 						name : "PIERRE",
	 * 						firstname : "Jean",
	 * 						phone : "0611223344" },
	 *  				user1 : { 
	 * 						id : "30",
	 * 						email : "bidulle@gmail.com",
	 * 						password : "123456",
	 * 						name : "PIERRE",
	 * 						firstname : "Victor",
	 * 						phone : "0611223344" }
	 * 				}
	 * @param field
	 * @param value
	 * @return
	 */
	
	public static JSONObject getUsersJSONProfileWhere(String field, String value) {
		
		JSONObject usersJSON = new JSONObject();
		
		if (value == null) {
			return new Error(field + " value is null");
		}
		
		if (value.equals("")) {
			return new Warning(field + " value is empty");
		}
		
		List<entities.User> users = null;
		
		try {
			users = UserDao.getUsersWhere(field, value);
			int index = 0;
			
			for (entities.User user : users) {
				JSONObject userJSON = new JSONObject();
				
				userJSON.put("id", user.getId());
				userJSON.put("email", user.getEmail());
				//userJSON.put("password", user.getPassword());
				userJSON.put("name", user.getName());
				userJSON.put("firstname", user.getFirstname());
				userJSON.put("phone", user.getPhone());
				
				usersJSON.put("user" +(index++), userJSON);
			}
			
		} catch (SQLException e) {
			return new Error("Internal SQL error");
		} catch (JSONException e) {
			return new Error("JSON exception");
		}
		
		if (users.isEmpty())
			return new Warning("Not users found for '" + field + "' = '" + value + "'");
		
		return usersJSON;
	}
	
	public static JSONObject filterUserPassword(JSONObject userJSON) {
		JSONObject userWithoutPassword = new JSONObject();
		Iterator<String> users = (Iterator<String>)userJSON.keys();
		
		try {
		
		while (users.hasNext()) {
			String user = users.next();
			Iterator<String> profile = ((JSONObject) userJSON.get(user)).keys();
			System.out.println(user);
			while (profile.hasNext()) {
				String key = profile.next();
				System.out.println(key);
				if (key.equals("password"))
					continue;
				userWithoutPassword.put(key, ((JSONObject) userJSON.get(user)).get(key));
			}
		}
		
		} catch (JSONException e) {
			return new Error("Filtering JSON password failure !");
		}
		return userWithoutPassword;
	}
	
	/**
	 * R�cup�re l'utilisateur correspondant � l'email pass� en param�tre
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

	public static void confirmUser(int id) throws UserNotFoundException, SQLException{ 
		if(!UserDao.userExists(id))
			throw new UserNotFoundException();
		UserDao.confirmUser(id);
	}
}
