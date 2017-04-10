package com.isa.ws.rate.utils;

/**
 * Generates keys with specified hierarchy information 
 *
 * @author isa
 *
 */
public interface HierarchialKeyGenerator extends KeyGenerator {
	/**
	 * Sets the hierarchy prefix
	 *
	 * @param category
	 * @return
	 */
	HierarchialKeyGenerator withPrefix(String category);

	/**
	 * Sets the seperator
	 * 
	 * @param seperator
	 * @return
	 */
	HierarchialKeyGenerator withSeperator(String seperator);
}
