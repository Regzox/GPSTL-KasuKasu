package servlets;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.tools.DataEncryption;
import json.Error;
import json.Success;
import json.Warning;
import lingua.Lingua;
import services.User;
import servlets.tools.templates.online.OnlinePostServlet;
import utils.SendEmail;

/**
 * 
 * @author Daniel RADEAU
 *
 */

public class ModifyUserServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 8159888483703533987L;

	@Override
	public void doBusiness(HttpServletRequest req, HttpServletResponse resp, Map<String, String> params)
			throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String[]> map = req.getParameterMap();
		String oldEmail = null, oldPassword = null, email = null, password = null, name = null, firstname = null, phone = null;
		entities.User oldUser = null, user = null;
		Cookie[] cookies = req.getCookies();
		String value = "";
		for (int i = 0; i < cookies.length; i++) {
		
				  if(cookies[i].getName().equals("lang"))
				  {
					  value = cookies[i].getValue();
				  }
		}
		if(value.equals(""))
			value="fr";

		for (String parameter : map.keySet()) {
			System.out.println(parameter + " : " + req.getParameter(parameter));
		}
		System.out.println();

		oldEmail = (map.containsKey("oldEmail")) ? req.getParameter("oldEmail") : null;
		oldPassword = (map.containsKey("oldPassword")) ? DataEncryption.md5(req.getParameter("oldPassword")) : null;
		email = (map.containsKey("newEmail")) ? req.getParameter("newEmail") : null;
		password = (map.containsKey("newPassword")) ? DataEncryption.md5(req.getParameter("newPassword")) : null;
		name = (map.containsKey("newName")) ? req.getParameter("newName") : null;
		firstname = (map.containsKey("newFirstname")) ? req.getParameter("newFirstname") : null;
		phone = (map.containsKey("newPhone")) ? req.getParameter("newPhone") : null;

		//		if (oldEmail != null) {
		//			if (!oldEmail.equals("")) {
		//				try {
		//					oldUser = User.getUser(req.getParameter("oldEmail"));
		//				} catch (Exception e) {
		//					resp.getWriter().print(new Warning("Current email isn't recognized"));
		//					return;
		//				}
		//			} else {
		//				resp.getWriter().print(new Warning("You havn't type your email"));
		//				return;
		//			}
		//		} 

		String userId = (String) req.getSession().getAttribute("userId");
		try {

			System.out.println();

			oldUser = User.getUserById(userId);
			System.out.println("oldUser : "+oldUser);
			oldEmail = oldUser.getEmail();
		
		} catch (Exception e1) {
			e1.printStackTrace();
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
					} catch (Exception e) {
						e.printStackTrace();
						resp.getWriter().print(new Error("We apologize, an internal error has occured during email searching"));
						return;
					} // It's ok email is being modify
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
			else
			{
					SendEmail.sendMail(oldEmail, 
							Lingua.get("modifMailSubject"
									, value), 
							Lingua.get("modifMailMessage"
									, value)
							);
			}

			if (name.equals(""))
				name = oldUser.getName();

			if (firstname.equals(""))
				firstname = oldUser.getFirstname();

			if (phone.equals(""))
				phone = "" + oldUser.getPhone();

		}  else {
			resp.getWriter().print(new Error("We apologize, the submition system may be faulty"));
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