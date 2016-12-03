package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Loaning;
import servlets.tools.templates.online.OnlineGetServlet;

/**
 * @author ANAGBLA Joan */
public class DemandeServlet extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"idbox"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {		 
		Loaning.requestItem(
				(String)request.getSession().getAttribute("userId"),
				(String) request.getParameter("idbox")
				);
		response.sendRedirect("/KasuKasu/useritems.jsp");//TODO async af show_item
	}
}