package servlets.tools.templates;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Servlet {	
	public void doBusiness(HttpServletRequest request, 
			HttpServletResponse response,Map<String, String>params)
					throws Exception ;
}