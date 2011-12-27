package es.viewerfree.gwt.server.viewer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import es.viewerfree.gwt.shared.dto.UserDto;

public interface AlbumManager {

	public String[] getAlbums(UserDto user);
	
	public String[] getPictures(UserDto user,String albumName);
	
	public void getCachedPicture(UserDto user,String albumName, String fotoName,String cachedPath,int height,OutputStream out) throws IOException;

	public void getPicture(File file, OutputStream out) throws IOException;
	
	public void getDefaultImage(OutputStream out) throws IOException;
	
	public void getPicture(UserDto user,String albumName, String fotoName,
			OutputStream out) throws IOException;
}