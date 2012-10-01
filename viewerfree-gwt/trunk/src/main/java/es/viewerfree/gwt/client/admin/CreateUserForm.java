package es.viewerfree.gwt.client.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.common.DialogBoxExt;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;

public class CreateUserForm extends DialogBoxExt implements ClickHandler,AsyncCallback<Void>{

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private static final Constants constants = GWT.create(Constants.class);

	private final UserServiceAsync userService = GWT.create(UserService.class);

	private final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

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

	private UserActionPanel usersTablePanel;
	
	private int column;
	
	private int row = 1;
	
	private List<CheckBox> albumsChecks = new ArrayList<CheckBox>();


	public CreateUserForm(UserActionPanel usersTablePanel) {
		super();
		this.usersTablePanel = usersTablePanel;
		Image image = new Image(constants.imagesPath()+constants.imageCloseButton());
		image.setStyleName("close");
		setCloseWidget(image);
		setHTML("<div style='font-weight: bold;font-family: arial,sans-serif;font-size: 15px;'>"+messages.createUser()+"</div>");
		add(getFormPanel());
		viewerService.getAlbums(new AsyncCallback<String[]>() {

			@Override
			public void onSuccess(String[] albums) {
				 addAlbumsField(albums);
				
				addCreateButton();
				
				show();
				center();
			}


			private FlexCellFormatter addAlbumsField(String[] albums) {
				row++;
				FlexCellFormatter cellFormatter = getFormPanel().getFlexCellFormatter();
				getFormPanel().setHTML(row, 0, "<HR/>");
				cellFormatter.setColSpan(row, 0, 4);
				cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
				row++;
				getFormPanel().setHTML(row, 0, messages.albumsLabel());
				row++;
				column = 0;
				for (String album : albums) {
					CheckBox albumCheck = new CheckBox(album);
					albumsChecks.add(albumCheck);
					getFormPanel().setWidget(row, column, albumCheck);
					cellFormatter.setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_LEFT);
					column++;
					if(column>3) {
						column=0;
						row++;
					}
				}
				return cellFormatter;
			}


			@Override
			public void onFailure(Throwable arg0) {
				ErrorMessageUtil.getErrorDialogBox(messages.adminCreatingUserError());
			}
		});
	}
	
	private void addCreateButton() {
		row++;
		FlexCellFormatter cellFormatter = getFormPanel().getFlexCellFormatter();
		getFormPanel().setWidget(row, 0, getButtonCreatePanel());
		cellFormatter.setColSpan(row, 0, 4);
		cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

	private FlexTable getFormPanel(){
		if(this.formPanel == null){
			this.formPanel = new FlexTable();
			this.formPanel.setCellSpacing(6);
			this.formPanel.addStyleName("adminForm");
			this.formPanel.setSize("750px", "300px");

			addField(messages.user(), getUserField());
			addField(messages.name(), getNameField());
			addField(messages.password(), getPasswordField());
			addField(messages.surname(), getSurnameField());
			addField(messages.confirmPassword(), getConfirmPasswordField());
			addField(messages.profile(), getListProfile());
			addField(messages.email(), getEmailField());
		}
		return this.formPanel;
	}

	private void addField(String label, Widget field) {
		this.formPanel.setHTML(row, column, label);
		this.formPanel.setWidget(row, column+1, field);
		column+=2;
		if(column>2){
			column=0;
			row++;
		}
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
		List<String> albums = new ArrayList<String>();
		for (CheckBox albumCheck : albumsChecks) {
			if(albumCheck.getValue()){
				albums.add(albumCheck.getText());
			}
		}
		userDto.setAlbums(albums);
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
		this.usersTablePanel.refresh();
	}
}
