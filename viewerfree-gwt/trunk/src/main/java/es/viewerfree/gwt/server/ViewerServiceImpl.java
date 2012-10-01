package es.viewerfree.gwt.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.server.service.SpringRemoteServiceServlet;
import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.AlbumDto;
import es.viewerfree.gwt.shared.dto.UserDto;

@SuppressWarnings("serial")
public class ViewerServiceImpl extends SpringRemoteServiceServlet implements ViewerService {
	
	private String thumbnailCachedPath;
	
	private String cachedPath;

	public String getCachedPath() {
		return cachedPath;
	}

	public void setCachedPath(String cachedPath) {
		this.cachedPath = cachedPath;
	}

	public String getThumbnailCachedPath() {
		return thumbnailCachedPath;
	}

	public void setThumbnailCachedPath(String thumbnailCachedPath) {
		this.thumbnailCachedPath = thumbnailCachedPath;
	}


	private AlbumManager albumManager;
	
	public AlbumManager getAlbumManager() {
		return albumManager;
	}

	public void setAlbumManager(AlbumManager albumManager) {
		this.albumManager = albumManager;
	}

	@Override
	public String[] getUserAlbums() {
		return getUserAlbums((UserDto)getSession(ParamKey.USER), this.albumManager.getAlbums());
	}

	private String[] getUserAlbums(UserDto userDto, String[] albums) {
		List<String> albumsList = new ArrayList<String>();
		for (String album : userDto.getAlbums()) {
			int index = Arrays.binarySearch(albums, album);
			if(index>=0){
				albumsList.add(albums[index]);
			}
		}
		return (String[]) albumsList.toArray(new String[albumsList.size()]) ;
	}

	@Override
	public AlbumDto getPictures(String albumName) {
		UserDto userDto =(UserDto)getSession(ParamKey.USER);
		String[] pictures = albumManager.getPictures(albumName);
		String[] cryptedPics = new String[pictures.length];
		for (int i = 0; i < cryptedPics.length; i++) {
			cryptedPics[i] = CryptoUtil.encrypt(pictures[i], userDto.getName());
		}
		AlbumDto albumDto = new AlbumDto(albumName, pictures);
		albumDto.setCryptedName(CryptoUtil.encrypt(albumName, userDto.getName()));
		albumDto.setCryptedPictures(cryptedPics);
		return albumDto;
	}

	@Override
	public void rotatePicture(int angle,String albumName, String pictureName) throws Exception {
		albumManager.rotateCachedPicture(albumName, new String[]{thumbnailCachedPath,cachedPath}, 
				pictureName, angle);
		
	}

	@Override
	public String[] getAlbums() {
		return this.albumManager.getAlbums();
	}


}
