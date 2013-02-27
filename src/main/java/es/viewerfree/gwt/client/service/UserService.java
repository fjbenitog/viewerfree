package es.viewerfree.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.service.ServiceException;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("springGwtServices/user")
public interface UserService extends RemoteService {
  Boolean login(String userName, String password) throws IllegalArgumentException;
  
  UserDto getUser();
  
  void createUser(UserDto user) throws ServiceException ;
  
  void ping();
  
  List<UserDto> getUsers() throws ServiceException;
  
  UserDto getUser(String name) throws ServiceException;
  
  void modifyUser(UserDto user) throws ServiceException;
  
  void delete(List<String> users) throws ServiceException;
}
