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
public class SearchItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchItemsServlet() {super();}

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

			if(!map.containsKey("query"))
				throw new Exception("Url is missing parameters"); 
			if(request.getParameter("query").equals(""))
				throw new Exception("Url is missing parameters");
			
			response.getWriter().print(Items.searchItems(
					request.getParameter("query"),userID));
			
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(new json.Error(e.getMessage()));}}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);}

}