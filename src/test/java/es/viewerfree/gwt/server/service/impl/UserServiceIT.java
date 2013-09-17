package es.viewerfree.gwt.server.service.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.viewerfree.gwt.server.ServiceTestSupport;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/ApplicationContext.xml"})
public class UserServiceIT extends ServiceTestSupport{
	

	private static final String USER2 = "USER2";

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
		insertUser(createUserDto(USER2));
		userService.delete(Arrays.asList(USER_NAME.toLowerCase()));
		assertNull(userService.getUser(USER_NAME));
	}

	@Test
	public void importExportUser() throws Exception {
		insertUser(createUserDto(USER_NAME,ALBUMS));
		insertUser(createUserDto(USER2,ALBUMS_2));
		String exportUsers = userService.exportUsers(Arrays.asList(USER_NAME,USER2));
		deleteFromTables(TABLES);
		userService.createUsersByXml(exportUsers);
		
		assertEquals(USER_NAME.toLowerCase(),userService.getUser(USER_NAME).getName());
		assertEquals(USER2.toLowerCase(),userService.getUser(USER2).getName());
		
		assertNotNull(exportUsers);
		
	}
	

}
