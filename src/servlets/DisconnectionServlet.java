package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enumerations.Url;

/**
 * Servlet de déconnexion, l'utilisateur émet une requête de déconnexion sans paramètres.
 * L'id d'utilisateur stocké dans la session est supprimé et l'utilisateur est redirigé vers
 * la page de connexion. L'utilisateur n'aura plus accès aux pages dites restreintes et devra
 * se reconnecter pour pouvoir se déconnecter.
 * 
 * @author Daniel RADEAU
 *
 */

public class DisconnectionServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3545293914840448417L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("userId");
		response.sendRedirect(Url.PORTAL.value());
	}
	
}
