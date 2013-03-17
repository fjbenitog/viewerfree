package es.viewerfree.gwt.server.viewer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import es.viewerfree.gwt.server.dao.DaoException;


public interface AlbumManager {

	public List<String> getAlbums();
	
	public List<String> getPictures(String albumName);
	
	public void getCachedPicture(String albumName,String fotoName, String cachedPath,int height,OutputStream out) throws Exception;

	public void getPicture(File file, OutputStream out) throws IOException;
	
	public void getDefaultImage(OutputStream out) throws IOException;
	
	public void getPicture(String albumName,String fotoName, OutputStream out) throws IOException;
	
	public void rotateCachedPicture(String albumName,String[] cachedPaths,String fotoName, int angle) throws Exception;

	public void createAlbum(String albumName);
	
	public void deletePicture(String albumName,String pciture) throws IOException;
	
	public void addTag(String userName,String albumName, String tagName) throws DaoException;
	
	public List<String> getAlbums(String userName, String tagName);
}