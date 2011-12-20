package es.viewerfree.gwt.client.util;

import es.viewerfree.gwt.client.common.ErrorDialogBox;

public class ErrorMessageUtil {

	private static ErrorDialogBox errorDialogBox = new ErrorDialogBox("");

	public static void getErrorDialogBox(String message){
		errorDialogBox.setMessage(message);
		errorDialogBox.center();
		errorDialogBox.show();
		errorDialogBox.focus();
	}
}
