package servlets;

import java.io.IOException;

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
		
		JSONObject object = null;
		System.out.println(request.getParameter("id"));
		if (request.getParameter("id") == null || request.getParameter("id").equals("")) {
			object = User.getUsersJSONProfileWhere("id", (String) session.getAttribute("userId"));
		} else {
			object = User.getUsersJSONProfileWhere("id", (String) request.getParameter("id"));
		}
		
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
