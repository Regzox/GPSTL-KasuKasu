package servlets.tools;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;

/**
 * @author ANAGLA Joan */
public class ServletToolBox {

	/**
	 * *GENERIC MANAGEMENT OF URL RELATED ERRORS AND SESSION RELATED ERRORS 
	 * *ADAPTATION OF  THE URL's PARAMETERS TO SERVICE METHODS 
	 * (ParameterMap's refinement)
	 * 		We choose to check the presence of "parametersNames" we are entitled
	 * 		to expect in the incoming url.
	 * @param request
	 * @param response
	 * @param epn
	 * @return
	 * @throws IOException
	 * @throws JSONException */
	public static Map<String, String> beforeOnlineBusiness(HttpServletRequest request,
			HttpServletResponse response,Set<String> epn) 
					throws IOException, JSONException {
		response.setContentType("text/plain");

		HttpSession session = request.getSession(false);
		if(session==null){
			response.getWriter().print(new json.Error("User not conected!"))
			;return null;}
		else if (session.getAttribute("userId")==null){
			response.getWriter().print(new json.Error("User not conected!"))
			;return null;}

		@SuppressWarnings("unchecked")
		Map<String,String>params=MapRefiner.refine(request.getParameterMap());	
 
		for(String expected : epn){
			if(!params.containsKey(expected)){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL MISUSED");
				return null;}
			if(request.getParameter(expected).equals("")){ 
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL MISUSED");
				return null;}}
		return params;}


	/**
	 * *GENERIC MANAGEMENT OF URL RELATED ERRORS AND SESSION RELATED ERRORS 
	 * *ADAPTATION OF  THE URL's PARAMETERS TO SERVICE METHODS 
	 * (ParameterMap's refinement)
	 * 		We choose to check the presence of "parametersNames" we are entitled
	 * 		to expect in the incoming url.
	 * @param request
	 * @param response
	 * @param epn
	 * @return
	 * @throws IOException
	 * @throws JSONException */
	public static Map<String, String> beforeOfflineBusiness(HttpServletRequest request,
			HttpServletResponse response,Set<String> epn) 
					throws IOException, JSONException {
		response.setContentType("text/plain");

		HttpSession session = request.getSession(false);
		if(session!=null)
			if (session.getAttribute("userId")!=null){
				response.getWriter().print(new json.Error("User already conected!"))
				;return null;}

		@SuppressWarnings("unchecked")
		Map<String, String>params=MapRefiner.refine(request.getParameterMap());	

		for(String expected : epn){
			if(!params.containsKey(expected)){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL MISUSED");
				return null;}
			if(request.getParameter(expected).equals("")){ 
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL MISUSED");
				return null;}}
		return params;}


	public static String md5(String input) {

		String md5 = null;

		if(null == input) return null;

		try {

			//Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");

			//Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());

			//Converts message digest value in base 16 (hex) 
			md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}


}