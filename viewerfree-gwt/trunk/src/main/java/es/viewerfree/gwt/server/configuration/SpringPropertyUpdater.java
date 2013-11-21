package es.viewerfree.gwt.server.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * Update Spring managed properties
 */
public class SpringPropertyUpdater implements ApplicationContextAware {

	private ConfigurableApplicationContext ctx;
	private static final String LOCATION_PROPERTY = "locations";
	private static final Log log = LogFactory.getLog(SpringPropertyUpdater.class);

	/**
	 * Update managed properties with new value
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public synchronized void  updateProperties(String name, String comments) throws FileNotFoundException, IOException {
		ConfigurableListableBeanFactory fb = ctx.getBeanFactory();
		BeanDefinition bf = fb.getBeanDefinition(name);
		MutablePropertyValues propertyValues = bf.getPropertyValues();
		PropertyValue propertyValue = propertyValues.getPropertyValue(LOCATION_PROPERTY);
		List<TypedStringValue> locations = (List<TypedStringValue>) propertyValue.getValue();
		for (TypedStringValue location : locations) {
			Resource res = ctx.getResource(location.getValue());
			Properties properties = new Properties();
			File file = res.getFile();
			properties.load(new FileInputStream(file));
			String property = properties.getProperty("jdbc.username");
			properties.setProperty("jdbc.username", "pepe");
			properties.store(new FileOutputStream(file), comments);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = (ConfigurableApplicationContext) applicationContext;

	}
}
