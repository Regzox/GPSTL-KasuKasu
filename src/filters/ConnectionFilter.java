package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.upmc.file.Resource;

public class ConnectionFilter implements Filter {
	
	private final static String USER_ID = "userId";
	private Resource resource;

	public void init(FilterConfig configuration) throws ServletException {
		resource = ((Resource) configuration.getServletContext().getAttribute("resource"));
	}
	
	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		if	(	path.startsWith("/css")	||
				path.startsWith("/js")	||
				path.startsWith("/fragments") ||
				path.startsWith("/images")
			) 
		{
			chain.doFilter(request, response);
			return;
		}
		
		System.out.println(request.getRequestURI());
		
		if (session.getAttribute(USER_ID) == null)
		{
			if ( !request.getRequestURI().contains("portal.jsp") && request.getRequestURI().contains("/restricted/") ) {
				System.out.println("REDIRECT !");
				response.sendRedirect(resource.absolutePath("portal_jsp"));
			}
		} 
		else 
		{
			if ( !path.contains("/restricted/") && path.contains(".jsp"))
			{
				System.out.println("REDIRECT !");
				response.sendRedirect(resource.absolutePath("dashboard_jsp"));
			}
		}
		
		chain.doFilter(request, response);
	}

}
