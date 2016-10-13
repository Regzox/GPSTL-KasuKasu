package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.UserNotFindException;
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
			response.setContentType("text/html");

			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");

			boolean verified = false;
			
			entities.User user = null;
			
			if(mail!=null && pass!=null) {
				if (!mail.equals("") && !pass.equals("")) {
					try {
						user = User.getUser(mail);
						verified = (user.getPassword().compareTo(pass) == 0);
					} catch (UserNotFindException e) {
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
//				Cookie c_mail=new Cookie("mail",mail);
//				Cookie c_pass=new Cookie("pass",pass);
				Cookie cookieId = new Cookie("userId", Integer.toString(user.getId()));
				
//				response.addCookie(c_mail);
//				response.addCookie(c_pass);
				response.addCookie(cookieId);
//				PrintWriter out = response.getWriter();
//				out.println("<html>");
//				out.println("<head>");
//				out.println("<title>Connexion success !</title>");
//				out.println("</head>");
//				out.println("<body>");
//				out.println("<h1>You are now connected</h1>");
//				out.println("</body>");
//				out.println("</html>");
//				out.flush();
//				out.close();
				
				response.sendRedirect("/KasuKasu/dashboard.jsp");
				
				System.out.println("User connexion successfull");

			}else{
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Connexion error !</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Mail or Password incorrect</h1>");
				out.println("</body>");
				out.println("</html>");
				out.flush();
				out.close();
				System.out.println("User connexion failed");
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("errorpage.jsp").forward(request, response);
		}

	}
}