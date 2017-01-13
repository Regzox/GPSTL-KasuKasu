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

public class ConnectionFilter implements Filter {
	
	private final static String USER_ID = "userId";

	public void init(FilterConfig configuration) throws ServletException {}
	
	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		System.out.println("CONNECTION FILTER : " + path);
		
		if	(	path.startsWith("/css")	||
				path.startsWith("/js")	||
				path.startsWith("/fragments") ||
				path.startsWith("/images")
			) 
		{
			chain.doFilter(request, response);
		}
		
		if (session.getAttribute(USER_ID) == null)
		{
			request.getRequestDispatcher("/disconnect").forward(request, response);
		}
		
		chain.doFilter(request, response);
	}

}
