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

/**
 * Servlet Filter implementation class ClientFilter
 */
@WebFilter("/ClientFilter")
public class ClientFilter implements Filter {

    public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setContentType("text/html");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		String username = (String) httpReq.getSession().getAttribute("username");
		String password = (String) httpReq.getSession().getAttribute("password");
		System.out.println("Inside filter client"+username+"|"+password);
		PrintWriter out = response.getWriter();
		if(username.equals("admin") && password.equals("admin")) {
			out.println("<center>Access denied to Admin user!</center");	
		}		
		else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
