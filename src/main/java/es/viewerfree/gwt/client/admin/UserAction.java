package es.viewerfree.gwt.client.admin;

import com.google.gwt.core.client.GWT;

import es.viewerfree.gwt.client.ViewerFreeMessages;

public enum UserAction {

	CREATE,
	MODIFY;

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	public String getLabel(){
		switch(this){
		case CREATE:
			return messages.createUser();
		case MODIFY:
			return messages.modifyUser();
		default:
			return "";
		}
	}
}
