package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import services.ExchangePoints;
import servlets.tools.templates.online.OnlinePostServlet;

public class AddExchangePointServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<String>(Arrays.asList(
				new String[]{"lat","lon","radius","name"}));}

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		response.getWriter().print(
				ExchangePoints.addExchangePoint(
						Double.parseDouble(params.get("lat")),
						Double.parseDouble(params.get("lon")),
						Integer.parseInt(params.get("radius")),
						(String) request.getSession().getAttribute("userId"),
						params.get("name")
						)
				);
	}
	
}