package com.isa.ws.rate.store;

public interface EntityDao<T, V> {
	
	public V get(T key);

	public void set(T key, V value);

	public void delete(T key);
}
