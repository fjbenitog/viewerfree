package es.viewerfree.gwt.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import es.viewerfree.gwt.server.dao.ITagDao;
import es.viewerfree.gwt.server.entities.Album;
import es.viewerfree.gwt.server.entities.Tag;
import es.viewerfree.gwt.server.entities.Tag.TagId;
import es.viewerfree.gwt.server.entities.User;

public class TagDao extends JpaDaoSupport implements ITagDao {

	@Override
	public void addTag(User user, String albumName, String tagName) {
		Tag tag = getEntityTag(user.getUser(), tagName);
		Album album = getEntityAlbum(albumName);
		if(tag == null){
			tag = composeTag(user, album, tagName);
		}else{
			if(!tag.getAlbums().contains(album)){
				tag.getAlbums().add(album);
			}
		}
		getJpaTemplate().merge(tag);
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
	public List<Tag> getTagsByAlbum(User user, String albumName) {
		List<Tag> tags = getJpaTemplate().findByNamedQuery("findTagsByAlbum", new Object[]{user.getId(),albumName});
		
		return tags;
	}
	
	private Tag getEntityTag(String userName, String tagName) {
		List<Tag> tags = getJpaTemplate().findByNamedQuery("findTagByName", new Object[]{tagName,userName});
		Tag tagEntity = null;
		if(tags.size()==1){
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
