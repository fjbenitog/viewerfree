package es.viewerfree.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import es.viewerfree.gwt.shared.dto.ConfigDto;
import es.viewerfree.gwt.shared.service.ServiceException;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("springGwtServices/config")
public interface ConfigurationService extends RemoteService {

	List<ConfigDto> getConfigValues() throws ServiceException;
	
	void updateConfigValues(List<ConfigDto> configDtos) throws ServiceException;
	
	void reload() throws ServiceException;
	
	void cleanCache() throws ServiceException;
}
