package dao.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.FriendsDao;
import dao.search.FuzzyFinder;
import dao.search.ObjetRSV;
import dao.search.PatternsHolder;
import dao.tools.DataEncryption;
import entities.User;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import kasudb.KasuDB;

/**
 * @author Anagbla Jean, Daniel RADEAU */
public class UserDao {

	public static DBCollection collection = KasuDB.getMongoCollection("users");
	//According to the customer demand fuzzyness is 2(omission|substitution|addition)
	private static int fuzzyness = 2; 
	
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
		System.out.println("$$value : "+value);
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
				.append("mdp",DataEncryption.md5(mdp))
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
	public static List<ObjetRSV> findUser(String userId, String query) {
		System.out.println("UserDao/findUser -> userId : "+ userId);
		return findUserCore(userId,query,fuzzyness,new BasicDBObject("_id",
				new BasicDBObject("$ne",new ObjectId(userId))));
	}
	

	/**
	 * Find an user according to the query among user's friends
	 * @param userId
	 * @param query
	 * @return */
	public static List<ObjetRSV> findFriends(String userId, String query) {
		System.out.println("UserDao/findFriends -> userId : "+ userId+" query:"+query);//debug
		BasicDBList list = new  BasicDBList(); 
		list.add(new BasicDBObject("_id",
				new BasicDBObject("$ne", new ObjectId(userId))));
		list.add(new BasicDBObject("_id",
				new BasicDBObject("$in", FriendsDao.myFriendsOID(userId))));
		return findUserCore(userId,query,fuzzyness, new BasicDBObject("$and", list));
	}
	

	/**
	 * Shared core of findUser and findFriend methods  
	 * @param userId
	 * @param query
	 * @param max_indulgence
	 * @param constraints
	 * @param limit
	 * @return */
	public static List<ObjetRSV> findUserCore(String userId, String query,
			int fuzzyness, BasicDBObject constraints){
		if(query.trim().length()==0) return new ArrayList<ObjetRSV>();
		System.out.println("UserDao/findUserCore -> "+
				Arrays.asList(query.trim().split(PatternsHolder.blank)));//debug
		
		Pattern p1 = Pattern.compile(PatternsHolder.email); //is email
		Pattern p2 = Pattern.compile(PatternsHolder.nums); //is phone number
		
		Set<String>queryWords = PatternsHolder.wordSet(query,PatternsHolder.blank);
		for(String word : queryWords)
			if(p1.matcher(word).matches())
				constraints.put("email",word);
			else if(p2.matcher(word).matches())
				constraints.put("numero",word);
		
		return FuzzyFinder.find(collection,queryWords, fuzzyness,
				Arrays.asList("nom","prenom"),constraints);
	}
	
	
	
	private static void testAdd(String prev,int nb){
		String alphabet = "aioeujnsb"/*cdfghklmpkrtvwxyz*/;
		prev=prev+alphabet.charAt(new Random().nextInt(alphabet.length()));
		addUser("x@x.fr", "ppp",prev, "lol", "0122345896");
		if(nb>0)	testAdd(prev,--nb);
	}
	
	
	/**
	 * local test
	 * @param args */
	public static void main(String[] args){
		testAdd("j",7);
		/*addUser("jojotut@tut.fr", "hardtobreakpassword", "joan", "joAm", "0122345896");
		System.out.println(Pattern.compile(
				PatternsHolder.email).matcher("Aa.a@ab.f").matches());
		System.out.println(Pattern.compile(
				PatternsHolder.nums).matcher("02287282").matches());
		String password = "MyPasswo3";
		System.out.println("MD5 in hex: " + DataEncryption.md5(password));*/			
	}
}