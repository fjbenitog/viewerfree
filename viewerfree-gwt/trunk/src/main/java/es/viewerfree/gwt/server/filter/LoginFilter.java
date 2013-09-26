package es.viewerfree.gwt.server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;


public class LoginFilter extends OncePerRequestFilter {

	
	private String adminPattern;


	public String getadminPattern() {
		return adminPattern;
	}

	public void setadminPattern(String adminPattern) {
		this.adminPattern = adminPattern;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
	throws ServletException, IOException {
		if( !validateUser(request,response) || !allowAdminAccess(request)){
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath());
		}
		else{
			chain.doFilter(request, response);
		}
		
	}
	
	private boolean validateUser(ServletRequest request,ServletResponse response) {
		return getUserDto(request)!=null;
	}

	private UserDto getUserDto(ServletRequest request) {
		return (UserDto)((HttpServletRequest)request).getSession(true).getAttribute(ParamKey.USER.toString());
	}
	
	private boolean allowAdminAccess(HttpServletRequest request){
		return !request.getRequestURI().toString().startsWith(adminPattern)  || getUserDto(request).getProfile().equals(UserProfile.ADMIN);
	}


}
