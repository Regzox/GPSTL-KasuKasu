package dao.users;

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

		/*boolean answer=false;
		String sql = "SELECT * FROM USERS WHERE email= '"+email+"' ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sql); 
		answer=rs.next();	
		rs.close();
		s.close();
		c.close();	
		return answer;*/
	}


	/**TODO TO TEST
	 * Check if user exists in database by his id
	 * @param id
	 * @return */
	public static boolean userExistsById(String id){
		return (collection.find(
				new BasicDBObject()
				.append("_id",new ObjectId(id)))).hasNext();

		/*boolean answer=false;
		String sql = "SELECT * FROM USERS WHERE id= '"+id+"' ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sql); 
		answer=rs.next();	
		rs.close();
		s.close();
		c.close();	
		return answer;*/
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


		/*String sqlQuery = "SELECT * FROM USERS WHERE email='" + email +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);

		if (!rs.next())
			throw new UserNotFoundException();

		User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numero"));

		if (rs.next())
			throw new UserNotUniqueException();

		rs.close();
		s.close();
		c.close();	
		return user;*/
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

		/*String sqlQuery = "SELECT * FROM USERS WHERE id='" + id +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);

		if (!rs.next())
			throw new UserNotFoundException();

		User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numero"));

		if (rs.next())
			throw new UserNotUniqueException();

		rs.close();
		s.close();
		c.close();	

		return user;*/
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

		/*List<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM USERS WHERE " + field + "='" + value + "';";
		Connection connection = KasuDB.SQLConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			User user = new User (
					resultSet.getInt("id"),
					resultSet.getString("email"),
					resultSet.getString("mdp"),
					resultSet.getString("nom"),
					resultSet.getString("prenom"),
					resultSet.getString("numero"));
			users.add(user);
		}

		resultSet.close();
		statement.close();
		connection.close();

		return users;*/
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

		/*String sqlQuery = "SELECT * FROM USERS WHERE email='" + oldUser.getEmail() +"';";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sqlQuery);

		if (!rs.next())
			throw new UserNotFoundException();

		sqlQuery = "UPDATE USERS SET " + 
				"email = '" + newUser.getEmail() + 
				"', mdp = '" + newUser.getPassword() + 
				"', nom = '" + newUser.getName() + 
				"', prenom = '"	+ newUser.getFirstname() + 
				"', numero = '"	+ newUser.getPhone() + 
				"' WHERE email = '" + oldUser.getEmail() + "';";

		if (rs.next())
			throw new UserNotUniqueException();

		s.executeUpdate(sqlQuery);

		rs.close();
		s.close();
		c.close();*/
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
				.append("mdp",mdp)
				.append("nom",nom)
				.append("prenom",prenom)
				.append("numero",numero)
				);
		/*String sql = "INSERT INTO USERS(email,mdp,nom,prenom,numero,accountdate) VALUES ("
				+ "'"+email+"' , '"+mdp+"' , '"+nom+"' , '"+prenom+"' , '"+numero
				+"' , '" +Tools.getCurrentTimeStamp()+ "' ) ;";
		System.out.println(sql); //sql debug
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql);
		s.close();
		c.close();*/
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

		/*String sql= "UPDATE USERS SET " + 
				"checked= '" + 1 +"' WHERE id = '" + id+ "' ;";
		Connection c = KasuDB.SQLConnection();
		Statement s = c.createStatement();
		s.executeUpdate(sql); 	
		s.close();
		c.close();*/
	}

	/**
	 * find an user according to the query
	 * @param userId
	 * @param query
	 * @return */
	public static DBCursor find(String userId, String query) {
		BasicDBObject bdbo = 
				new BasicDBObject()
				.append("_id",
						new BasicDBObject()
						.append("$ne", userId));

		Pattern p1 = Pattern.compile(".+@.+"); //is email
		Pattern p2 = Pattern.compile("\\d+"); //is phone number
		List<String> nouns = new ArrayList<>();

		System.out.println( Arrays.asList(query.trim().split(" ")));

		for(String word : Arrays.asList(query.trim().split(" ")))
			if(p1.matcher(word).matches())
				bdbo.append("email",word);
			else if(p2.matcher(word).matches())
				bdbo.append("numero",word);
			else
				nouns.add(word);

		if(nouns.size()>0){
			BasicDBList identity = new BasicDBList();

			if(nouns.size()>1)
				for(String noun : nouns)
					identity.add(new BasicDBObject()
							.append("nom",noun)
							.append("prenom",
									new BasicDBObject()
									.append("$in", nouns)));
			else if(nouns.size()==1){
				identity.add(
						new BasicDBObject()
						.append("nom",nouns.get(0)));
				identity.add(
						new BasicDBObject()
						.append("prenom",nouns.get(0)));
			}

			bdbo.append("$or", identity);
		}
		return collection.find(bdbo);
	}



	/**
	 * local test
	 * @param args */
	public static void main(String[] args){
		//addUser("j@j.fr", "hardtobreakpassword", "A", "j", "0122345896");
		System.out.println(Pattern.compile(".+@.+").matcher("Aa.a@ab.f").matches());
		System.out.println(Pattern.compile("\\d+").matcher("02287282").matches());
	}
}