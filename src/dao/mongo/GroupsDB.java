package dao.mongo;

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

/**
 * @author Anagbla Jean */									
public class GroupsDB{

	private static DBCollection collection = KasuDB.getCollection("Groups");

	public static void openGroup(String gname,int ownerID){
		collection.insert(
				new BasicDBObject()
				.append("name",gname)
				.append("owner",ownerID)
				.append("date",new Date()));}

	public static void addMember(String gid, int memberID) throws DatabaseException{
		//$addToSet do not add the item to the given field if it already contains it
		collection.update(
				new BasicDBObject()
				.append("_id",new ObjectId(gid))
				,new BasicDBObject()
				.append("$addToSet"
						,new BasicDBObject()
						.append("members",memberID)));}

	public static DBCursor userGroups(int userID) throws DatabaseException {  
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
	
	public static void main(String[] args) throws DatabaseException, JSONException{
		collection.drop();//reset : determinism required for the tests
		openGroup("amis", 1);
		openGroup("maison", 2);
		openGroup("bureau", 3);
		String id="580a89f174a4f912f56a7568";
		System.out.println(userGroups(1));
		System.out.println(groupMembers(id));
		addMember(id,12);
		System.out.println(userGroups(2));
		System.out.println(groupMembers(id));
	}

}