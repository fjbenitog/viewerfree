package es.viewerfree.gwt.server.service.rest.impl;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import es.viewerfree.gwt.server.service.rest.IRestViewerService;

@Path("/")
@Produces("text/plain")
public class RestViewerService implements IRestViewerService {

	@Override
	@GET
    @Path("/time")
	public String getTime() {
		// TODO Auto-generated method stub
		return new Date().toString();
	}

}
