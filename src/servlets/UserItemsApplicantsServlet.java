package servlets;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Loaning;
import servlets.tools.templates.online.OnlineGetServlet;

/**
 * * @author Anagbla Jean */
public class UserItemsApplicantsServlet extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		response.getWriter().print(
				Loaning.userItemsApplicants(
						(String)request.getSession().getAttribute("userId")));		
	}

}