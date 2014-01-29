package es.viewerfree.gwt.server.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import es.viewerfree.gwt.shared.dto.AlbumDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@Service
public interface IAlbumService {

	List<String> getAlbums(String user) throws ServiceException;
	
	AlbumDto getPictures(String user, String albumName);
	
	void uploadPictures(InputStream in, String albumName, String imageName) throws ServiceException;
}
