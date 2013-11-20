package es.viewerfree.gwt.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.ITagDao;
import es.viewerfree.gwt.server.entities.Album;
import es.viewerfree.gwt.server.entities.Tag;
import es.viewerfree.gwt.server.entities.Tag.TagId;
import es.viewerfree.gwt.server.entities.User;

@Repository
public class TagDao implements ITagDao {

	private static final String DELETE_TAG_FROM_ALBUM = "Delete from  viewerfree.VF_ALBUM_TAG a " +
			"where a.ALBUMS_NAME = ? AND a.VF_TAG_TAG_NAME = ? AND a.vf_tag_user_ID_USER = ?";
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void addTag(User user, String albumName, String tagName) {
		Tag tag = getEntityTag(user.getUser(), tagName);
		Album album = getEntityAlbum(albumName);
		if(tag == null){
			tag = composeTag(user, album, tagName);
			entityManager.merge(tag);
		}else{
			if(!tag.getAlbums().contains(album)){
				tag.getAlbums().add(album);
			}
		}
	}


	@Override
	public List<String> getAlbumsByTag(String userName, String tagName) {
		List<String> sTags = new ArrayList<String>();
		Tag tagEntity = getEntityTag(userName, tagName);
		if(tagEntity != null){
			for (Album album : tagEntity.getAlbums()) {
				sTags.add(album.getName());
			}
		}
		return sTags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getTagsByAlbum(String user, String albumName) {
		Query namedQuery = entityManager.createNamedQuery("findTagsByAlbum").setParameter(1, user).setParameter(2, albumName);
		return (List<Tag>) namedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getOtherTagsByAlbum(String user, String albumName) {
		Query namedQuery = entityManager.createNamedQuery("findOtherTags").setParameter(1, user).setParameter(2, albumName);
		return namedQuery.getResultList();
	}

	@Override
	public void removeTag(User user, String albumName, String tagName) throws DaoException {
		try{
			 entityManager.createNativeQuery(DELETE_TAG_FROM_ALBUM).setParameter(1, albumName)
					.setParameter(3, user).setParameter(2, tagName).executeUpdate();
		}catch(Exception ex){
			throw new DaoException("Error deleting tag",ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getTagsByUser(String user) {
		Query namedQuery = entityManager.createNamedQuery("findTagsByUser").setParameter(1, user);
		return namedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	private Tag getEntityTag(String userName, String tagName) {
		Query namedQuery = entityManager.createNamedQuery("findTagByName").setParameter(1, tagName).setParameter(2, userName);
		List<Tag> tags = namedQuery.getResultList();
		Tag tagEntity = null;
		if(tags!=null && tags.size()==1){
			tagEntity = tags.get(0);
		}
		return tagEntity;
	}

	@SuppressWarnings("unchecked")
	private Album getEntityAlbum(String albumName) {
		Query namedQuery = entityManager.createNamedQuery("findAlbumByName").setParameter(1, albumName);
		List<Album> albums = namedQuery.getResultList();
		Album albumEntity = null;
		if(albums.size()==1){
			albumEntity = albums.get(0);
		}
		return albumEntity;
	}

	private Tag composeTag(User user, Album album, String tagName) {
		Tag tag = new Tag();
		TagId tagId = new TagId();
		tagId.setName(tagName);
		tagId.setUser(user);
		tag.setTagId(tagId);
		tag.setAlbums(Arrays.asList(album));
		return tag;
	}


}
