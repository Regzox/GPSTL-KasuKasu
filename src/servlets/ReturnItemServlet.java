package servlets;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.Loaning;
import servlets.tools.templates.online.OnlinePostServlet;

public class ReturnItemServlet extends OnlinePostServlet {

	private static final long serialVersionUID = 4617273883572402667L;

	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {		
		
		JSONObject result = Loaning.returnItem(params.get("loan_id"));
		response.getWriter().print(result);
	}

}
