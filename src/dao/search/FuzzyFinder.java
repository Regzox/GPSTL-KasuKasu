package dao.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import dao.users.UserDao;
import exceptions.DatabaseException;

/**Generic query finder '"Facebook"' search engine's style 
 * (strict but open to the end of the word) (but much more fuzzyfied 
 * (NOT REALLY IMPRESSIVE))
 * @author AJoan */
public class FuzzyFinder {

	/**Find in "in" collection the set of words (contained in wordsSet)
	 * And return a sorted list of results according some "constraints" and
	 * inFields (fields of database on which search is done)
	 * @param query
	 * @return */
	public static List<ObjetRSV> find(DBCollection in, Set<String> wordsSet,
			int fuzzyness,	List<String> inFields, BasicDBObject constraints){		
		System.out.println("FuzzyFinder/find -> "+in+" "+wordsSet+" "+fuzzyness+" "+inFields+" "+constraints);//debug

		BasicDBObject criteria = new BasicDBObject();
		for(Map.Entry<String,Object> constraint : constraints.entrySet())
			criteria.append(constraint.getKey(),constraint.getValue());	

		List<Pattern> nouns = new ArrayList<>();
		for(String word : wordsSet)	
			nouns.add(Pattern.compile(PatternsHolder.fuzzyfy(word, fuzzyness)
					, Pattern.CASE_INSENSITIVE));

		BasicDBList orlist = new BasicDBList(); 
		for(String field : inFields)
			orlist.add(new BasicDBObject(field, new BasicDBObject("$in", nouns)));
		criteria.append("$or", orlist);

		System.out.println("\ncriteria="+criteria+"\n");//debug 

		return pertinence(wordsSet, in.aggregate(Arrays.asList(
				(DBObject) new BasicDBObject("$match",criteria)
				//debug TODO comment 
				//,(DBObject) new BasicDBObject("$project",new BasicDBObject("nom",1).append("prenom",1).append("_id", 0))
				))
				.results(), inFields);
	}


	/**Get Iterable database input and return a relevant list of ObjectRSV   
	 * @param query
	 * @return
	 * @throws DatabaseException
	 * @throws JSONException */
	private static List<ObjetRSV> pertinence(Set<String> wordsSet,
			Iterable<DBObject> input, List<String>inFields){
		System.out.println("pertinence input : "+input);
		Map<Integer,List<ObjetRSV>> groups = new HashMap<>();		
		for(DBObject doc : input){			
			int min=Integer.MAX_VALUE;
			int mac=0;//minimum accumulator 
			System.out.println("doc("+doc+")");//debug
			List<Integer> minimums= new ArrayList<>();
			for(String infield : inFields){
				min=Integer.MAX_VALUE;
				for(String word : wordsSet){
					String field=(String)doc.get(infield);
					int ld = LevenshteinDistance.LD(word,field);
					System.out.print("LD("+word+" , "+field+")="+ld+"  ");//debug
					min = ld<min ? ld : min ;
				}
				minimums.add(min);
				mac+=min;//(minimum of summing words minimum for each field)
				System.out.print("--> [min="+min+" & mac="+mac+"]|	 ");//debug
			}
			Collections.sort(minimums);
			int classs=minimums.get(0);
			System.out.println("minLD("+doc+")="+classs+" & mac="+mac+" minimums="+minimums+"\n");//debug
			if(groups.get(classs)==null)
				groups.put(classs,new ArrayList<>());
			groups.get(classs).add(new ObjetRSV(doc,mac));
		}
		List<ObjetRSV>relevant=new ArrayList<>();
		//using sorted groups is efficient only for 1-2 fields no more 
		for(Map.Entry<Integer,List<ObjetRSV>> me : groups.entrySet()){
			System.out.println("relevants["+me.getKey()+"] : ");//debug
			Collections.sort(me.getValue());
			relevant.addAll(me.getValue());
			for(ObjetRSV o : me.getValue())	System.out.println(o);//Debug
		}
		return relevant;
	}


	/**
	 * local test
	 * @param args */
	public static void main(String[] args){
		find(UserDao.collection,PatternsHolder.wordSet("ttanck", PatternsHolder.blank), 2,
				Arrays.asList("nom","prenom"),	new  BasicDBObject());
	}

}