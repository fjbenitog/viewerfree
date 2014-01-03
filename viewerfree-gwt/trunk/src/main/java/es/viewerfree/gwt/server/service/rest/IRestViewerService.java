package es.viewerfree.gwt.server.service.rest;

import java.util.List;

public interface IRestViewerService {
	
	String getTime();
	
	String login(String authorization)  throws Exception;
	
	List<String> getAlbums(String user) throws Exception;
	
	List<String> getTags(String user) throws Exception;
	
	List<String> getAlbumByTags(String user, String album) throws Exception;
}
