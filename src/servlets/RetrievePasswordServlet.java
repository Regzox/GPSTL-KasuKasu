package servlets;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import exceptions.UserNotFoundException;
import services.User;
import servlets.tools.templates.online.OnlineGetServlet;
import utils.SendEmail;

/**
 * @author Célien CREMINON
 */
public class RetrievePasswordServlet extends OnlineGetServlet {
	private static final long serialVersionUID = 1L;
	

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"mail"}));}

	
	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		PrintWriter out = response.getWriter();
		String mail = request.getParameter("mail");
		 
				try{
					User.getUser(mail); // Retrait de d�claration de entities.User user inutile 
				} catch(UserNotFoundException e){
					JSONObject error = new JSONObject();
					error.put("error", "unknown email");
					request.getRequestDispatcher("error.jsp").forward(request, response);
					return;
				}
			 
		String mdp = User.getUser(mail).getPassword(); //TODO PB not safe

		System.out.println("Envoie du mail à : "+ mail);
		String contenu = "Voici votre mot de passe : " + mdp;
		String sujet = "Recuperation du mot de passe";
		
		SendEmail.sendMail(mail, sujet, contenu);

		request.getRequestDispatcher("portal.jsp").forward(request, response);
		
		out.flush();
		out.close();
		
	}
}
