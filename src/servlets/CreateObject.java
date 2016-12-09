package servlets;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.items.ItemsDB;
import servlets.tools.templates.online.OnlinePostServlet;
import utils.ParametersChecker;

/**
 * Servlet pour sauvegarder un objet dans la BD.
 * @author Giuseppe FEDERICO
 *
 */
public class CreateObject extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;
	// Logger
	private static Logger logger = Logger.getLogger( CreateObject.class.getName() );
	
	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"nom","description","groupe","coordonees"}));}

	
	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		PrintWriter out = response.getWriter(); 
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
				"coordonees"
				)){
			logger.warning("A request parameter is missing."); 
			out.write( new JSONObject().put("error", "A request parameter is missing.").toString() ); return;
			
		}
		System.out.println(request.getParameter("groupe"));
		// Création objet JSON qui va être sauvegardé dans la BD.
		JSONObject object = new JSONObject();          
		object.put( "owner"        ,  userId						         );
		object.put( "status"        ,  "available");
		object.put( "les id des points(todo)",  request.getParameter("coordonees"));
		object.put( "title"        ,  request.getParameter("nom")            );
		object.put( "description"  ,  request.getParameter("description")    );
		object.put( "groups"       ,  new JSONArray(request.getParameter("groupe")));

		// Sauvegarde l'objet dans la BD
		ItemsDB.addItem(object);

		// Génération d'une réponse JSON
		out.write( new JSONObject().put("success", "Object added.").toString() );
		
		logger.fine("Object " + request.getParameter("nom") + "added"); 
	} 
}