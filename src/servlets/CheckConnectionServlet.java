package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.Error;
import json.Success;

public class CheckConnectionServlet extends HttpServlet {

	private static final long serialVersionUID = -5217076308100104778L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((String) request.getSession().getAttribute("userId") != null) {
			response.getWriter().print(new Success("Always connected"));
		} else {
			response.getWriter().print(new Error("No active session"));
		}
	}
}
