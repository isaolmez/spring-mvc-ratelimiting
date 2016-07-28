package com.isa.ws.rate.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component("fileBasedProperties")
public class AppProperties implements IAppProperties {
	private static final Logger logger = LoggerFactory.getLogger(AppProperties.class);
	private String appPropertiesFileName;
	private Properties properties;

	/**
	 * Initialize Spring Component
	 */
	@PostConstruct
	public void initialize() {
		// Initialize properties file
		appPropertiesFileName = "application.properties";

		// Read application specific properties file
		Resource resource = new ClassPathResource(appPropertiesFileName);
		try {
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Get property value with key
	 */
	public String getProperty(String key) {
		if (key == null || key.isEmpty() || !properties.containsKey(key)) {
			return null;
		}

		return properties.getProperty(key);
	}

	/**
	 * Get property value with key
	 */
	@Override
	public String getProperty(String key, String defaultValue) {
		String result = getProperty(key);
		if (result != null) {
			return result;
		} else {
			return defaultValue;
		}
	}

	/**
	 * Get property value casted to integer
	 */
	@Override
	public int getPropertyAsInteger(String key, int defaultValue) {
		if (key == null || key.isEmpty() || !properties.containsKey(key)) {
			return defaultValue;
		}

		return Integer.parseInt(properties.getProperty(key));
	}

	/**
	 * Get property value casted to boolean
	 */
	public boolean getPropertyAsBoolean(String key, boolean defaultValue) {
		if (key == null || key.isEmpty() || !properties.containsKey(key)) {
			return defaultValue;
		}

		return Boolean.parseBoolean(properties.getProperty(key));
	}

	/**
	 * Get property value casted to a String array
	 */
	public List<String> getPropertyAsList(String key) {
		if (key == null || key.isEmpty() || !properties.containsKey(key)) {
			return new ArrayList<>();
		}

		String values = properties.getProperty(key);
		String[] parts = values.split(",");
		List<String> result = new ArrayList<>();
		for (int i = 0; i < parts.length; i++) {
			result.add(parts[i].trim());
		}

		return result;
	}
}
