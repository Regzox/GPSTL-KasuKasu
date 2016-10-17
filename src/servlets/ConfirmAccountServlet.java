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
public class ConfirmAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ConfirmAccountServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Map<String,String[]> map=request.getParameterMap();
			//TODO
			//mettre un id c mieux qe mettre lemail en clair sinon n'imorte qui peut confirmer nip 
			//@ùail l'id c'est mieux une clé serait encore mieux mais new tab to disscus with others
			if(	map.containsKey("id")&& !request.getParameter("id").equals("")) 	
						User.confirmUser(Integer.parseInt(request.getParameter ("id")));
			else 
				throw new Exception("Wrong Url! Missing parameters\n Il manque des parametres dans l'URL!");

			response.sendRedirect("/KasuKasu/dashboard.jsp");
			
		}catch (Exception e) {
			e.printStackTrace(); //local debug
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);}
	}
}