package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.Loaning;
import servlets.tools.templates.online.OnlineGetServlet;
/**
 * 
 * @author Ouiza Bouyahiaoui
 *
 */
public class ApplicantLoaningServlet extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {		 
		HttpSession session=request.getSession();
		response.getWriter().print(Loaning.applicantLoanings(
				(String) request.getParameter((String) session.getAttribute("userId")))
				);
	}
}
