package es.viewerfree.gwt.server.service.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.viewerfree.gwt.server.ServiceTestSupport;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/ApplicationContext.xml"})
public class UserServiceITest extends ServiceTestSupport{

	@Before
	public void setUp() throws ServiceException{
		deleteFromTables(TABLES);
	}
	
	@Test
	public void getAdminUser() throws Exception {
		insertUser(createUserDto());
		List<UserDto> allUsers = userService.getAllUsers();
		assertEquals(1,allUsers.size());
		assertEquals(USER_NAME.toLowerCase(),allUsers.get(0).getName());
	}
	
	@Test
	public void createUser() throws Exception {
		UserDto user = insertUser(createUserDto());
		assertEquals(USER_NAME.toLowerCase(),user.getName());
		assertEquals(ALBUMS,user.getAlbums());
	}
	
	@Test
	public void updateUserAlbums() throws Exception {
		UserDto user = insertUser(createUserDto());
		modifyUserAlbums(user, ALBUMS_2);
		user = userService.getUser(USER_NAME);
		assertEquals(USER_NAME.toLowerCase(),user.getName());
		assertEquals(ALBUMS_2,user.getAlbums());
	}

	
	@Test
	public void deleteUser() throws Exception {
		insertUser(createUserDto());
		userService.delete(Arrays.asList(USER_NAME.toLowerCase()));
		assertNull(userService.getUser(USER_NAME));
	}

	

}
