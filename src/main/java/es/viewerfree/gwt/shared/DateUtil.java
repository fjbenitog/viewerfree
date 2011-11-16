package es.viewerfree.gwt.shared;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	
	public static SimpleDateFormat getDateFormat(){
		return new SimpleDateFormat("dd MMMM,yyyy");
	}
	
	public static SimpleDateFormat getDateFormatDDMMYYYY(){
		return new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public static String getToDay(){
		return getDateFormat().format(new Date());
	}
	
}
