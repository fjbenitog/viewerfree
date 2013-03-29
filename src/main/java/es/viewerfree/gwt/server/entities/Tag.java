package es.viewerfree.gwt.server.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity()
@Table(name="vf_tag")
@NamedQueries({@NamedQuery(name="findTagByName",query="SELECT t from Tag t where t.tagId.name = ? AND t.tagId.user.user = ?")})
@NamedNativeQueries({@NamedNativeQuery(name="findTagsByAlbum",query="SELECT t.TAG_NAME,t.USER_ID_USER from viewerfree.VF_TAG t, viewerfree.VF_ALBUM_TAG a, viewerfree.VF_USERS u " +
		"where u.LOGIN = ? AND t.USER_ID_USER = u.ID_USER AND a.ALBUMS_NAME = ? AND t.TAG_NAME = a.VF_TAG_TAG_NAME ORDER BY t.TAG_NAME",resultClass=Tag.class),
		@NamedNativeQuery(name="findOtherTags",query="SELECT t.TAG_NAME,t.USER_ID_USER from viewerfree.VF_TAG t, viewerfree.VF_USERS u " +
						"where u.LOGIN = ?1 AND t.USER_ID_USER = u.ID_USER AND" +
						" t.TAG_NAME NOT IN( SELECT t.TAG_NAME from viewerfree.VF_TAG t, viewerfree.VF_ALBUM_TAG a, viewerfree.VF_USERS u " +
		"where u.LOGIN = ?1 AND t.USER_ID_USER = u.ID_USER AND a.ALBUMS_NAME = ?2 AND t.TAG_NAME = a.VF_TAG_TAG_NAME ) ORDER BY t.TAG_NAME",resultClass=Tag.class),
				@NamedNativeQuery(name="findTagsByUser",query="SELECT t.TAG_NAME,t.USER_ID_USER from viewerfree.VF_TAG t, viewerfree.VF_USERS u " +
						"where u.LOGIN = ? AND t.USER_ID_USER = u.ID_USER ORDER BY t.TAG_NAME",resultClass=Tag.class)})

public class Tag implements Serializable{


	@EmbeddedId
	private TagId tagId;

	@ManyToMany( cascade = {CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinTable(name="vf_album_tag")
	private Collection<Album> albums;


	public TagId getTagId() {
		return tagId;
	}

	public void setTagId(TagId tagId) {
		this.tagId = tagId;
	}


	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", albums=" + albums + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albums == null) ? 0 : albums.hashCode());
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (albums == null) {
			if (other.albums != null)
				return false;
		} else if (!albums.equals(other.albums))
			return false;
		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId))
			return false;
		return true;
	}

	public Collection<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(Collection<Album> albums) {
		this.albums = albums;
	}

	@Embeddable
	public static class TagId implements Serializable{

		@Column(name="TAG_NAME")
		private String name;

		@ManyToOne(fetch=FetchType.LAZY)
		private User user;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TagId other = (TagId) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (user == null) {
				if (other.user != null)
					return false;
			} else if (!user.equals(other.user))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "TagId [name=" + name + ", user=" + user + "]";
		}


	}

}
