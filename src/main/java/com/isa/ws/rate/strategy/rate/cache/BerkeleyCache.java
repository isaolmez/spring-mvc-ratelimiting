package com.isa.ws.rate.strategy.rate.cache;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

/**
 * Berkeley DB based implementation of ICache API.
 * 
 * @author isa
 *
 */
@Component("berkeleyCache")
public class BerkeleyCache implements ICache<String, Object, Future> {

	@Override
	public Future set(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future set(String key, Object value, int ttl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future delete(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static void main(String[] args) {
		try {
			URL url = new URL("https://www.google.com");
			System.out.println(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
