package es.viewerfree.gwt.server.service.rest.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.viewerfree.gwt.server.service.IAlbumService;
import es.viewerfree.gwt.server.service.ITagService;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.service.rest.IRestViewerService;
import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.dto.AlbumDto;
import es.viewerfree.gwt.shared.dto.UserDto;

@Path("/")
public class RestViewerService implements IRestViewerService {

	@Autowired
	private IUserService userService;

	@Autowired
	private IAlbumService albumService;

	@Autowired
	private ITagService tagService;

	@Autowired
	private AlbumManager albumManager;

	@Value("${thumbnail.path}")
	private String thumbnailCachedPath;

	@Value("${thumbnail.max.height}")
	private int thumbnailHeight;

	@Value("${preview.path}")
	private String thumbnailPath;

	@Value("${image.max.height}")
	private int height;

	@Override
	@GET
	@Path("/time")
	@Produces("text/plain")
	public String getTime() {
		return new Date().toString();
	}

	@Override
	@GET
	@Path("/login/{authorization}")
	@Produces("text/plain")
	public String login(@PathParam("authorization") String authorization) {
		try {
			String values[] = authorization.split("\\@");
			if(values==null || values.length<2){
				return "";
			}
			UserDto credentials;
			credentials = userService.getCredentials(values[1], values[0]);
			if(credentials!=null){
				return CryptoUtil.encrypt(credentials.getName()+":"+System.currentTimeMillis(), credentials.getName());
			}else{
				return "";
			}
		} catch (Exception e) {
			throw new WebApplicationException(e,Response.serverError().build());
		}

	}

	@Override
	@GET
	@Path("/{user}/albums")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String>  getAlbums(@PathParam("user") String user)  {
		try {
			return albumService.getAlbums(user);
		} catch (Exception e) {
			throw new WebApplicationException(e,Response.serverError().build());
		}
	}

	@Override
	@GET
	@Path("/{user}/tags")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String>  getTags(@PathParam("user") String user)  {
		try {
			return tagService.getTags(user);
		} catch (Exception e) {
			throw new WebApplicationException(e,Response.serverError().build());
		}
	}

	@Override
	@GET
	@Path("/{user}/{tag}/albums")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getAlbumByTags(@PathParam("user") String user, @PathParam("tag") String tag) {
		try {
			return tagService.getAlbums(user, tag);
		} catch (Exception e) {
			throw new WebApplicationException(e,Response.serverError().build());
		}
	}

	@Override
	@GET
	@Path("/{user}/albums/{album}")
	@Produces(MediaType.APPLICATION_JSON)
	public AlbumDto getPictures(@PathParam("user") String user,
			@PathParam("album") String albumName) {
		return albumService.getPictures(user, albumName);
	}

	@Override
	@GET
	@Path("/{user}/{album}/{pic}/{type}")
	@Produces("image/jpeg")
	public Response getImage(@PathParam("user") String user,
			@PathParam("album") String encriptedAlbum,
			@PathParam("pic") String encriptedPic,
			@PathParam("type") String picType) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String album = CryptoUtil.decrypt(encriptedAlbum, user);
			String pic = CryptoUtil.decrypt(encriptedPic, user);
			Action type = Action.valueOf(picType);
			switch (type) {
			case SHOW_THUMBNAIL:
				albumManager.getCachedPicture(album,pic, thumbnailCachedPath,thumbnailHeight,outputStream);
				break;
			case SHOW_PICTURE:
				albumManager.getCachedPicture(album,pic, thumbnailPath,height,outputStream);
				break;
			}
			ResponseBuilder response = Response.ok(outputStream.toByteArray());
			response.header("Content-Disposition",
					"attachment; filename="+pic);
			return response.build();
		} catch (Exception e) {
			throw new WebApplicationException(e,Response.serverError().build());
		}
	}

	@Override
	@POST
	@Path("/{user}/{album}/{pic}/")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadImage(@Multipart("file") MultipartBody multipart,
			@PathParam("album") String user,
			@PathParam("album") String albumName,
			@PathParam("pic") String picName) {
		try {
			albumService.uploadPictures(multipart.getRootAttachment()
					.getDataHandler().getInputStream(), albumName, picName);
		} catch (Exception e) {
			throw new WebApplicationException(e,Response.serverError().build());
		}
	}


}
