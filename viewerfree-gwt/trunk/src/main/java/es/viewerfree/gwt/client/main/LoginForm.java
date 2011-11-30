package es.viewerfree.gwt.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;

public class LoginForm extends FlexTable{


	private TextBox userField;

	private PasswordTextBox passwordField;

	private Button enterButton;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	public LoginForm() {
		setCellSpacing(6);
		setStyleName("loginForm");
		FlexCellFormatter cellFormatter = getFlexCellFormatter();

		setHTML(0, 0, messages.login());
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		// Add some standard form options
		setHTML(1, 0, messages.user());
		setWidget(1, 1, getUserField());
		setHTML(2, 0, messages.password());
		setWidget(2, 1, getPasswordField());
		setWidget(3, 0, getEnterButton());
		cellFormatter.setColSpan(3, 0, 2);
		cellFormatter.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
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
		}
		return this.enterButton;
	}

}
