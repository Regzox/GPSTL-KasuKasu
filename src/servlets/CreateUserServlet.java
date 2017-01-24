package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import exceptions.UserNotFoundException;
import services.User;
import servlets.tools.templates.offline.OfflinePostServlet;
import utils.Tools;

/**
 * * @author Anagbla Jean */
public class CreateUserServlet extends OfflinePostServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<String>(Arrays.asList(
				new String[]{"email","mdp","nom","prenom","numero"}));}

	@Override
	public void doBusiness(HttpServletRequest request, 
			HttpServletResponse response, Map<String, String> params)
					throws Exception {
		Cookie[] cookies = request.getCookies();
		String value = "";
		for (int i = 0; i < cookies.length; i++) {
		
				  if(cookies[i].getName().equals("lang"))
				  {
					  value = cookies[i].getValue();
				  }
		}
		if(value.equals(""))
			value="fr";
		
		entities.User user = null;
		
		try {
			 user = User.getUser(params.get("email"));
		} catch ( UserNotFoundException e ) {
			System.out.println("Utilisateur non trouvé");
		}
		
		if ( user == null ) {
			
			JSONObject json = User.createUser(
					value,
					params.get("email"),
					params.get("mdp"),
					params.get("nom"), 
					params.get("prenom"),
					params.get("numero"));
			
			user = User.getUser(params.get("email"));
			request.getSession().setAttribute("userId", user.getId());
			request.getSession().setMaxInactiveInterval(3600*24);//24 inactive hours before session invalidation
			response.getWriter().print(( json != null ) ? json : Tools.serviceMessage(1));
			
		} else {
			response.getWriter().print(Tools.serviceMessage("Cet email est déjà utilisé"));
		}		

	}

}