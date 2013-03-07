package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.ViewerFreeMessages;

public class ConfirmDialogBox extends DialogBox {

	private Button cancelButton;
	
	private Button acceptButton;
	
	private VerticalPanel dialogVPanel;
	
	private HorizontalPanel buttonsPanel;
	
	private HTML message;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	public ConfirmDialogBox(String strMessage){
		this();
		getMessage().setHTML(strMessage);
	}
	
	public ConfirmDialogBox() {
		super();
		setHTML("<div style='font-weight: bold;'>"+messages.confirmLabel()+"</div>");
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setWidget(getDialogVPanel());
	}
	
	public void addAcceptClickHandler(ClickHandler clickHandler){
		getAcceptButton().addClickHandler(clickHandler);
	}
	
	private Button getCancelButton(){
		if(this.cancelButton==null){
			this.cancelButton = new Button(messages.cancelLabel());
			this.cancelButton.getElement().setId("cancelButton");
			this.cancelButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
		}
		return this.cancelButton;
	}
	
	private Button getAcceptButton(){
		if(this.acceptButton==null){
			this.acceptButton = new Button(messages.AcceptLabel());
			this.acceptButton.getElement().setId("acceptButton");
		}
		return this.acceptButton;
	}
	
	private VerticalPanel getDialogVPanel(){
		if(this.dialogVPanel==null){
			this.dialogVPanel = new VerticalPanel();
			dialogVPanel.getElement().setAttribute("cellpadding", "15");
			dialogVPanel.add(getMessage());
			dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
			dialogVPanel.add(getButtonsPanel());
		}
		return this.dialogVPanel;
	}
	
	private HorizontalPanel getButtonsPanel(){
		if(this.buttonsPanel==null){
			this.buttonsPanel = new HorizontalPanel();
			buttonsPanel.setWidth("100%");
			buttonsPanel.add(getCancelButton());
			buttonsPanel.setCellHorizontalAlignment(getCancelButton(), HorizontalPanel.ALIGN_LEFT);
			buttonsPanel.add(getAcceptButton());
			buttonsPanel.setCellHorizontalAlignment(getAcceptButton(), HorizontalPanel.ALIGN_RIGHT);
		}
		return this.buttonsPanel;
	}
	
	public HTML getMessage(){
		if(this.message==null){
			this.message = new HTML("");
			this.message.setStyleName("confirmDialog");
		}
		return this.message;
	}
	
	public void focus(){
		getCancelButton().setFocus(true);
	}

	public void setMessage(String message){
		getMessage().setHTML(message);
	}
	
	public void invoke(){
		center();
		show();
		focus();
	}

}
