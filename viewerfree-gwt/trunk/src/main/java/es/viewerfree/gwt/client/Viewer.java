package es.viewerfree.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.common.BaseEntryPoint;
import es.viewerfree.gwt.client.common.ErrorDialogBox;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;

public class Viewer extends BaseEntryPoint {
	  
	private VerticalPanel contentPanel;
	
	private HorizontalPanel barPanel;
	
	private Label logoutLink;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	private ErrorDialogBox errorDialogBox;

	@Override
	protected Panel getContentPanel() {
		if(this.contentPanel==null){
			this.contentPanel = new VerticalPanel();
			this.contentPanel.setWidth("100%");
			this.contentPanel.add(getBarPanel());
		}
		return this.contentPanel ;
	}

	private HorizontalPanel getBarPanel(){
		if(this.barPanel==null){
			this.barPanel = new HorizontalPanel();
			this.barPanel.add(getLogoutLink());
			this.barPanel.setCellHorizontalAlignment(getLogoutLink(), HorizontalPanel.ALIGN_RIGHT);
			this.barPanel.setStyleName("barPanel");
		}
		return this.barPanel;
	}

	private Label getLogoutLink(){
		if(this.logoutLink==null){
			this.logoutLink = new Label(messages.logout());
			this.logoutLink.setStyleName("logoutLink");
			this.logoutLink.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent clickevent) {
					userService.logout(new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void arg0) {
							Window.Location.replace(GWT.getHostPageBaseURL()+"?locale="+LocaleInfo.getCurrentLocale().getLocaleName());
						}
						
						@Override
						public void onFailure(Throwable throwable) {
							getErrorDialogBox().center();
							getErrorDialogBox().show();
							getErrorDialogBox().focus();
						}
					});
				}
			});
		}
		return this.logoutLink;
	}

	private ErrorDialogBox getErrorDialogBox(){
		if(this.errorDialogBox == null){
			this.errorDialogBox = new ErrorDialogBox(messages.logoutError());
		}
		return this.errorDialogBox;
	}
	
}
