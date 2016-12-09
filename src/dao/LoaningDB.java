package dao;

import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import dao.items.ItemsDB;
import exceptions.DatabaseException;
import kasudb.KasuDB;

/**
 * @author ANAGBLA Joan, Celien Creminon, Wafae Cheglal, Ouiza Bouyahiaoui */
public class LoaningDB {

	public static DBCollection collection = KasuDB.getMongoCollection("loaning");//LOANING_REQUESTS
	public static DBCollection requests = KasuDB.getMongoCollection("lrqst");
	public static DBCollection items = KasuDB.getMongoCollection("items");

	/**
	 * Add an applicant's request for an item 
	 * @param idApplicant
	 * @param idItem */
	public static void requestItem(String idApplicant,String idItem){
		requests.insert(
				new BasicDBObject()
				.append("id_applicant", idApplicant)
				.append("id_item", idItem)
				.append("when", new Date())
				.append("debut", "") //TODO
				.append("fin", "")//TODO
				);
	}


	/**
	 * ADMIN FUNCTION
	 * Check if a request is already sent for an item by the same applicant 
	 * @param idApplicant
	 * @param idItem */
	public static boolean requestExists(String idApplicant,String idItem){
		return requests.find(
				new BasicDBObject()
				.append("id_applicant", idApplicant)
				.append("id_item", idItem)			
				).hasNext();
	}


	/**
	 * Accept an applicant's request for an item
	 * @param idRequest 
	 * @throws DatabaseException */
	public static void acceptRequests(String idApplicant, String idItem) throws DatabaseException{
		//System.out.println("idApplicant : " + idApplicant+" idItem : "+idItem);//debug
		DBCursor dbc = requests.find(
				new BasicDBObject()
				.append("id_item", idItem)
				.append("id_applicant", idApplicant)
				);
		if(dbc.count()!=1)
			throw new DatabaseException
			("LoaningDB : Database is inconsistent between : loaning <-> lrqst");
		DBObject dbo= dbc.next();
		collection.insert(
				new BasicDBObject()
				.append("id_applicant", dbo.get("id_applicant"))
				.append("id_item", dbo.get("id_item"))
				.append("when", new Date())
				.append("debut", "") //TODO
				.append("fin", "")//TODO
				);
		requests.remove(
				new BasicDBObject()
				.append("_id", new ObjectId(dbo.get("_id").toString()))
				);
		ItemsDB.setItemStatus(idItem,"borrowed");
	}


	/**
	 * Refuse an applicant's request for an item
	 * @param idRequest */
	public static void refuseRequests(String idApplicant, String idItem){
		requests.remove(
				new BasicDBObject()
				.append("id_applicant", idApplicant)
				.append("id_item", idItem)
				);
	}


	/**
	 * List all the current applicant's requests
	 * @param idApplicant
	 * @return */
	public static DBCursor applicantRequests(String idApplicant){
		return requests.find(
				new BasicDBObject()
				.append("id_applicant", idApplicant));
	}

	
	/**
	 * List all the objects borrowed by a user
	 * @param idApplicant
	 * @return */
	public static DBCursor applicantLoanings(String idApplicant){
		return collection.find(
				new BasicDBObject()
				.append("id_applicant", idApplicant));
	}
	
	
	public static DBCursor itemApplicants(String id_item){
		return requests.find(
				new BasicDBObject()
				.append("id_item", id_item));
	}
	
	/**
	 * Remove an loan by removing from the collection "loanings" and setting up to 'available' the item referenced in this one
	 * 
	 * TODO Push the removed loan to another saving collection
	 * 
	 * @param id_loan
	 */
	
	public static void removeLoan(String id_loan) {
		System.out.println(id_loan);
		DBCursor dbc = collection.find(new BasicDBObject("_id", new ObjectId(id_loan)));
		DBObject loan = dbc.next();
		if (dbc.count() == 1) {
			collection.remove(loan);
			BasicDBObject requestedItem = new BasicDBObject().append("_id", new ObjectId((String)loan.get("id_item")));
			System.out.println("LAON ID : " + loan.get("id_item"));
			dbc = items.find(requestedItem);
			requestedItem = (BasicDBObject) dbc.next(); //TODO FIX NoSuchElementException
			requestedItem.append("status", "available");
			items.update(new BasicDBObject().append("_id", new ObjectId(requestedItem.getString("_id"))), requestedItem);
		}
	}

	public static void main(String[] args) {
		requestItem("1","idObject");
		System.out.println(applicantRequests("5849a585641a80878d717279").next());
		System.out.println(applicantLoanings("5849a585641a80878d717279").next());
	}
}