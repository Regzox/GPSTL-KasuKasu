package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Items;


/**
 * * @author Anagbla Jean */
public class SearchItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchItemsServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//TODO LATER CHECK SESSION OK 
			
			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();
			response.setContentType("text/plain");

			if(!map.containsKey("query"))
				throw new Exception("Url is missing parameters"); 
			if(request.getParameter("query").equals(""))
				throw new Exception("Url is missing parameters");
			
			response.getWriter().print(Items.search(request.getParameter("query")));
			
		}catch (Exception e) {
			//TODO ERROR HERE
			//"[object Object] parsererror SyntaxError: JSON.parse: unexpected non-whitespace character after JSON data at line 1 column 14 of the JSON data"
			e.printStackTrace(); //local debug
			response.getWriter().print(new json.Error(e.getMessage()));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}