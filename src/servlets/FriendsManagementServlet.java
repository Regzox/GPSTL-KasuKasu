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
import servlets.tools.templates.online.OnlinePostServlet;

public class FriendsManagementServlet extends OnlinePostServlet{
	private static final long serialVersionUID = 1L;


	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id","typeOfRequest"}));}
	
		@Override
		public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
				throws Exception {	
			JSONObject jsResponse = new JSONObject();
			HttpSession session=request.getSession();
 			String userId = (String) session.getAttribute("userId");
			String otherId = (String) request.getParameter("id");
			String typeOfRequest = (String) request.getParameter("typeOfRequest");
			if(otherId == null){
				jsResponse.put("error", "ID of the other user is empty or null");
			}else if(typeOfRequest.isEmpty() || typeOfRequest == null){
				jsResponse.put("error", "No type of request specified");
			}else if(userId == otherId){
				jsResponse.put("error", "You can't request or add yourself as a Friend");
			}else{
				switch(Integer.parseInt(typeOfRequest)){
				case 1 : // addFriend
					if(!Friends.areFriends(userId, otherId)){
						if(Friends.isPending(otherId,userId)){
							Friends.addFriend(userId,otherId);
							Friends.removeRequest(otherId, userId);
							Friends.removeRequest(userId, otherId);
							jsResponse.put("success", "Friend added");
							response.sendRedirect("/KasuKasu/restricted/pendingrequests.jsp");
						}else{
							jsResponse.put("success", "You don't have any request from this user");
							response.sendRedirect("/KasuKasu/restricted/pendingrequests.jsp");
						}
					}else{
						jsResponse.put("success", "Already Friend");
						response.sendRedirect("/KasuKasu/restricted/pendingrequests.jsp");
					}
					break;
				case 2 : // removeFriend
					Friends.removeFriend(userId,otherId);
					jsResponse.put("success", "Friend removed");
					response.sendRedirect("/KasuKasu/restricted/myfriends.jsp");
					break;
				case 3 : // addRequest
					// From -> To
					if(!Friends.areFriends(userId, otherId)){
						Friends.addRequest(userId,otherId);
						jsResponse.put("success", "Friend request sent");
						response.sendRedirect("/KasuKasu/restricted/finduser.jsp");
					}else{
						jsResponse.put("success", "Already Friend");
						response.sendRedirect("/KasuKasu/restricted/finduser.jsp");
						
					}
					break;
				case 4 : // removeRequest
					//From -> To
					Friends.removeRequest(otherId, userId);
					jsResponse.put("success","Request removed");
					response.sendRedirect("/KasuKasu/restricted/pendingrequests.jsp");
					break ;
				default:
					jsResponse.put("error", "type of request unknown");
				}
			}
			response.getWriter().print(jsResponse);
		 
		}
}
