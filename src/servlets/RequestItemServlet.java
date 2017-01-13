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
 * @author ANAGBLA Joan, Wafae CHEGLAL*/
public class RequestItemServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {	
			Cookie[] cookies = request.getCookies();
			String value = "";
			for (int i = 0; i < cookies.length; i++) {
			
					  if(cookies[i].getName().equals("lang"))
					  {
						  value = cookies[i].getValue();
					  }
			}
		response.getWriter().print(Loaning.requestItem(value,
				(String)request.getSession().getAttribute("userId"),
				(String) request.getParameter("id"))
				);		
	}
}