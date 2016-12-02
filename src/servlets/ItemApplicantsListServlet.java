package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.Loaning;

/**
 * * @author Anagbla Jean */
public class ItemApplicantsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemApplicantsListServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HttpSession session=request.getSession();
			if(session ==null)
			{response.getWriter().print(new json.Error("User not conected!"))
			;return;}
			if(session.getAttribute("userId") ==null){
			response.getWriter().print(new json.Error("User not conected!"));
			return;}
			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();
			response.setContentType("text/plain");
		
			if( ! map.containsKey("id"))
				throw new Exception("Url is missing parameters.");

			if( request.getParameter("id").equals(""))
				throw new Exception("Url is missing parameters.");
			
			response.getWriter().print(Loaning.itemApplicants(request.getParameter("id")));	
			
		}catch (Exception e) {//TODO ERROR HERE
			//"[object Object] parsererror SyntaxError: JSON.parse: unexpected non-whitespace character after JSON data at line 1 column 14 of the JSON data"
			e.printStackTrace(); //local debug
			response.getWriter().print(new json.Error(e.getMessage())); 
		}
	}

}