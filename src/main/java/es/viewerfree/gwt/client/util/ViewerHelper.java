package es.viewerfree.gwt.client.util;

import com.google.gwt.core.client.GWT;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.ParamKey;

public class ViewerHelper {
	
	private static final Constants constants = GWT.create(Constants.class);
	
	public static String createUrlImage(final String albumName, String imageName, Action action) {
		StringBuffer urlImage = new StringBuffer();
		urlImage.append(GWT.getModuleBaseURL()).append(constants.imageService()+"?")
		.append(ParamKey.ALBUM_NAME).append("=").append(albumName)
		.append("&").append(ParamKey.PICTURE_NAME).append("=").append(imageName)
		.append("&").append(ParamKey.ACTION).append("=").append(action);
		return urlImage.toString();
	}
}
