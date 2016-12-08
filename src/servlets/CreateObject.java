package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.items.ItemsDB;
import utils.ParametersChecker;

/**
 * Servlet pour sauvegarder un objet dans la BD.
 * @author Giuseppe FEDERICO
 *
 */
public class CreateObject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Logger
	private static Logger logger = Logger.getLogger( CreateObject.class.getName() ); 


	public CreateObject() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter(); 
		response.setContentType("application/json;charset=UTF-8");

		try {
			
			// Récuperation de l'ID de session
			HttpSession session=request.getSession();
	        String userId = (String) session.getAttribute("userId");
	        
	        if( userId == null ) {
	        	logger.severe("User ID is empty, problem with the session."); 
	        	out.write( new JSONObject().put("error", "User ID is empty.").toString() );
	        	return;
	        	
	        }

			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();

			//Teste si les paramètres d'une reqûete sont présents et de longueur non nulle.
			if( ParametersChecker.testMultipleNonEmpty(		map, 
					"nom",
					"description",
					"groupe",
       				"coordonnees"
					)){
				logger.warning("A request parameter is missing."); 
				out.write( new JSONObject().put("error", "A request parameter is missing.").toString() ); return;
				
			}
			
			// Création objet JSON qui va être sauvegardé dans la BD.
			JSONObject object = new JSONObject();          
			object.put( "owner"        ,  userId						         );
			object.put( "title"        ,  request.getParameter("nom")            );
			object.put( "description"  ,  request.getParameter("description")    );
			object.put( "groupe"       ,  request.getParameter("groupe")         );
			object.put( "coordonnees"  ,  request.getParameter("coordonnees")    );

			// Sauvegarde l'objet dans la BD
			ItemsDB.addItem(object);

			// Génération d'une réponse JSON
			out.write( new JSONObject().put("success", "Object added.").toString() );
			
			logger.fine("Object " + request.getParameter("nom") + "added"); 


		} catch (Exception e) {
			logger.severe("Error with object " + request.getParameter("nom") + e.getMessage()); 
			response.getWriter().print(new json.Error("Sorry, an error has occurred.")); 
			
			
		}



	}

}