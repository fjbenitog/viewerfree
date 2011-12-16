package es.viewerfree.gwt.server.filter;

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

import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.UserDto;


public class LoginFilter implements Filter {

	private static final String LOGIN_URL = "/";
	@SuppressWarnings("unused")
	private FilterConfig _filterConfig = null; 

	public void destroy() {
		_filterConfig =null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if( !validateUser(request,response)){
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+LOGIN_URL);
		}
		else{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		_filterConfig = filterConfig;
	}

	private boolean validateUser(ServletRequest request,ServletResponse response) {
		HttpSession httpSession= ((HttpServletRequest)request).getSession(true);
		UserDto user = (UserDto)httpSession.getAttribute(ParamKey.USER.toString());
		if(user==null){
			return false;
		}
		return true;
	}

}
