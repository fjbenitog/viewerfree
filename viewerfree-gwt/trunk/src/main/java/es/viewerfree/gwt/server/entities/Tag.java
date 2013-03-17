package es.viewerfree.gwt.server.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity()
@Table(name="vf_tag")
@NamedQueries({@NamedQuery(name="findTagByName",query="SELECT t from Tag t where t.id.name = ? AND t.id.user.user = ?")})
	public class Tag implements Serializable{
 

	@EmbeddedId 
	private TagId id;

	@ManyToMany( cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
	private Set<Album> albums;

	
	public TagId getId() {
		return id;
	}

	public void setId(TagId id) {
		this.id = id;
	}

	@Embeddable
	public static class TagId implements Serializable{
		
		@Column(name="name")
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

	@Override
	public String toString() {
		return "Tag [id=" + id + ", albums=" + albums + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albums == null) ? 0 : albums.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Set<Album> getAlbums() {
		return albums;
	}
	
	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
	
}
