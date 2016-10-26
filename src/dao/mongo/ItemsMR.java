package dao.mongo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import kasudb.KasuDB;

public class ItemsMR {

	public static DBCollection collection = KasuDB.getCollection("Items");

	public static List<ObjetRSV> search(String query){
		DBCursor c=collection.find();
		List li = new ArrayList<ObjetRSV>();
		while(c.hasNext()){
			DBObject dbo=c.next();
			li.add(new ObjetRSV(dbo, 0));
		}
		return li;
	}

}
