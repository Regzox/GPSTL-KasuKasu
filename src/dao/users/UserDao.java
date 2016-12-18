package dao.users;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.FriendsDao;
import dao.tools.DataEncryption;
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
	public static Iterable<DBObject> find(String userId, String query) {
		//System.out.println("UserDao/find -> userId : "+ userId);
		Map<String,BasicDBObject>constraints=new HashMap<>();
		constraints.put("_id",new BasicDBObject("$ne",new ObjectId(userId)));
		return findUserCore(userId,query,0,constraints,1);
	}

	/**
	 * find an user according to the query among user's friends
	 * @param userId
	 * @param query
	 * @return */
	public static Iterable<DBObject> findAmongFriends(String userId, String query) {
		System.out.println("UserDao/findAmongFriends -> userId : "+ userId+" query:"+query);//debug
		//System.out.println(findUserCore(userId,query));//debug
		Map<String,BasicDBObject>constraints=new HashMap<>();
		constraints.put("_id",new BasicDBObject("$ne",new ObjectId(userId)));
		constraints.put("_id",new BasicDBObject("$in",FriendsDao.myFriendsOID(userId)));
		return findUserCore(userId,query,0,constraints,1);
	}

	/**
	 * Centralization of the db object to find (shared by functions find and findAmongFriends  )
	 * @param userId
	 * @param query
	 * @return */
	public static Iterable<DBObject> findUserCore(String userId, String query, 
			int max_indulgence, Map<String,BasicDBObject>constraints,int limit){		
		System.out.println("UserDao/find -> "+Arrays.asList(query.trim().split(PatternsHolder.blank)));//debug

		BasicDBObject criterion = new BasicDBObject();
		for(Map.Entry<String,BasicDBObject> constraint : constraints.entrySet())
			criterion.append(constraint.getKey(),constraint.getValue());		

		//TODO voir si mieux d utiliser un set ou pas
		Pattern p1 = Pattern.compile(PatternsHolder.email); //is email
		Pattern p2 = Pattern.compile(PatternsHolder.phoneNumber); //is phone number

		//misspellings possibilities with i mistakes by word		
		Map<Integer,List<Pattern>> levels_of_indulgence = new HashMap<>();
		for(String word : Arrays.asList(query.trim().split(PatternsHolder.blank)))
			if(p1.matcher(word).matches())
				criterion.append("email",word);
			else if(p2.matcher(word).matches())
				criterion.append("numero",word);
			else for(int i=0;i<=max_indulgence;i++)
				levels_of_indulgence.put(i, Arrays.asList(
						Pattern.compile(
						"^"+word,Pattern.CASE_INSENSITIVE))); //TODO ph call

		BasicDBList criteria = new BasicDBList();
		BasicDBList conds = new BasicDBList();
		List<String> names = Arrays.asList(new String[]{"nom","prenom"});
		for(int i : levels_of_indulgence.keySet())
			for(String name : names){
				BasicDBObject criterioni=((BasicDBObject)criterion.clone())
						.append(name,
								new BasicDBObject("$in",
										levels_of_indulgence.get(i)));
				criteria.add(criterioni);				

				BasicDBObject cond = new BasicDBObject("$cond", 
						new BasicDBObject("if",criterioni)
						.append("then",i)
						.append("else",0)//TODO do ka nan?
						);
				conds.add(cond);
			}

		System.out.println("criteria="+criteria);
		System.out.println("conds="+conds);

		Iterable<DBObject> output = collection.aggregate(Arrays.asList(
				(DBObject) new BasicDBObject("$match",
						new BasicDBObject("$or",criteria)), //match entire criteria
				(DBObject) new BasicDBObject("$limit",limit)/*,//early limit number of processing documents 
				/*(DBObject) new BasicDBObject("$project", 
						new BasicDBObject("pertinence",
								new BasicDBObject("$add",conds)))*/
				)
				).results();
		return output;


		//TODO match all exact  
		//TODO match 1 exact
		//TODO y exacts x approx
		//TODO y exacts x approx
	}


	/**
	 * local test
	 * @param args */
	public static void main(String[] args){
		//addUser("j@j.fr", "hardtobreakpassword", "A", "j", "0122345896");
		//System.out.println(Pattern.compile(".+@.+").matcher("Aa.a@ab.f").matches());
		//System.out.println(Pattern.compile("\\d+").matcher("02287282").matches());
		//String password = "MyPasswo3";
		//System.out.println("MD5 in hex: " + md5(password));
		for(DBObject dbObject : findUserCore(
				"5851476fd4fa474871be3d76","t",0,new HashMap<String,BasicDBObject>(),1))
			System.out.println(dbObject);

				
	}

}