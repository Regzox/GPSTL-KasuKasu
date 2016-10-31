package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.UserDao;
import dao.UsersImagesDao;
import exceptions.StringNotFoundException;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import json.Error;
import json.Success;
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
	 * Retourne un objet json representant une liste d'utilisateurs trouves pour le champ 'field' et sa valeur 'value' <br/>
	 * 
	 * Exemple : 	&nbspgetUsersJSONProfileWhere("nom", "PIERRE") returns <br/>
	 * 				{ 	user0 : { <br/>
	 * 						id : "42", <br/>
	 * 						email : "truc@gmail.com", <br/>
	 * 						password : "123456", <br/>
	 * 						name : "PIERRE", <br/>
	 * 						firstname : "Jean", <br/>
	 * 						phone : "0611223344" }, <br/>
	 *  				user1 : { <br/>
	 * 						id : "30", <br/>
	 * 						email : "bidulle@gmail.com", <br/>
	 * 						password : "123456", <br/>
	 * 						name : "PIERRE", <br/>
	 * 						firstname : "Victor", <br/>
	 * 						phone : "0611223344" } <br/>
	 * 				} <br/>
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
	
	public static JSONObject getUsersJSONProfileFromIds(ArrayList<Integer> ids) throws UserNotFoundException, UserNotUniqueException{
		
		JSONObject usersJSON = new JSONObject();
		JSONArray usersArray = new JSONArray();
		if (ids == null)
			return new Error("Ids value is null");
	
		List<entities.User> users = new ArrayList<entities.User>();
		
		try {
			for(Integer i : ids)
			users.add(UserDao.getUser(i));
	
			for (entities.User user : users) {
				JSONObject userJSON = new JSONObject();
				
				userJSON.put("id", user.getId());
				userJSON.put("email", user.getEmail());
				//userJSON.put("password", user.getPassword());
				userJSON.put("name", user.getName());
				userJSON.put("firstname", user.getFirstname());
				userJSON.put("phone", user.getPhone());
				
				usersArray.put(userJSON);
			}
			usersJSON.put("users", usersArray);
			
		} catch (SQLException e) {
			return new Error("Internal SQL error");
		} catch (JSONException e) {
			return new Error("JSON exception");
		}
		
		if (users.isEmpty())
			return new Warning("No users found");
		
		return usersJSON;
	}
	
	/**
	 * Get user image path
	 * @param user
	 * @return
	 */
	
	public static JSONObject getUserImage(entities.User user) {
		JSONObject response = null;
		
		try {
			String url = UsersImagesDao.getUserImage(user);
			response = new Success(url);
		} catch (SQLException e) {
			response = new Error("SQLException when getting user picture : " + e.toString());
		} catch (Exception e) {
			response = new Warning("No picture for this user");
		}
		
		return response;
	}
	
	/**
	 * Put new url for user image
	 * 
	 * @param user
	 * @param url
	 * @return
	 */
	
	public static JSONObject putUserImage(entities.User user, String url) {
		JSONObject response = null;
		
		try {
			UsersImagesDao.addUserImage(user, url);
			response = new Success("Picture successfully added");
		} catch (SQLException e) {
			try {
				UsersImagesDao.updateUserImage(user, url);
				response = new Success("Picture successfully updated");
			} catch (SQLException f) {
				f.printStackTrace();
				response = new Error("Adding and updating picture failed");
			}
		}
		
		return response;
	}
	
	/**
	 * Remove user image
	 * @param user
	 * @return
	 */
	
	public static JSONObject removeUserPIcture (entities.User user) {
		JSONObject response = null;
		
		try {
			UsersImagesDao.removeUserImage(user);
			response = new Success("Picture successfully removed");
		} catch (SQLException e) {
			response = new Error("Removing picture failed");
		}
		
		return response;
	}
	
	public static JSONObject filterUserPassword(JSONObject userJSON) {
		JSONObject userWithoutPassword = new JSONObject();
		@SuppressWarnings("unchecked")
		Iterator<String> users = (Iterator<String>)userJSON.keys();
		
		try {
		
		while (users.hasNext()) {
			String user = users.next();
			@SuppressWarnings("unchecked")
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
	 * Recupere l'utilisateur correspondant a l'email passe en parametre
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
	
	public static entities.User getUserById(String id) throws SQLException, Exception {
		List<entities.User> users = UserDao.getUsersWhere("id", id);
		if (users.size() == 1)
			return users.get(0);
		throw new UserNotUniqueException();
	}

	public static void confirmUser(int id) throws UserNotFoundException, SQLException{ 
		if(!UserDao.userExists(id))
			throw new UserNotFoundException();
		UserDao.confirmUser(id);
	}
}
