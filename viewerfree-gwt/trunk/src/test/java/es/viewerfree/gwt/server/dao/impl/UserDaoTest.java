package es.viewerfree.gwt.server.dao.impl;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaTemplate;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dto.UserDto;
import es.viewerfree.gwt.server.dto.UserProfile;
import es.viewerfree.gwt.server.entities.User;

public class UserDaoTest {

	private UserDao _userDao;
	private static final String USER="USER";
	private static final String PASSWORD ="PASSWORD";
	private JpaTemplate _jpaTemplateMock; 
	
	private User _user;
	
	@Before
	public void setUp() throws Exception {
		_user = new User();
		_user.setUser(USER);
		_user.setPassword(PASSWORD);
		_user.setProfile(UserProfile.ADMIN.toString());
		_jpaTemplateMock = Mockito.mock(JpaTemplate.class);
		_userDao = new UserDao();
		_userDao.setJpaTemplate(_jpaTemplateMock);
	}

	@After
	public void tearDown() throws Exception {
		_userDao = null;
		_jpaTemplateMock = null;
	}

	@Test
	public void testFindUser() throws DaoException {
		List<User> list = new ArrayList<User>();
		list.add(_user);
		Mockito.when(_jpaTemplateMock.findByNamedQuery("findUserByUser", new Object[]{USER})).thenReturn(list);
		UserDto user = _userDao.getUser(USER);
		assertEquals(user.getSurname(),_user.getSurname());
	}
	
	@Test
	public void testFindUserReturnMoreThanOne() throws DaoException {
		List<User> list = new ArrayList<User>();
		list.add(_user);
		list.add(_user);
		Mockito.when(_jpaTemplateMock.findByNamedQuery("findUserByUser", new Object[]{USER})).thenReturn(list);
		assertNull(_userDao.getUser(USER));
	}
	
	@Test(expected=DaoException.class)
	public void testFindUserException() throws DaoException {
		List<User> list = new ArrayList<User>();
		list.add(_user);
		Mockito.doThrow(new DataAccessExceptionImpl("TEST")).when(_jpaTemplateMock).findByNamedQuery("findUserByUser", new Object[]{USER});
		_userDao.getUser(USER);
	}
	
	@SuppressWarnings({ "serial", "unused" })
	private class DataAccessExceptionImpl extends DataAccessException{

		public DataAccessExceptionImpl(String msg) {
			super(msg);
		}
		
	}
}
