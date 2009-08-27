package com.netxs.Zewar;

import java.sql.*;
 
//import javax.sql.*;
import java.util.Properties;
//import java.io.*;
import javax.servlet.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.netxs.Zewar.DataSources.*;

public class SecurityFilter implements Filter {

	private Properties securityMapping =  new Properties();
	private Connection connection = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			// Read properties file.
			this.securityMapping.load(this.getClass().getResourceAsStream("/Security.properties"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		//release resources obtained in init here
		securityMapping.clear();
		securityMapping = null;
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,	FilterChain chain) throws IOException, ServletException {

		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);
		//ensure that the session is created if not present already
		String requestUri = request.getRequestURI(); //obtain the requested URI
		requestUri = requestUri.replaceFirst(request.getContextPath(),"");
		byte UriIndicator = 0, mask = 0;
		//Logout 
		if (requestUri.endsWith("Logout.do")) {
			request.removeAttribute("hasAuthenticated");
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath()+"/System/Authenticate.do");
			return;
		}

		//Authenticate
		Boolean hasAuthenticated = (session.getAttribute("hasAuthenticated")==null) ? Boolean.FALSE : (Boolean)session.getAttribute("hasAuthenticated");
		if (! (hasAuthenticated.booleanValue())) {
			if (requestUri.endsWith("Authenticate.do")) {
				if (authenticateUser(request)){
					response.sendRedirect(request.getContextPath()+"/User/Home.do");
				} else {
					chain.doFilter(request, response);
				}	
			} else {
				response.sendRedirect(request.getContextPath()+"/System/Authenticate.do");
			}
		
		}
		else {
			//Authorize: Check user access allow
			try {
				UriIndicator= Byte.parseByte(this.securityMapping.getProperty(requestUri));	
			} catch (Exception e){
				UriIndicator = 0;
			}
			mask = Byte.parseByte((String)session.getAttribute("roleId"));
			System.out.print("URI="+requestUri+", Indicator="+UriIndicator+", mask="+mask+", Result="+(int)(UriIndicator&mask));
			if ((UriIndicator&mask)==0) {
				RequestDispatcher requestDispatcher =	request.getRequestDispatcher("/System/AccessDenied.do");
				requestDispatcher.forward(request, response);
				return;
			}
		}
		
		chain.doFilter(request, response);
		return;

	}
	
	public boolean authenticateUser(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(true);

			ResultSet resultSet = null;
			Statement statement = null; 
			connection = (Connection) new DBConnection().getMyPooledConnection();
//			request.setAttribute("error",Boolean.toString(connection==null));
			statement = connection.createStatement();
			String loginName = request.getParameter("loginName");
			String loginPassword = request.getParameter("loginPassword");
			if(loginName==null || loginName.trim().length()==0 || loginPassword==null || loginPassword.trim().length()==0 ) {
				request.setAttribute("error", "Login Id or Password required");
				return false;
			}
			resultSet = statement.executeQuery("SELECT USER_ID , ROLE_ID, LOGIN_NAME, LOGIN_PASSWORD  FROM USERS WHERE LOGIN_NAME='"+request.getParameter("loginName")+"' AND LOGIN_PASSWORD='"+request.getParameter("loginPassword")+"'");
			if (resultSet.next()) {
				if (resultSet.getString("LOGIN_NAME").equals(request.getParameter("loginName")) && resultSet.getString("LOGIN_PASSWORD").equals(request.getParameter("loginPassword"))) { 
					session.setAttribute("userId",resultSet.getString("USER_ID"));
					session.setAttribute("roleId",resultSet.getString("ROLE_ID"));
					session.setAttribute("hasAuthenticated",Boolean.TRUE); 
					resultSet.close();
					statement.close(); 
					connection.close();
					return true;
				}
			} else {
				request.setAttribute("error", "Invalid Login Id or Password .");
				return false;
			}
		} catch (Exception e) {  
			e.printStackTrace();
			request.setAttribute( "error",e.getMessage());
		}
		return false;
	}
}
