package es.viewerfree.gwt.server.dao;

import java.util.List;

import es.viewerfree.gwt.server.dto.UserDto;


public interface IUserDao {

	public List<UserDto> findUser(String user, String password) throws DaoException ;
	
	public UserDto getUser(String user) throws DaoException ;
	
	public UserDto getUser(Long id) throws DaoException ;
	
	public void createUser(UserDto userDto) throws DaoException;
	
	public List<UserDto> findAllUsers() throws DaoException;
	
	public void modifyUser(UserDto userDto) throws DaoException;

}