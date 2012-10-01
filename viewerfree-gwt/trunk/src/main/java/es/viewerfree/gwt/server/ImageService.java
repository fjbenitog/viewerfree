package es.viewerfree.gwt.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.UserDto;

public class ImageService implements HttpRequestHandler {

	private AlbumManager albumManager;

	private String thumbnailCachedPath;

	private int thumbnailHeight;

	private String cachedPath;

	private int height;
	
	public String getThumbnailCachedPath() {
		return thumbnailCachedPath;
	}
	
	public void setThumbnailCachedPath(String thumbnailCachedPath) {
		this.thumbnailCachedPath = thumbnailCachedPath;
	}
	
	public int getThumbnailHeight() {
		return thumbnailHeight;
	}
	
	public void setThumbnailHeight(int thumbnailHeight) {
		this.thumbnailHeight = thumbnailHeight;
	}

	public String getCachedPath() {
		return cachedPath;
	}

	public void setCachedPath(String cachedPath) {
		this.cachedPath = cachedPath;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public AlbumManager getAlbumManager() {
		return albumManager;
	}

	public void setAlbumManager(AlbumManager albumManager) {
		this.albumManager = albumManager;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			sendPicture(response, getAlbumName(request), getPictureName(request),
					getImageType(request),
					getUserDto(request),
					outputStream);
		} catch (Exception e) {
			e.printStackTrace();
			albumManager.getDefaultImage(outputStream);

		}finally{
			try {
				if(outputStream!=null){
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private Action getImageType(HttpServletRequest request) {
		return Action.valueOf(request.getParameter(ParamKey.ACTION.toString()));
	}

	private String getPictureName(HttpServletRequest request) {
		String picName = request.getParameter(ParamKey.PICTURE_NAME.toString());
		return CryptoUtil.decrypt(picName, getUserDto(request).getName());
	}

	private String getAlbumName(HttpServletRequest request) {
		String albumName = request.getParameter(ParamKey.ALBUM_NAME.toString());
		return CryptoUtil.decrypt(albumName, getUserDto(request).getName());
	}

	private UserDto getUserDto(HttpServletRequest request) {
		return (UserDto) request.getSession().getAttribute(ParamKey.USER.toString());
	}

	private void sendPicture(HttpServletResponse response, String album,
			String picture, Action action, UserDto userDto,
			ServletOutputStream outputStream) throws Exception,
			UnsupportedEncodingException {
		switch (action) {
		case SHOW_THUMBNAIL:
			getAlbumManager().getCachedPicture(album,picture, getThumbnailCachedPath(),getThumbnailHeight(),outputStream);
			break;
		case SHOW_PICTURE:
			getAlbumManager().getCachedPicture(album,picture, getCachedPath(),getHeight(),outputStream);
			break;
		case SHOW_REAL_PICTURE:
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition","inline; filename="+URLEncoder.encode(picture,"utf-8"));
			getAlbumManager().getPicture(album,picture, outputStream);
			break;
		}
	}

}
