package com.isa.ws.rate.utils;

/**
 * Generates keys with specified hierarchy information 
 *
 * @author isa
 *
 */
public interface IHierarchialKeyGenerator extends IKeyGenerator {
	/**
	 * Sets the hierarchy prefix 
	 * 
	 * @param category
	 * @return	
	 */
	IHierarchialKeyGenerator withPrefix(String category);

	/**
	 * Sets the seperator
	 * 
	 * @param seperator
	 * @return
	 */
	IHierarchialKeyGenerator withSeperator(String seperator);
}
