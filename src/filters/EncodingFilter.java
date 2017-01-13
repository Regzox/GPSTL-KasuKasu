package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

    public static final String ENCODING = "encoding";
    private String encoding;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter(ENCODING);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, 
                         FilterChain filterChain) 
                         throws IOException, ServletException {
    	System.out.println("ENCODING : " + encoding);
    	req.setCharacterEncoding(encoding);
    	System.out.println(req.getCharacterEncoding());
    	resp.setCharacterEncoding(encoding);
        //resp.setContentType("text/html; charset="+encoding);
        System.out.println(resp.getCharacterEncoding());
        filterChain.doFilter(req, resp);
    }

    public void destroy() {}

}
