package es.viewerfree.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import es.viewerfree.gwt.shared.dto.AlbumDto;

@RemoteServiceRelativePath("springGwtServices/viewer")
public interface ViewerService extends RemoteService{

	String[] getUserAlbums();
	
	String[] getAlbums();
	
	AlbumDto getPictures(String albumName);
	
	void rotatePicture(int angle,String albumName,String pictureName) throws Exception;
}
