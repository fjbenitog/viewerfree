package es.viewerfree.gwt.server.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity()
@Table(name="vf_tag")
@NamedQueries({@NamedQuery(name="findTagByName",query="SELECT t from Tag t where t.name = ? AND t.user.user = ?")})
	public class Tag implements Serializable{
 

	@Id
	@Column(name="TAG_NAME")
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;

	@ManyToMany( cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
	@JoinTable(name="vf_album_tag",
			joinColumns=@JoinColumn(name="TAG_NAME"),
	        inverseJoinColumns=@JoinColumn(name="NAME"))
	private Collection<Album> albums;

	

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
	public String toString() {
		return "Tag [name=" + name + ", user=" + user + ", albums=" + albums
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albums == null) ? 0 : albums.hashCode());
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
		Tag other = (Tag) obj;
		if (albums == null) {
			if (other.albums != null)
				return false;
		} else if (!albums.equals(other.albums))
			return false;
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
	
	public Collection<Album> getAlbums() {
		return albums;
	}
	
	public void setAlbums(Collection<Album> albums) {
		this.albums = albums;
	}
	
}
