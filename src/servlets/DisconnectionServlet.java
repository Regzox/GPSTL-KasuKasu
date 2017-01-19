package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.upmc.file.Resource;

/**
 * Servlet de d�connexion, l'utilisateur �met une requ�te de d�connexion sans param�tres.
 * L'id d'utilisateur stock� dans la session est supprim� et l'utilisateur est redirig� vers
 * la page de connexion. L'utilisateur n'aura plus acc�s aux pages dites restreintes et devra
 * se reconnecter pour pouvoir se d�connecter.
 * 
 * @author Daniel RADEAU
 *
 */

public class DisconnectionServlet extends HttpServlet {
	private static final long serialVersionUID = -3545293914840448417L;
	private Resource resource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		resource = ((Resource) this.getServletContext().getAttribute("resource"));
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("userId");
		try {
			response.sendRedirect(resource.absolutePath("portal_jsp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
