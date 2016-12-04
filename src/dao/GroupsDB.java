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
	/*
	 * TODO  suppr des membres a un groupe
	 */

	private static DBCollection collection = KasuDB.getMongoCollection("groups");

	public static void openGroup(String gname,String ownerID) throws GroupExistsException{
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

	public static void updateGroupName(String gid,String ownerID, String gname)
			throws NotPermitedException{
		DBCursor dbc = collection.find(new BasicDBObject()
				.append("name",gname)
				.append("owner",ownerID));
		if(!dbc.hasNext())
			throw new NotPermitedException
			("Vous n'avez pas le droit de modifier ce groupe!");
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(gid))
				,new BasicDBObject()
				.append("name",gname));}

	public static void closeGroup(String gname,String ownerID) throws NotPermitedException{
		DBCursor dbc = collection.find(new BasicDBObject()
				.append("name",gname)
				.append("owner",ownerID));
		if(!dbc.hasNext())
			throw new NotPermitedException
			("Vous n'avez pas le droit de supprimer ce groupe!");
		collection.remove(
				new BasicDBObject()
				.append("name",gname)
				.append("owner",ownerID)
				);}

	public static void addMember(String gid, String memberID){
		//$addToSet do not add the item to the given field if it already contains it
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(gid))
				,new BasicDBObject()
				.append("$addToSet"
						,new BasicDBObject()
						.append("members",memberID)));}
	
	public static void removeMember(String gid, String memberID){
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(gid))
				,new BasicDBObject()
				.append("$pull"
						,new BasicDBObject()
						.append("members",memberID)));}

	public static DBCursor userGroups(String userID) {  
		return collection.find(
				new BasicDBObject()
				.append("owner",userID));}

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
		openGroup("amis", "1");
		openGroup("maison", "2");
		openGroup("bureau", "3");
		String id="580a89f174a4f912f56a7568";
		System.out.println(userGroups("1"));
		System.out.println(groupMembers(id));
		addMember(id,"12");
		System.out.println(userGroups("2"));
		System.out.println(groupMembers(id));
	}

}