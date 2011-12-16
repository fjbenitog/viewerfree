package es.viewerfree.gwt.server.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.viewerfree.gwt.shared.ParamKey;

@SuppressWarnings("serial")
public abstract class SpringRemoteServiceServlet extends RemoteServiceServlet {

	protected  HttpServletRequest getHttpServletRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	protected Object getSession(ParamKey key){
		return getHttpServletRequest().getSession().getAttribute(key.toString());
	}
	
	protected void setSession(ParamKey key, Object object){
		getHttpServletRequest().getSession().setAttribute(key.toString(), object);
	}
	

}
