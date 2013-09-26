package es.viewerfree.gwt.server;

import java.util.List;

import es.viewerfree.gwt.client.service.ConfigurationService;
import es.viewerfree.gwt.server.configuration.IConfigurationService;
import es.viewerfree.gwt.server.service.SpringRemoteServiceServlet;
import es.viewerfree.gwt.shared.dto.ConfigDto;
import es.viewerfree.gwt.shared.service.ServiceException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConfigurationServiceImpl extends SpringRemoteServiceServlet implements ConfigurationService {

	private IConfigurationService configurationService;

	@Override
	public List<ConfigDto> getConfigValues() throws ServiceException {
		return configurationService.getConfigurationProperties();
	}
	public IConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(IConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	
	@Override
	public void updateConfigValues(List<ConfigDto> configDtos) throws ServiceException {
		configurationService.updateConfigurationProperties(configDtos);
	}
	@Override
	public void reload() throws ServiceException {
		configurationService.reloadConfiguration();
	}



}
