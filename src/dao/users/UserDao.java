package dao.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import enumerations.Status;
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
				.append("checked",false)
				.append("date",new Date())
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
	 * check if user account is checked :
	 * return true if the 24 hours time limit is not exceeded
	 * return false after the 24 hours time limit if the user has not confirmed 
	 * his account else true 
	 * @rebasetested 
	 * @param id */
	public static boolean isConfirmed(String id){
		int nb_days = 1; //time limit to confirm user account before checked access requirement
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -nb_days); //nb_days before current date
		return collection.find(
				new BasicDBObject("_id",new ObjectId(id))
				.append("$or",new BasicDBObject[]{
						new BasicDBObject("checked",true),
						new BasicDBObject("date",
								new BasicDBObject("$gt",cal.getTime()))}
						)
				).hasNext();
	}



	/**ADMIN FUNCTION : 
	 * remove from database unconfirmed account since nb_days */
	public static void deleteUnconfirmedAccounts(){
		int nb_days = 7; //time limit to confirm user account before its deletion
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -nb_days); //nb_days before current date
		collection.remove(
				new BasicDBObject("date",new BasicDBObject("$lt",cal.getTime()))
				.append("checked", false));
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

		return FuzzyFinder.findPertinent(collection,queryWords, fuzzyness,
				Arrays.asList("nom","prenom"),constraints,"^");
	}



	@SuppressWarnings("unused")
	private static void testAdd(String prev,int nb){
		String alphabet = "aioeujnsb"/*cdfghklmpkrtvwxyz*/;
		prev=prev+alphabet.charAt(new Random().nextInt(alphabet.length()));
		addUser("x@x.fr", "ppp",prev, "lol", "0122345896");
		if(nb>0)	testAdd(prev,--nb);
	}

	public static DBObject getUser(String id) throws UserNotFoundException, UserNotUniqueException{
		DBCursor dbc = collection.find(
				new BasicDBObject()
				.append("_id",new ObjectId(id)));

		if (!dbc.hasNext())
			throw new UserNotFoundException();

		if (dbc.count()>1)
			throw new UserNotUniqueException();

		DBObject user = dbc.next();
		return user;
	}

	public static void setStatus(String userId,Status status){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(userId))
				,new BasicDBObject()
				.append("$set",
						new BasicDBObject()						
						.append("status",status.toString())));
	}

	public static void setAdmin(String userId){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(userId))
				,new BasicDBObject()
				.append("$set",
						new BasicDBObject()						
						.append("isAdmin","true")));
	}

	public static void unsetAdmin(String userId){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(userId))
				,new BasicDBObject()
				.append("$set",
						new BasicDBObject()						
						.append("isAdmin","false")));
	}

	public static boolean isAdmin(String userId) throws UserNotFoundException, UserNotUniqueException{
		DBObject user = getUser(userId);
		String status;
		try{
			status=user.get("isAdmin").toString();
			return status.compareTo("true")==0;
		}catch(Exception e){
			return false;
		}
	}

	public static void freeze(String userId) throws UserNotFoundException, UserNotUniqueException {
		if(isNormal(userId))
			setStatus(userId,Status.FROZEN);
	}

	public static void unfreeze(String userId) throws UserNotFoundException, UserNotUniqueException {
		if(isFrozen(userId))
			setStatus(userId,Status.NORMAL);
	}

	public static void ban(String userId) {
		setStatus(userId,Status.BANNED);
	}

	public static void unban(String userId) throws UserNotFoundException, UserNotUniqueException {
		if(isBanned(userId))
			setStatus(userId,Status.NORMAL);
	}

	public static boolean isFrozen(String userId) throws UserNotFoundException, UserNotUniqueException {
		return getStatus(userId).equals(Status.FROZEN);
	}

	public static boolean isBanned(String userId) throws UserNotFoundException, UserNotUniqueException {
		return getStatus(userId).equals(Status.BANNED);
	}

	public static boolean isNormal(String userId) throws UserNotFoundException, UserNotUniqueException{
		return getStatus(userId).equals(Status.NORMAL);
	}

	public static Status getStatus(String userId) throws UserNotFoundException, UserNotUniqueException{
		DBObject user = getUser(userId);
		String status;
		try{
			status=user.get("status").toString();
		}catch(Exception e){
			return Status.NORMAL;
		}

		switch(status){
		case "FROZEN" :
			return Status.FROZEN;
		case "BANNED" :
			return Status.BANNED;
		default:
			return Status.NORMAL;
		}
	}


	/**
	 * Set user to vacation mode to true
	 * All user's items will be disabled to loan 
	 * (
	 * 	they wont appear in search 's results 
	 * )
	 * @param id */
	public static void goOnVacation(String id){
		collection.update(
				new BasicDBObject("_id",new ObjectId(id)),
				new BasicDBObject("$set", new BasicDBObject("vacation",true))); 
	}


	/**
	 * Set user to vacation mode to false
	 * All user's items will be enabled to loan 
	 * (
	 * 	they will appear in search 's results 
	 * )
	 * NB: The specified value in the $unset expression (i.e. "") 
	 * does not impact the operation
	 * @param id */
	public static void exitVacation(String id) {		
		collection.update(
				new BasicDBObject("_id",new ObjectId(id)),
				new BasicDBObject("$unset", new BasicDBObject("vacation","")));
	}
	
	
	/**
	 * local test
	 * @param args 
	 * @throws UserNotUniqueException 
	 * @throws UserNotFoundException */
	public static void main(String[] args) throws UserNotFoundException, UserNotUniqueException{
		//testAdd("j",7);
		//addUser("joujou@ondabeat.fr", "hardtobreakpassword", "je", "lola", "0122345896");
		//deleteUnconfirmedAccounts();
		//addUser("joujou@ondabeat.fr", "hardtobreakpassword", "joanne", "joana", "0122345896");
		//addUser("jojotut@tut.fr", "hardtobreakpassword", "joan", "joAm", "0122345896");
		//System.out.println("isConfirmed="+isConfirmed("586ef97a6c7e0d1c7185d4b9"));
		//System.out.println(Pattern.compile(
		//PatternsHolder.email).matcher("Aa.a@ab.f").matches());
		//System.out.println(Pattern.compile(
		//PatternsHolder.nums).matcher("02287282").matches());
		//String password = "MyPasswo3";
		//System.out.println("MD5 in hex: " + DataEncryption.md5(password));

		//		System.out.println("**UNBAN**");
		//		unban("58593a49cc29874daef62069");
		//		System.out.println("Status ? : "+getStatus("58593a49cc29874daef62069"));
		//
		//		System.out.println("**FREEZE**");
		//		freeze("58593a49cc29874daef62069");
		//		System.out.println("Status ? : "+getStatus("58593a49cc29874daef62069"));
		//		
		//		System.out.println("**BAN**");
		//		ban("58593a49cc29874daef62069");
		//		System.out.println("Status ? : "+getStatus("58593a49cc29874daef62069"));
		//		
		//		System.out.println("**FREEZE**");
		//		freeze("58593a49cc29874daef62069");
		//		System.out.println("Status ? : "+getStatus("58593a49cc29874daef62069"));
		//		
		//		System.out.println("**UNFREEZE**");
		unfreeze("58593a49cc29874daef62069");
		System.out.println("Status ? : "+getStatus("58593a49cc29874daef62069"));
		//		
		//		System.out.println("**UNBAN**");
		//		unban("58593a49cc29874daef62069");
		//		System.out.println("Status ? : "+getStatus("58593a49cc29874daef62069"));

		//setAdmin("58593a49cc29874daef62069");
		//System.out.println(isAdmin("58593a49cc29874daef62069"));
		//		
		//		unsetAdmin("58593a49cc29874daef62069");
		//		System.out.println(isAdmin("58593a49cc29874daef62069"));

		//		confirmUser("58593a49cc29874daef62069");

	}

}