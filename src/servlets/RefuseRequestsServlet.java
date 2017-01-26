package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Loaning;
import servlets.tools.templates.online.OnlinePostServlet;

/**
 * @author ANAGBLA Joan */
public class RefuseRequestsServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"applicant","item"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {		 
		Cookie[] cookies = request.getCookies();
		String lang = "";
		for (int i = 0; i < cookies.length; i++) 
			if(cookies[i].getName().equals("lang"))
				lang = cookies[i].getValue();
		
		response.getWriter().print(Loaning.refuseRequests(
				(String) request.getParameter("applicant"),
				(String) request.getParameter("item"),
				lang
				)
				);
	}
}