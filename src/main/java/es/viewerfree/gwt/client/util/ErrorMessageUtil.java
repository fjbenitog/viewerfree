package es.viewerfree.gwt.client.util;

import es.viewerfree.gwt.client.common.ErrorDialogBox;

public class ErrorMessageUtil {

	private static ErrorDialogBox errorDialogBox = new ErrorDialogBox("");

	public static  ErrorDialogBox getErrorDialogBox(String message){
		errorDialogBox.setMessage(message);
		return errorDialogBox;
	}
}
