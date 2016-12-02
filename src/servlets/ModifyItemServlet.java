package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.Items;


/**
 * * @author Anagbla Joan */
public class ModifyItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModifyItemServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HttpSession session=request.getSession();
			if(session ==null)
			{response.getWriter().print(new json.Error("User not conected!"))
				;return;}
			String userID = (String) session.getAttribute("userId");
			if(userID ==null){
				response.getWriter().print(new json.Error("User not conected!"));
				return;}
			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();
			response.setContentType("text/plain");

			if(!map.containsKey("id"))
				throw new Exception("Url is missing parameters"); 
			if( request.getParameter("id").equals(""))
				throw new Exception("Url is missing parameters.");

			response.getWriter().print(
					Items.updateItem(
							userID,
							request.getParameter("id"),
							request.getParameter("title"),
							request.getParameter("description")
							)
					);

		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(new json.Error(e.getMessage()));}}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);}

}