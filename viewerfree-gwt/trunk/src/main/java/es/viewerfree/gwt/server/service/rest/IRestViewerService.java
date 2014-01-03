package es.viewerfree.gwt.server.service.rest;

import java.util.List;

public interface IRestViewerService {
	
	String getTime();
	
	String login(String authorization)  throws Exception;
	
	List<String> getAlbums(String token) throws Exception;
}
