package servlets.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.admin.CleanUserAccountsProgram;

/**
 * @author AJoan */
public class StopCUAProgramServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Set<String> epn= new HashSet<>( //expectedParameterNames
			Arrays.asList(new String[]{}));
	 
	public StopCUAProgramServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* GENERIC MANAGEMENT OF INPUT ERRORS
		 * We choose to check the presence of "parametersNames" we are entitled to expect in the incoming url */
		@SuppressWarnings("unchecked")
		Map<String, String[]>params=request.getParameterMap();
		for(String expected : epn)
			if(!params.containsKey(expected) || request.getParameter(expected).equals("")){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL MISUSED"); //Client's fault not mine
				return;
			}
		//Business code lines		
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		try {
			out.println("[Admin Mode]");
			out.println("Remote CUAProg Management : trying to stop CUAPrgram...");
			out.println(CleanUserAccountsProgram.stop());
		} catch (Exception e) {out.println(e);}
	}
}
