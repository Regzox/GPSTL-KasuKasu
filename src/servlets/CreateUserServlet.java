package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.User;

/**
 * * @author Anagbla Jean */
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	public CreateUserServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//TODO CREATE SESSION AS FIRST CONNECTION  

			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();
			response.setContentType("text/plain");

			if( !(map.containsKey("email") && map.containsKey("mdp")    
					&& map.containsKey("nom") && map.containsKey("prenom")  
					&& map.containsKey("numero"))) 
				throw new Exception("Url is missing parameters.");

			if( request.getParameter("email").equals("")
					|| request.getParameter ("mdp").equals("")
					|| request.getParameter ("nom").equals("")
					|| request.getParameter ("prenom").equals("")
					|| request.getParameter ("numero").equals(""))
				throw new Exception("Url is missing parameters.");

			JSONObject json = User.createUser(
					request.getParameter ("email"),
					request.getParameter ("mdp"),
					request.getParameter ("nom"), 
					request.getParameter ("prenom"),
					request.getParameter("numero"));

			entities.User user = User.getUser(request.getParameter ("email"));

			request.getSession().setAttribute("userId", user.getId());

			response.getWriter().print(json);
		}				

		catch (Exception e) {//TODO ERROR HERE indic : send empty json or dont send any json
			//"[object Object] parsererror SyntaxError: JSON.parse: unexpected non-whitespace character after JSON data at line 1 column 14 of the JSON data"
			e.printStackTrace(); //local debug
			response.getWriter().print(new json.Error(e.getMessage())); 
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}