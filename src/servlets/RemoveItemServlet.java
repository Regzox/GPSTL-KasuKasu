package servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Items;
import servlets.tools.templates.online.OnlinePostServlet;

/**
 * @author ANAGBLA Joan */
public class RemoveItemServlet extends OnlinePostServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		super.epn= new HashSet<>(Arrays.asList(new String[]{"id"}));}

	
	@Override
	public void doBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
			throws Exception {
		response.getWriter().print(Items.removeItem(
				(String)request.getSession().getAttribute("userId"),
				params.get("id")));
	}
       
    

}
