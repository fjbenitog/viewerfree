package es.viewerfree.gwt.server.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.viewerfree.gwt.server.service.IAlbumService;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.dto.AlbumDto;
import es.viewerfree.gwt.shared.dto.PictureDto;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@Service
public class AlbumService implements IAlbumService {

	@Autowired
	private IUserService userService;

	@Autowired
	private AlbumManager albumManager;

	@Value("${album.path}")
	private String albumPath;

	@Value("${application.path}")
	private String applicationPath;

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

	@Override
	public AlbumDto getPictures(String user, String albumName) {
		List<String> pictures = albumManager.getPictures(albumName);
		List<PictureDto> pictureDtos = new ArrayList<PictureDto>();
		for (String pic : pictures) {
			PictureDto pictureDto = new PictureDto(pic, CryptoUtil.encrypt(pic, user));
			pictureDtos.add(pictureDto);
		}
		AlbumDto albumDto = new AlbumDto(albumName, pictureDtos);
		albumDto.setCryptedName(CryptoUtil.encrypt(albumName, user));
		return albumDto;
	}

	@Override
	public void uploadPictures(InputStream in, String albumName,
			String imageName) throws ServiceException{
		FileOutputStream output = null;
		try {
			File fileImage = new File(getPath()+"/"+albumName+"/"+imageName);
			if(fileImage.exists()){
				throw new FileUploadException("Error: This picture already exists in this album.");
			}
			output = new FileOutputStream(fileImage);
			IOUtils.copyLarge(in, output);
		} catch (Exception e) {
			throw new ServiceException("Error uploading Image", e);
		}finally{
			IOUtils.closeQuietly(output);

		}
	}

	private String getPath() {
		return getApplicationPath()+"/"+getAlbumPath();
	}

	public String getAlbumPath() {
		return albumPath;
	}

	public void setAlbumPath(String albumPath) {
		this.albumPath = albumPath;
	}

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

}
