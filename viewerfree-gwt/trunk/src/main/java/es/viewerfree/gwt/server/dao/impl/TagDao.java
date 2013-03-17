package es.viewerfree.gwt.server.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		if(tag == null){
			tag = composeTag(user, albumName, tagName);
		}else{
			Album album = new Album();
			album.setName(albumName);
			tag.getAlbums().add(album);
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

	private Tag getEntityTag(String userName, String tagName) {
		List<Tag> tags = getJpaTemplate().findByNamedQuery("findTagByName", new Object[]{tagName,userName});
		Tag tagEntity = null;
		if(tags.size()==1){
			tagEntity = tags.get(0);
		}
		return tagEntity;
	}

	private Tag composeTag(User user, String albumName, String tagName) {
		Tag tag;
		tag = new Tag();
		TagId id = new TagId();
		id.setName(tagName);
		id.setUser(user);
		tag.setId(id);
		Album album = new Album();
		album.setName(albumName);
		Set<Album> albums = new HashSet<Album>();
		albums.add(album);
		tag.setAlbums(albums);
		return tag;
	}
}
