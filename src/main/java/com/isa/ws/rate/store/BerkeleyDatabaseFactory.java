package com.isa.ws.rate.store;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

@Component("berkeleyDBFactory")
public class BerkeleyDatabaseFactory implements DatabaseFactory {

	private Environment dbEnv;
	
	@Value("${berkeley.path}")
	private String path;

	@PostConstruct
	public void init() {
		System.out.println("Init in " + getClass());
		try {
			// create a configuration for DB environment
			EnvironmentConfig envConf = new EnvironmentConfig();
			// environment will be created if not exists
			envConf.setAllowCreate(true);
			// open/create the DB environment using config
			dbEnv = new Environment(new File(path), envConf);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Database getDatabase(String name) {
		// create a configuration for DB
		DatabaseConfig databaseConfig = new DatabaseConfig();
		// db will be created if not exits
		databaseConfig.setAllowCreate(true);
		return getDatabase(name, databaseConfig);
	}

	@Override
	public Database getDatabase(String name, DatabaseConfig databaseConfig) {
		// create/open testDB using config
		Database databaseHandle = null;
		try {
			databaseHandle = dbEnv.openDatabase(null, name, databaseConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		return databaseHandle;
	}

	@PreDestroy
	public void preDestroy(){
		System.out.println("Predestroy in " + getClass());
		if(dbEnv != null){
			try {
				dbEnv.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
	}
}
