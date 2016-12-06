package services;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dao.GroupsDB;
import dao.users.UserDao;
import exceptions.DatabaseException;
import exceptions.GroupExistsException;
import exceptions.NotPermitedException;
import utils.Tools;

/**
 * No heavy error management,
 * the server ensures that the outputs are always limited 
 * to what the client has the right to do in the current context
 * @author Anagbla Jean 
 * **@goodToKnow ! FLUENT STYLE CODE*/
public class Groups {

	/**
	 * Create a new group in db
	 * @param gname
	 * @param ownerID
	 * @throws GroupExistsException */
	public static JSONObject createGroup(String name,String userId) 
			throws DatabaseException,JSONException{		
		try {
			GroupsDB.createGroup(name,userId);
			return new json.Success("Ohayo Mina sama");
		} catch (GroupExistsException e) {return new json.Error("Le groupe '"+name+"' existe deja!");}}


	/**
	 * Update the group name
	 * @param gid
	 * @param ownerID
	 * @param gname
	 * @throws NotPermitedException */
	public static JSONObject updateGroupName(String gid,String ownerID, String gname)
			throws JSONException{
		if(!GroupsDB.checkAthorization(ownerID,gid))
			return Tools.serviceRefused
					("Vous n'avez pas le droit de modifier ce groupe!", -1);
		GroupsDB.updateGroupName(gid, ownerID, gname);
		return Tools.serviceMessage(1);
	}


	/**
	 * Delete the group
	 * @param gid
	 * @param ownerID
	 * @return 
	 * @throws NotPermitedException 
	 * @throws JSONException */
	public static JSONObject deleteGroup(String gid,String ownerID) throws NotPermitedException, JSONException{
		if(!GroupsDB.checkAthorization(ownerID,gid))
			return Tools.serviceRefused
					("Vous n'avez pas le droit de supprimer ce groupe!", -1);
		GroupsDB.deleteGroup(gid, ownerID);
		return Tools.serviceMessage(1);
	}


	/**
	 * Add a new member to the group
	 * @param gid
	 * @param member*/
	public static JSONObject addMember(String gid,String ownerID,String member) throws JSONException, DatabaseException {
		System.out.println("gid : "+gid+" member : "+member+" owner :"+ownerID);//Debug
		if(!GroupsDB.checkAthorization(ownerID,gid))			
			return new json.Error
					("Vous n'avez pas le droit de modifier ce groupe!");
		if(member.equals(GroupsDB.groupOwner(gid))){			
			return new json.Error
					("Vous ne pouvez pas vous ajouter dans votre propre groupe");}
		GroupsDB.addMember(gid, member);
		return new json.Success("LOL");}


	/**
	 * Remove a member from the group
	 * @param gid
	 * @param memberID 
	 * @throws JSONException */
	public static JSONObject removeMember(String gid,String ownerID, String memberID) throws JSONException{
		if(!GroupsDB.checkAthorization(ownerID,gid))
			return Tools.serviceRefused
					("Vous n'avez pas le droit de modifier ce groupe!", -1);
		GroupsDB.removeMember(gid, memberID);
		return Tools.serviceMessage(1);
	}


	/**
	 * Return the list of user's groups
	 * @param userID
	 * @return */
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
	
	
	public void name() {
		BasicDBObject javaToMongo = new BasicDBObject();
		BasicDBObject MongoToJava = new BasicDBObject();
		
		MongoToJava
		.append("_id", "id")
		.append("name", "name")
		.append("owner", "owner")
		.append("owner", "owner")
		.append("date", "date");
		
		javaToMongo
		.append("id", "_id")
		.append("name", "name")
		.append("owner", "owner")
		.append("owner", "owner")
		.append("date", "date");
		
			
	}
	


	/**
	 * Return the list of group's members
	 * @param gid
	 * @return */
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