package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.util.JSON;

import json.Error;
import json.Success;
import services.User;

public class CheckConnectionServlet extends HttpServlet {

	private static final long serialVersionUID = -5217076308100104778L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId != null) {
			JSONObject json = new JSONObject();
			try {
				json.put("userId", User.getUsersJSONProfileWhere("id", userId));
			} catch (JSONException e) {
				response.getWriter().print(new Error("JSON error !"));
				return;
			}
			response.getWriter().print(new Success(json));
		} else {
			response.getWriter().print(new Error("No active session"));
		}
	}
}
