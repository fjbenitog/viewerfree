package es.viewerfree.gwt.server;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

public class ServiceTestSupport {

	protected static final String PASSWORD = "xxx";
	protected static final List<String> ALBUMS = Arrays.asList("Album1","Album2");
	protected static final List<String> ALBUMS_2 = Arrays.asList("Album3","Album4","Album5");
	protected static final String USER_NAME = "USER";
	protected static final String[] TABLES = {"viewerfree.VF_ALBUM_USER","viewerfree.VF_ALBUM_TAG",
		"viewerfree.VF_ALBUM","viewerfree.VF_TAG","viewerfree.VF_USERS"};
	
	@Autowired
	protected IUserService userService;
	
	protected SimpleJdbcTemplate simpleJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	protected int deleteFromTables(String... names) {
		return SimpleJdbcTestUtils.deleteFromTables(this.simpleJdbcTemplate, names);
	}
	
	protected UserDto insertUser(UserDto userDto) throws ServiceException {
		userService.createUser(userDto);
		UserDto user = userService.getUser(userDto.getName());
		return user;
	}

	protected UserDto createUserDto(String name) {
		return createUserDto(name,ALBUMS);
	}
	
	protected UserDto createUserDto(String name,List<String> albums) {
		UserDto userDto = new UserDto(name, PASSWORD);
		userDto.setAlbums(albums);
		return userDto;
	}
	
	protected UserDto createUserDto() {
		return createUserDto(USER_NAME,ALBUMS);
	}
	
	protected void modifyUserAlbums(UserDto user, List<String> albums) throws ServiceException {
		user.setAlbums(albums);
		userService.modifyUser(user);
	}
}
