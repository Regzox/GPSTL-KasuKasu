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
 * Servlet implementation class AddPointEmprunt
 */
public class AddPointEmprunt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPointEmprunt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter(); 
		response.setContentType("application/json;charset=UTF-8");

		try {

			String points  =  request.getParameter("points")    ;


			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();

			//Teste si les paramètres d'une reqûete sont présents et de longueur non nul.
			if( ParametersChecker.testMultipleNonEmpty(		map, 
					"points"))
				out.write( new JSONObject().put("error", "A request parameter is missing.").toString() );

			// Création objet JSON qui va être sauvegardé dans la BD.
			JSONObject object = new JSONObject();          
			
			object.put( "points"  ,  points  );

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
