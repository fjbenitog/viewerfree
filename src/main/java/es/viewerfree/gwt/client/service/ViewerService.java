package es.viewerfree.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import es.viewerfree.gwt.shared.dto.AlbumDto;

@RemoteServiceRelativePath("springGwtServices/viewer")
public interface ViewerService extends RemoteService{

	String[] getAlbums();
	
	AlbumDto getPictures(String albumName);
}
