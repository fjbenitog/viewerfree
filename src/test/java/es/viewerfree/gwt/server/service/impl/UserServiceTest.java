package es.viewerfree.gwt.server.service.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.service.ServiceException;
import es.viewerfree.gwt.shared.dto.UserDto;

public class UserServiceTest {


	private UserService _userService;
	private IUserDao _userDao;
	private static final String USER="USER";
	private static final String PASSWORD ="PASSWORD";
	private UserDto _userDto;
	private static final String HASH_PWD=BCrypt.hashpw(PASSWORD,BCrypt.gensalt());

	@Before
	public void setUp() throws Exception {
		_userDto = new UserDto(USER,HASH_PWD);
		_userDao = Mockito.mock(IUserDao.class);
		_userService = new UserService();
		_userService.setUserDao(_userDao);
	}
	
	@Test
	public void testGetCredentials() throws Exception {
		List<UserDto> list = new ArrayList<UserDto>();
		list.add(_userDto);
		Mockito.when(_userDao.getUser(USER)).thenReturn(_userDto);
		assertEquals(_userDto, _userService.getCredentials(USER, PASSWORD));
	}
	
	@Test
	public void testGetCredentialsNoResults() throws Exception {
		Mockito.when(_userDao.getUser(USER)).thenReturn(_userDto);
		assertNull( _userService.getCredentials(USER, "NO_PASSWORD"));
	}
	
	@Test(expected=ServiceException.class)
	public void testGetCredentialsException() throws Exception {
		Mockito.when(_userDao.getUser(USER)).thenThrow(new DaoException(""));
		try{
			_userService.getCredentials(USER, PASSWORD);
		}catch (Exception e) {
			assertEquals("Error getting Credentials",e.getMessage());
			throw e;
		}
	}

}
