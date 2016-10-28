package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.Friends;

public class FriendsManagementServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try{
			JSONObject jsResponse = new JSONObject();
			HttpSession session=request.getSession();
			response.setContentType("text/html");
			String userId = (String) session.getAttribute("userId");
			String otherId = (String) request.getParameter("id");
			String typeOfRequest = (String) request.getParameter("typeOfRequest");
			if(otherId == null){
				jsResponse.put("error", "ID of the other user is empty or null");
			}else if(typeOfRequest.isEmpty() || typeOfRequest == null){
				jsResponse.put("error", "No type of request specified");
			}else{
				switch(Integer.parseInt(typeOfRequest)){
				case 1 : // addFriend
					Friends.addFriend(userId,otherId);
					Friends.removeRequest(otherId, userId);
					Friends.removeRequest(userId, otherId);
					jsResponse.put("success", "Friend added");
					break;
				case 2 : // removeFriend
					Friends.removeFriend(userId,otherId);
					jsResponse.put("success", "Friend removed");
					break;
				case 3 : // addRequest
					// From -> To
					if(!Friends.areFriends(userId, otherId)){
						Friends.addRequest(userId,otherId);
						jsResponse.put("success", "Friend added");
					}else{
						jsResponse.put("success", "Already Friend");
					}
					break;
				case 4 : // removeRequest
					//From -> To
					Friends.removeRequest(otherId, userId);
					break ;
				default:
					jsResponse.put("error", "type of request unknown");
				}
			}
			response.getWriter().print(jsResponse);
		}catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);
		}
	}
}
