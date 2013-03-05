package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.shared.ParamKey;

public class UploadPicForm extends DialogBoxExt {

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private static final Constants constants = GWT.create(Constants.class);

	private Button actionButton;

	private FileUpload picField;

	private FlexTable formPanel;

	private LayoutPanel buttonActionPanel;

	private Image loaderImage;

	private int column;

	private int row = 1;
	
	private FormPanel form;
	
	private VerticalPanel mainPanel;
	
	private String albumName;
	
	private RefreshWidgetListener refreshWidgetListener;


	public UploadPicForm(String albumName) {
		super();
		this.albumName = albumName;
		Image image = new Image(constants.imagesPath()+constants.imageCloseButton());
		image.setStyleName("close");
		setCloseWidget(image);
		setHTML("<div style='font-weight: bold;font-family: arial,sans-serif;font-size: 15px;'>"+messages.uploadPicture()+"</div>");
		getForm().setWidget(getMainPanel());
		addActionButton();
		add(getForm());
		show();
		center();
	}

	private VerticalPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new VerticalPanel();
			this.mainPanel.setSize("375px", "150px");
			this.mainPanel.add(new Hidden(ParamKey.ALBUM_NAME.toString(), this.albumName));
			this.mainPanel.add(getFormPanel());
		}
		return this.mainPanel;
	}

	private FormPanel getForm(){
		if(this.form==null){
			this.form = new FormPanel();
			this.form.setAction(GWT.getHostPageBaseURL()+constants.uploadService());
			this.form.setEncoding(FormPanel.ENCODING_MULTIPART);
			this.form.setMethod(FormPanel.METHOD_POST);
			this.form.addSubmitCompleteHandler(new SubmitCompleteUploadHandler(this));

		}
		return this.form;
	}
	
	private void addActionButton() {
		row++;
		FlexCellFormatter cellFormatter = getFormPanel().getFlexCellFormatter();
		getFormPanel().setWidget(row, 0, getButtonActionPanel());
		cellFormatter.setColSpan(row, 0, 4);
		cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

	private FlexTable getFormPanel(){
		if(this.formPanel == null){
			this.formPanel = new FlexTable();
			this.formPanel.setCellSpacing(6);
			this.formPanel.addStyleName("adminForm");
			this.formPanel.setWidth("100%");
			this.formPanel.setHeight("100%");

			addField(messages.pictureLabel(), getPicField());
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


	private Button getActionButton(){
		if(this.actionButton == null){
			this.actionButton = new Button();
			this.actionButton.setText(messages.uploadPicture());
			this.actionButton.addClickHandler(new UploadClickHandler());
		}
		return this.actionButton;
	}

	private FileUpload getPicField(){
		if(this.picField == null){
			this.picField = new FileUpload();
			this.picField.setName("imageFile");
			this.picField.setWidth("100%");
		}
		return this.picField;
	}


	private LayoutPanel getButtonActionPanel(){
		if(this.buttonActionPanel == null){
			this.buttonActionPanel = new LayoutPanel();
			this.buttonActionPanel.setHeight("30px");
			this.buttonActionPanel.add(getActionButton());
			this.buttonActionPanel.setWidgetLeftRight(getActionButton(), 120, Unit.PX, 0, Unit.PX);
		}
		return this.buttonActionPanel;
	}

	private Image getLoaderImage(){
		if(this.loaderImage == null){
			this.loaderImage = new Image(constants.adminImagesPath()+constants.imageLoader());
		}
		return this.loaderImage;
	}

	

	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		getPicField().setEnabled(enabled);
		getActionButton().setEnabled(enabled);
	}

	private void setErrorMessage(String message) {
		getFormPanel().setHTML(0, 0,"<div class='error'>"+message+"</div>");
		FlexCellFormatter cellFormatter = getFormPanel().getFlexCellFormatter();
		cellFormatter.setColSpan(0, 0, 4);
		cellFormatter.setHorizontalAlignment(0,0, HasHorizontalAlignment.ALIGN_CENTER);
	}

	private class UploadClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent arg0) {
			setErrorMessage("");
			getPicField().getElement().getStyle().clearBorderColor();
			StringBuffer message = new StringBuffer();
			if(getPicField().getFilename().isEmpty()){
				message.append("<div>"+messages.adminUserMandatory()+"</div>");
				getPicField().getElement().getStyle().setBorderColor("red");
			}
			if(message.length()>0){
				setErrorMessage(message.toString());
				return;
			}
			
			getButtonActionPanel().add(getLoaderImage());
			getButtonActionPanel().setWidgetLeftRight(getLoaderImage(), 230, Unit.PX, 0, Unit.PX);
			getButtonActionPanel().setWidgetTopBottom(getLoaderImage(), 6, Unit.PX, 6, Unit.PX);
			getForm().submit();
		}
		
	}
	
	
	
	private class SubmitCompleteUploadHandler implements SubmitCompleteHandler{

		private UploadPicForm form;


		public SubmitCompleteUploadHandler(UploadPicForm form) {
			super();
			this.form = form;
		}


		@Override
		public void onSubmitComplete(SubmitCompleteEvent event) {
			getButtonActionPanel().remove(getLoaderImage());
			String results = event.getResults();
			if(!results.contains("OK")){
				setErrorMessage("<div>"+results+"</div>");
				return;
			}
			if(refreshWidgetListener!=null){
				refreshWidgetListener.refresh();
			}
			form.hide();
		}
		
	}
	
	public RefreshWidgetListener getRefreshWidgetListener() {
		return refreshWidgetListener;
	}

	public void setRefreshWidgetListener(RefreshWidgetListener refreshWigetListener) {
		this.refreshWidgetListener = refreshWigetListener;
	}
	

}
