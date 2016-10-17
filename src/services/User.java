package services;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDao;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import linguee.Lingua;
import linguee.StringNotFoundException;
import utils.SendEmail;
import json.Error;
import json.Warning;
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
	 * Retourne un objet json représentant une liste d'utilisateurs trouvés pour le champ 'field' et sa valeur 'value'
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
				userJSON.put("password", user.getPassword());
				userJSON.put("name", user.getName());
				userJSON.put("firstname", user.getFirstname());
				userJSON.put("phone", user.getPhone());
				
				usersJSON.put("user" +index, userJSON);
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

	public static void confirmUser(int id) throws UserNotFoundException, SQLException{ 
		if(!UserDao.userExists(id))
			throw new UserNotFoundException();
		UserDao.confirmUser(id);
	}
}
