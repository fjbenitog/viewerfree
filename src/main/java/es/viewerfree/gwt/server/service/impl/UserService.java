package es.viewerfree.gwt.server.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.dto.UserDto;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.service.ServiceException;




public class UserService implements IUserService {

	private String _key;

	private IUserDao _userDao;

	public void createUser(UserDto userDto ) throws ServiceException {
		try {
			userDto.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt()));
			userDto.setName(userDto.getName().toLowerCase());
			getUserDao().createUser(userDto);
		} catch (DaoException e) {
			throw new ServiceException("Error creating user",e);
		}
	}

	public UserDto getCredentials(String user, String password) throws ServiceException {
		try {
			UserDto userDto = _userDao.getUser(user);
			if(userDto!=null && !BCrypt.checkpw(password, userDto.getPassword())) {
				return null;
			}
			return userDto;

		} catch (DaoException e) {
			throw new ServiceException("Error getting Credentials",e);
		}
	}

	public UserDto getUser(String user) throws ServiceException {
			try {
				return _userDao.getUser(user.toLowerCase());
			} catch (DaoException e) {
				throw new ServiceException("Error listing users",e);
			}
	}
	
	public List<UserDto> getAllUsers() throws ServiceException {
		try {
			return _userDao.findAllUsers();
		} catch (DaoException e) {
			throw new ServiceException("Error getting all users",e);
		}
	}
	
	public IUserDao getUserDao() {
		return _userDao;
	}

	public void setUserDao(IUserDao userDao) {
		_userDao = userDao;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public UserDto getUser(Long id) throws ServiceException {
		try {
			return _userDao.getUser(id);
		} catch (DaoException e) {
			throw new ServiceException("Error getting User by Id",e);
		}
	}

	public void modifyUser(UserDto userDto) throws ServiceException {
		try {
			_userDao.modifyUser(userDto);
		} catch (DaoException e) {
			throw new ServiceException("Error modifying User",e);
		}
	}

}
