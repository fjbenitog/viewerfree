package es.viewerfree.gwt.server.util;

import javax.servlet.http.HttpServletRequest;

import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.UserDto;

public class ServletUtils {

	private ServletUtils(){}
	
	public static Action getAction(HttpServletRequest request, ParamKey key) {
		return Action.valueOf(request.getParameter(key.toString()));
	}
	
	public static String getEncriptedParameter(HttpServletRequest request, ParamKey key) {
		String picName = request.getParameter(key.toString());
		return CryptoUtil.decrypt(picName, getUserDto(request).getName());
	}
	
	public static  UserDto getUserDto(HttpServletRequest request) {
		return (UserDto) request.getSession().getAttribute(ParamKey.USER.toString());
	}
}
