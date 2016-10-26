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
 * Filtre qui ajoute systématiquement dans les pages la liste de scripts comprises dans l'attribut 'includes'
 * 
 * @author Daniel
 *
 */

public class JSFilter implements Filter
{
	@SuppressWarnings("unused")
    private FilterConfig filterConfig = null;
	
	/**
	 * Liste des fichiers JS à ajouter à toutes les pages du site.
	 */
	
	private String[] includes = {
			"<script type='text/javascript' src='/KasuKasu/js/paths.js'></script>"
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
        CharResponseWrapper wrappedResponse = new CharResponseWrapper(
                (HttpServletResponse)response);
                
        chain.doFilter(request, wrappedResponse);
        
        if (wrappedResponse.getContentType() != null) {
	        if (wrappedResponse.getContentType().contains("text/html")) {
	        	
	            String responseModified = new String(wrappedResponse.toString());
	            String headEnd = "</head>";
       
	            for (String js : includes)
	            	responseModified = responseModified.replace(headEnd, js + '\n' + headEnd);
	            
	            response.getWriter().write(responseModified);
	        }
        } else {
        	if (wrappedResponse.toString() != null)
        		response.getWriter().write(wrappedResponse.toString());
        }
    }
}
