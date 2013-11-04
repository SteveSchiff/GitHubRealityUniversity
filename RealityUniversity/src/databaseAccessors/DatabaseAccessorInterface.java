package databaseAccessors;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * The Interface databaseAccessorInterface sets the connection information for all databaseAccessors.
 */
public interface DatabaseAccessorInterface {
	

	
	
	// JDBC Driver and URL
	/** The Constant JDBC_DRIVER. */
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	
	/** The Constant DATABASE. */
	// The "\\realityu_production.dat" should be changed to "/realityu_production.dat" when program
	// is to be run on a Mac and quite possible also if run on a Linux system.
	String DATABASE = "jdbc:sqlite:" + System.getProperty("user.dir") + "/realityu_production.dat";
	
	/** The Constant USER. */
	static final String USER = "project";

	/** The Constant PASS. */
	static final String PASS = "project";

	/** The Constant XML Jobs File. */
	static final File XML_DEFAULT_JOBS = new File("lib/jobs.xml");

	/** The Constant DATE_FORMAT. */
	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	

	/**
	 * This method makes sure table exists<br>
	 * The DAO will check each time an action is performed.
	 * 
	 * @return Returns True/False
	 * 
	 */
	//boolean doesJobsTableExist();

	/**
	 * Creates a table
	 * 
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	int createTable();
}