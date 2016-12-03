package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enumerations.Url;
import services.User;
import servlets.tools.templates.online.OnlinePostServlet;

/**
 * * @author Anagbla Jean */
public class ConfirmAccountServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		User.confirmUser(request.getParameter ("id"));
		//TODO create session if not exists before redirect
		response.sendRedirect(Url.DASHBOARD.value());		
	}
}