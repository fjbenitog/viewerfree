package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;

public class Logout extends Label {

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	public Logout(String text) {
		setText(text);
		setStyleName("barLink");
		addClickHandler(new LogoutClickHandler());
	}

	private class LogoutClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent clickevent) {
			userService.logout(new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void arg0) {
					Window.Location.replace(GWT.getHostPageBaseURL()+"?locale="+LocaleInfo.getCurrentLocale().getLocaleName());
				}
				
				@Override
				public void onFailure(Throwable throwable) {
					ErrorMessageUtil.getErrorDialogBox(messages.logoutError());
				}
			});
		}
		
	}
	
}
