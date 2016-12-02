package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import dao.mongo.ObjectTools;
import dao.mongo.PointEmpruntDB;
import services.PointEmprunt;
import utils.ParametersChecker;
import utils.Tools;

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


			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();


			//Teste si les paramètres d'une reqûete sont présents et de longueur non nul.
			if( ParametersChecker.testMultipleNonEmpty(map,"points"))
				out.write( new JSONObject().put("error", "A request parameter is missing.").toString() );


			
			JSONObject points  =  new JSONObject(request.getParameter("points"));
			JSONArray jObj = points.getJSONArray("points");
			boolean bool = true;
			for(int i = 0; i < jObj.length(); i++)
			{
			     JSONObject point = jObj.getJSONObject(i);
			     JSONObject result =PointEmprunt.createPointEmprunt(Integer.parseInt(userId), point.getString("nom"), point.getDouble("lat"), point.getDouble("lon"), point.getInt("radius"));
			     if (!result.getString("message").equals("1")) bool = false;

			      
			}
			
			if (bool==true) response.getWriter().print(Tools.serviceMessage(1));
			else response.getWriter().print(Tools.serviceMessage("error"));


			
			
			
		} catch (Exception e) {
			// Rédiréction vers une page d'erreur
			e.printStackTrace();
			response.getWriter().print(new json.Error(e.getMessage())); 
	
		}



	}

}
