package servlets.messenger;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Messenger;
import servlets.tools.templates.online.OnlineGetServlet;

public class InterlocutorsServlet extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;
	public InterlocutorsServlet() {super();}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		response.getWriter().print(Messenger.interlocutors(
				(String) request.getSession().getAttribute("userId")));}

}
