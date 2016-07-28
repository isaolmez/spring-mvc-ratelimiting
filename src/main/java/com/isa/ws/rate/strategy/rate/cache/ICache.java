package com.isa.ws.rate.strategy.rate.cache;

/**
 * 
 * Cache API required to perform basic set/get/delete operations
 * 
 * @author isa
 *
 * @param <TKey>		Key type for cache entry
 * @param <TValue>		Value type for cache entry
 * @param <TBoolean>	Return value for cache operation
 */
public interface ICache<TKey, TValue, TBoolean> {
	/**
	 * Store the key/value 
	 * 
	 * @param key
	 * @param value
	 * @return		Operation status
	 */
	TBoolean set(TKey key, TValue value);

	/**
	 * Store the key/value with specified TTL
	 * 
	 * @param key
	 * @param value		
	 * @param ttl
	 * @return		Operation status
	 */
	TBoolean set(TKey key, TValue value, int ttl);

	/**
	 * Delete the key/value
	 * 
	 * @param key	
	 * @return		Operation status
	 */
	TBoolean delete(TKey key);

	/**
	 * Fetch the value with given key
	 * 
	 * @param key	Key for entry
	 * @return		Value
	 */
	TValue get(TKey key);
}
