package es.viewerfree.gwt.server.dao.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaTemplate;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.entities.User;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;

public class UserDaoTest {

	private UserDao userDao;
	private static final String USER="USER";
	private static final String PASSWORD ="PASSWORD";
	private JpaTemplate jpaTemplateMock; 
	
	private User _user;
	
	@Before
	public void setUp() throws Exception {
		_user = new User();
		_user.setUser(USER);
		_user.setPassword(PASSWORD);
		_user.setProfile(UserProfile.ADMIN.toString());
		jpaTemplateMock = Mockito.mock(JpaTemplate.class);
		userDao = new UserDao();
		userDao.setJpaTemplate(jpaTemplateMock);
	}

	@After
	public void tearDown() throws Exception {
		userDao = null;
		jpaTemplateMock = null;
	}

	@Test
	public void testFindUser() throws DaoException {
		List<User> list = new ArrayList<User>();
		list.add(_user);
		Mockito.when(jpaTemplateMock.findByNamedQuery("findUserByUser", new Object[]{USER})).thenReturn(list);
		UserDto user = userDao.getUser(USER);
		assertEquals(user.getSurname(),_user.getSurname());
	}
	
	@Test
	public void testFindUserReturnMoreThanOne() throws DaoException {
		List<User> list = new ArrayList<User>();
		list.add(_user);
		list.add(_user);
		Mockito.when(jpaTemplateMock.findByNamedQuery("findUserByUser", new Object[]{USER})).thenReturn(list);
		assertNull(userDao.getUser(USER));
	}
	
	@Test(expected=DaoException.class)
	public void testFindUserException() throws DaoException {
		List<User> list = new ArrayList<User>();
		list.add(_user);
		Mockito.doThrow(new DataAccessExceptionImpl("TEST")).when(jpaTemplateMock).findByNamedQuery("findUserByUser", new Object[]{USER});
		userDao.getUser(USER);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		UserDto userDto = new UserDto(USER,PASSWORD);
		userDao.createUser(userDto);
		User user = new User();
		user.setUser(USER);
		user.setPassword(PASSWORD);
		user.setProfile(UserProfile.NORMAL.toString());
		Mockito.verify(jpaTemplateMock).persist(user);
	}
	
	@Test(expected=DaoException.class)
	public void testICreateUserException() throws DaoException {
		User user = new User();
		user.setUser(USER);
		user.setPassword(PASSWORD);
		user.setProfile(UserProfile.NORMAL.toString());
		Mockito.doThrow(new DataAccessExceptionImpl("TEST")).when(jpaTemplateMock).persist(user);
		UserDto userDto = new UserDto(USER,PASSWORD);
		userDao.createUser(userDto);
		
	}
	
	@SuppressWarnings({ "serial" })
	private class DataAccessExceptionImpl extends DataAccessException{

		public DataAccessExceptionImpl(String msg) {
			super(msg);
		}
		
	}
}
