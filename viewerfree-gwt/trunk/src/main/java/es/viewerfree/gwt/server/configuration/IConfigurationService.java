package es.viewerfree.gwt.server.configuration;

import java.util.List;

import es.viewerfree.gwt.shared.dto.ConfigDto;
import es.viewerfree.gwt.shared.service.ServiceException;

public interface IConfigurationService {

	public List<ConfigDto> getConfigurationProperties() throws ServiceException;
	
	public void updateConfigurationProperties(List<ConfigDto>  configDtos) throws ServiceException;
	
	void reloadConfiguration() throws ServiceException;
}
