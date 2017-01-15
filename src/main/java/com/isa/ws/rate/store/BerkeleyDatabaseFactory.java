package com.isa.ws.rate.store;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.config.ApplicationConfiguration;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

@Component("berkeleyDBFactory")
public class BerkeleyDatabaseFactory implements DatabaseFactory {
	private static final Logger LOG = LoggerFactory.getLogger(BerkeleyDatabaseFactory.class);
	
	@Autowired
	private ApplicationConfiguration config;
	
	private Environment dbEnv;
	
	@PostConstruct
	public void init() {
		try {
			// create a configuration for DB environment
			EnvironmentConfig envConf = new EnvironmentConfig();
			// environment will be created if not exists
			envConf.setAllowCreate(true);
			// open/create the DB environment using config
			dbEnv = new Environment(new File(config.getBerkeleyDatabasePath()), envConf);
		} catch (DatabaseException e) {
			LOG.error("Error occurred: {}", e);
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
			LOG.error("Error occurred: {}", e);
		}

		return databaseHandle;
	}

	@PreDestroy
	public void preDestroy(){
		if(dbEnv != null){
			try {
				dbEnv.close();
			} catch (DatabaseException e) {
				LOG.error("Error occurred: {}", e);
			}
		}
	}
}
