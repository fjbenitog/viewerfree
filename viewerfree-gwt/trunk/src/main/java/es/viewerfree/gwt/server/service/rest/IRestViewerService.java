package es.viewerfree.gwt.server.service.rest;

import java.util.List;

import javax.ws.rs.core.Response;

import es.viewerfree.gwt.shared.dto.AlbumDto;

public interface IRestViewerService {
	
	String getTime();
	
	String login(String authorization) ;
	
	List<String> getAlbums(String user) ;
	
	List<String> getTags(String user) ;
	
	List<String> getAlbumByTags(String user, String album) ;
	
	AlbumDto getPictures(String user,String albumName);
	
	Response getImage(String user,String encriptedAlbum, String pic, String imageType);
}
