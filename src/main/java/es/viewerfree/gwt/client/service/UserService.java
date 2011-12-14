package es.viewerfree.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("springGwtServices/user")
public interface UserService extends RemoteService {
  Boolean login(String userName, String password) throws IllegalArgumentException;
  void logout();
}
