package es.viewerfree.gwt.server.viewer;

import es.viewerfree.gwt.shared.dto.UserDto;

public interface AlbumManager {

	public String[] getAlbums(UserDto user);

}