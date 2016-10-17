package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.User;

/**
 * * @author Anagbla Jean */
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateUserServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Map<String,String[]> map=request.getParameterMap();
			response.setContentType("text/plain");

			if(   	map.containsKey("email")     && !request.getParameter("email").equals("")
					&&map.containsKey("mdp")     && !request.getParameter ("mdp").equals("")
					&&map.containsKey("nom")	 && !request.getParameter ("nom").equals("")
					&&map.containsKey("prenom")  && !request.getParameter ("prenom").equals("")
					&&map.containsKey("numero")  && !request.getParameter ("numero").equals(""))
			{
				//TODO CREATE COOKIE FOR SESSION TRACKING
				response.getWriter().print(
						User.createUser(request.getParameter ("email"), request.getParameter ("mdp"),
								request.getParameter ("nom"), request.getParameter ("prenom"),
								request.getParameter("numero")));
			}else 
				throw new Exception("Wrong Url! Missing parameters\n Il manque des parametres dans l'URL!");

		}catch (Exception e) {
			e.printStackTrace(); //local debug
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);}
	}
}