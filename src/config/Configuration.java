package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.upmc.file.Resource;

@WebListener
public class Configuration implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext(); 
		String origin = servletContext.getRealPath("/");
		Resource rsrc = new Resource(origin);
		servletContext.setAttribute("resource", rsrc);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
