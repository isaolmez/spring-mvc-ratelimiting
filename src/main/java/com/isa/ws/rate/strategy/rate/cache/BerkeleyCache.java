package com.isa.ws.rate.strategy.rate.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.store.BasicDao;

/**
 * Berkeley DB based implementation of ICache API.
 * 
 * @author isa
 *
 */
@Component("berkeleyCache")
public class BerkeleyCache implements ICache<String, Object, Future> {

	@Autowired
	@Qualifier("basicDao")
	private BasicDao counterDao;

	private ExecutorService executor;
	
	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public Future set(String key, Object value) {
		Future future = executor.submit(()->{
			counterDao.set(key, value);
		});
		
		return future;
	}

	@Override
	public Future set(String key, Object value, int ttl) {
		Future future = executor.submit(()->{
			counterDao.set(key, value);
		});
		
		return future;
	}

	@Override
	public Future delete(String key) {
		Future future = executor.submit(()->{
			counterDao.delete(key);
		});
		
		return future;
	}

	@Override
	public Object get(String key) {
		Future future = executor.submit(()->{
			return counterDao.get(key);
		});
		Object result = null;
		
		try {
			result = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
