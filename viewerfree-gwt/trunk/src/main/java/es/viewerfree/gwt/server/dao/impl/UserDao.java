package es.viewerfree.gwt.server.dao.impl;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.entities.User;



public class UserDao extends JpaDaoSupport implements IUserDao{


	public User getUser(String user) throws DaoException {
		try{
			return getUserEntity(user);
		}catch (Exception e) {
			throw new DaoException("Unanabled to find a User",e);
		}
	}

	@SuppressWarnings("unchecked")
	private User getUserEntity(String user) throws DaoException{
		List<User> users = getJpaTemplate().findByNamedQuery("findUserByUser", new Object[]{user});
		User userEntity = null;
		if(users.size()==1){
			userEntity = users.get(0);
		}
		return userEntity;
	}

	public void mergeUser(User user)throws DaoException {
		try{
			getJpaTemplate().merge(user);
		}catch (Exception e) {
			throw new DaoException("Unanabled to create a User",e);
		}

	}
	

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() throws DaoException {
		try{

			return  getJpaTemplate().findByNamedQuery("findAllUsers");
		}catch (Exception e) {
			throw new DaoException("Unanabled to find all Users",e);
		}
	}

	public User getUser(Long id) throws DaoException {
		return getJpaTemplate().find(User.class, id);
	}

	@Override
	public void delete(List<String> users) throws DaoException {
		for (String user : users) {
			getJpaTemplate().remove(getUserEntity(user));
		}
	}


}
