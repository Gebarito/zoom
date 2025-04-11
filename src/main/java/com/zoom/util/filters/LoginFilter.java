package com.zoom.util.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.zoom.modelo.Usuario;

@WebFilter(urlPatterns = "/restricted/*", servletNames = "{Faces Servlet}")
public class LoginFilter extends AbstractFilter implements Filter{

	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
				
		HttpServletRequest req = (HttpServletRequest) request;
		@SuppressWarnings("unused")
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		Usuario user = (Usuario) session.getAttribute("usuario");
				
		if (session.isNew() || user == null) {
			doLogin(request, response, req);
		} else {
			chain.doFilter(request, response);
		}
	}	
	
	public void destroy() {}
}
