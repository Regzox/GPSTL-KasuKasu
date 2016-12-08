package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Loaning;
import services.User;
import servlets.tools.templates.online.OnlinePostServlet;
import utils.SendEmail;

/**
 * @author ANAGBLA Joan, Wafae CHEGLAL*/
public class RequestItemServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;
	private static String to;
	private static String subject;
	private static String contenu;
	
	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {		 
		response.getWriter().print(Loaning.requestItem(
				(String)request.getSession().getAttribute("userId"),
				(String) request.getParameter("id"))
				);
		to = User.getUserById((String)request.getSession().getAttribute("userId")).getEmail();
		subject ="Demande d'emprunt";
		contenu ="Vous avez une demande d'emprunt, Merci de checker ton compte";
		SendEmail.sendMail(to, subject, contenu);

		
	}
}