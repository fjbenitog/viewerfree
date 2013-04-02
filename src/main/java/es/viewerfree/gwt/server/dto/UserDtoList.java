package es.viewerfree.gwt.server.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import es.viewerfree.gwt.shared.dto.UserDto;

@XmlRootElement(name="viewerfree")
public class UserDtoList {

	private List<UserDto> users;

	@XmlElementWrapper(name = "users")
	@XmlElement(name = "user")
	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
	
	
}
