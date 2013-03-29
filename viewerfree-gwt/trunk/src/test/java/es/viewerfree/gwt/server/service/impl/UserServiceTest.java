package es.viewerfree.gwt.server.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.shared.dto.UserDto;

public class UserServiceTest {


	private UserService userService;
	private IUserDao userDao;
	private static final String USER="USER";
	private static final String PASSWORD ="PASSWORD";
	private UserDto userDto;
	private static final String HASH_PWD=BCrypt.hashpw(PASSWORD,BCrypt.gensalt());

	@Before
	public void setUp() throws Exception {
		userDto = new UserDto(USER,HASH_PWD);
		userDao = Mockito.mock(IUserDao.class);
		userService = new UserService();
		userService.setUserDao(userDao);
	}
	
	@Test
	public void testname() throws Exception {
		
	}
	
//	@Test
//	public void testGetCredentials() throws Exception {
//		List<UserDto> list = new ArrayList<UserDto>();
//		list.add(userDto);
//		Mockito.when(userDao.getUser(USER)).thenReturn(userDto);
//		assertEquals(userDto, userService.getCredentials(USER, PASSWORD));
//	}
//	
//	@Test
//	public void testGetCredentialsNoResults() throws Exception {
//		Mockito.when(userDao.getUser(USER)).thenReturn(userDto);
//		assertNull( userService.getCredentials(USER, "NO_PASSWORD"));
//	}
//	
//	@Test(expected=ServiceException.class)
//	public void testGetCredentialsException() throws Exception {
//		Mockito.when(userDao.getUser(USER)).thenThrow(new DaoException(""));
//		try{
//			userService.getCredentials(USER, PASSWORD);
//		}catch (Exception e) {
//			assertEquals("Error getting Credentials",e.getMessage());
//			throw e;
//		}
//	}
//
//	@Test
//	public void testCreateUser() throws Exception {
//		ArgumentCaptor<UserDto> arg = ArgumentCaptor.forClass(UserDto.class);
//		userDto.setPassword(PASSWORD);
//		userService.createUser(userDto);
//		Mockito.verify(userDao).createUser(arg.capture());
//		assertEquals(USER.toLowerCase(),arg.getValue().getName());
//		assertTrue(BCrypt.checkpw(PASSWORD, arg.getValue().getPassword()));
//	}
//	
//	@Test(expected=ServiceException.class)
//	public void testCreateUserException() throws Exception {
//		Mockito.doThrow(new DaoException("")).when(userDao).createUser(Mockito.any(UserDto.class));
//		try{
//			userService.createUser(userDto);
//		}catch (Exception e) {
//			assertEquals("Error creating user",e.getMessage());
//			throw e;
//		}
//	}
//	
//	@Test
//	public void testGetUser() throws Exception {
//		List<UserDto> list = new ArrayList<UserDto>();
//		list.add(userDto);
//		Mockito.when(userDao.getUser(USER.toLowerCase())).thenReturn(userDto);
//		assertEquals(userDto, userService.getUser(USER));
//	}
//	
//	@Test
//	public void testGetUserNoResults() throws Exception {
//		Mockito.when(userDao.getUser(USER.toLowerCase())).thenReturn(null);
//		assertNull( userService.getUser(USER));
//	}
//	
//	@Test(expected=ServiceException.class)
//	public void testGetUserException() throws Exception {
//		Mockito.when(userDao.getUser(USER.toLowerCase())).thenThrow(new DaoException(""));
//		try{
//			userService.getUser(USER);
//		}catch (Exception e) {
//			assertEquals("Error getting user",e.getMessage());
//			throw e;
//		}
//	}
//	
	
}
