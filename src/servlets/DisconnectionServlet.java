package servlets;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.upmc.file.Resource;
import servlets.tools.templates.online.OnlineGetServlet;

/**
 * Servlet de déconnexion, l'utilisateur émet une requête de déconnexion sans paramètres.
 * L'id d'utilisateur stocké dans la session est supprimé et l'utilisateur est redirigé vers
 * la page de connexion. L'utilisateur n'aura plus accès aux pages dites restreintes et devra
 * se reconnecter pour pouvoir se déconnecter.
 * 
 * @author Daniel RADEAU
 *
 */

public class DisconnectionServlet extends OnlineGetServlet {
	private static final long serialVersionUID = -3545293914840448417L;
	private Resource resource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		resource = ((Resource) this.getServletContext().getAttribute("resource"));
	}
	
	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		request.getSession().removeAttribute("userId");
		response.sendRedirect(resource.absolutePath("portal_jsp"));
	}
	
}
