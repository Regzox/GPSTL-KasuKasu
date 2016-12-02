package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import services.Demande;
import services.Friends;
import services.User;

/**
 * Servlet implementation class DemandeServlet
 */
public class DemandeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		JSONObject jsResponse = new JSONObject();
		HttpSession session=request.getSession();
		String userId = (String) session.getAttribute("userId");
		String otherId = (String) request.getParameter("owner");
		//System.out.println("hahowa id user : "+otherId);
		String idObject = (String) request.getParameter("idbox");
		System.out.println(" id object : "+idObject);
		
			Demande.addDemande(userId, otherId ,idObject);
			System.out.println("demande sent");
			jsResponse.put("success", "demande request sent");
			//response.sendRedirect("/KasuKasu/portal.jsp");
			//response.sendRedirect("/KasuKasu/demande.jsp");
			String typeOfRequest = (String) request.getParameter("typeOfRequest");

			JSONObject js=new JSONObject();

			if(userId==null)
				js.put("error", "Not connected, no id found");
			else if(typeOfRequest==null)
				js.put("error", "No type of request specified");
			else{
				switch(Integer.parseInt(typeOfRequest)){
				case 1 :
					try {
						System.out.println("laaa");
						js.put("result", User.getUsersJSONProfileFromIds(Demande.DemandesArray(userId)));
					} catch (UserNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UserNotUniqueException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					js.put("success", "Friends List");
					break;
			
				default:
					js.put("error", "type of request unknown");
				}
				response.getWriter().print(js);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
