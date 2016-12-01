package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.mongo.ObjectTools;
import dao.mongo.PointEmpruntDB;
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
			
			// Récuperation de l'ID de session
			HttpSession session=request.getSession();
	        String userId = (String) session.getAttribute("userId");
	        
	        if( userId == null ) {
	        	out.write( new JSONObject().put("error", "User ID is empty.").toString() );
	        	return;
	        	
	        }

			String points  =  request.getParameter("points");
			//System.out.println(points);


			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();


			//Teste si les paramètres d'une reqûete sont présents et de longueur non nul.
			if( ParametersChecker.testMultipleNonEmpty(map,"points"))
				out.write( new JSONObject().put("error", "A request parameter is missing.").toString() );

			// Création objet JSON qui va être sauvegardé dans la BD.
			JSONObject point = new JSONObject(); 
			point.put("userId",userId);	
			point.put("points",points);

			// Sauvegarde l'objet dans la BD
			PointEmpruntDB.addPoint(point);
			out.write( new JSONObject().put("success", "Object added.").toString() );

		} catch (Exception e) {
			// Rédiréction vers une page d'erreur
			e.printStackTrace();
			//request.setAttribute("error", e); //remote debug
			//request.getRequestDispatcher("errorpage.jsp").forward(request, response);
			response.getWriter().print(new json.Error("Sorry, an error has occurred.")); 

		}



	}

}
