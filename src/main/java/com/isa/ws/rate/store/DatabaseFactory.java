package com.isa.ws.rate.store;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;

public interface DatabaseFactory {
	Database getDatabase(String name);

	Database getDatabase(String name, DatabaseConfig databaseConfig);
}
