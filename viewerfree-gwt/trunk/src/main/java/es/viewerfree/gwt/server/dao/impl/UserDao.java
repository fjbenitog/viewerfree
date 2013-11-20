package es.viewerfree.gwt.server.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;


import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.entities.Tag;
import es.viewerfree.gwt.server.entities.User;


@Repository
public class UserDao implements IUserDao{

	@PersistenceContext
	EntityManager entityManager;

	public User getUser(String user) throws DaoException {
		try{
			return getUserEntity(user);
		}catch (Exception e) {
			throw new DaoException("Unanabled to find a User",e);
		}
	}

	@SuppressWarnings("unchecked")
	private User getUserEntity(String user) throws DaoException{
		Query namedQuery = entityManager.createNamedQuery("findUserByUser");
		namedQuery.setParameter(1, user);
		List<User> users = namedQuery.getResultList();
		User userEntity = null;
		if(users.size()==1){
			userEntity = users.get(0);
		}
		return userEntity;
	}

	public void mergeUser(User user)throws DaoException {
		try{
			entityManager.merge(user);
		}catch (Exception e) {
			throw new DaoException("Unanabled to create a User",e);
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() throws DaoException {
		try{
			Query namedQuery = entityManager.createNamedQuery("findAllUsers");
			return  namedQuery.getResultList();
		}catch (Exception e) {
			throw new DaoException("Unanabled to find all Users",e);
		}
	}

	public User getUser(Long id) throws DaoException {
		return entityManager.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(List<String> users) throws DaoException {
		for (String user : users) {
			User userEntity = getUserEntity(user);
			Query namedQuery = entityManager.createNamedQuery("findTagsByUser");
			namedQuery.setParameter(1, user);
			List<Tag> tags = namedQuery.getResultList();
			for (Tag tag : tags){
				entityManager.remove(tag);
			}
			if(userEntity!=null)
				entityManager.remove(userEntity);
		}
	}


}
