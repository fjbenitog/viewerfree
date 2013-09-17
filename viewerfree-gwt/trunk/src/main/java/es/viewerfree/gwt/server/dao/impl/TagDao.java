package es.viewerfree.gwt.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.ITagDao;
import es.viewerfree.gwt.server.entities.Album;
import es.viewerfree.gwt.server.entities.Tag;
import es.viewerfree.gwt.server.entities.Tag.TagId;
import es.viewerfree.gwt.server.entities.User;

public class TagDao extends JpaDaoSupport implements ITagDao {

	private static final String DELETE_TAG_FROM_ALBUM = "Delete from  viewerfree.VF_ALBUM_TAG a " +
					"where a.ALBUMS_NAME = ? AND a.VF_TAG_TAG_NAME = ? AND a.vf_tag_user_ID_USER = ?";

	@Override
	public void addTag(User user, String albumName, String tagName) {
		Tag tag = getEntityTag(user.getUser(), tagName);
		Album album = getEntityAlbum(albumName);
		if(tag == null){
			tag = composeTag(user, album, tagName);
			getJpaTemplate().merge(tag);
		}else{
			if(!tag.getAlbums().contains(album)){
				tag.getAlbums().add(album);
			}
		}
	}


	@Override
	public List<String> getAlbumsByTag(String userName, String tagName) {
		Tag tagEntity = getEntityTag(userName, tagName);
		List<String> sTags = new ArrayList<String>();
		for (Album album : tagEntity.getAlbums()) {
			sTags.add(album.getName());
		}
		return sTags;
	}

	@Override
	public List<Tag> getTagsByAlbum(String user, String albumName) {
		List<Tag> tags = getJpaTemplate().findByNamedQuery("findTagsByAlbum", new Object[]{user,albumName});
		
		return tags;
	}
	
	@Override
	public List<Tag> getOtherTagsByAlbum(String user, String albumName) {
		return getJpaTemplate().findByNamedQuery("findOtherTags", new Object[]{user,albumName});
	}
	
	@Override
	public void removeTag(User user, String albumName, String tagName) throws DaoException {
		try{
		EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager 
				  (getJpaTemplate ().getEntityManagerFactory () );
		em.createNativeQuery(DELETE_TAG_FROM_ALBUM).setParameter(1, albumName)
		.setParameter(3, user).setParameter(2, tagName).executeUpdate();
		}catch(Exception ex){
			throw new DaoException("Error deleting tag",ex);
		}
	}
	
	@Override
	public List<Tag> getTagsByUser(String user) {
		return getJpaTemplate().findByNamedQuery("findTagsByUser", new Object[]{user});
	}
	
	private Tag getEntityTag(String userName, String tagName) {
		List<Tag> tags = getJpaTemplate().findByNamedQuery("findTagByName", new Object[]{tagName,userName});
		Tag tagEntity = null;
		if(tags!=null && tags.size()==1){
			tagEntity = tags.get(0);
		}
		return tagEntity;
	}
	
	private Album getEntityAlbum(String albumName) {
		List<Album> albums = getJpaTemplate().findByNamedQuery("findAlbumByName", new Object[]{albumName});
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
