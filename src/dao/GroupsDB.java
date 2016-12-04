package dao;

import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import kasudb.KasuDB;
import exceptions.DatabaseException;
import exceptions.GroupExistsException;
import exceptions.NotPermitedException;

/**
 * @author Anagbla Jean */									
public class GroupsDB{

	private static DBCollection collection = KasuDB.getMongoCollection("groups");

	/**
	 * Create a new group in db
	 * @param gname
	 * @param ownerID
	 * @throws GroupExistsException */
	public static void createGroup(String gname,String ownerID) throws GroupExistsException{
		DBCursor dbc = collection.find(new BasicDBObject()
				.append("name",gname)
				.append("owner",ownerID));
		if(dbc.hasNext())
			throw new GroupExistsException();
		collection.insert(
				new BasicDBObject()
				.append("name",gname)
				.append("owner",ownerID)
				.append("date",new Date()));}

	
	/**
	 * Check if user's right on the object   
	 * @param userId
	 * @param id
	 * @return */
	public static boolean checkAthorization(String userId,String gid) {
		return  collection.find(new BasicDBObject()
				.append("_id",gid)
				.append("owner",userId)).hasNext();
	}
	
	
	/**
	 * Update the group name
	 * @param gid
	 * @param ownerID
	 * @param gname
	 * @throws NotPermitedException */
	public static void updateGroupName(String gid,String ownerID, String gname){	
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(gid))
				,new BasicDBObject()
				.append("name",gname));}

	/**
	 * Delete the group
	 * @param gid
	 * @param ownerID
	 * @throws NotPermitedException */
	public static void deleteGroup(String gid,String ownerID) throws NotPermitedException{
		collection.remove(
				new BasicDBObject()
				.append("name",gid)
				.append("owner",ownerID)
				);}

	/**
	 * Add a new member to the group
	 * @param gid
	 * @param memberID */
	public static void addMember(String gid, String memberID){
		//$addToSet do not add the item to the given field if it already contains it
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(gid))
				,new BasicDBObject()
				.append("$addToSet"
						,new BasicDBObject()
						.append("members",memberID)));}
	
	
	/**
	 * Remove a member from the group
	 * @param gid
	 * @param memberID */
	public static void removeMember(String gid, String memberID){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(gid))
				,new BasicDBObject()
				.append("$pull"
						,new BasicDBObject()
						.append("members",memberID)));}

	
	/**
	 * Return the list of user's groups
	 * @param userID
	 * @return */
	public static DBCursor userGroups(String userID) {  
		return collection.find(
				new BasicDBObject()
				.append("owner",userID));}

	/**
	 * Return the list of group's members
	 * @param gid
	 * @return */
	public static BasicDBList groupMembers(String gid) { 
		DBObject dbo = collection.findOne(
				new BasicDBObject().append("_id",new ObjectId(gid)));
		if(dbo!=null)
			return (BasicDBList) dbo.get("members");
		return null;
	}

	/**
	 * ADMIN FUNCTION
	 * @param userID
	 * @return
	 * @throws DatabaseException */
	public static DBCursor userGroupsMembership(String userID) {  			
		return collection.find(
				new BasicDBObject()
				.append("members",userID));}


	public static void main(String[] args) throws DatabaseException, JSONException, GroupExistsException{
		collection.drop();//reset : determinism required for the tests
		createGroup("amis", "1");
		createGroup("maison", "2");
		createGroup("bureau", "3");
		String id="580a89f174a4f912f56a7568";
		System.out.println(userGroups("1"));
		System.out.println(groupMembers(id));
		addMember(id,"12");
		System.out.println(userGroups("2"));
		System.out.println(groupMembers(id));
	}

}