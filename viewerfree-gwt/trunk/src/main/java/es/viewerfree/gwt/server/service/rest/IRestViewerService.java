package es.viewerfree.gwt.server.service.rest;

import java.util.List;

import javax.ws.rs.core.Response;

import es.viewerfree.gwt.shared.dto.AlbumDto;

public interface IRestViewerService {
	
	String getTime();
	
	String login(String authorization)  throws Exception;
	
	List<String> getAlbums(String user) throws Exception;
	
	List<String> getTags(String user) throws Exception;
	
	List<String> getAlbumByTags(String user, String album) throws Exception;
	
	AlbumDto getPictures(String user,String albumName)throws Exception;
	
	Response getImage(String user,String encriptedAlbum, String pic, String imageType) throws Exception;
}
