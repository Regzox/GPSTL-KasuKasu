package services;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.UserDao;
import dao.mongo.GroupsDB;
import exceptions.DatabaseException;
import exceptions.GroupExistsException;

/**
 * No heavy error management,
 * the server ensures that the outputs are always limited 
 * to what the client has the right to do in the current context
 * @author Anagbla Jean 
 * **@goodToKnow ! FLUENT STYLE CODE*/
public class Groups {
	public static JSONObject createGroup(String name,String userId) 
			throws DatabaseException,JSONException{		
		try {
			GroupsDB.openGroup(name,userId);
			return new json.Success("Ohayo Mina sama");
		} catch (GroupExistsException e) {return new json.Error("Le groupe '"+name+"' existe deja!");}}

	public static void addMember(String groupID,String member) throws JSONException, DatabaseException {
		GroupsDB.addMember(groupID, member);}

	public static JSONObject userGroups(String userID) throws DatabaseException, JSONException{
		JSONArray jar=new JSONArray();
		DBCursor cursor = GroupsDB.userGroups(userID);
		cursor.sort(new BasicDBObject("date",-1)); 
		while (cursor.hasNext()){
			DBObject dbo=cursor.next();
			jar.put(new JSONObject()
					.put("id",dbo.get("_id"))
					.put("name",dbo.get("name"))
					.put("type","group")
					.put("owner",dbo.get("owner"))
					.put("date",dbo.get("date")));}
		return new JSONObject().put("groups",jar);}


	public static JSONObject groupMembers(String groupID) throws JSONException, DatabaseException, SQLException{
		BasicDBList members= GroupsDB.groupMembers(groupID);
		JSONArray jar = new JSONArray();
		if(members!=null)
			for(Object member : members){
				jar.put(new JSONObject()
						.put("id",(String)member)
						.put("type","member")
						.put("name",UserDao.getUserById((String)member).getFirstname()
								+" "+UserDao.getUserById((String)member).getName()));}
		return new JSONObject().put("members",jar);}  

}