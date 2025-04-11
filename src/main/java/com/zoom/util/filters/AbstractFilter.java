package com.zoom.util.filters;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractFilter {

	public void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req)
			throws IOException, ServletException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("/restricted/home/Login.xhtml");
		dispatcher.forward(request,  response);
	}
	
	public void acessoNegado(ServletRequest request, ServletResponse response, HttpServletRequest req) 
			throws IOException, ServletException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("acessoNegado");
		dispatcher.forward(request,  response);
	}

}
