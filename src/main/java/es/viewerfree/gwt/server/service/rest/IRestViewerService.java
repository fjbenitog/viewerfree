package es.viewerfree.gwt.server.service.rest;

public interface IRestViewerService {
	
	String getTime();
	
	String login(String authorization)  throws Exception;

}
