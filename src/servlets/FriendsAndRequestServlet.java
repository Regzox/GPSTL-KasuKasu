package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.Friends;
import services.User;

/**
 * Servlet implementation class RetrieveUsersServlet
 */
public class FriendsAndRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FriendsAndRequestServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			HttpSession session=request.getSession();
			response.setContentType("text/html");
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
					js.put("success", "Friend added");
					break;
				case 2 : // removeFriend
					js.put("result", User.getUsersJSONProfileFromIds(Friends.pendingRequestsArray(userId)));
					js.put("success", "Friend removed");
					break;
				default:
					js.put("error", "type of request unknown");
				}
				response.getWriter().print(js);
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
