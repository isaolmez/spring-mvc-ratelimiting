package com.isa.ws.rate.store;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.OperationStatus;

@Component("counterDao")
public class CounterDao implements EntityDao<String, Integer> {
	private static final Logger LOG = LoggerFactory.getLogger(CounterDao.class);
	
	@Autowired
	@Qualifier("berkeleyDBFactory")
	private DatabaseFactory databaseFactory;

	private Database database;

	@PostConstruct
	public void init() {
		database = databaseFactory.getDatabase("basic");
	}

	@PreDestroy
	public void preDestroy(){
		if(database != null){
			try {
				database.close();
			} catch (DatabaseException e) {
				LOG.error("Error occurred: {}", e);
			}
		}
	}
	
	@Override
	public Integer get(String key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		StringBinding.stringToEntry(key, keyEntry);
		Integer result = null;

		try {
			OperationStatus status = database.get(null, keyEntry, dataEntry, null);
			if (status == OperationStatus.SUCCESS) {
				result = IntegerBinding.entryToInt(dataEntry);
			}
		} catch (DatabaseException e) {
			LOG.error("Error occurred: {}", e);
		}

		return result;
	}

	@Override
	public void set(String key, Integer value) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		StringBinding.stringToEntry(key, keyEntry);
		IntegerBinding.intToEntry(value, dataEntry);
		try {
			database.put(null, keyEntry, dataEntry);
		} catch (DatabaseException e) {
			LOG.error("Error occurred: {}", e);
		}
	}

	@Override
	public void delete(String key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		StringBinding.stringToEntry(key, keyEntry);
		try {
			database.delete(null, keyEntry);
		} catch (DatabaseException e) {
			LOG.error("Error occurred: {}", e);
		}
	}

}
