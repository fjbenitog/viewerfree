package es.viewerfree.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/viewer")
public interface ViewerService extends RemoteService{

	String[] getAlbums();
	
	String[] getPictures(String albumName);
}
