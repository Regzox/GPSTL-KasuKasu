package utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONException;
import org.json.JSONObject;

public class Tools{

	/**
	 * renvoie un message
	 * @param msg
	 * @return  
	 * @throws JSONException	 */
	public static JSONObject serviceMessage(Object msg) throws JSONException{
		return new JSONObject().put("message",msg);}

	/**
	 * renvoie un message d'erreur et le code d'erreur correspondant
	 * @param msg
	 * @param errorCode
	 * @return	 */
	public static JSONObject serviceRefused(Object msg, int errorCode)throws JSONException{
		JSONObject json=new JSONObject();
		json.put("error",msg);
		json.put("erreurcode",errorCode);
		return json;}
	
	/**
	 * Return the complete StackTrace of the throwable as String 
	 * @param thr
	 * @return */
	public static String getStackTrace(Throwable thr){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		thr.printStackTrace(pw);
		return sw.toString();
	}
	

	/**
	 * Balance two words to the same length
	 * @param word1
	 * @param word2 */
	public static void balanceWordsLengths(String[]word1, String[]word2){
		if(word1.length>0 && word2.length>0)
			while(((Integer)word1[0].length()).compareTo(word2[0].length())!=0)
				if(word1[0].length()<word2[0].length())
					word1[0]+="*";
				else if(word2[0].length()<word1[0].length())
					word2[0]+="*"; 
	}
	
}