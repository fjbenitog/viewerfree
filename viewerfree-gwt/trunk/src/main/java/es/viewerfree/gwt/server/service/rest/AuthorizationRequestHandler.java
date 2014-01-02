package es.viewerfree.gwt.server.service.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.ext.ResponseHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;

@Provider
public class AuthorizationRequestHandler implements RequestHandler,ResponseHandler {

	@Override
	public Response handleRequest(Message message, ClassResourceInfo classResourceInfo) {
		System.err.println("Entroooooo---------");
		return null;
	}

	@Override
	public Response handleResponse(Message message, OperationResourceInfo operationResourceInfo,
			Response response) {
		// TODO Auto-generated method stub
		return response;
	}

}
