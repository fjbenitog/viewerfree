package es.viewerfree.gwt.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.dto.UserDto;
import es.viewerfree.gwt.server.dto.UserProfile;
import es.viewerfree.gwt.server.entities.User;



public class UserDao extends JpaDaoSupport implements IUserDao{

	public List<UserDto> findUser(String user, String password)throws DaoException {
		try{
		
			List<User> users = getJpaTemplate().findByNamedQuery("findUserByUserAndPassword", new Object[]{user,password});
			List<UserDto> usersDto = new ArrayList<UserDto>();
			for (User userEntity : users) {
				usersDto.add(toUserDto(userEntity));
			}
			return usersDto;
		}catch (Exception e) {
			throw new DaoException("Unanabled to find a User",e);
		}
	}
	
	public UserDto getUser(String user) throws DaoException {
		try{
			
			List<User> users = getJpaTemplate().findByNamedQuery("findUserByUser", new Object[]{user});
			if(users.size()==1){
				return toUserDto(users.get(0));
			}
			return null;
		}catch (Exception e) {
			throw new DaoException("Unanabled to find a User",e);
		}
	}
	
	public void createUser(UserDto userDto)throws DaoException {
		try{
			getJpaTemplate().persist(toUser(userDto));
		}catch (Exception e) {
			throw new DaoException("Unanabled to create a User",e);
		}

	}
	private User toUser(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setUser(userDto.getName());
		user.setPassword(userDto.getPassword());
		UserProfile profile = userDto.getProfile();
		user.setProfile(profile!=null?profile.toString():UserProfile.NORMAL.toString());
		user.setName(userDto.getFullName());
		user.setSurname(userDto.getSurname());
		user.setEmail(userDto.getEmail());
		return user;
	}
	
	private UserDto toUserDto(User user){
		UserDto userDto = new UserDto(user.getUser(), user.getPassword());
		userDto.setId(user.getId());
		userDto.setProfile(UserProfile.valueOf(user.getProfile()));
		userDto.setFullName(user.getName());
		userDto.setSurname(user.getSurname());
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	public List<UserDto> findAllUsers() throws DaoException {
		try{
			
			List<User> users = getJpaTemplate().findByNamedQuery("findAllUsers");
			List<UserDto> usersDto = new ArrayList<UserDto>();
			for (User userEntity : users) {
				usersDto.add(toUserDto(userEntity));
			}
			return usersDto;
		}catch (Exception e) {
			throw new DaoException("Unanabled to find all Users",e);
		}
	}

	public UserDto getUser(Long id) throws DaoException {
		return toUserDto(getJpaTemplate().find(User.class, id));
	}

	public void modifyUser(UserDto userDto) throws DaoException {
		User user = getJpaTemplate().find(User.class, userDto.getId());		
		user.setPassword(userDto.getPassword());
		UserProfile profile = userDto.getProfile();
		user.setProfile(profile!=null?profile.toString():UserProfile.NORMAL.toString());
		user.setName(userDto.getFullName());
		user.setSurname(userDto.getSurname());
		user.setEmail(userDto.getEmail());
	}

}
