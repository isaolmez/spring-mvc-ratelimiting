package com.isa.ws.rate.store;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

	@Autowired
	@Qualifier("berkeleyDBFactory")
	private DatabaseFactory databaseFactory;

	private Database database;

	@PostConstruct
	public void init() {
		System.out.println("Init in " + getClass());
		database = databaseFactory.getDatabase("basic");
	}

	@PreDestroy
	public void preDestroy(){
		System.out.println("Predestroy in " + getClass());
		if(database != null){
			try {
				database.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
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
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void set(String key, Integer value) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();
		StringBinding.stringToEntry(key, keyEntry);
		IntegerBinding.intToEntry(value, dataEntry);
		OperationStatus status = null;
		try {
			status = database.put(null, keyEntry, dataEntry);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String key) {
		DatabaseEntry keyEntry = new DatabaseEntry();
		StringBinding.stringToEntry(key, keyEntry);
		OperationStatus status = null;
		try {
			status = database.delete(null, keyEntry);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

}
