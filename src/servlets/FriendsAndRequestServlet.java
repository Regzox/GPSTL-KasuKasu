package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.Friends;
import services.User;
import servlets.tools.templates.online.OnlineGetServlet;

/**
 * Servlet implementation class RetrieveUsersServlet
 */
public class FriendsAndRequestServlet extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"typeOfRequest"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		HttpSession session=request.getSession();
		JSONObject js=new JSONObject();
		String userId = (String) session.getAttribute("userId");
		String typeOfRequest = (String) request.getParameter("typeOfRequest");
		if(userId==null)
			js.put("error", "Not connected, no id found");
		else if(typeOfRequest==null)
			js.put("error", "No type of request specified");
		else{
			switch(Integer.parseInt(typeOfRequest)){
			case 1 :
				js.put("result", User.getUsersJSONProfileFromIds(Friends.myFriendsArray(userId)));
				js.put("success", "Friends List");
				break;
			case 2 : // removeFriend
				js.put("result", User.getUsersJSONProfileFromIds(Friends.pendingRequestsArray(userId)));
				js.put("success", "Pending Requests List");
				break;
			default:
				js.put("error", "type of request unknown");
			}
			response.getWriter().print(js);
		}
	 		
	}

			

}
