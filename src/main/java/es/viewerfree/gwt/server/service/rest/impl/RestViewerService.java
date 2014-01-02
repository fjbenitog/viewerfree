package es.viewerfree.gwt.server.service.rest.impl;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.service.rest.IRestViewerService;
import es.viewerfree.gwt.server.util.CryptoUtil;
import es.viewerfree.gwt.shared.dto.UserDto;

@Path("/")
@Produces("text/plain")
public class RestViewerService implements IRestViewerService {

	@Autowired
	private IUserService userService;
	
	@Override
	@GET
    @Path("/time")
	public String getTime() {
		// TODO Auto-generated method stub
		return new Date().toString();
	}

	@Override
	@GET
    @Path("/login/{authorization}")
	public String login(@PathParam("authorization") String authorization)  throws Exception{
		String values[] = authorization.split("\\@");
		UserDto credentials = userService.getCredentials(values[1], values[0]);
		if(credentials!=null){
			return CryptoUtil.encrypt(credentials.getName()+":"+System.currentTimeMillis(), credentials.getName());
		}else{
			return "";
		}
		
	}

}
