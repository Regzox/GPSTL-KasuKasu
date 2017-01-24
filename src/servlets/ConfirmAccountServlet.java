package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.upmc.file.Resource;
import services.User;

/**
 * * @author Anagbla Jean */
public class ConfirmAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Resource resource;

	@Override
	public void init() throws ServletException {
		super.init();
		resource = ((Resource) this.getServletContext().getAttribute("resource"));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String uid = request.getParameter ("id");
			User.confirmUser(uid);
			//init session
			HttpSession session=request.getSession();
			request.getSession().setMaxInactiveInterval(3600*24);//24 inactive hours before session invalidation
			session.setAttribute("userId", uid);
			//send cookie
			Cookie cookieId = new Cookie("userId", uid);
			response.addCookie(cookieId);
			//redirect to dashboard
			response.sendRedirect(resource.absolutePath("searchitems_jsp"));
		} catch ( Exception e ) {
			e.printStackTrace();
			response.getWriter().print(new json.Error("Une erreur s'est produite."));
		}
	}
}