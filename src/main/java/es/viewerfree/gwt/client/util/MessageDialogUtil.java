package es.viewerfree.gwt.client.util;

import es.viewerfree.gwt.client.common.ErrorDialogBox;

public class MessageDialogUtil {

	private static ErrorDialogBox errorDialogBox = new ErrorDialogBox("");
	
	public static ErrorDialogBox getErrorDialogBox(String message){
		errorDialogBox.setMessage(message);
		errorDialogBox.center();
		errorDialogBox.show();
		errorDialogBox.focus();
		return errorDialogBox;
	}
	
}
