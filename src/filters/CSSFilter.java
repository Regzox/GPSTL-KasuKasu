package filters;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Filtre d'injection de la sidebar dans les pages (expérimental)
 * 
 * @author Daniel
 *
 */

public class CSSFilter implements Filter {
	@SuppressWarnings("unused")
    private FilterConfig filterConfig = null;
	
	/**
	 * Liste de CSS à injecter dans les pages
	 */
	
	private String[] includes = {
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/sidebar.css' />",
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/bootstrap.min.css' />",
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/interface/menu.css' />",
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/interface/sidebar.css' />",
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/interface/navbar.css' />",
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/interface/footer.css' />",
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/interface/flex-blocs.css' />",
			"<link rel='stylesheet' type='text/css' href='/KasuKasu/css/interface/scroll.css' />"
	};

	class CharResponseWrapper extends HttpServletResponseWrapper {
		  protected CharArrayWriter charWriter;

		  protected PrintWriter writer;

		  protected boolean getOutputStreamCalled;

		  protected boolean getWriterCalled;

		  public CharResponseWrapper(HttpServletResponse response) {
		    super(response);

		    charWriter = new CharArrayWriter();
		  }

		  public ServletOutputStream getOutputStream() throws IOException {
		    if (getWriterCalled) {
		      throw new IllegalStateException("getWriter already called");
		    }

		    getOutputStreamCalled = true;
		    return super.getOutputStream();
		  }

		  public PrintWriter getWriter() throws IOException {
		    if (writer != null) {
		      return writer;
		    }
		    if (getOutputStreamCalled) {
		      throw new IllegalStateException("getOutputStream already called");
		    }
		    getWriterCalled = true;
		    writer = new PrintWriter(charWriter);
		    return writer;
		  }

		  public String toString() {
		    String s = null;

		    if (writer != null) {
		      s = charWriter.toString();
		    }
		    return s;
		  }
		}

    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }

    public void destroy()
    {
        filterConfig = null;
    }

    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {    	
        CharResponseWrapper responseWrapper = new CharResponseWrapper(
                (HttpServletResponse)response);
                
        chain.doFilter(request, responseWrapper);
        
        if (responseWrapper.getContentType() != null) {
	        if (responseWrapper.getContentType().contains("text/html")) {
	        	
	            String responseModified = new String(responseWrapper.toString());
	            String headEnd = "</head>";
       	            
	            for (String css : includes) {
	            	responseModified = responseModified.replace(headEnd, css + '\n' + headEnd);
	            }
	            	            
	            response.getWriter().write(responseModified);
	        }
	        else {
	        	if (responseWrapper.toString() != null)
	        		response.getWriter().write(responseWrapper.toString());
	        }
        } else {
        	if (responseWrapper.toString() != null)
        		response.getWriter().write(responseWrapper.toString());
        }
    }
}
