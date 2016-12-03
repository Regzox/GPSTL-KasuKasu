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
		json.put("erreur",msg);
		json.put("erreurcode",errorCode);
		return json;}
	
	/**
	 * Return the current timestamp
	 * @return */
	public static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date now = new java.util.Date();
		return new java.sql.Timestamp(now.getTime());
	}

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
	
}