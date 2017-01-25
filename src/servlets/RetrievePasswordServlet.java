package servlets;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import exceptions.UserNotFoundException;
import fr.upmc.file.Resource;
import lingua.Lingua;
import services.User;
import servlets.tools.templates.offline.OfflineGetServlet;

import utils.SendEmail;

/**
 * @author Celien CREMINON
 */
public class RetrievePasswordServlet extends OfflineGetServlet {
	private static final long serialVersionUID = 1L;

	private Resource resource;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"mail"}));
		resource = ((Resource) this.getServletContext().getAttribute("resource"));
	}


	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		PrintWriter out = response.getWriter();
		String mail = request.getParameter("mail");

		Cookie[] cookies = request.getCookies();
		String value = "";
		for (int i = 0; i < cookies.length; i++) {

			if(cookies[i].getName().equals("lang"))
			{
				value = cookies[i].getValue();
			}
		}
		if(value.equals(""))
		{
			value="fr";
		}

		try{
			User.getUser(mail); // Retrait de declaration de entities.User user inutile 
		} catch(UserNotFoundException e){
			JSONObject error = new JSONObject();
			error.put("error", "unknown email");
			request.getRequestDispatcher(resource.relativePath("error_jsp")).forward(request, response);
			return;
		}

		 
		SendEmail.sendMail(mail,
				Lingua.get("retMailSubject", value), 
				Lingua.get("retMailMessage", value)
				+"http://localhost:8080/KasuKasu/confirm?id="+User.getUser(mail).getId()
				);

		response.sendRedirect(resource.absolutePath("portal_jsp"));

		out.flush();
		out.close();

	}

}
