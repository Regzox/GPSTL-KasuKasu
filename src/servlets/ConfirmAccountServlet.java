package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import enumerations.Url;
import services.User;
import servlets.tools.templates.offline.OfflinePostServlet;

/**
 * * @author Anagbla Jean */
public class ConfirmAccountServlet extends OfflinePostServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		String uid = request.getParameter ("id");
		User.confirmUser(uid);
		//init session
		HttpSession session=request.getSession();
		request.getSession().setMaxInactiveInterval(3600*24);//24 inactive hours before session invalidation
		session.setAttribute("userId", uid);
		//send cookie
		Cookie cookieId = new Cookie("userId", uid);
		response.addCookie(cookieId);
		//redirect to dashboard
		response.sendRedirect(Url.DASHBOARD.value());		
	}
}