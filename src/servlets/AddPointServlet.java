package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.ExchangePoints;
import utils.Tools;

/**
 * Servlet implementation class AddPoint
 */
public class AddPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPointServlet() {
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
		JSONObject points;
		try {
			points = new JSONObject(request.getParameter("points"));
			JSONArray jObj = points.getJSONArray("points");
			//boolean bool = true;
			for(int i = 0; i < jObj.length(); i++)
			{
			     JSONObject point = jObj.getJSONObject(i);
			    
			     ExchangePoints.addExchangePoint(
							point.getDouble("lat"),
							point.getDouble("lon"),
							point.getInt("radius"),
							(String) request.getSession().getAttribute("userId"),
							point.getString("nom")
							);
			      
	         }
			response.getWriter().print(Tools.serviceMessage(1));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
