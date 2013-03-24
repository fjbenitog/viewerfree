package es.viewerfree.gwt.server.service;

import java.util.List;

import es.viewerfree.gwt.shared.service.ServiceException;

public interface ITagService {

	List<String> getTags(String userName, String albumName) throws ServiceException;
	
	List<String> getTags(String userName) throws ServiceException;
	
	List<String> getOtherTags(String userName, String albumName) throws ServiceException;
	
	void addTag(String userName, String albumName, String tagName) throws ServiceException;
	
	List<String> getAlbums(String userName, String tagName) throws ServiceException;
	
	void removeTag(String userName, String albumName, String tagName)  throws ServiceException;
}
