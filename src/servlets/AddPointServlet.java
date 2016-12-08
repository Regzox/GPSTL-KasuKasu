package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.ExchangePoints;
import servlets.tools.templates.online.OnlinePostServlet;
import utils.Tools;


public class AddPointServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<String>(Arrays.asList(
				new String[]{"points"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		JSONObject points;
		try {
			points = new JSONObject(request.getParameter("points"));
			JSONArray jObj = points.getJSONArray("points");
			for(int i = 0; i < jObj.length(); i++)
			{
				JSONObject point = jObj.getJSONObject(i);
				System.out.println((String) request.getSession().getAttribute("userId"));
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
			e.printStackTrace();
		}
	}

}
