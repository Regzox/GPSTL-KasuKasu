package dao.users;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.FriendsDao;
import dao.tools.PatternsHolder;
import entities.User;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import kasudb.KasuDB;

/**
 * @author Anagbla Jean, Daniel RADEAU */
public class UserDao {

	public static DBCollection collection = KasuDB.getMongoCollection("users");

	/**
	 * Check if user exists in database by his email
	 * @rebasetested
	 * @param email
	 * @return */
	public static boolean userExistsByEmail(String email){
		return (collection.find(
				new BasicDBObject()
				.append("email",email))).hasNext();
	}



	/**TODO TO TEST
	 * Check if user exists in database by his id
	 * @param id
	 * @return */
	public static boolean userExistsById(String id){
		return (collection.find(
				new BasicDBObject()
				.append("_id",new ObjectId(id)))).hasNext();
	}

	/**
	 * EN : getUser(email) : Returns the object representation of the user designed by the email passed in parameters.
	 * FR : getUser(email) : Retourne un objet representant l'utilisateur dont l'email correspond a celui passe en parametre.
	 * @param email
	 * @return
	 * @rebasetested
	 * @throws UserNotFoundException
	 * @throws UserNotUniqueException
	 */
	public static User getUserByEmail(String email)
			throws UserNotFoundException,
			UserNotUniqueException
	{
		DBCursor dbc = collection.find(
				new BasicDBObject()
				.append("email",email));

		if (!dbc.hasNext())
			throw new UserNotFoundException();

		if (dbc.count()>1)
			throw new UserNotUniqueException();

		DBObject user = dbc.next();
		return new User(
				user.get("_id").toString(),
				(String)user.get("email"),
				(String)user.get("mdp"), 
				(String)user.get("nom"), 
				(String)user.get("prenom"),
				(String)user.get("numero"));
	}

	/**
	 * EN : getUser(email) : Returns the object representation of the user designed by the id passed in parameters.
	 * FR : getUser(email) : Retourne un objet reprï¿½sentant l'utilisateur dont l'id correspond ï¿½ celui passer en paramï¿½tre.
	 * @param id
	 * @return
	 * @throws UserNotFoundException
	 * @throws UserNotUniqueException
	 * @rebasetested
	 */
	public static User getUserById(String id)
			throws UserNotFoundException,
			UserNotUniqueException
	{	
		DBCursor dbc = collection.find(
				new BasicDBObject()
				.append("_id",new ObjectId(id)));

		if (!dbc.hasNext())
			throw new UserNotFoundException();

		if (dbc.count()>1)
			throw new UserNotUniqueException();

		DBObject user = dbc.next();
		return new User(
				user.get("_id").toString(),
				(String)user.get("email"),
				(String)user.get("mdp"), 
				(String)user.get("nom"), 
				(String)user.get("prenom"),
				(String)user.get("numero"));
	}

	/**
	 * Retourne la liste des utilisateurs où le champ 'fields' a pour valeur 'value'. 
	 * @param field
	 * @param value
	 * @rebasetested
	 * @return
	 */
	public static List<User> getUsersWhere(String field, String value){
		DBCursor dbc;
		if(field.equals("_id"))
			dbc = collection.find(
					new BasicDBObject()
					.append(field,new ObjectId(value))); 
		else dbc = collection.find(
				new BasicDBObject()
				.append(field,value)); 

		List<User> users = new ArrayList<User>();
		while (dbc.hasNext()) {
			DBObject user = dbc.next();
			users.add(
					new User(
							user.get("_id").toString(),
							(String)user.get("email"),
							(String)user.get("mdp"), 
							(String)user.get("nom"), 
							(String)user.get("prenom"),
							(String)user.get("numero"))
					);
		}
		return users;
	}

	/**
	 * FR updateUser : Remplace la valeur des champs de l'utilisateur par de nouvelles.
	 * @param oldUser
	 * @param newUser
	 * @throws UserNotFoundException
	 * @throws UserNotUniqueException
	 * @rebasetested
	 */
	public static void updateUser(User oldUser, User newUser) 
			throws UserNotFoundException,
			UserNotUniqueException
	{
		userExistsByEmail(oldUser.getEmail());//useless if database is consistent (user exists in db & is not duplicated)
		collection.update(
				new BasicDBObject()
				.append("email",oldUser.getEmail()),
				new BasicDBObject()
				.append("$set", 
						new BasicDBObject()
						.append("email",newUser.getEmail())
						.append("mdp",newUser.getPassword())
						.append("nom",newUser.getName())
						.append("numero",newUser.getPhone()) 
						.append("prenom",newUser.getFirstname())
						.append("email",newUser.getEmail()))
				); 
	}


