package dao.mapreduce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import dao.users.UserDao;

public class QueryFinder {
	
	/**
	 * Generic query finder Facebook style
	 * @param query
	 * @return */
	public static Iterable<DBObject> find(DBCollection collection,List<String> queryWords,List<String> fields, 
			int max_indulgence, BasicDBObject constraints,int limit){		
		System.out.println("UserDao/find -> "+queryWords);//debug

		BasicDBObject criterion = new BasicDBObject();
		for(Map.Entry<String,Object> constraint : constraints.entrySet())
			criterion.append(constraint.getKey(),constraint.getValue());		

		//misspellings possibilities with i mistakes by word		
		Map<Integer,List<Pattern>> levels_of_indulgence = new HashMap<>();
		for(String word : queryWords)
			for(int i=0;i<=max_indulgence;i++)
				levels_of_indulgence.put(i, Arrays.asList(
						Pattern.compile(
								"^"+word,Pattern.CASE_INSENSITIVE))); //TODO ph call

		BasicDBList criteria = new BasicDBList();
		BasicDBList conds = new BasicDBList();

		for(int i : levels_of_indulgence.keySet())
			for(String field : fields){
				BasicDBObject criterioni=((BasicDBObject)criterion.clone())
						.append(field,
								new BasicDBObject("$in",
										levels_of_indulgence.get(i)));
				criteria.add(criterioni);				

				BasicDBList eqcomptab = new BasicDBList();
				eqcomptab.add("$$pattern");eqcomptab.add("$"+field);
				criterioni =((BasicDBObject)criterion.clone());
				criterioni.append("$anyElementTrue",
						new BasicDBObject("$map",
								new BasicDBObject()
								.append("input",queryWords)//levels_of_indulgence.get(i)//not working with regex
								.append("as","pattern")
								.append("in",
										new BasicDBObject("$eq",eqcomptab))));				
				BasicDBObject cond = new BasicDBObject("$cond", 
						new BasicDBObject()
						.append("if",criterioni)
						.append("then",i)
						.append("else",-1)); //TODO do ka nan?
				conds.add(cond);
			}

		System.out.println("criteria="+criteria);//debug 
		System.out.println("conds="+conds);//debug

		Iterable<DBObject> output = collection.aggregate(Arrays.asList(
				(DBObject) new BasicDBObject("$match",
						new BasicDBObject("$or",criteria)), //match entire criteria
				(DBObject) new BasicDBObject("$limit",limit),//early limit number of processing documents 
				(DBObject) new BasicDBObject("$project", 
						new BasicDBObject("pertinence",
								new BasicDBObject("$add",conds))
								.append("nom", 1).append("prenom", 1)
								.append("email", 1).append("numero", 1)),
				new BasicDBObject("$group",
						new BasicDBObject("_id","$pertinence")
				.append("users", new BasicDBObject("$push","$$ROOT")))))
				.results();
		return output;


		//TODO match all exact  
		//TODO match 1 exact
		//TODO y exacts x approx
		//TODO y exacts x approx
	}


	/**
	 * local test
	 * @param args */
	public static void main(String[] args){
		for(DBObject dbObject : 
			find(UserDao.collection, Arrays.asList("tutanck","amont"), 
					Arrays.asList("nom","prenom"), 0, new  BasicDBObject(), 1)
			)System.out.println(dbObject);
	}


}
