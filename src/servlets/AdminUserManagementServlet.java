package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.upmc.file.Resource;
import services.User;

public class AdminUserManagementServlet extends HttpServlet{

	private Resource resource;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		resource = ((Resource) this.getServletContext().getAttribute("resource"));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			
			String action = request.getParameter("action");
			String userId = request.getParameter("userId");
			if(action==null || userId==null)
				return;
			
			String isAdmin =(String) request.getSession().getAttribute("isAdmin");
			if(isAdmin == null)
				response.sendRedirect(resource.absolutePath("memberprofile_jsp")+"?id="+userId);
			if(!isAdmin.equals("true"))
				response.sendRedirect(resource.absolutePath("memberprofile_jsp")+"?id="+userId);
			
			System.out.println(action +" : "+userId);
			switch(action){
			case "1" :
					User.freeze(userId);
					break;
			case "2" :
					User.unfreeze(userId);
					break;
			case "3" :
					User.ban(userId);
					break;
			default:
				break;
			}
			
			response.sendRedirect(resource.absolutePath("memberprofile_jsp")+"?id="+userId);
			
		} catch (Exception e){
			e.printStackTrace();
			response.getWriter().print(new json.Error("Une erreur s'est produite."));}}
			
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);}
}
