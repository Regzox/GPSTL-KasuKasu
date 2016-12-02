package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import json.Error;
import json.Warning;
import services.Data;
import services.User;

/**
 * Handles user images 
 * 
 * @author Daniel RADEAU
 *
 */

public class ImagesServlet extends HttpServlet {
	
	private static final long serialVersionUID = -9114705042526848246L;
	
	private String[] allowedExtentions = {".png", ".jpg", ".gif", ".svg"};
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = (String) request.getSession().getAttribute("userId");
		
		if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
			userId = request.getParameter("id");
		}
		
		try {
			entities.User user = User.getUserById(userId);
			JSONObject json = User.getUserImage(user);
			
			json.put("success", json.getString("success").replaceAll(".*(/data)", "/KasuKasu/data"));
			
			System.out.println(json.getString("success"));
			
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(new Error("Something goes wrong when getting user by id"));	
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Data.UPLOAD_DIRECTORY = getServletContext().getRealPath("/data") + "/";
		
		try {
			entities.User user = User.getUserById((String)request.getSession().getAttribute("userId"));
			String url = Data.uploadFile(request);
			boolean allowed = false;
			String low_url = url.toLowerCase();
			
			for (String extention : allowedExtentions)
				allowed |= low_url.endsWith(extention);
			
			if (!allowed) {
				ArrayList<String> urlsToDelete = new ArrayList<String>();
				urlsToDelete.add(url);
				Data.deleteFiles(urlsToDelete);
				response.getWriter().print(new Warning("File  isn't allowed"));
				return;
			}
			
			System.out.println(url);
			
			response.getWriter().print(User.putUserImage(user, url));
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(new Error(e.getMessage()));
		}
		
	}
}
