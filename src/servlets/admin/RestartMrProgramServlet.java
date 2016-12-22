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

import services.admin.ItemsMRProgram;

/**
 * @author AJoan */
public class RestartMrProgramServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Set<String> epn= new HashSet<>( //expectedParameterNames
			Arrays.asList(new String[]{"time"}));
	 
	public RestartMrProgramServlet() {super();}

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
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		//Business code lines		
		try {
			out.println("[Admin Mode]");
			out.println("Remote MRProg Management : trying to restart MRPrgram...");
			out.println(ItemsMRProgram.restart(Integer.parseInt(request.getParameter("time"))));
		} catch (Exception e) {out.println(e);}
	}
}
