package es.viewerfree.gwt.server.configuration.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.viewerfree.gwt.server.viewer.impl.AlbumManagerImpl;
import es.viewerfree.gwt.shared.dto.ConfigDto;
import es.viewerfree.gwt.shared.service.ServiceException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationTestContext.xml"})
public class ConfigurationServiceIT {

	@Autowired
	ConfigurationService configurationService;
	@Autowired
	 AlbumManagerImpl albumManager;
	
	@Test
	public void getConfigurationProperties() throws ServiceException {
		List<ConfigDto> configurationProperties = configurationService.getConfigurationProperties();
		assertTrue(configurationProperties.size()>0);
	}
	
	@Test
	public void updateConfigurationProperties() throws  ServiceException {
		List<ConfigDto> configDtos = new ArrayList<ConfigDto>();
		ConfigDto configDto = new ConfigDto();
		configDto.setFileName("config.local.properties");
		configDto.setKey("jdbc.username");
		configDto.setValue(String.valueOf(System.currentTimeMillis()));
		configDtos.add(configDto);
		configurationService.updateConfigurationProperties(configDtos);
	}
	
	@Test
	public void reloadConfiguration() throws ServiceException {
		assertEquals("pictures", albumManager.getAlbumPath());
		List<ConfigDto> configDtos = new ArrayList<ConfigDto>();
		ConfigDto configDto = new ConfigDto();
		configDto.setFileName("config.local.properties");
		configDto.setKey("application.path");
		configDto.setValue("./testing");
		configDtos.add(configDto);
		configurationService.updateConfigurationProperties(configDtos);
		configurationService.reloadConfiguration();
		assertEquals("./testing", albumManager.getApplicationPath());
	}

}
