package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.ViewerFreeMessages;

public class ErrorDialogBox extends DialogBox {

	private Button closeButton;
	
	private VerticalPanel dialogVPanel;
	
	private HTML message;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	
	public ErrorDialogBox(String strMessage){
		this();
		getMessage().setHTML(strMessage);
	}
	
	public ErrorDialogBox() {
		super();
		setHTML("<div style='font-weight: bold;'>"+messages.errorTitle()+"</div>");
		setAnimationEnabled(true);
		setWidget(getDialogVPanel());
	}
	
	private Button getCloseButton(){
		if(this.closeButton==null){
			this.closeButton = new Button("Close");
			this.closeButton.getElement().setId("closeButton");
			this.closeButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
		}
		return this.closeButton;
	}
	
	private VerticalPanel getDialogVPanel(){
		if(this.dialogVPanel==null){
			this.dialogVPanel = new VerticalPanel();
			dialogVPanel.add(getMessage());
			dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
			dialogVPanel.add(getCloseButton());
		}
		return this.dialogVPanel;
	}
	
	public HTML getMessage(){
		if(this.message==null){
			this.message = new HTML("");
			this.message.setStyleName("errorDialog");
		}
		return this.message;
	}
	
	public void focus(){
		getCloseButton().setFocus(true);
	}


}
