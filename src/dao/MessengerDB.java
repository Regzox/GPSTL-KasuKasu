package dao;

import java.util.Arrays;
import java.util.Date;

import org.json.JSONException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;

import kasudb.KasuDB;


/**
 * @author AJoan */
public class MessengerDB {

	private static DBCollection collection = KasuDB.getMongoCollection("messenger");

	/**Add a new message between two users 
	 * @param sender
	 * @param recipient
	 * @param message
	 * @throws DbException*/
	public static void newMessage(String sender,String recipient,String message) {
		collection.insert(
				new BasicDBObject()
				.append("sender",sender)
				.append("recipient",recipient)
				.append("message",message)
				.append("date",new Date()));}

	/**
	 * Chronological shuffled List of message between two users (A & B)
	 * @param talkerA
	 * @param talkerB
	 * @return
	 * @throws DbException */
	public static DBCursor messages(String talkerA, String talkerB){ 
		BasicDBList orlist = new BasicDBList();
		orlist.addAll(Arrays.asList(
				new BasicDBObject[]{
						new BasicDBObject()
						.append("sender",talkerB)
						.append("recipient",talkerA)
						,new BasicDBObject()
						.append("sender",talkerA)
						.append("recipient",talkerB)
				}));
		return collection.find(
				new BasicDBObject()
				.append("$or",orlist));}

	/**
	 * Chronological shuffled List of message involving user
	 * @param userID
	 * @return
	 * @throws DbException */
	public static DBCursor messages(String userID){ 
		BasicDBList orlist = new BasicDBList();
		orlist.addAll(Arrays.asList(
				new BasicDBObject[]	{
						new BasicDBObject()
						.append("sender",userID)
						,new BasicDBObject()
						.append("recipient",userID)
				}));
		return collection.find(
				new BasicDBObject()
				.append("$or",orlist));}

	public static void main(String[] args) throws MongoException, JSONException {
		MessengerDB.collection.drop();//reset : determinism required for tests
		newMessage("lola58", "jo42", "ohayo jo");
		newMessage("jo42", "lola58", "kombawa lola");
		System.out.println(messages("lola58", "jo42"));
		System.out.println(messages("d910952c404b4b6cca5d6f61a5ab9df0"));
	}

}