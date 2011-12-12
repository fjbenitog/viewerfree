package es.viewerfree.gwt.server.initialize;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import es.viewerfree.gwt.server.dto.UserDto;
import es.viewerfree.gwt.server.dto.UserProfile;
import es.viewerfree.gwt.server.service.IUserService;

public class InitializeDBTest {

	private static final String PASSWORD = "admin";

	private static final String USER = "admin";

	private InitializeDB initializeDB;
	
	private IUserService userService;

	private UserDto userDto;
	
	@Before
	public void setUp() throws Exception {
		userService = Mockito.mock(IUserService.class);
		initializeDB = new InitializeDB();
		initializeDB.setUserService(userService);
		userDto = new UserDto(USER, PASSWORD);
		userDto.setProfile(UserProfile.ADMIN);
	}
	
	@Test
	public void testSetApplicationContextFirstTime() throws Exception {
		Mockito.when(userService.getUser(USER)).thenReturn(null);
		initializeDB.setApplicationContext(null);
		Mockito.verify(userService,Mockito.times(1)).createUser(userDto);
	}
	
	@Test
	public void testSetApplicationContextSecondTime() throws Exception {
		Mockito.when(userService.getUser(USER)).thenReturn(userDto);
		initializeDB.setApplicationContext(null);
		Mockito.verify(userService,Mockito.times(0)).createUser(userDto);
	}

}
