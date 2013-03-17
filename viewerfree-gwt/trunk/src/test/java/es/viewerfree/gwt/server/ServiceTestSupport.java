package es.viewerfree.gwt.server;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

public class ServiceTestSupport {

	protected static final String PASSWORD = "xxx";
	protected static final List<String> ALBUMS = Arrays.asList("Album1","Album2");
	protected static final List<String> ALBUMS_2 = Arrays.asList("Album3","Album4","Album5");
	protected static final String USER_NAME = "USER";
	
	@Autowired
	protected IUserService userService;
	
	protected UserDto insertUser(UserDto userDto) throws ServiceException {
		userService.createUser(userDto);
		UserDto user = userService.getUser(userDto.getName());
		return user;
	}

	protected UserDto createUserDto(String name) {
		UserDto userDto = new UserDto(name, PASSWORD);
		userDto.setAlbums(ALBUMS);
		return userDto;
	}
	
	protected UserDto createUserDto() {
		return createUserDto(USER_NAME);
	}
	
	protected void modifyUserAlbums(UserDto user, List<String> albums) throws ServiceException {
		user.setAlbums(albums);
		userService.modifyUser(user);
	}
}
