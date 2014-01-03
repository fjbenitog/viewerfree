package es.viewerfree.gwt.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.viewerfree.gwt.shared.service.ServiceException;

@Service
public interface IAlbumService {

	List<String> getAlbums(String user) throws ServiceException;
}
