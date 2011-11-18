package es.viewerfree.gwt.shared;


import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;


public class DateUtil {

	
	public static String getToDay(){
		return DateTimeFormat.getFormat("dd MMMM,yyyy").format(new Date());
	}
	
}
