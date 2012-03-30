package es.viewerfree.gwt.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.common.DialogBoxExt;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;

public class CreateUserForm extends DialogBoxExt implements ClickHandler,AsyncCallback<Void>{

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private static final Constants constants = GWT.create(Constants.class);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	private Button createButton;
	
	private TextBox userField;
	
	private PasswordTextBox passwordField;
	
	private PasswordTextBox confirmPasswordField;
	
	private TextBox nameField;
	
	private TextBox surnameField;
	
	private TextBox emailField;
	
	private ListBox listProfile;
	
	private FlexTable formPanel;
	
	private LayoutPanel buttonCreatePanel;
	
	private Image loaderImage;
	
	private AdminPanel adminPanel;
	
	public CreateUserForm(AdminPanel adminPanel) {
		super();
		this.adminPanel = adminPanel;
		Image image = new Image(constants.imagesPath()+constants.imageCloseButton());
		image.setStyleName("close");
		setCloseWidget(image);
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
			this.formPanel.setHTML(1, 0, messages.user());
			this.formPanel.setWidget(1, 1, getUserField());
			this.formPanel.setHTML(2, 0, messages.password());
			this.formPanel.setWidget(2, 1, getPasswordField());
			this.formPanel.setHTML(3, 0, messages.confirmPassword());
			this.formPanel.setWidget(3, 1, getConfirmPasswordField());
			this.formPanel.setHTML(4, 0, messages.email());
			this.formPanel.setWidget(4, 1, getEmailField());
			this.formPanel.setHTML(1, 2, messages.name());
			this.formPanel.setWidget(1, 3, getNameField());
			this.formPanel.setHTML(2, 2, messages.surname());
			this.formPanel.setWidget(2, 3, getSurnameField());
			this.formPanel.setHTML(3, 2, messages.profile());
			this.formPanel.setWidget(3, 3, getListProfile());
			this.formPanel.setWidget(5, 0, getButtonCreatePanel());
			cellFormatter.setColSpan(5, 0, 4);
			cellFormatter.setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
		return this.formPanel;
	}
	private Button getCreateButton(){
		if(this.createButton == null){
			this.createButton = new Button();
			this.createButton.setText(messages.createUser());
			this.createButton.addClickHandler(this);
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
			this.listProfile.addItem("NORMAL", UserProfile.NORMAL.toString());
			this.listProfile.addItem("ADMIN", UserProfile.ADMIN.toString());
			this.listProfile.setWidth("100%");
		}
		return this.listProfile;
	}
	
	
	private LayoutPanel getButtonCreatePanel(){
		if(this.buttonCreatePanel == null){
			this.buttonCreatePanel = new LayoutPanel();
			this.buttonCreatePanel.setHeight("30px");
			this.buttonCreatePanel.add(getCreateButton());
			this.buttonCreatePanel.setWidgetLeftRight(getCreateButton(), 260, Unit.PX, 260, Unit.PX);
		}
		return this.buttonCreatePanel;
	}

	private Image getLoaderImage(){
		if(this.loaderImage == null){
			this.loaderImage = new Image(constants.adminImagesPath()+constants.imageLoader());
		}
		return this.loaderImage;
	}

	@Override
	public void onClick(ClickEvent clickevent) {
		setErrorMessage("");
		getUserField().getElement().getStyle().clearBorderColor();
		getNameField().getElement().getStyle().clearBorderColor();
		getSurnameField().getElement().getStyle().clearBorderColor();
		getPasswordField().getElement().getStyle().clearBorderColor();
		getConfirmPasswordField().getElement().getStyle().clearBorderColor();
		StringBuffer message = new StringBuffer();
		if(getUserField().getText().isEmpty()){
			message.append("<div>"+messages.adminUserMandatory()+"</div>");
			getUserField().getElement().getStyle().setBorderColor("red");
		}
		if(getNameField().getText().isEmpty()){
			message.append("<div>"+messages.adminNameMandatory()+"</div>");
			getNameField().getElement().getStyle().setBorderColor("red");
		}
		if(getPasswordField().getText().isEmpty()){
			message.append("<div>"+messages.adminPasswordMandatory()+"</div>");
			getPasswordField().getElement().getStyle().setBorderColor("red");
		}
		if(!getPasswordField().getText().equals(getConfirmPasswordField().getText())){
			message.append("<div>"+messages.adminConfirmPasswordError()+"</div>");
			getPasswordField().getElement().getStyle().setBorderColor("red");
			getConfirmPasswordField().getElement().getStyle().setBorderColor("red");
		}
		if(message.length()>0){
			setErrorMessage(message.toString());
			return;
		}
		UserDto userDto = new UserDto(getUserField().getText(), getPasswordField().getText());
		userDto.setEmail(getEmailField().getText());
		userDto.setFullName(getNameField().getText());
		userDto.setProfile(UserProfile.valueOf(getListProfile().getValue(getListProfile().getSelectedIndex())));
		userDto.setSurname(getSurnameField().getText());
		getButtonCreatePanel().add(getLoaderImage());
		getButtonCreatePanel().setWidgetLeftRight(getLoaderImage(), 372, Unit.PX, 238, Unit.PX);
		getButtonCreatePanel().setWidgetTopBottom(getLoaderImage(), 6, Unit.PX, 6, Unit.PX);
		
		setEnabled(false);
		userService.createUser(userDto, this);
		
	}
	
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		getUserField().setEnabled(enabled);
		getNameField().setEnabled(enabled);
		getSurnameField().setEnabled(enabled);
		getPasswordField().setEnabled(enabled);
		getConfirmPasswordField().setEnabled(enabled);
		getEmailField().setEnabled(enabled);
		getListProfile().setEnabled(enabled);
		getCreateButton().setEnabled(enabled);
	}

	private void setErrorMessage(String message) {
		getFormPanel().setHTML(0, 0,"<div class='error'>"+message+"</div>");
		FlexCellFormatter cellFormatter = getFormPanel().getFlexCellFormatter();
		cellFormatter.setColSpan(0, 0, 4);
		cellFormatter.setHorizontalAlignment(0,0, HasHorizontalAlignment.ALIGN_CENTER);
	}


	@Override
	public void onFailure(Throwable throwable) {
		getFormPanel().remove(getLoaderImage());
		ErrorMessageUtil.getErrorDialogBox(messages.adminCreatingUserError());
		setErrorMessage("");
		setEnabled(true);
		this.hide();
	}

	@Override
	public void onSuccess(Void arg0) {
		getFormPanel().remove(getLoaderImage());
		this.hide();
		this.adminPanel.refresh();
	}
}
