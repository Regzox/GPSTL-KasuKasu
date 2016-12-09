package servlets;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.User;
import servlets.tools.templates.online.OnlineGetServlet;
import json.Error;
import json.Success;
import json.Warning;

public class UserProfileServlet extends OnlineGetServlet {
	private static final long serialVersionUID = -4160552536218426991L;
	
	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		HttpSession session = request.getSession();
		JSONObject object = null;
		
		//System.out.println(request.getParameter("id"));
		
		if (request.getParameter("id") == null || request.getParameter("id").equals("")) {
			object = User.getUsersJSONProfileWhere("_id", (String) session.getAttribute("userId"));
		} else {
			object = User.getUsersJSONProfileWhere("_id", (String) request.getParameter("id"));
		}
		
		if (object instanceof Error || object instanceof Warning)
			response.getWriter().println(object);
		else
			response.getWriter().println(new Success(object));
	}			 
}