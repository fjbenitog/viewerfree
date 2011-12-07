package es.viewerfree.gwt.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;

public class LoginForm extends FlexTable{


	private TextBox userField;

	private PasswordTextBox passwordField;

	private Button enterButton;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	public LoginForm() {
		setCellSpacing(6);
		setStyleName("loginForm");
		FlexCellFormatter cellFormatter = getFlexCellFormatter();


		// Add some standard form options
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
			this.userField .setWidth("100%");
		}
		return this.userField;
	}

	private PasswordTextBox getPasswordField(){
		if(this.passwordField == null){
			this.passwordField = new PasswordTextBox();
			this.passwordField .setWidth("100%");
		}
		return this.passwordField;
	}

	private Button getEnterButton(){
		if(this.enterButton == null){
			this.enterButton = new Button();
			this.enterButton.setText(messages.login());
			this.enterButton.addClickHandler(new LoginActionHandler());
		}
		return this.enterButton;
	}

	

	private class LoginActionHandler implements ClickHandler{

		public void onClick(ClickEvent clickevent) {
			userService.login(getUserField().getText(),getPasswordField().getText(),new AsyncCallback<Boolean>() {
				
				public void onSuccess(Boolean result) {
					String message = "";
					if(result){
						message = "Usuario correcto";
					}else{
						message = "Usuario no es valido";
					}
					setHTML(0, 0,"<div class='error'>"+message+"</div>");
					FlexCellFormatter cellFormatter = getFlexCellFormatter();
					cellFormatter.setColSpan(0, 0, 2);
					cellFormatter.setHorizontalAlignment(0,0, HasHorizontalAlignment.ALIGN_CENTER);
				}
				
				public void onFailure(Throwable throwable) {
					setHTML(0, 0,throwable.getMessage());
					FlexCellFormatter cellFormatter = getFlexCellFormatter();
					cellFormatter.setColSpan(0, 0, 2);
					cellFormatter.setHorizontalAlignment(0,0, HasHorizontalAlignment.ALIGN_CENTER);
				}
			});
			
		}
		
	}
	
	
	public void focus(){
		getUserField().setFocus(true);
		getUserField().selectAll();
	}
}