	/**
	 * Add an user in database
	 * @param email
	 * @param mdp
	 * @param nom
	 * @param prenom
	 * @param numero 
	 * @rebasetested */
	public static void addUser(String email,String mdp,String nom,String prenom,String numero){ 
		collection.insert(
				new BasicDBObject()
				.append("email",email)
				.append("mdp",md5(mdp))
				.append("nom",nom)
				.append("prenom",prenom)
				.append("numero",numero)
				);
	}


	/**
	 * Set user account as checked
	 * @rebasetested 
	 * @param id */
	public static void confirmUser(String id){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(id)),
				new BasicDBObject()
				.append("$set",
						new BasicDBObject()
						.append("checked",true))
				);
	}

	/**
	 * find an user according to the query
	 * @param userId
	 * @param query
	 * @return */
	public static DBCursor find(String userId, String query) {
		//System.out.println("UserDao/find -> userId : "+ userId);
		return collection.find(findUserCore(userId,query));
	}

	/**
	 * find an user according to the query among user's friends
	 * @param userId
	 * @param query
	 * @return */
	public static DBCursor findAmongFriends(String userId, String query) {
		System.out.println("UserDao/findAmongFriends -> userId : "+ userId+" query:"+query);//debug
		//System.out.println(findUserCore(userId,query));//debug
		return collection.find(
				findUserCore(userId,query)
				.append("_id",
						new BasicDBObject()
						.append("$in",FriendsDao.myFriendsOID(userId) )
						)
				);
	}


	/**
	 * Centralization of the db object to find (shared by functions find and findAmongFriends  )
	 * @param userId
	 * @param query
	 * @return */
	public static BasicDBObject findUserCore(String userId, String query){
		BasicDBObject bdbo = 
				new BasicDBObject()
				.append("_id",
						new BasicDBObject()
						.append("$ne", new ObjectId(userId)));

		Pattern p1 = Pattern.compile(PatternsHolder.email); //is email
		Pattern p2 = Pattern.compile(PatternsHolder.phoneNumber); //is phone number
		List<Pattern> nouns = new ArrayList<>();

		System.out.println("UserDao/find -> "+Arrays.asList(query.trim().split(PatternsHolder.blank)));//debug

		for(String word : Arrays.asList(query.trim().split(PatternsHolder.blank)))
			if(p1.matcher(word).matches())
				bdbo.append("email",word);
			else if(p2.matcher(word).matches())
				bdbo.append("numero",word);
			else
				nouns.add(
						Pattern.compile(
								"^"+word,
								Pattern.CASE_INSENSITIVE
								)
						);

		if(nouns.size()>0){
			BasicDBList identity = new BasicDBList();

			if(nouns.size()>1)
				identity.add(new BasicDBObject()
						.append("nom",
								new BasicDBObject()
								.append("$in", nouns)
								)
						.append("prenom",
								new BasicDBObject()
								.append("$in", nouns)
								)
						);				

			else if(nouns.size()==1){
				identity.add(
						new BasicDBObject()
						.append("nom",
								new BasicDBObject()
								.append("$in", nouns)
								)
						);
				identity.add(
						new BasicDBObject()
						.append("prenom",
								new BasicDBObject()
								.append("$in", nouns)
								)
						);
			}
			bdbo.append("$or", identity);
		}
		return bdbo;
	}



	public static String md5(String input) {

		String md5 = null;

		if(null == input) return null;

		try {

			//Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");

			//Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());

			//Converts message digest value in base 16 (hex) 
			md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}



	/**
	 * local test
	 * @param args */
	public static void main(String[] args){
		//addUser("j@j.fr", "hardtobreakpassword", "A", "j", "0122345896");
		System.out.println(Pattern.compile(".+@.+").matcher("Aa.a@ab.f").matches());
		System.out.println(Pattern.compile("\\d+").matcher("02287282").matches());
		String password = "MyPasswo3";

		System.out.println("MD5 in hex: " + md5(password));
	}
}