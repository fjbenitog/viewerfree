package es.viewerfree.gwt.server;

import java.util.ArrayList;
import java.util.List;

import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.server.service.ITagService;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.service.SpringRemoteServiceServlet;
import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.AlbumDto;
import es.viewerfree.gwt.shared.dto.PictureDto;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@SuppressWarnings("serial")
public class ViewerServiceImpl extends SpringRemoteServiceServlet implements ViewerService {
	
	private IUserService userService;
	
	private ITagService tagService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


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
	public List<String> getUserAlbums() throws ServiceException {
		return getUserAlbums(userService.getUser((((UserDto)getSession(ParamKey.USER)).getId())), this.albumManager.getAlbums());
	}

	private List<String> getUserAlbums(UserDto userDto, List<String> albums) {
		List<String> albumsList = new ArrayList<String>();
		for (String album : userDto.getAlbums()) {
			if(albums.contains(album)){
				albumsList.add(album);
			}
		}
		return albumsList ;
	}

	@Override
	public AlbumDto getPictures(String albumName) {
		UserDto userDto =(UserDto)getSession(ParamKey.USER);
		List<String> pictures = albumManager.getPictures(albumName);
		List<PictureDto> pictureDtos = new ArrayList<PictureDto>();
		for (String pic : pictures) {
			PictureDto pictureDto = new PictureDto(pic, CryptoUtil.encrypt(pic, userDto.getName()));
			pictureDtos.add(pictureDto);
		}
		AlbumDto albumDto = new AlbumDto(albumName, pictureDtos);
		albumDto.setCryptedName(CryptoUtil.encrypt(albumName, userDto.getName()));
		return albumDto;
	}

	@Override
	public void rotatePicture(int angle,String albumName, String pictureName) throws Exception {
		albumManager.rotateCachedPicture(albumName, new String[]{thumbnailCachedPath,cachedPath}, 
				pictureName, angle);
		
	}

	@Override
	public List<String> getAlbums() {
		return this.albumManager.getAlbums();
	}

	@Override
	public void createAlbum(String albumName) {
		this.albumManager.createAlbum(albumName);
		
	}

	@Override
	public void deletePicture(String albumName, String pictureName)
			throws Exception {
		this.albumManager.deletePicture(albumName, pictureName);
	}

	@Override
	public void addTag(String albumName, String tagName) throws Exception {
		tagService.addTag(((UserDto)getSession(ParamKey.USER)).getName(), albumName, tagName);
	}

	@Override
	public List<String> getTags(String album) throws Exception {
		return tagService.getTags(((UserDto)getSession(ParamKey.USER)).getName(), album);
	}
	
	public ITagService getTagService() {
		return tagService;
	}

	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}



}
