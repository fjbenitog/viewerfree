package es.viewerfree.gwt.server.viewer.impl;

import java.io.File;
import java.io.FilenameFilter;

import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.dto.UserDto;


public class AlbumManagerImpl implements AlbumManager {
	
	private  FilenameFilter _dirfilenameFilter;
	private String _albumPath;
	private String _applicationPath;

	public String[] getAlbums(UserDto user){
		File albumsPath = new File(getPathUser(user));
		if(!albumsPath.exists()){
			albumsPath.mkdirs();
		}
		return  albumsPath.list(_dirfilenameFilter);
	}

	public FilenameFilter getDirfilenameFilter() {
		return _dirfilenameFilter;
	}

	public void setDirfilenameFilter(FilenameFilter dirfilenameFilter) {
		_dirfilenameFilter = dirfilenameFilter;
	}

	private String getPathUser(UserDto user) {
		return getApplicationPath()+"/"+getAlbumPath()+"/"+user.getName();
	}

	public String getAlbumPath() {
		return _albumPath;
	}

	public void setAlbumPath(String albumPath) {
		_albumPath = albumPath;
	}

	public String getApplicationPath() {
		return _applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		_applicationPath = applicationPath;
	}


}
