package es.viewerfree.gwt.server;

import java.util.ArrayList;
import java.util.List;

import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.server.service.IAlbumService;
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
	
	private IAlbumService albumService;
	
	public IAlbumService getAlbumService() {
		return albumService;
	}

	public void setAlbumService(IAlbumService albumService) {
		this.albumService = albumService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
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
		return albumService.getAlbums(((UserDto)getSession(ParamKey.USER)).getName());
	}

	@Override
	public AlbumDto getPictures(String albumName) {
		UserDto userDto =(UserDto)getSession(ParamKey.USER);
		return albumService.getPictures(userDto.getName(), albumName);
	}

	@Override
	public void rotatePicture(int angle,String albumName, String pictureName) throws Exception {
		albumManager.rotateCachedPicture(albumName, pictureName, angle);

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

	@Override
	public List<String> getOtherTags(String album) throws Exception {
		return tagService.getOtherTags(((UserDto)getSession(ParamKey.USER)).getName(), album);
	}

	@Override
	public void removeTag(String albumName, String tagName) throws Exception {
		tagService.removeTag(((UserDto)getSession(ParamKey.USER)).getName(), albumName, tagName);
	}

	@Override
	public List<String> getTags() throws Exception {
		return tagService.getTags(((UserDto)getSession(ParamKey.USER)).getName());
	}

	@Override
	public List<String> geAlbumsByTag(String tagName) throws ServiceException {
		return tagService.getAlbums(((UserDto)getSession(ParamKey.USER)).getName(), tagName);
	}

	public ITagService getTagService() {
		return tagService;
	}

	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}

}
