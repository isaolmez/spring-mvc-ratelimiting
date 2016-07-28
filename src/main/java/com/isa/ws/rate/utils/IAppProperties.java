package com.isa.ws.rate.utils;

import java.util.List;

/**
 * API for managing application properties/settings
 * 
 * @author isa
 *
 */
public interface IAppProperties {
	/**
	 * Get property value with key
	 *
	 * @param key
	 *            key to be searched
	 * @return property value
	 */
	String getProperty(String key);
	
	/**
	 * Get property value with key
	 *
	 * @param key
	 *            key to be searched
	 * @param defaultValue
	 *            default value if no value has been found
	 * @return property value
	 */
	String getProperty(String key, String defaultValue);

	/**
	 * Get property value casted to integer
	 *
	 * @param key
	 *            key to be searched
	 * @param defaultValue
	 *            default value if no value has been found
	 * @return property value
	 */
	int getPropertyAsInteger(String key, int defaultValue);

	/**
	 * Get property value casted to boolean
	 *
	 * @param key
	 *            key to be searched
	 * @param defaultValue
	 *            default value if no value has been found
	 * @return property value
	 */
	boolean getPropertyAsBoolean(String key, boolean defaultValue);
	
	/**
	 * @param key key to be searched
	 * @return	list of values
	 */
	public List<String> getPropertyAsList(String key);
}
