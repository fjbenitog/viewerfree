package es.viewerfree.gwt.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.ITagDao;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.entities.Tag;
import es.viewerfree.gwt.server.service.ITagService;
import es.viewerfree.gwt.shared.service.ServiceException;

public class TagService implements ITagService {

	private IUserDao userDao;
	
	private ITagDao tagDao;
	
	@Override
	public void addTag(String userName, String albumName, String tagName)
			throws ServiceException {
		try {
			tagDao.addTag(userDao.getUser(userName),albumName,tagName);
		}catch (DaoException e) {
			throw new ServiceException("Error Adding Tag",e);
		}

	}

	@Override
	public List<String> getAlbums(String userName, String tagName)
			throws ServiceException {
		return tagDao.getAlbumsByTag(userName, tagName);
	}
	
	@Override
	public List<String> getTags(String userName, String albumName) throws ServiceException{
		try {
			List<String> tagsName = new ArrayList<String>();
			List<Tag> tags = tagDao.getTagsByAlbum(userDao.getUser(userName), albumName);
			for (Tag tag : tags) {
				tagsName.add(tag.getTagId().getName());
			}
			return tagsName;
		} catch (DaoException e) {
			throw new ServiceException("Error getting Tag",e);
		}
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public ITagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(ITagDao tagDao) {
		this.tagDao = tagDao;
	}


}
