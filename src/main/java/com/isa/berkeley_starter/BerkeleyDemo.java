package com.isa.berkeley_starter;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import java.io.File;

/**
 * Author: Gokhan Atil 
 * Web: http://www.gokhanatil.com
 */
public class BerkeleyDemo {

  public static void main(String[] args) {

    try {
      // create a configuration for DB environment
      EnvironmentConfig envConf = new EnvironmentConfig();
      // environment will be created if not exists
      envConf.setAllowCreate(true);

      // open/create the DB environment using config
      Environment dbEnv = new Environment(
              new File("d:\\berkeleydb"), envConf);

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

      Employee emp = new Employee( "Gokhan", "gokhan@gokhanatil", "IT");

      // assign 1 to key
      IntegerBinding.intToEntry(1, key);
      // insert into database
      testDB.put(null, key, emp.objectToEntry());

      // assign 2 to key
      IntegerBinding.intToEntry(2, key);
      // assign new values
      emp.setEmployee("Jack", "jack@nowhere", "SALES");
      // insert into database
      testDB.put(null, key, emp.objectToEntry());

      // assign 3 to key
      IntegerBinding.intToEntry(3, key);
      // assign new values
      emp.setEmployee("Lee", "jet@lee", "BILLING");
      // insert into database
      testDB.put(null, key, emp.objectToEntry());

      // assign 2 to key
      IntegerBinding.intToEntry(2, key);      

      // read from database (find key=2)
      if ((testDB.get(null, key, data, null)
              == OperationStatus.SUCCESS)) {

        // assign new values from dataentry to emp
        emp.entryToObject(data);
        // display emp
        emp.Display();

      } else {
        System.out.println("Couldn't find");
      }

      // important: do not forget to close them!
      testDB.close();
      dbEnv.close();

    } catch (DatabaseException dbe) {
      System.out.println("Error :" + dbe.getMessage());
    }
  }
}