package es.viewerfree.gwt.server.dao;

import java.util.List;

import es.viewerfree.gwt.server.entities.User;

public interface ITagDao {
	
	public void addTag(User user,String albumName, String tagName);
	
	public List<String> getAlbumsByTag(String userName, String tagName);
}
