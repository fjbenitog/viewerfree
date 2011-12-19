package es.viewerfree.gwt.server;

import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.server.service.SpringRemoteServiceServlet;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.UserDto;

@SuppressWarnings("serial")
public class ViewerServiceImpl extends SpringRemoteServiceServlet implements ViewerService {

	private AlbumManager albumManager;
	
	public AlbumManager getAlbumManager() {
		return albumManager;
	}

	public void setAlbumManager(AlbumManager albumManager) {
		this.albumManager = albumManager;
	}

	@Override
	public String[] getAlbums() {
		return this.albumManager.getAlbums((UserDto) getSession(ParamKey.USER)) ;
	}

}
