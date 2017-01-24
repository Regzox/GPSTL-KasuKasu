package services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.MessengerDB;
import dao.users.UserDao;
import exceptions.DatabaseException;
import utils.Tools;

/**
 * @author AJoan
 ***@goodToKnow ! FLUENT STYLE CODE */
public class Messenger {

	private static int maxInOne=15;

	/**
	 * Add a new message to private conversation
	 * between logged user and an remote user
	 * @param skey
	 * @param remoteuser
	 * @param message
	 * @return
	 * @throws DbException
	 * @throws JSONException */
	public static JSONObject newPrivateMessage(String userID,String remoteuser,String message) 
			throws JSONException{		
		MessengerDB.newMessage(userID,remoteuser,message);
		return Tools.serviceMessage(1);}


	/**
	 * Return private conversation between logged user and an remote user
	 * @param skey
	 * @param remoteuser
	 * @return
	 * @throws DatabaseException
	 * @throws JSONException */
	public static JSONObject conversation(String userID,String remoteuser) 
			throws DatabaseException, JSONException{
		JSONArray jar=new JSONArray();
		DBCursor cursor =MessengerDB.messages(userID,remoteuser);
		cursor.sort(new BasicDBObject("date",-1)); 
		cursor.limit(maxInOne);
		while (cursor.hasNext()){
			DBObject dbo=cursor.next();
			entities.User sender = UserDao.getUserById((String)dbo.get("sender"));
			entities.User recipient = UserDao.getUserById((String)dbo.get("recipient"));

					jar.put(new JSONObject()
							.put("id",dbo.get("_id"))
							.put("type","message")
							.put("sender",dbo.get("sender"))
							.put("sendername",sender.getName()+" "+sender.getFirstname())
							.put("recipient",dbo.get("recipient"))
							.put("recipientname",recipient.getName()+" "+recipient.getFirstname())
							.put("message",dbo.get("message"))
							.put("date",Tools.reshapeDateLong((Date)dbo.get("date"))));
		}
		return new JSONObject().put("messages", jar);
	}



	/**
	 * Return the interlocutors of the current user
	 * @param userID
	 * @return
	 * @throws JSONException
	 * @throws DatabaseException
	 */
	public static JSONObject interlocutors(String userID) 
			throws JSONException, DatabaseException{
		JSONArray jar=new JSONArray();
		Set<String>unicqids=new HashSet<>();
		DBCursor cursor =MessengerDB.messages(userID);
		cursor.sort(new BasicDBObject("date",-1)); 
		cursor.limit(maxInOne);
		while (cursor.hasNext()){
			DBObject dbo=cursor.next();
			unicqids.add(dbo.get("sender").equals(userID)==true?
					(String)dbo.get("recipient") : (String)dbo.get("sender"));}
		for(String unicqid : unicqids){
			entities.User interlocutor = UserDao.getUserById(unicqid);
			jar.put(new JSONObject()
					//.put("id",dbo.get("_id")) //useless for now for our configuration
					.put("type","speaker")
					.put("interlocutor",interlocutor.getName()+" "+interlocutor.getFirstname())
					.put("id",unicqid));
		}
		return new JSONObject().put("speakers",jar);}


	public static void main(String[] args) throws JSONException, DatabaseException {
		System.out.println(interlocutors("a981a551a770b86e02f3a5a0f49ee2bd"));
		Date d = (Date)MessengerDB.messages("588610d8ed0a2422703f1ad4").next().get("date");
		System.out.println(d);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		System.err.println(""+cal.get(Calendar.DAY_OF_MONTH)+"/"+
				cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)+" "
				+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)
				+":"+cal.get(Calendar.SECOND));
	}
}