package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.Groups;

/**
 * * @author Anagbla Jean */
public class CreateGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateGroupServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

			if( ! map.containsKey("name"))
				throw new Exception("Url is missing parameters.");

			if( request.getParameter("name").equals(""))
				throw new Exception("Url is missing parameters.");

			response.getWriter().print(Groups.createGroup(request.getParameter("name"),
					(String) session.getAttribute("userId")));
			
		}catch (Exception e) {
			e.printStackTrace(); //local debug
			response.getWriter().print(new json.Error(e.getMessage())); 
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}