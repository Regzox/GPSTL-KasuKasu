package servlets.tools.templates.online;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlets.tools.ServletToolBox;
import servlets.tools.templates.Servlet;

/**
 * * @author Anagbla Jean */
public abstract class OnlinePostServlet extends HttpServlet implements Servlet{
	private static final long serialVersionUID = 1L;
	
	/**it is default initialized with an empty hashSet to avoid NullPointerException
	 * that means it permissive by default (there are no expected parameters )*/
	protected Set<String> epn=new HashSet<String>(); //expectedParameterNames	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Map<String, String>params=ServletToolBox.beforeOnlineBusiness
					(request, response, epn);
			if(params==null) 	return;

			doBusiness(request,response,params);

		} catch (Exception e){
			e.printStackTrace();
			response.getWriter().print(new json.Error("Une erreur s'est produite."));}}
			
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);}

	public OnlinePostServlet() {super();}
}