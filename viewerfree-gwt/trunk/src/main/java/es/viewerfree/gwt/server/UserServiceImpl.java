package es.viewerfree.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.service.ServiceException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
UserService {

	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Boolean login(String userName, String password)
	throws IllegalArgumentException {
		try {
			return userService.getCredentials(userName, password)!=null;
		} catch (ServiceException e) {
			new IllegalArgumentException(e);
		}
		return false;
	}
}
