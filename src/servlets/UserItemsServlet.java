package servlets;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Items;
import servlets.tools.templates.online.OnlineGetServlet;

/**
 * * @author Anagbla Joan */
public class UserItemsServlet extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		if(request.getParameter("query")==null
				||request.getParameter("query").equals("")
				)
			response.getWriter().print(Items.userItems(
					"",
					(String) request.getSession().getAttribute("userId"))
					);
			
		else
			response.getWriter().print(Items.userItems(
					params.get("query"),
					(String) request.getSession().getAttribute("userId"))
					);
	}

}