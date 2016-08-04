package com.isa.berkeley_starter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Sequence;
import com.sleepycat.je.SequenceConfig;

public class BerkeleyConnection {
	public static void main(String[] args) {
		try {
			// create a configuration for DB environment
			EnvironmentConfig envConf = new EnvironmentConfig();
			// environment will be created if not exists
			envConf.setAllowCreate(true);

			// open/create the DB environment using config
			Environment dbEnv = new Environment(new File("d:\\berkeleydb"), envConf);

			// create a configuration for DB
			DatabaseConfig dbConf = new DatabaseConfig();
			// db will be created if not exits
			dbConf.setAllowCreate(true);

			// create/open testDB using config
			Database testDB = dbEnv.openDatabase(null, "testDB", dbConf);

			// key
			DatabaseEntry key = new DatabaseEntry();
			// data
			DatabaseEntry data = new DatabaseEntry();

			// sequence will be created if not exits
			SequenceConfig seqConf = new SequenceConfig();
			seqConf.setAllowCreate(true);

			// give a name to sequence
			DatabaseEntry entMySeq = new DatabaseEntry();
			StringBinding.stringToEntry("MY_SEQ", entMySeq);

			// open (or create) sequence
			Sequence mySeq = testDB.openSequence(null, entMySeq, seqConf);

			// will use it for formatting numbers
			NumberFormat formatter = new DecimalFormat("000000");

			// 500.000 records
			for (int i = 0; i < 500000; i++) {

				// read a number from sequence, set key 
				// yes I could just use "i" but I wanted to test sequences
				IntegerBinding.intToEntry((int) mySeq.get(null, 1), key);

				// format and assign "i" to data
				StringBinding.stringToEntry("N#" + formatter.format(i), data);

				// insert key/value pair to database
				testDB.put(null, key, data);

			}
			// empty the data
			StringBinding.stringToEntry("", data);

			// random generator for generating random number
			Random generator = new Random();

			// assing a random number to key
			IntegerBinding.intToEntry((int) generator.nextInt(500000), key);

			// read from database
			if ((testDB.get(null, key, data, null) == OperationStatus.SUCCESS)) {

				// display key/value pair
				System.out.println("Key  :" + IntegerBinding.entryToInt(key));
				System.out.println("Data :" + StringBinding.entryToString(data));

			} else {
				System.out.println("Couldn't find");
			}

			// important: do not forget to close them!
			mySeq.close();
			testDB.close();
			dbEnv.close();

		} catch (DatabaseException dbe) {
			System.out.println("Error :" + dbe.getMessage());
		}
	}
}
