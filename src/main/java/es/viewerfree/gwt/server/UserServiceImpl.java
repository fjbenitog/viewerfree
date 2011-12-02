package es.viewerfree.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.viewerfree.gwt.client.service.UserService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
UserService {



	public Boolean login(String userName, String password)
	throws IllegalArgumentException {
		// TODO Auto-generated method stub
		System.err.println("Entro en el servicio");
		return false;
	}
}
