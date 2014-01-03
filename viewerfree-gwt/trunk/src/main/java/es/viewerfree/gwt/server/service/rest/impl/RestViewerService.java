package es.viewerfree.gwt.server.service.rest.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.viewerfree.gwt.server.service.IAlbumService;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.service.rest.IRestViewerService;
import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.shared.dto.UserDto;

@Path("/")
public class RestViewerService implements IRestViewerService {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAlbumService albumService;
	
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
	public String login(@PathParam("authorization") String authorization)  throws Exception{
		String values[] = authorization.split("\\@");
		UserDto credentials = userService.getCredentials(values[1], values[0]);
		if(credentials!=null){
			return CryptoUtil.encrypt(credentials.getName()+":"+System.currentTimeMillis(), credentials.getName());
		}else{
			return "";
		}
		
	}

	@Override
	@GET
    @Path("/{user}/albums")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String>  getAlbums(@PathParam("user") String user) throws Exception {
		return albumService.getAlbums(user);
	}

}
