package servlets;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.SimpleTimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Loaning;
import servlets.tools.templates.online.OnlinePostServlet;

/**
 * @author ANAGBLA Joan, Giuseppe FEDERICO, Wafae CHEGLAL*/
public class RequestItemServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id", "debut", "fin"}));
		
	}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {	
			Cookie[] cookies = request.getCookies();
			String lang = "";
			for (int i = 0; i < cookies.length; i++) 
					  if(cookies[i].getName().equals("lang"))
						  lang = cookies[i].getValue();
			
			
			// Parse begin/end dates from the request.
	        SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
	        parser.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
	        Date date_debut = parser.parse( request.getParameter("debut") );
	        Date date_fin = parser.parse( request.getParameter("fin") );
			
		response.getWriter().print(
				Loaning.requestItem(lang, 
						(String)request.getSession().getAttribute("userId"),
						(String) request.getParameter("id"),
						date_debut, 
						date_fin
						)
				);		
	}
}