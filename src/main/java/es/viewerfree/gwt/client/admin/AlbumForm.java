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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.common.DialogBoxExt;
import es.viewerfree.gwt.client.common.RefreshWidgetListener;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;

public class AlbumForm extends DialogBoxExt implements ClickHandler,AsyncCallback<Void>{

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private static final Constants constants = GWT.create(Constants.class);

	private final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private Button actionButton;

	private TextBox AlbumField;

	private FlexTable formPanel;

	private LayoutPanel buttonActionPanel;

	private Image loaderImage;

	private int column;

	private int row = 1;
	
	private RefreshWidgetListener refreshWidgetListener;

	public AlbumForm() {
		super();
		Image image = new Image(constants.imagesPath()+constants.imageCloseButton());
		image.setStyleName("close");
		setCloseWidget(image);
		setHTML("<div style='font-weight: bold;font-family: arial,sans-serif;font-size: 15px;'>"+messages.createAlbum()+"</div>");
		add(getFormPanel());
		addActionButton();
		show();
		center();
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
			this.formPanel.setSize("375px", "150px");

			addField(messages.albumLabel(), getAlbumField());
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
			this.actionButton.setText(messages.createAlbum());
			this.actionButton.addClickHandler(this);
		}
		return this.actionButton;
	}

	private TextBox getAlbumField(){
		if(this.AlbumField == null){
			this.AlbumField = new TextBox();
			this.AlbumField.setMaxLength(30);
			this.AlbumField.setWidth("100%");
		}
		return this.AlbumField;
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

	@Override
	public void onClick(ClickEvent clickevent) {
		setErrorMessage("");
		getAlbumField().getElement().getStyle().clearBorderColor();
		StringBuffer message = new StringBuffer();
		if(getAlbumField().getText().isEmpty()){
			message.append("<div>"+messages.adminUserMandatory()+"</div>");
			getAlbumField().getElement().getStyle().setBorderColor("red");
		}
		if(message.length()>0){
			setErrorMessage(message.toString());
			return;
		}
		
		getButtonActionPanel().add(getLoaderImage());
		getButtonActionPanel().setWidgetLeftRight(getLoaderImage(), 230, Unit.PX, 0, Unit.PX);
		getButtonActionPanel().setWidgetTopBottom(getLoaderImage(), 6, Unit.PX, 6, Unit.PX);
		viewerService.createAlbum(getAlbumField().getText(), this);
	}

	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		getAlbumField().setEnabled(enabled);
		getActionButton().setEnabled(enabled);
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
		MessageDialogUtil.getErrorDialogBox(messages.adminCreatingUserError());
		setErrorMessage("");
		setEnabled(true);
		this.hide();
	}

	@Override
	public void onSuccess(Void value) {
		getFormPanel().remove(getLoaderImage());
		this.hide();
		if(refreshWidgetListener!=null){
			refreshWidgetListener.refresh();
		}
	}


	public RefreshWidgetListener getRefreshWidgetListener() {
		return refreshWidgetListener;
	}


	public void setRefreshWidgetListener(RefreshWidgetListener refreshWidgetListener) {
		this.refreshWidgetListener = refreshWidgetListener;
	}

	

}
