package es.viewerfree.gwt.server.viewer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


public interface AlbumManager {

	public String[] getAlbums();
	
	public String[] getPictures(String albumName);
	
	public void getCachedPicture(String albumName,String fotoName, String cachedPath,int height,OutputStream out) throws Exception;

	public void getPicture(File file, OutputStream out) throws IOException;
	
	public void getDefaultImage(OutputStream out) throws IOException;
	
	public void getPicture(String albumName,String fotoName, OutputStream out) throws IOException;
	
	public void rotateCachedPicture(String albumName,String[] cachedPaths,String fotoName, int angle) throws Exception;
}