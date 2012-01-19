package es.viewerfree.gwt.server;

import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.service.SpringRemoteServiceServlet;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends SpringRemoteServiceServlet implements UserService {

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
			UserDto credentials = userService.getCredentials(userName, password);
			setSession(ParamKey.USER,credentials);
			return credentials!=null;
		} catch (ServiceException e) {
			new IllegalArgumentException(e);
		}
		return false;
	}

	@Override
	public void logout() {
		getHttpServletRequest().getSession().invalidate();
	}

	@Override
	public UserDto getUser() {
		return (UserDto) getSession(ParamKey.USER);
	}

	@Override
	public void createUser(UserDto user) throws ServiceException{
		userService.createUser(user);
	}
}
