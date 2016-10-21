package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.User;
import json.Error;
import json.Success;
import json.Warning;

public class UserProfileServlet extends HttpServlet {

	private static final long serialVersionUID = -4160552536218426991L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws 
			ServletException,
			IOException
	{	
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("userId"));
		if (session.getAttribute("userId") == null) {
			response.getWriter().print(new Error("No session !"));
			return;
		}
		
//		@SuppressWarnings("unchecked")
//		Map<String, String> map = request.getParameterMap();
//		
//		if (!map.containsKey("email")) {
//			response.getWriter().print(new Error("The request parameters map doesn't contains the key 'email'"));
//			return;
//		}
		
		JSONObject object = User.getUsersJSONProfileWhere("id", (String) session.getAttribute("userId"));
		//object = User.filterUserPassword(object);
		
		if (object instanceof Error || object instanceof Warning)
			response.getWriter().println(object);
		else
			response.getWriter().println(new Success(object));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
