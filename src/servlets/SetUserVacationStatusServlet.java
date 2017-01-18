package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.User;
import servlets.tools.templates.online.OnlinePostServlet;

/**
 * * @author Anagbla Joan */
public class SetUserVacationStatusServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"vacation"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		response.getWriter().print(User.setVacationStatus(
				(String) request.getSession().getAttribute("userId"),
				Boolean.parseBoolean(params.get("vacation"))));	
	}
}