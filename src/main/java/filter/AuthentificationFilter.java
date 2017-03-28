package filter;

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

/**
 * Servlet Filter implementation class AuthentificationFilter
 */
public class AuthentificationFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		
		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		System.out.println(req.getServletPath());
		/*
		if(req.getServletPath().equals("/authentification/input") ||  
		   req.getServletPath().equals("/authentification/execute") ||
		   req.getServletPath().equals("/") ||
		   req.getServletPath().equals("/index.html")) {
			chain.doFilter(request, response);
			return;
		}	*/
			
		HttpSession session = req.getSession();
		if(session.getAttribute("user") != null) {
			chain.doFilter(request, response);
		}
		else {
			res.sendRedirect("../authentification/login");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
