package com.isa.ws.rate.utils;

/**
 * Generates hierarchial keys to be stored in cache
 * 
 * @author isa
 *
 */
public interface IKeyGenerator {
	
	/**
	 * Return the prefixed key
	 * 
	 * @param str
	 * @return		Key
	 */
	String toKey(String str);

	/**
	 * Returns the prefixed key generated from object
	 * 
	 * @param o
	 * @return
	 */
	String toKey(Object o);
}
