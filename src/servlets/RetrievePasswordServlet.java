package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import enumerations.Url;
import exceptions.UserNotFoundException;
import services.User;
import utils.SendEmail;

/**
 * @author Célien CREMINON
 */
public class RetrievePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RetrievePasswordServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();

			String mail = request.getParameter("mail");

			if(mail != null){
				if(!mail.equals("")){
					try{
						User.getUser(mail); // Retrait de d�claration de entities.User user inutile 
					} catch(UserNotFoundException e){
						JSONObject error = new JSONObject();
						error.put("error", "unknown email");
						request.getRequestDispatcher("error.jsp").forward(request, response);

						//out.print(error);
						return;
					}
				}else{
					System.out.println(
							"[Warning] Email empty ! \n" +
									"\temail : " + mail);
				}
			}

			String mdp = User.getUser(mail).getPassword(); //TODO PB not safe

			System.out.println("Envoie du mail à : "+ mail);
			String contenu = "Voici votre mot de passe : " + mdp;
			String sujet = "Recuperation du mot de passe";
			SendEmail.sendMail(mail, sujet, contenu);
			
			request.getRequestDispatcher("portal.jsp").forward(request, response);

			


			out.flush();
			out.close();
			

	}catch(Exception e){
		e.printStackTrace();
		request.getRequestDispatcher(Url.ERROR_PAGE.value()).forward(request, response);
	}

}

}
