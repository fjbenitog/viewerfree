package es.viewerfree.gwt.shared.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1202865512547719197L;

	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserDto() {
		super();
	}

	private String _name;
	
	private String _fullName;
	
	private String _surname;
	
	private String _email;
	
	private List<String> albums;
	
	public String getFullName() {
		return _fullName;
	}

	public void setFullName(String fullName) {
		_fullName = fullName;
	}

	public String getSurname() {
		return _surname;
	}

	public void setSurname(String surname) {
		_surname = surname;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String email) {
		_email = email;
	}

	private String password;
	
	private String _gmailUser;
	
	private String _gmailPassword;
	
	private String language;
	
	private UserProfile _profile;
	
	public UserProfile getProfile() {
		return _profile;
	}

	public void setProfile(UserProfile profile) {
		_profile = profile;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGmailUser() {
		return _gmailUser;
	}

	public void setGmailUser(String gmailUser) {
		_gmailUser = gmailUser;
	}

	public String getGmailPassword() {
		return _gmailPassword;
	}

	public void setGmailPassword(String gmailPassword) {
		_gmailPassword = gmailPassword;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public UserDto(String name, String password) {
		super();
		_name = name;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElementWrapper(name = "albums")
	@XmlElement(name = "album")
	public List<String> getAlbums() {
		return albums;
	}
	
	public void setAlbums(List<String> albums) {
		this.albums = albums;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_email == null) ? 0 : _email.hashCode());
		result = prime * result
				+ ((_fullName == null) ? 0 : _fullName.hashCode());
		result = prime * result
				+ ((_gmailPassword == null) ? 0 : _gmailPassword.hashCode());
		result = prime * result
				+ ((_gmailUser == null) ? 0 : _gmailUser.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result
				+ ((_profile == null) ? 0 : _profile.hashCode());
		result = prime * result
				+ ((_surname == null) ? 0 : _surname.hashCode());
		result = prime * result + ((albums == null) ? 0 : albums.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		UserDto other = (UserDto) obj;
		if (_email == null) {
			if (other._email != null)
				return false;
		} else if (!_email.equals(other._email))
			return false;
		if (_fullName == null) {
			if (other._fullName != null)
				return false;
		} else if (!_fullName.equals(other._fullName))
			return false;
		if (_gmailPassword == null) {
			if (other._gmailPassword != null)
				return false;
		} else if (!_gmailPassword.equals(other._gmailPassword))
			return false;
		if (_gmailUser == null) {
			if (other._gmailUser != null)
				return false;
		} else if (!_gmailUser.equals(other._gmailUser))
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_profile != other._profile)
			return false;
		if (_surname == null) {
			if (other._surname != null)
				return false;
		} else if (!_surname.equals(other._surname))
			return false;
		if (albums == null) {
			if (other.albums != null)
				return false;
		} else if (!albums.equals(other.albums))
			return false;
		if (id != other.id)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", _name=" + _name + ", _fullName="
				+ _fullName + ", _surname=" + _surname + ", _email=" + _email
				+ ", albums=" + albums + ", password=" + password
				+ ", _gmailUser=" + _gmailUser + ", _gmailPassword="
				+ _gmailPassword + ", language=" + language + ", _profile="
				+ _profile + "]";
	}

	
}
