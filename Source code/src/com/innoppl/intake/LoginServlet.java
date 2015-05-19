package com.innoppl.intake;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.innoppl.utility.ClientHelper;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String user = request.getParameter("j_username");
    	String pass = request.getParameter("j_password");
    	
    	ClientHelper serverUrl = new ClientHelper();
    	
    	String url = "";//serverUrl.BuildServerUrl(user,pass);
	
    	String xml = null;
		xml = serverUrl.getHTML(url);
		if(xml==null || xml.isEmpty()){
			//throw new NullPointerException();
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response); 
		}
		else
    	getServletContext().getRequestDispatcher("/search.jsp").forward(request, response); 
    }

}
