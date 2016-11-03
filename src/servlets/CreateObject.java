package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.mongo.ObjectTools;
import utils.ParametersChecker;

/**
 * Servlet pour sauvegarder un objet dans la BD.
 * @author Giuseppe FEDERICO
 *
 */
public class CreateObject extends HttpServlet {
	private static final long serialVersionUID = 1L;


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

			String nom          =  request.getParameter("nom")            ;
			String description  =   request.getParameter("description")   ;
			String datedebut    =   request.getParameter("datedebut")     ;
			String datefin      =  request.getParameter("datefin")        ;
			String groupe       =   request.getParameter("groupe")        ;
			String coordonnees  =  request.getParameter("coordonnees")    ;


			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();

			//Teste si les paramètres d'une reqûete sont présents et de longueur non nul.
			if( ParametersChecker.testMultipleNonEmpty(		map, 
					"nom",
					"description",
					"datedebut",
					"datefin",
					"groupe",  
					"coordonnees"))
				out.write( new JSONObject().put("error", "A request parameter is missing.").toString() );

			// Création objet JSON qui va être sauvegardé dans la BD.
			JSONObject object = new JSONObject();          
			object.put( "nom"          ,  nom          );
			object.put( "description"  ,  description  );
			object.put( "datedebut"    ,  datedebut    );
			object.put( "datefin"      ,  datefin      );
			object.put( "groupe"       ,  groupe       );
			object.put( "coordonnees"  ,  coordonnees  );

			// Sauvegarde l'objet dans la BD
			ObjectTools.addObject(object);

			out.write( new JSONObject().put("success", "Object added.").toString() );

		} catch (Exception e) {
			// Rédiréction vers une page d'erreur
			e.printStackTrace();
			request.setAttribute("error", e); //remote debug
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);

		}



	}

}
