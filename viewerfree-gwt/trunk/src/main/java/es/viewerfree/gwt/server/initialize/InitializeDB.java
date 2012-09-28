package es.viewerfree.gwt.server.initialize;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;
import es.viewerfree.gwt.shared.service.ServiceException;

public class InitializeDB implements ApplicationContextAware {

	private static final String ADMIN = "admin";
	private IUserService _userService;


	public IUserService getUserService() {
		return _userService;
	}

	public void setUserService(IUserService userService) {
		_userService = userService;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		try {
			UserDto user = _userService.getUser(ADMIN);
			if(user==null){
				System.err.println("--------------------------------------------");
				UserDto userDto = new UserDto(ADMIN, ADMIN);
				userDto.setProfile(UserProfile.ADMIN);
//				List<String> albums = new ArrayList<String>();
//				albums.add("Australia_1");
//				albums.add("Australia_2");
//				userDto.setAlbums(albums);
//				userDto.setFullName("Francisco Javier");
//				userDto.setSurname("Benito Gómez");
				_userService.createUser(userDto);
				UserDto user2 = _userService.getUser(ADMIN);
				System.err.println(user2);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

}
