package com.iiht.evaluation.eloan.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter("/AdminFilter")
public class AdminFilter implements Filter {

    public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		String username = (String) httpReq.getSession().getAttribute("username");
		String password = (String) httpReq.getSession().getAttribute("password");
		System.out.println("Inside filter admin"+username+"|"+password);
		PrintWriter out = response.getWriter();
		if(username.equals("admin")) {
			if(password.equals("admin")) 
			{
				chain.doFilter(request, response);
			}
			else 
			{
				out.println("<center>Incorrect admin credentials!</center");		
			}
		}
		else if(username.equals("") || password.equals("")) {
				out.println("<center>Access denied without login!</center");
		}
		else{
			out.println("<center>Access denied to Client user!</center");
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
