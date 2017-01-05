package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.User;
import servlets.tools.templates.offline.OfflinePostServlet;

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

		JSONObject json = User.createUser(
				params.get("email"),
				params.get("mdp"),
				params.get("nom"), 
				params.get("prenom"),
				params.get("numero"));

		request.getSession().setAttribute("userId", 
				User.getUser(params.get("email")).getId());
		request.getSession().setMaxInactiveInterval(3600*24);//24 inactive hours before session invalidation
		response.getWriter().print(json); 
	}

}