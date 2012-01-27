package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;

import es.viewerfree.gwt.client.Constants;

public class Logout extends HTML {

	private static final Constants constants = GWT.create(Constants.class);
	
	public Logout(String text) {
		setHTML("<a style='color: #A8A88D;' href=\""+GWT.getHostPageBaseURL()+constants.logoutService()+"\">"+text+"</a>");
		setStyleName("barLink");
	}
	
}
