package es.viewerfree.gwt.server.service.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.viewerfree.gwt.server.ServiceTestSupport;
import es.viewerfree.gwt.server.service.ITagService;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationTestContext.xml"})
public class UserServiceIT extends ServiceTestSupport{
	
	private static final String USER2 = "USER2";
	
	private static final String TAG2 = "TAG2";
	
	private static final String TAG1 = "TAG1";
	
	@Autowired
	private ITagService tagService;

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
		UserDto user = insertUser(createUserDto());
		insertUser(createUserDto(USER2));
		tagService.addTag(user.getName(), ALBUMS.get(0), TAG2);
		userService.delete(Arrays.asList(USER_NAME.toLowerCase()));
		assertNull(userService.getUser(USER_NAME));
	}

	@Test
	public void importExportUser() throws Exception {
		UserDto user = insertUser(createUserDto(USER_NAME,ALBUMS));
		UserDto user2 =insertUser(createUserDto(USER2,ALBUMS_2));
		tagService.addTag(user.getName(), ALBUMS.get(0), TAG2);
		tagService.addTag(user2.getName(), ALBUMS.get(1), TAG1);
		
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG2));
		assertEquals(Arrays.asList(ALBUMS.get(1)),tagService.getAlbums(user2.getName(), TAG1));
		
		String exportUsers = userService.exportUsers(Arrays.asList(USER_NAME,USER2));
//		System.err.println(exportUsers);
		deleteFromTables(TABLES);
		userService.createUsersByXml(exportUsers);
		
		user = userService.getUser(user.getName());
		user2 = userService.getUser(user2.getName());
		
		assertEquals(USER_NAME.toLowerCase(),userService.getUser(USER_NAME).getName());
		assertEquals(ALBUMS,user.getAlbums());
		assertEquals(USER2.toLowerCase(),userService.getUser(USER2).getName());
		assertEquals(ALBUMS_2,user2.getAlbums());
		assertEquals(Arrays.asList(ALBUMS.get(0)),tagService.getAlbums(user.getName(), TAG2));
		assertEquals(Arrays.asList(ALBUMS.get(1)),tagService.getAlbums(user2.getName(), TAG1));
		
		assertNotNull(exportUsers);
		
	}
	

}
