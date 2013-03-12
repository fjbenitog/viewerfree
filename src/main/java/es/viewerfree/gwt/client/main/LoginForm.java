package es.viewerfree.gwt.client.main;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;

public class LoginForm extends FlexTable{


	private TextBox userField;

	private PasswordTextBox passwordField;

	private Button enterButton;

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);

	private final UserServiceAsync userService = GWT.create(UserService.class);

	
	private LoginActionHandler actionHandler;

	
	public LoginForm() {
		setCellSpacing(6);
		setStyleName("loginForm");
		FlexCellFormatter cellFormatter = getFlexCellFormatter();
		setHTML(1, 0, messages.user());
		setWidget(1, 1, getUserField());
		setHTML(2, 0, messages.password());
		setWidget(2, 1, getPasswordField());
		setWidget(3, 0, getEnterButton());
		cellFormatter.setColSpan(3, 0, 2);
		cellFormatter.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
		getUserField().setFocus(true);
		getUserField().selectAll();
	}

	private TextBox getUserField(){
		if(this.userField == null){
			this.userField = new TextBox();
			this.userField.setWidth("100%");
			this.userField.addKeyUpHandler(getLoginActionHandler());
		}
		return this.userField;
	}

	private PasswordTextBox getPasswordField(){
		if(this.passwordField == null){
			this.passwordField = new PasswordTextBox();
			this.passwordField.setWidth("100%");
			this.passwordField.addKeyUpHandler(getLoginActionHandler());
		}
		return this.passwordField;
	}

	private Button getEnterButton(){
		if(this.enterButton == null){
			this.enterButton = new Button();
			this.enterButton.setText(messages.login());
			this.enterButton.addClickHandler(getLoginActionHandler());
		}
		return this.enterButton;
	}

	private LoginActionHandler getLoginActionHandler(){
		if(this.actionHandler == null){
			this.actionHandler = new LoginActionHandler();
		}
		return this.actionHandler;
	}

	private class LoginActionHandler implements ClickHandler, KeyUpHandler{


		public void onClick(ClickEvent clickevent) {
//			loginWithGoogle();
			proccesLogin();
		}

		private void loginWithGoogle() {
			String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
			String CLIENT_ID = "768415354622.apps.googleusercontent.com"; // available from the APIs console
			String BUZZ_PHOTOS_SCOPE = "https://www.googleapis.com/auth/photos";


			AuthRequest req = new AuthRequest(AUTH_URL, CLIENT_ID).withScopes(BUZZ_PHOTOS_SCOPE); // Can specify multiple scopes here
			
			Auth.get().login(req, new com.google.gwt.core.client.Callback<String, Throwable>() {
				
				@Override
				public void onSuccess(String token) {
					System.err.println("Token:"+token);
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					
				}
			});
		}

		public void onKeyUp(KeyUpEvent event) {
			if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
				proccesLogin();
			}
		}

		private void proccesLogin() {
			setEnable(false);
			setMessageLogin("<div class='warning'>"+messages.logingInMessage()+"</div>");
			userService.login(getUserField().getText(),getPasswordField().getText(),new AsyncCallback<Boolean>() {

				public void onSuccess(Boolean result) {
					if(result){
						Window.Location.replace(GWT.getHostPageBaseURL()+constants.viewerAppPath()+"?locale="+LocaleInfo.getCurrentLocale().getLocaleName());
					}else{
						setMessageLogin("<div class='error'>"+messages.userNotValid()+"</div>");
					}
					setEnable(true);
				}
				
				public void onFailure(Throwable throwable) {
					String authenticationError = messages.authenticationError();
					if(constants.debugMode()){
						authenticationError+=throwable.getMessage();
					}
					MessageDialogUtil.getErrorDialogBox(authenticationError);
					setMessageLogin("");
					setEnable(true);
				}
			});
		}


	}
	
	private void setMessageLogin(String message) {
		setHTML(0, 0,message);
		FlexCellFormatter cellFormatter = getFlexCellFormatter();
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0,0, HasHorizontalAlignment.ALIGN_CENTER);
	}

	public void setEnable(boolean enable) {
		getEnterButton().setEnabled(enable);
		getUserField().setEnabled(enable);
		getPasswordField().setEnabled(enable);
	}

	public void focus(){
		getUserField().setFocus(true);
		getUserField().selectAll();
	}
}
