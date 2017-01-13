package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import services.User;
import servlets.tools.ServletToolBox;
import servlets.tools.templates.offline.OfflinePostServlet;

public class ConnectUserServlet extends OfflinePostServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"mail","pass"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		JSONObject js=new JSONObject();
		String mail = request.getParameter("mail");
		String pass = request.getParameter("pass");
		boolean verified = false;

		entities.User user = null;			 
		user = User.getUser(mail);
		verified = (user.getPassword().compareTo(ServletToolBox.md5(pass)) == 0);

		if(verified)
			if(User.isBanned(user.getId())){
				js.put("error", "You have been permanently banned");
				response.getWriter().print(js);
			}else if (User.isFrozen(user.getId())){
				js.put("error", "You have been suspended temporarily");
				response.getWriter().print(js);
			}else if(User.isConfirmed(user.getId())){
				//init session
				HttpSession session=request.getSession();
				request.getSession().setMaxInactiveInterval(3600*24);//24 inactive hours before session invalidation
				session.setAttribute("userId", user.getId());
				String ia="false";
				if(User.isAdmin(user.getId()))
					ia="true";
				session.setAttribute("isAdmin",ia);

				//send cookie
				Cookie cookieId = new Cookie("userId", user.getId());
				response.addCookie(cookieId);

				//js.put("response", 1);
				response.getWriter().print(js);
				//response.sendRedirect(Url.SEARCHITEMS.value());
				//response.sendRedirect("/KasuKasu/dashboard.jsp");
				System.out.println("User connexion successfull");
			}else
				response.getWriter().print(js.put("error", "User not confirmed"));
		else{
			js.put("error", "Wrong mail or Password...");
			response.getWriter().print(js);
		}	
	}	 
}