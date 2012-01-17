package es.viewerfree.gwt.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.common.DialogBoxExt;

public class CreateUserForm extends DialogBoxExt {

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private static final Constants constants = GWT.create(Constants.class);
	
	private Button createButton;
	
	private TextBox userField;
	
	private PasswordTextBox passwordField;
	
	private PasswordTextBox confirmPasswordField;
	
	private TextBox nameField;
	
	private TextBox surnameField;
	
	private TextBox emailField;
	
	private ListBox listProfile;
	
	private FlexTable formPanel;
	
	public CreateUserForm() {
		super(new Image(constants.imagesPath()+constants.imageCloseButton()));
		setHTML("<div style='font-weight: bold;font-family: arial,sans-serif;font-size: 15px;'>"+messages.createUser()+"</div>");
		add(getFormPanel());
	}

	private FlexTable getFormPanel(){
		if(this.formPanel == null){
			this.formPanel = new FlexTable();
			this.formPanel.setCellSpacing(6);
			this.formPanel.addStyleName("adminForm");
			this.formPanel.setSize("700px", "300px");
			FlexCellFormatter cellFormatter = this.formPanel.getFlexCellFormatter();
			this.formPanel.setHTML(0, 0, messages.user());
			this.formPanel.setWidget(0, 1, getUserField());
			this.formPanel.setHTML(1, 0, messages.password());
			this.formPanel.setWidget(1, 1, getPasswordField());
			this.formPanel.setHTML(2, 0, messages.confirmPassword());
			this.formPanel.setWidget(2, 1, getConfirmPasswordField());
			this.formPanel.setHTML(3, 0, messages.email());
			this.formPanel.setWidget(3, 1, getEmailField());
			this.formPanel.setHTML(0, 2, messages.name());
			this.formPanel.setWidget(0, 3, getNameField());
			this.formPanel.setHTML(1, 2, messages.surname());
			this.formPanel.setWidget(1, 3, getSurnameField());
			this.formPanel.setHTML(2, 2, messages.profile());
			this.formPanel.setWidget(2, 3, getListProfile());
			this.formPanel.setWidget(4, 0, getCreateButton());
			cellFormatter.setColSpan(4, 0, 4);
			cellFormatter.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
		return this.formPanel;
	}
	private Button getCreateButton(){
		if(this.createButton == null){
			this.createButton = new Button();
			this.createButton.setText(messages.createUser());
		}
		return this.createButton;
	}
	
	private TextBox getUserField(){
		if(this.userField == null){
			this.userField = new TextBox();
			this.userField.setMaxLength(8);
			this.userField.setWidth("100%");
		}
		return this.userField;
	}
	
	private PasswordTextBox getPasswordField(){
		if(this.passwordField == null){
			this.passwordField = new PasswordTextBox();
			this.passwordField.setMaxLength(8);
			this.passwordField.setWidth("100%");
		}
		return this.passwordField;
	}
	
	private PasswordTextBox getConfirmPasswordField(){
		if(this.confirmPasswordField == null){
			this.confirmPasswordField = new PasswordTextBox();
			this.confirmPasswordField.setMaxLength(8);
			this.confirmPasswordField.setWidth("100%");
		}
		return this.confirmPasswordField;
	}
	
	private TextBox getEmailField(){
		if(this.emailField == null){
			this.emailField = new TextBox();
			this.emailField.setWidth("100%");
		}
		return this.emailField;
	}
	
	private TextBox getNameField(){
		if(this.nameField == null){
			this.nameField = new TextBox();
			this.nameField.setWidth("100%");
		}
		return this.nameField;
	}
	
	private TextBox getSurnameField(){
		if(this.surnameField == null){
			this.surnameField = new TextBox();
			this.surnameField.setWidth("100%");
		}
		return this.surnameField;
	}
	
	private ListBox getListProfile(){
		if(this.listProfile == null){
			this.listProfile = new ListBox();
			this.listProfile.addItem("NORMAL", "0");
			this.listProfile.addItem("ADMIN", "1");
			this.listProfile.setWidth("100%");
		}
		return this.listProfile;
	}
}
