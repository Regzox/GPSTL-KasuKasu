package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import enumerations.Url;
import exceptions.UserNotFoundException;
import exceptions.UserNotUniqueException;
import services.User;

public class ConnectUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ConnectUserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//TODO
		try{
			HttpSession session=request.getSession();
			response.setContentType("text/html");
			JSONObject js=new JSONObject();
			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");
			boolean verified = false;

			entities.User user = null;

			if(mail!=null && pass!=null) {
				if (!mail.equals("") && !pass.equals("")) {
					try {
						user = User.getUser(mail);
						verified = (user.getPassword().compareTo(md5(pass)) == 0);
					} catch (UserNotFoundException e) {
						System.out.println(e.getMessage());
					} catch	(UserNotUniqueException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println(
							"[Warning] Email or password empty : \n" +
									"\temail : " + mail + '\n' +
									"\tpassword : " + pass);
				}
			}

			if(verified){

				Cookie cookieId = new Cookie("userId", user.getId());
				session.setAttribute("userId", user.getId());
				response.addCookie(cookieId);
				
//				js.put("response", 1);
				response.getWriter().print(js);
				//response.sendRedirect(Url.SEARCHITEMS.value());
				
				//response.sendRedirect("/KasuKasu/dashboard.jsp");
				System.out.println("User connexion successfull");
				

			}else{
				System.out.println("Error ?");
				js.put("error", "Wrong mail or Password...");
				response.getWriter().print(js);
				//response.sendError(401, "Wrong mail or Password");
				//System.out.println("User connexion failed");
			}
		}catch (Exception e) {
			e.printStackTrace();
			//request.setAttribute("error", e); //remote debug
			//request.getRequestDispatcher(Url.ERROR_PAGE.value()).forward(request, response);
			response.getWriter().print(new json.Error("Sorry, an error has occurred.")); 
		}

	}

public static String md5(String input) {
		
		String md5 = null;
		
		if(null == input) return null;
		
		try {
			
		//Create MessageDigest object for MD5
		MessageDigest digest = MessageDigest.getInstance("MD5");
		
		//Update input string in message digest
		digest.update(input.getBytes(), 0, input.length());

		//Converts message digest value in base 16 (hex) 
		md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}
}