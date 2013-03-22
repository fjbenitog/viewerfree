package es.viewerfree.gwt.server.dao;

import java.util.List;

import es.viewerfree.gwt.server.entities.Tag;
import es.viewerfree.gwt.server.entities.User;


public interface IUserDao {

	public User getUser(String user) throws DaoException ;
	
	public User getUser(Long id) throws DaoException ;
	
	public void mergeUser(User user) throws DaoException;
	
	public List<User> findAllUsers() throws DaoException;
	
	public void delete(List<String> user) throws DaoException;
	
}