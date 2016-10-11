package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		resp.getWriter().append("FUCK ! ");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<String, String> map = req.getParameterMap();
		String oldEmail = null, oldPassword = null, email = null, password = null, name = null, firstname = null, phone = null;
		entities.User oldUser = null, user = null;
		
		for (String parameter : map.keySet()) {
			System.out.println(parameter + " : " + req.getParameter(parameter));
		}
		
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
					resp.getWriter().append("Current email isn't recognized");
					return;
				}
			} else {
				resp.getWriter().append("You havn't type your email");
				return;
			}
		} 

		if (oldUser != null && oldPassword != null && email != null && password != null && name != null && firstname != null && phone != null) {
			
			if (!oldUser.getPassword().equals(oldPassword)) {
				resp.getWriter().append("Current password isn't recognized");
				return;
			}
			
			if (email.equals(""))
				email = oldUser.getEmail();
			else {		
				if (!email.equals(oldEmail)) {
					try {
						User.getUser(email);
						resp.getWriter().append("Your new email is already choosen");
					} catch (SQLException e) {
						e.printStackTrace();
						resp.getWriter().append("We are apologize, an internal error has occur");
						return;
					} catch (Exception e) {} // It's ok email is being modify
				}
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
			resp.getWriter().append("We are apologize, an internal error has occur");
			return;
		}

		try {
			user = new entities.User(email, password, name, firstname, Integer.parseInt(phone));
			User.updateUser(oldEmail, oldPassword, user);
			resp.getWriter().append("Your account has correctly been updated");
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().append("We are apologize, an internal error has occur");
			return;
		}

	}

}
