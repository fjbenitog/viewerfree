package es.viewerfree.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import es.viewerfree.gwt.shared.dto.AlbumDto;

@RemoteServiceRelativePath("springGwtServices/viewer")
public interface ViewerService extends RemoteService{

	List<String> getUserAlbums();
	
	List<String> getAlbums();
	
	AlbumDto getPictures(String albumName);
	
	void rotatePicture(int angle,String albumName,String pictureName) throws Exception;
	
	void createAlbum(String albumName);
}
