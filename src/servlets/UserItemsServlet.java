package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.Items;


/**
 * * @author Anagbla Jean */
public class UserItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserItemsServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HttpSession session=request.getSession();
			if(session ==null)
			{response.getWriter().print(new json.Error("User not conected!"));
			return;}
			String userID = (String) session.getAttribute("userId");
			if(userID==null){
			response.getWriter().print(new json.Error("User not conected!"));
			return;}

			response.setContentType("text/plain");
			response.getWriter().print(Items.userItems(Integer.parseInt(userID)));}
		catch (Exception e) {
			e.printStackTrace(); //local debug
			response.getWriter().print(new json.Error(e.getMessage()));	}}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);}

}