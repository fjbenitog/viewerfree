package es.viewerfree.gwt.server.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.entities.Album;
import es.viewerfree.gwt.server.entities.User;
import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;
import es.viewerfree.gwt.shared.service.ServiceException;




public class UserService implements IUserService {

	private IUserDao _userDao;

	public void createUser(UserDto userDto ) throws ServiceException {
		try {
			userDto.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt()));
			userDto.setName(userDto.getName().toLowerCase());
			getUserDao().mergeUser(toUser(new User(), userDto));
		} catch (DaoException e) {
			throw new ServiceException("Error creating user",e);
		}
	}

	public UserDto getCredentials(String userName, String password) throws ServiceException {
		try {
			User user = _userDao.getUser(userName);
			if(user!=null && !BCrypt.checkpw(password, user.getPassword())) {
				return null;
			}
			return toUserDto(user);

		} catch (DaoException e) {
			throw new ServiceException("Error getting Credentials",e);
		}
	}

	public UserDto getUser(String user) throws ServiceException {
			try {
				return toUserDto(_userDao.getUser(user.toLowerCase()));
			} catch (DaoException e) {
				throw new ServiceException("Error getting user",e);
			}
	}
	
	public List<UserDto> getAllUsers() throws ServiceException {
		try {
			List<User> allUsers = _userDao.findAllUsers();
			List<UserDto> users = new ArrayList<UserDto>();
			for (User user : allUsers) {
				users.add(toUserDto(user));
			}
			return users;
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

	public UserDto getUser(Long id) throws ServiceException {
		try {
			return toUserDto(_userDao.getUser(id));
		} catch (DaoException e) {
			throw new ServiceException("Error getting User by Id",e);
		}
	}

	public void modifyUser(UserDto userDto) throws ServiceException {
		try {
			User user = toUser(_userDao.getUser(userDto.getName()), userDto);
			if(!user.getPassword().equals("****")){
				user.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt()));
			}
			_userDao.mergeUser(user);
		} catch (DaoException e) {
			throw new ServiceException("Error modifying User",e);
		}
	}

	@Override
	public void delete(List<String> users) throws ServiceException {
		try {
			_userDao.delete(users);
		} catch (DaoException e) {
			throw new ServiceException("Error deleting user",e);
		}
	}
	
	private User toUser(User user,UserDto userDto) {
		if(user==null || userDto==null){
			return null;
		}
		//		user.setId(userDto.getId());
		user.setUser(userDto.getName());
		if(!userDto.getPassword().equals("****")){
			user.setPassword(userDto.getPassword());
		}
		UserProfile profile = userDto.getProfile();
		user.setProfile(profile!=null?profile.toString():UserProfile.NORMAL.toString());
		user.setName(userDto.getFullName());
		user.setSurname(userDto.getSurname());
		user.setEmail(userDto.getEmail());

		Collection<Album> albums = new ArrayList<Album>();

		List<String> albumsArr = userDto.getAlbums();
		if(albumsArr!=null){
			for(String albumName:albumsArr){
				Album album = new Album();
				album.setName(albumName);
				albums.add(album);
			}
			user.setAlbums(albums);
		}
		return user;
	}

	private UserDto toUserDto(User user){
		if(user==null){
			return null;
		}
		UserDto userDto = new UserDto(user.getUser(), user.getPassword());
		userDto.setId(user.getId());
		userDto.setProfile(UserProfile.valueOf(user.getProfile()));
		userDto.setFullName(user.getName());
		userDto.setSurname(user.getSurname());
		userDto.setEmail(user.getEmail());
		List<String> albums = new ArrayList<String>();
		Collection<Album> albumsList = user.getAlbums();
		if(albumsList!=null){
			for(Album album :albumsList){
				albums.add(album.getName());
			}
		}
		userDto.setAlbums(albums);
		return userDto;
	}

}
