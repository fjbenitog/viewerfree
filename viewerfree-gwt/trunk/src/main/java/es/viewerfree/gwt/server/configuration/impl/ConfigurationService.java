package es.viewerfree.gwt.server.configuration.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import es.viewerfree.gwt.server.FileUploadService;
import es.viewerfree.gwt.server.ImageService;
import es.viewerfree.gwt.server.configuration.IConfigurationService;
import es.viewerfree.gwt.server.viewer.impl.AlbumManagerImpl;
import es.viewerfree.gwt.shared.dto.ConfigDto;
import es.viewerfree.gwt.shared.service.ServiceException;

public class ConfigurationService implements IConfigurationService, ApplicationContextAware {

	private ConfigurableApplicationContext ctx;
	private static final String LOCATION_PROPERTY = "locations";

	private AlbumManagerImpl albumManager;
	private FileUploadService fileUploadService;
	private ImageService imageService;

	@Override
	public List<ConfigDto> getConfigurationProperties()  throws ServiceException {
		List<File> configurationFiles;
		try {
			configurationFiles = getConfigurationFiles();
			List<ConfigDto> configDtos = new ArrayList<ConfigDto>();
			for (File file : configurationFiles) {
				Properties properties = loadPropertyFile(file);
				Enumeration<Object> keys = properties.keys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					ConfigDto configDto = new ConfigDto();
					configDto.setKey(key);
					configDto.setValue(properties.getProperty(key));
					configDto.setLabel(key);
					configDto.setFileName(file.getName());
					configDtos.add(configDto);
				}
			}
			Collections.sort(configDtos);
			return configDtos;
		} catch (IOException e) {
			throw new ServiceException("Error getting properties", e);
		}
	}

	@Override
	public void updateConfigurationProperties(List<ConfigDto> configDtos) throws ServiceException {
		List<File> configurationFiles;
		try {
			configurationFiles = getConfigurationFiles();
			for (File file : configurationFiles) {
				Properties properties = loadPropertyFile(file);
				for (ConfigDto configDto : configDtos) {
					if(file.getName().equals(configDto.getFileName())){
						properties.setProperty(configDto.getKey(), configDto.getValue());
					}
				}
				storePropertyFile(properties,file);
			}
		} catch (IOException e) {
			throw new ServiceException("Error updating properties", e);
		}
	}

	@Override
	public void reloadConfiguration()  throws ServiceException {
		Properties properties;
		try {
			properties = mergeProperties(getConfigurationFiles());
			albumManager.setAlbumPath(properties.getProperty("album.path"));
			albumManager.setApplicationPath(properties.getProperty("application.path"));
			albumManager.setCachedPath(properties.getProperty("preview.path"));
			albumManager.setThumbnailCachedPath(properties.getProperty("thumbnail.path"));
			fileUploadService.setAlbumPath(properties.getProperty("album.path"));
			fileUploadService.setApplicationPath(properties.getProperty("application.path"));
			imageService.setCachedPath(properties.getProperty("preview.path"));
			imageService.setHeight(Integer.parseInt(properties.getProperty("image.max.height")));
			imageService.setThumbnailCachedPath(properties.getProperty("thumbnail.path"));
			imageService.setThumbnailHeight(Integer.parseInt(properties.getProperty("thumbnail.max.height")));
		} catch (IOException e) {
			throw new ServiceException("Error reloading values", e);
		}
	}

	private List<File> getConfigurationFiles() throws IOException{
		ConfigurableListableBeanFactory fb = ctx.getBeanFactory();
		BeanDefinition bf = fb.getBeanDefinition("propertyConfigurer");
		MutablePropertyValues propertyValues = bf.getPropertyValues();
		PropertyValue propertyValue = propertyValues.getPropertyValue(LOCATION_PROPERTY);
		List<TypedStringValue> locations = (List<TypedStringValue>) propertyValue.getValue();
		List<File> configFiles = new ArrayList<File>();
		for (TypedStringValue location : locations) {
			Resource res = ctx.getResource(location.getValue());
			configFiles.add(res.getFile());
		}
		return configFiles;
	}

	private Properties mergeProperties(List<File> propertyFiles) throws IOException{
		Properties base = new Properties();
		for (File file : propertyFiles) {
			base.putAll(loadPropertyFile(file));
		}
		return base;
	}

	private Properties loadPropertyFile(File file) throws IOException{
		FileInputStream fileInputStream = new FileInputStream(file);
		try{
			Properties properties = new Properties();
			properties.load(fileInputStream);
			return properties;
		}finally{
			fileInputStream.close();
		}
	}

	private void storePropertyFile(Properties properties, File file) throws IOException{
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		try{
			properties.store(fileOutputStream, "Updated by Application");
		}finally{
			fileOutputStream.close();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = (ConfigurableApplicationContext) applicationContext;

	}

	public AlbumManagerImpl getAlbumManager() {
		return albumManager;
	}

	public void setAlbumManager(AlbumManagerImpl albumManager) {
		this.albumManager = albumManager;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

}
