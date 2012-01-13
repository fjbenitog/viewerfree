package es.viewerfree.gwt.server;

import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.server.service.SpringRemoteServiceServlet;
import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.AlbumDto;
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

	@Override
	public AlbumDto getPictures(String albumName) {
		UserDto userDto =(UserDto)getSession(ParamKey.USER);
		String[] pictures = albumManager.getPictures((UserDto) getSession(ParamKey.USER), albumName);
		String[] cryptedPics = new String[pictures.length];
		for (int i = 0; i < cryptedPics.length; i++) {
			cryptedPics[i] = CryptoUtil.encrypt(pictures[i], userDto.getName());
		}
		AlbumDto albumDto = new AlbumDto(albumName, pictures);
		albumDto.setCryptedName(CryptoUtil.encrypt(albumName, userDto.getName()));
		albumDto.setCryptedPictures(cryptedPics);
		return albumDto;
	}


}
