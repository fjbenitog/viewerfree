package es.viewerfree.gwt.client.service;

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
}
