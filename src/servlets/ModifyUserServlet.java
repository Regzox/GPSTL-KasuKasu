package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.Error;
import json.Success;
import json.Warning;
import services.User;

/**
 * 
 * @author Daniel RADEAU
 *
 */

public class ModifyUserServlet extends HttpServlet {

	private static final long serialVersionUID = 8159888483703533987L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().append("Sorry you can't do 'GET' on this service ...");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<String, String> map = req.getParameterMap();
		String oldEmail = null, oldPassword = null, email = null, password = null, name = null, firstname = null, phone = null;
		entities.User oldUser = null, user = null;
		
		
		for (String parameter : map.keySet()) {
			System.out.println(parameter + " : " + req.getParameter(parameter));
		}
		System.out.println();
		
		oldEmail = (map.containsKey("oldEmail")) ? req.getParameter("oldEmail") : null;
		oldPassword = (map.containsKey("oldPassword")) ? req.getParameter("oldPassword") : null;
		email = (map.containsKey("newEmail")) ? req.getParameter("newEmail") : null;
		password = (map.containsKey("newPassword")) ? req.getParameter("newPassword") : null;
		name = (map.containsKey("newName")) ? req.getParameter("newName") : null;
		firstname = (map.containsKey("newFirstname")) ? req.getParameter("newFirstname") : null;
		phone = (map.containsKey("newPhone")) ? req.getParameter("newPhone") : null;
		
		if (oldEmail != null) {
			if (!oldEmail.equals("")) {
				try {
					oldUser = User.getUser(req.getParameter("oldEmail"));
				} catch (Exception e) {
					resp.getWriter().print(new Warning("Current email isn't recognized"));
					return;
				}
			} else {
				resp.getWriter().print(new Warning("You havn't type your email"));
				return;
			}
		} 

		if (	oldUser != null && 
				oldPassword != null && 
				email != null && 
				password != null && 
				name != null && 
				firstname != null && 
				phone != null) 
		{
			
			if (!oldUser.getPassword().equals(oldPassword)) {
				resp.getWriter().print(new Warning("Current password isn't recognized"));
				return;
			}
			
			if (email.equals(""))
				email = oldUser.getEmail();
			else {		
				if (!email.equals(oldEmail)) {
					try {
						User.getUser(email);
						resp.getWriter().print(new Warning("Your new email is already choosen"));
					} catch (SQLException e) {
						e.printStackTrace();
						resp.getWriter().print(new Error("We are apologize, an internal error has occur during email searching"));
						return;
					} catch (Exception e) {} // It's ok email is being modify
				}
			}
			
			if (	email.equals("") &&
					password.equals("") &&
					name.equals("") &&
					firstname.equals("") &&
					phone.equals(""))
			{
				resp.getWriter().print(new Warning("No changes to apply"));
				return;
			}
			
			if (password.equals(""))
				password = oldUser.getPassword();
			
			if (name.equals(""))
				name = oldUser.getName();
			
			if (firstname.equals(""))
				firstname = oldUser.getFirstname();
			
			if (phone.equals(""))
				phone = "" + oldUser.getPhone();
			
		}  else {
			resp.getWriter().print(new Error("We are apologize, the submit system maybe faulty"));
			return;
		}

		try {
			user = new entities.User(email, password, name, firstname, phone);
			User.updateUser(oldEmail, oldPassword, user);
			resp.getWriter().print(new Success("Your account has correctly been updated"));
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().print(new Error("We are apologize, the update transaction has failed"));
			return;
		}

	}

}
