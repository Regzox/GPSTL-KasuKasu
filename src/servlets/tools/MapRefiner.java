package servlets.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ANAGBLA Joan */
public class MapRefiner {

	/**
	 * @description  reshape input map from Map<String,String[]> to Map<String,String>
	 * by replacing the table of string by his first element 
	 * @param patameterMap
	 * @return */
	public static Map<String,String> refine(Map<String,String[]> patameterMap) {
		Map<String,String> simpleKeyValMap = new HashMap<String, String>();
		for(Map.Entry<String,String[]> kv : patameterMap.entrySet())
			simpleKeyValMap.put(kv.getKey(), kv.getValue()[0]);
		return simpleKeyValMap;
	}
}
