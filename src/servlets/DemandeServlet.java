package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.Demande;

/**
 * Servlet implementation class DemandeServlet
 */
public class DemandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session=request.getSession();
			if(session ==null)
			{response.getWriter().print(new json.Error("User not conected!"))
				;return;}
			String userID = (String) session.getAttribute("userId");
			if(userID ==null){
				response.getWriter().print(new json.Error("User not conected!"));
				return;}
			String idObject = (String) request.getParameter("idbox");
			System.out.println(" id object : "+idObject);

			Demande.addDemande(userID,idObject);
			System.out.println("demande sent");
			response.sendRedirect("/KasuKasu/useritems.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
