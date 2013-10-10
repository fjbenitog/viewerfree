package es.viewerfree.gwt.server.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import es.viewerfree.gwt.server.entities.Tag;
import es.viewerfree.gwt.shared.dto.TagDto;
import es.viewerfree.gwt.shared.dto.UserDto;

@XmlRootElement(name="viewerfree")
public class UserDtoList {

	private List<UserTags> users;

	@XmlElementWrapper(name = "users")
	@XmlElement(name = "user")
	public List<UserTags> getUsers() {
		return users;
	}

	public void setUsers(List<UserTags> users) {
		this.users = users;
	}
	
	public static final class UserTags {
		
		private UserDto userDto;
		
		private List<TagDto> tags;
		
		public List<TagDto> getTags() {
			return tags;
		}

		public void setTags(List<TagDto> tags) {
			this.tags = tags;
		}

		public UserDto getUserDto() {
			return userDto;
		}

		public void setUserDto(UserDto userDto) {
			this.userDto = userDto;
		}
	}
	
	
}
