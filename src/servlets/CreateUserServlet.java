package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.User;

/**
 * Servlet implementation class CreateUserServlet
 * * @author Anagbla Jean
 */
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			//Recuperation de la map des parametres de l'url
			Map<String,String[]> map=request.getParameterMap();
			response.setContentType("text/html");

			//Verification de la presence des parametres requis dans l'url
			if(   	map.containsKey("email")     && !request.getParameter("email").equals("")
					&&map.containsKey("mdp")     && !request.getParameter ("mdp").equals("")
					&&map.containsKey("nom")	 && !request.getParameter ("nom").equals("")
					&&map.containsKey("prenom")  && !request.getParameter ("prenom").equals("")
					&&map.containsKey("numero")  && !request.getParameter ("numero").equals("")){

				//Recuperation des parametres puisqu'ils sont presents
				String email = request.getParameter ("email");
				String mdp = request.getParameter ("mdp");
				String nom = request.getParameter ("nom");
				String prenom = request.getParameter ("prenom");
				String numero = request.getParameter("numero");


				//Creer l'utilisteur en bdd via le service associE
				if(!User.createUser(email, mdp, nom, prenom,numero)){
//					System.out.println("gfgf");
					PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>User's email already exists!</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h2>Vous possedez déjà un compte avec cet email</h2>");
					out.println("</body>");
					out.println("</html>");
				}else
					response.sendRedirect("connexion.jsp");//Ok tout est bon on peut maintenant se connecter

			}else{
//				System.out.println("gf8");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Wrong Url! Missing parameters</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>Il manque des parametres à l'URL!</h2>");
				out.println("</body>");
				out.println("</html>");
			}
		}catch (Exception e) {
			e.printStackTrace();
//			System.out.println("7gf");
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);
		}
	}

}
