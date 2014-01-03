package es.viewerfree.gwt.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.viewerfree.gwt.server.service.IAlbumService;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@Service
public class AlbumService implements IAlbumService {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private AlbumManager albumManager;

	@Override
	public List<String> getAlbums(String user) throws ServiceException {
		UserDto userDto = userService.getUser(user);
		List<String> albums = albumManager.getAlbums();
		List<String> albumsList = new ArrayList<String>();
		for (String album : userDto.getAlbums()) {
			if(albums.contains(album)){
				albumsList.add(album);
			}
		}
		return albumsList ;
	}

}
