package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import enumerations.Url;
import services.User;

public class FindUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/******/
		try{
			
			/** TODO
			 * 	Si l'utilisateur n'est pas connecté
			 *  Le Renvoyer sur la page de connexion
			 */
			response.setContentType("text/html");
			String search=request.getParameter("search");
			String value=request.getParameter("value");
			JSONObject js=new JSONObject();
			if(value==null || search==null)
				js.put("error", "Un des champs est vide.");
			else{
				JSONObject rep=User.getUsersJSONProfileWhere(search,value);
				js.put("users", rep);
				js.put("response", 1);
				js.put("id", request.getSession().getAttribute("userId"));
				//System.out.println(rep);
			}
			response.getWriter().print(js);
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher(Url.ERROR_PAGE.value()).forward(request, response);
		}
	}
}
