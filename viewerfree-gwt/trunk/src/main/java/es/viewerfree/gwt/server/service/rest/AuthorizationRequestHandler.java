package es.viewerfree.gwt.server.service.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.ext.ResponseHandler;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;

import es.viewerfree.gwt.server.util.CryptoUtil;

@Provider
public class AuthorizationRequestHandler implements RequestHandler,ResponseHandler {

	@Override
	public Response handleRequest(Message message, ClassResourceInfo classResourceInfo) {
		String path = message.get(Message.PATH_INFO).toString();
		if(!path.startsWith("/service/viewer/login/")){
			Map<String, List<String>> headers = (Map<String, List<String>>)message.get(Message.PROTOCOL_HEADERS);
			List<String> tokenList = headers.get("VIEWER_TOKEN");
			if(tokenList!=null){
				String token = tokenList.get(0);
				String user = path.split("/")[3];
				message.getExchange().getSession().put("USER", user);
				String[] values = CryptoUtil.decrypt(token, user).split(":");
				if(user.equals(values[0]) 
						&& (System.currentTimeMillis()-Long.parseLong(values[1]))<360000){
					return null;
				}
			}
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.FORBIDDEN);
			builder.entity("Invalid User.");
			Response response = builder.build();
			return response;
		}else{
			return null;
		}
	}

	@Override
	public Response handleResponse(Message message, OperationResourceInfo operationResourceInfo,
			Response response) {
		String user = (String) message.getExchange().getSession().get("USER");
		if(user!=null){
			String token = CryptoUtil.encrypt(user+":"+System.currentTimeMillis(), user);
			response.getHeaders().putSingle("VIEWER_TOKEN", token);
		}
		return response;
	}

}
