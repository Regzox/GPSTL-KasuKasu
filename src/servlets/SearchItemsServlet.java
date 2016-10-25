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

			@SuppressWarnings("unchecked")
			Map<String,String[]> map=request.getParameterMap();
			response.setContentType("text/plain");

			if(map.containsKey("query")
					&& !request.getParameter("query").equals(""))
				response.getWriter().print(
						Items.search(request.getParameter("query")));
			else 
				throw new Exception("Wrong Url! Missing parameters\n Il manque des parametres dans l'URL!");

		}catch (Exception e) {
			e.printStackTrace(); //local debug
			response.getWriter().print(new json.Error(e.getMessage()));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);}

}