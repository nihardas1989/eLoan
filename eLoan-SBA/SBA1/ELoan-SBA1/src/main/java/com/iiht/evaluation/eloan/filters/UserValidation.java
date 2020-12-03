package com.iiht.evaluation.eloan.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class UserValidation
 */
@WebFilter("/UserValidation")
public class UserValidation implements Filter {
	private ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		String username = (String) httpReq.getSession().getAttribute("username");
		String password = (String) httpReq.getSession().getAttribute("password");
		System.out.println("Inside user validation"+username+"|"+password);
		System.out.println("Inside user validation param"+request.getParameter("username")+"|"+request.getParameter("password"));
		PrintWriter out = response.getWriter();
		if(request.getParameter("username")==null || request.getParameter("password")==null) {
			if(username.equals("") || password.equals("")) {
				out.println("<center>Access denied without login!</center");
			}
			else {
				chain.doFilter(request, response);
			}
		}
		else if(username.equals("") || password.equals("")) {
			if(request.getParameter("username").equals("") || request.getParameter("password").equals("")) {
				out.println("<center>Username or password empty!</center");
			}
			else {
				chain.doFilter(request, response);
			}
		}	
	}
	

    public void destroy() {
		// TODO Auto-generated method stub
	}

}

