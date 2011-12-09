package es.viewerfree.gwt.server.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public abstract class SpringRemoteServiceServlet extends RemoteServiceServlet {

	protected  HttpServletRequest getHttpServletRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	protected Object getSession(String key){
		return getHttpServletRequest().getSession().getAttribute(key);
	}
	
	protected void setSession(String key, Object object){
		getHttpServletRequest().getSession().setAttribute(key, object);
	}
	

}
