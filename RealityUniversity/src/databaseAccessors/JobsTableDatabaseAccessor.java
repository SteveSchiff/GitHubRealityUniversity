package databaseAccessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import obj.Job;

/**
 * The Class JobsDAO retrieves data from the "jobs" table.
 */
public class JobsTableDatabaseAccessor implements DatabaseAccessorInterface {

	private Map<String, List<Job>> mapJobsByCategory = new HashMap<>();

	private List<Job> jobsList = new ArrayList<>(); // List of objects being
													// returned

	private List<Job> jobCategoryList = new ArrayList<>();

	/**
	 * Find jobs and sort them by category.
	 * 
	 * @return A Map of Job objects.
	 */
	public Map<String, List<Job>> getJobsByCategory() {

		return mapJobsByCategory;
	} // end findJobsByCategory method

	/**
	 * Search for Jobs
	 * 
	 * @param where
	 *            : The column name in the table to look for.
	 * @param criteria
	 *            : The term to look for in the column.
	 * 
	 * @return Returns a List of Job objects.
	 */
	public List<Job> select(String where, Object criteria) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		String sql = "";
		String strWhere = "";
		String strGroupBy = "";

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			if (where != null) { // if a WHERE condition exists
				strWhere = " WHERE " + where + " = " + criteria;
			}

			sql = "SELECT * FROM jobs" + strWhere + strGroupBy;
			stmt = conn.prepareStatement(sql);

			// Get ResultSet by Column Name
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Job jobObject = new Job(); // Create a new job object

				jobObject.setID(rs.getInt("id"));
				jobObject.setName(rs.getString("name"));
				jobObject.setType(rs.getString("type"));
				jobObject.setIndustry(rs.getString("industry"));
				jobObject.setCategory(rs.getString("category"));
				jobObject.setAnnGrossSal(rs.getDouble("annGrossSal"));
				jobObject.setMonGrossSal(rs.getDouble("monGrossSal"));
				jobObject.setMarAnnualTax(rs.getDouble("marAnnualTax"));
				jobObject.setMarMonthlyTax(rs.getDouble("marMonthlyTax"));
				jobObject.setMarAfterTax(rs.getDouble("marAfterTax"));
				jobObject.setSinAnnualTax(rs.getDouble("sinAnnualTax"));
				jobObject.setSinMonthlyTax(rs.getDouble("sinMonthlyTax"));
				jobObject.setSinAfterTax(rs.getDouble("sinAfterTax"));
				jobObject.setGPA(rs.getInt("gpa"));
				jobObject.setLoan(rs.getDouble("loan"));

				// If the current job's category matches current key
				if (mapJobsByCategory.containsKey(jobObject.getCategory()))
					// Add it to the current ArrayList
					jobCategoryList = mapJobsByCategory.get(jobObject
							.getCategory());
				else
					// Otherwise, start a new ArrayList
					jobCategoryList = new ArrayList<>();

				jobCategoryList.add(jobObject); // Add this job to the List of
												// jobs

				// Add the job to the map with the category as the key
				mapJobsByCategory.put(jobObject.getCategory(), jobCategoryList);

				// ********

				jobsList.add(jobObject); // Add this job to the List of jobs
			} // end while

		} catch (SQLException se) {
			// Handle Errors for JDBC
			System.out.println("JDBC Error. Current DB: " + DATABASE);
		} catch (Exception e) {
			// Handle Errors for Class
			System.out.println("Class Error. Current DB: " + DATABASE);
		} finally {
			// Close Resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // Nothing to do

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // End Finally Try/Catch
		} // End Try/Catch

		return jobsList;
	} // -- end select() method

	/**
	 * Updates a Job.
	 * 
	 * @param job
	 *            : The Job object being updated.
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int update(Job job) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "UPDATE jobs SET name=?, type=?, industry=?, category=?,annGrossSal=?, monGrossSal=?, marAnnualTax=?,"
					+ "marMonthlyTax=?, marAfterTax=?, sinAnnualTax=?, sinMonthlyTax=?, sinAfterTax=?, gpa=?, loan=? WHERE id=?";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, job.getName());
			stmt.setString(2, job.getType());
			stmt.setString(3, job.getIndustry());
			stmt.setString(4, job.getCategory());

			stmt.setDouble(5, job.getAnnGrossSal());
			stmt.setDouble(6, job.getMonGrossSal());
			stmt.setDouble(7, job.getMarAnnualTax());
			stmt.setDouble(8, job.getMarMonthlyTax());
			stmt.setDouble(9, job.getMarAfterTax());
			stmt.setDouble(10, job.getSinAnnualTax());
			stmt.setDouble(11, job.getSinMonthlyTax());
			stmt.setDouble(12, job.getSinAfterTax());

			stmt.setInt(13, job.getGPA());
			stmt.setDouble(14, job.getLoan());
			stmt.setInt(15, job.getID());

			rows = stmt.executeUpdate();

			// Close Query and Database Connection
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle Errors for Class
			e.printStackTrace();
		} finally {
			// Close Resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // Nothing to do

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // End Finally Try/Catch
		} // End Try/Catch

		return rows;
	} // -- end update() method

	/**
	 * Insert.
	 * 
	 * @param job
	 *            : The Job object being inserted.
	 * 
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int insert(Job job) {

		// Variable Declarations
		Connection conn = null;
		Statement stmt = null;
		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			stmt = conn.createStatement();
			String sql = "INSERT INTO jobs(name, type, industry,"
					+ "category, annGrossSal, monGrossSal, marAnnualTax,marMonthlyTax,"
					+ "marAfterTax, sinAnnualTax, sinMonthlyTax, sinAfterTax, gpa, loan)"
					+ "VALUES('"
					+ job.getName()
					+ "', '"
					+ job.getType()
					+ "', '"
					+ job.getIndustry()
					+ "', '"
					+ job.getCategory()
					+ "', "
					+ job.getAnnGrossSal()
					+ ", "
					+ job.getMonGrossSal()
					+ ", "
					+ job.getMarAnnualTax()
					+ ", "
					+ job.getMarMonthlyTax()
					+ ", "
					+ job.getMarAfterTax()
					+ ", "
					+ job.getSinAnnualTax()
					+ ", "
					+ job.getSinMonthlyTax()
					+ ", "
					+ job.getSinAfterTax()
					+ ", "
					+ job.getGPA()
					+ ", "
					+ job.getLoan() + ")";

			rows = stmt.executeUpdate(sql);

			// Close Query and Database Connection
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle Errors for Class
			e.printStackTrace();
		} finally {
			// Close Resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // Nothing to do

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // End Finally Try/Catch
		} // End Try/Catch

		return rows;
	} // -- end insert() method

	/**
	 * Delete a Job object from the database.
	 * 
	 * @param job
	 *            : the ID of the job
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int delete(Job job) {

		// Variable Declarations
		Connection conn = null;
		Statement stmt = null;
		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			stmt = conn.createStatement();
			String sql = "DELETE FROM jobs WHERE ID = " + job.getID();

			// Execute SQL Statement
			rows = stmt.executeUpdate(sql);

			// Close Query and Database Connection
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle Errors for Class
			e.printStackTrace();
		} finally {
			// Close Resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // Nothing to do

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // End Finally Try/Catch
		} // End Try/Catch

		return rows;
	} // -- end delete() method

	public boolean doesJobsTableExist() {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "SELECT * FROM sqlite_master";
			stmt = conn.prepareStatement(sql);

			// Get ResultSet by Column Name
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String tName = rs.getString("name");
				if (tName.equals("jobs")) {

					return true;
				}
			}

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle Errors for Class
			e.printStackTrace();
		} finally {
			// Close Resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // Nothing to do

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // End Finally Try/Catch
		} // End Try/Catch

		return false;
	} // -- end doesJobsTableExist() method

	public int createTable() {

		if (!doesJobsTableExist()) {
			JOptionPane
					.showMessageDialog(null,
							"Error: Table 'jobs' does not exist! A new table will be created now.");

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;

			int rows = 0;

			try {

				// Connect to Database
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DATABASE, USER, PASS);

				// Create SQL Statement
				String sql = "CREATE TABLE IF NOT EXISTS 'jobs' ("
						+ "'id' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
						+ "'name' VARCHAR NOT NULL , 'type' VARCHAR NOT NULL , "
						+ "'industry' VARCHAR NOT NULL , "
						+ "'category' VARCHAR NOT NULL , "
						+ "'annGrossSal' DOUBLE NOT NULL , "
						+ "'monGrossSal' DOUBLE NOT NULL , "
						+ "'marAnnualTax' DOUBLE NOT NULL , "
						+ "'marMonthlyTax' DOUBLE NOT NULL , "
						+ "'marAfterTax' DOUBLE NOT NULL , "
						+ "'sinAnnualTax' DOUBLE NOT NULL , "
						+ "'sinMonthlyTax' DOUBLE NOT NULL , "
						+ "'sinAfterTax' DOUBLE NOT NULL , " + "'gpa' INTEGER"
						+ "'loan' DOUBLE NOT NULL);";

				stmt = conn.prepareStatement(sql);
				rows = stmt.executeUpdate();

				// Close Query and Database Connection
				stmt.close();
				conn.close();

			} catch (SQLException se) {
				// Handle Errors for JDBC
				se.printStackTrace();
			} catch (Exception e) {
				// Handle Errors for Class
				System.getProperty("user.dir");
				e.printStackTrace();
			}

			// populate table with the default set of jobs information
			insertDefaultJobs();

			return rows;
		} else {

			return 0;
		}
	}

	// end of checkTable method

	public int insertDefaultJobs() {

		System.out.println("");
		System.out.println("DEFAULT JOBS");

		List<Job> jobsList = new ArrayList<>();

		jobsList.add(new Job(1, "Architect", "Production", "Engineering",
				"Architecture/Engineering ", 40300, 3358, 10880, 907, 2451,
				12896, 1075, 2283, 5, 10000));
		jobsList.add(new Job(2, "Drafter", "Production", "Engineering",
				"Architecture/Engineering ", 39000, 3250, 10530, 878, 2372,
				12480, 1040, 2210, 4, 10000));
		jobsList.add(new Job(3, "Engineer", "Production", "Engineering",
				"Architecture/Engineering ", 53100, 4425, 14340, 1195, 3220,
				16992, 1416, 3009, 5, 15000));
		jobsList.add(new Job(4, "Actor", "Service", "Entertainment",
				"Art/Media/Entertainment ", 32397, 2700, 8748, 729, 1971,
				10367, 864, 1836, 3, 5000));
		jobsList.add(new Job(5, "Audio/Visual technician", "Service",
				"Entertainment", "Art/Media/Entertainment ", 41500, 3458,
				11205, 933, 2525, 13280, 1107, 2351, 5, 10000));
		jobsList.add(new Job(6, "Choreographer", "Service", "Entertainment",
				"Art/Media/Entertainment ", 27626, 2302, 7459, 622, 1680, 8840,
				737, 1565, 3, 5000));
		jobsList.add(new Job(7, "Graphic artist", "Service", "Entertainment",
				"Art/Media/Entertainment ", 46000, 3833, 12420, 1045, 2798,
				14720, 1227, 2606, 5, 20000));
		jobsList.add(new Job(8, "Interior designer", "Service",
				"Entertainment", "Art/Media/Entertainment ", 36600, 3050, 9882,
				824, 2226, 11712, 976, 2074, 4, 10000));
		jobsList.add(new Job(9, "Multi-media artist/animator", "Service",
				"Entertainment", "Art/Media/Entertainment ", 45900, 3825,
				12393, 1033, 2792, 14688, 1224, 2601, 5, 15000));
		jobsList.add(new Job(10, "Photographer", "Service", "Entertainment",
				"Art/Media/Entertainment ", 51200, 4267, 13824, 1152, 3115,
				16384, 1365, 1902, 5, 15000));
		jobsList.add(new Job(11, "Public relations", "Service",
				"Entertainment", "Art/Media/Entertainment ", 45500, 3792,
				12286, 1023, 2769, 17280, 1440, 2353, 5, 15000));
		jobsList.add(new Job(12, "Reporter", "Service", "Entertainment",
				"Art/Media/Entertainment ", 31000, 2583, 8369, 697, 1886, 9918,
				826, 1757, 3, 10000));
		jobsList.add(new Job(13, "Writer", "Service", "Entertainment",
				"Art/Media/Entertainment ", 43500, 3625, 11475, 979, 2646,
				13920, 1160, 2465, 5, 15000));
		jobsList.add(new Job(14, "Accountant", "Service", "Business",
				"Business/Sales ", 40300, 3358, 10879, 906, 2452, 12894, 1074,
				2284, 5, 10000));
		jobsList.add(new Job(15, "Cashier", "Service", "Business",
				"Business/Sales ", 19900, 1658, 5373, 448, 1210, 6366, 530,
				1128, 1, 0));
		jobsList.add(new Job(16, "Loan Clerk", "Service", "Business",
				"Business/Sales ", 33700, 2808, 9098, 758, 2050, 10783, 898,
				1910, 3, 5000));
		jobsList.add(new Job(17, "Loan Officer", "Service", "Business",
				"Business/Sales ", 57600, 4800, 15552, 1296, 3504, 18432, 1536,
				3264, 5, 20000));
		jobsList.add(new Job(18, "Marketing rep", "Service", "Business",
				"Business/Sales ", 84900, 7075, 22923, 1910, 5165, 27168, 2264,
				4811, 5, 30000));
		jobsList.add(new Job(19, "Retail sales", "Service", "Business",
				"Business/Sales ", 23200, 1933, 6264, 522, 1411, 7422, 618,
				1315, 2, 0));
		jobsList.add(new Job(20, "Retail sales supervisor", "Service",
				"Business", "Business/Sales ", 55000, 4583, 14850, 1238, 3345,
				17600, 1466, 3117, 5, 10000));
		jobsList.add(new Job(21, "Retail sales manager", "Service", "Business",
				"Business/Sales ", 43000, 3583, 11610, 968, 2615, 13760, 1147,
				2436, 5, 5000));
		jobsList.add(new Job(22, "Custodian", "Service", "", "Cleaning", 26000,
				2166, 7020, 585, 1581, 8320, 693, 1473, 3, 0));
		jobsList.add(new Job(23, "Groundskeeper/Landscaper", "Service", "",
				"Cleaning", 25000, 2083, 6750, 5625, 1235, 5130, 427, 1156, 3,
				0));
		jobsList.add(new Job(24, "Computer repair", "Production",
				"Engineering", "Computers", 44000, 3666, 11880, 990, 2676,
				14080, 1173, 2493, 5, 10000));
		jobsList.add(new Job(25, "Network Administrator", "Production",
				"Engineering", "Computers", 69000, 5583, 18090, 1508, 4075,
				21440, 1787, 3796, 5, 20000));
		jobsList.add(new Job(26, "Programmer", "Production", "Engineering",
				"Computers", 63000, 5250, 17010, 1418, 3832, 20160, 1680, 3570,
				5, 20000));
		jobsList.add(new Job(27, "Support specialist", "Production",
				"Engineering", "Computers", 36660, 3215, 11880, 990, 2676,
				14080, 1173, 2493, 4, 5000));
		jobsList.add(new Job(28, "Video game designer", "Production",
				"Engineering", "Computers", 69000, 5750, 18630, 1553, 4197,
				22080, 1840, 39103, 5, 20000));
		jobsList.add(new Job(29, "Chef", "Service", "Food", "Food Service",
				48000, 4000, 12960, 1080, 2920, 15360, 1280, 2720, 5, 10000));
		jobsList.add(new Job(30, "Food preparation", "Service", "Food",
				"Food Service", 36000, 3000, 9720, 810, 2190, 11520, 960, 2040,
				4, 0));
		jobsList.add(new Job(31, "Food service managment", "Service", "Food",
				"Food Service", 49000, 4083, 13230, 1103, 2980, 15680, 1303,
				2780, 5, 5000));
		jobsList.add(new Job(32, "Waiter/waitress/bartender", "Service",
				"Food", "Food Service", 18000, 1500, 4860, 405, 1095, 5760,
				480, 1020, 1, 0));
		jobsList.add(new Job(33, "Chiropractor", "Service", "Medical",
				"Healthcare/Research Sciences ", 106000, 8833, 28620, 2385,
				6448, 33920, 2827, 6006, 5, 30000));
		jobsList.add(new Job(34, "Dental assistant/Lab technician", "Service",
				"Medical", "Healthcare/Research Sciences ", 34000, 2833, 9180,
				765, 2068, 10880, 907, 1926, 3, 10000));
		jobsList.add(new Job(35, "Dental hygienist", "Service", "Medical",
				"Healthcare/Research Sciences ", 52000, 4333, 14040, 1170,
				3163, 16640, 1387, 2946, 5, 15000));
		jobsList.add(new Job(36, "Dentist", "Service", "Medical",
				"Healthcare/Research Sciences ", 157000, 13083, 42390, 3533,
				9550, 50240, 4187, 8896, 5, 40000));
		jobsList.add(new Job(37, "Doctor", "Service", "Medical",
				"Healthcare/Research Sciences ", 36275, 3023, 10450, 870, 2229,
				11300, 942, 2081, 4, 40000));
		jobsList.add(new Job(38, "Licensed practical nurse", "Service",
				"Medical", "Healthcare/Research Sciences ", 48000, 4000, 12960,
				1080, 2920, 15360, 1280, 2720, 5, 15000));
		jobsList.add(new Job(39, "Nurse aid", "Service", "Medical",
				"Healthcare/Research Sciences ", 30000, 2500, 8100, 625, 1875,
				9600, 800, 1700, 3, 5000));
		jobsList.add(new Job(40, "Optician", "Service", "Medical",
				"Healthcare/Research Sciences ", 38000, 3166, 10260, 855, 2311,
				12160, 1013, 2153, 4, 10000));
		jobsList.add(new Job(41, "Paramedic", "Service", "Medical",
				"Healthcare/Research Sciences ", 63000, 5250, 17010, 1418,
				3832, 20160, 1680, 3570, 5, 10000));
		jobsList.add(new Job(42, "Pharmacist", "Service", "Medical",
				"Healthcare/Research Sciences ", 93600, 7800, 26001, 2167,
				5633, 29760, 2680, 5120, 5, 40000));
		jobsList.add(new Job(43, "Pharmacy technician", "Service", "Medical",
				"Healthcare/Research Sciences ", 25000, 2083, 6750, 563, 1520,
				8000, 667, 1416, 3, 5000));
		jobsList.add(new Job(44, "Physician Assistant", "Service", "Medical",
				"Healthcare/Research Sciences ", 62000, 5166, 16740, 1395,
				3771, 19840, 1653, 3513, 5, 20000));
		jobsList.add(new Job(45, "Physical therapist", "Service", "Medical",
				"Healthcare/Research Sciences ", 81000, 6750, 21870, 1823,
				4927, 25920, 2160, 4590, 5, 25000));
		jobsList.add(new Job(46, "Recreational therapist", "Service",
				"Medical", "Healthcare/Research Sciences ", 51000, 4250, 13770,
				1148, 3102, 16320, 1360, 2890, 5, 15000));
		jobsList.add(new Job(47, "Registered nurse", "Service", "Medical",
				"Healthcare/Research Sciences ", 73000, 6083, 19710, 1643,
				4440, 23368, 1947, 4136, 5, 25000));
		jobsList.add(new Job(48, "Respiratory therapist", "Service", "Medical",
				"Healthcare/Research Sciences ", 53000, 4416, 14310, 1193,
				3223, 16960, 1413, 3003, 5, 15000));
		jobsList.add(new Job(49, "Technicians(Medical & Lab)", "Service",
				"Medical", "Healthcare/Research Sciences ", 48200, 4016, 10014,
				1085, 2931, 15424, 1285, 2731, 5, 15000));
		jobsList.add(new Job(50, "Vet assistant", "Service", "Medical",
				"Healthcare/Research Sciences ", 23000, 1916, 6210, 518, 1398,
				7360, 613, 1303, 2, 0));
		jobsList.add(new Job(51, "Vet technician", "Service", "Medical",
				"Healthcare/Research Sciences ", 33000, 2750, 8910, 743, 2007,
				10560, 880, 1870, 3, 5000));
		jobsList.add(new Job(52, "Veterinarian", "Service", "Medical",
				"Healthcare/Research Sciences ", 85000, 7083, 22950, 1913,
				5170, 27200, 441, 6642, 5, 40000));
		jobsList.add(new Job(53, "Correctional officer", "Service",
				"Public Safety", "Law Enforcement/Public Safety", 30000, 2500,
				8100, 675, 1825, 9600, 800, 1700, 3, 5000));
		jobsList.add(new Job(54, "Crime Scene Investigator", "Service",
				"Public Safety", "Law Enforcement/Public Safety", 51000, 4250,
				13770, 1148, 3102, 16320, 1360, 2890, 5, 10000));
		jobsList.add(new Job(55, "Detective", "Service", "Public Safety",
				"Law Enforcement/Public Safety", 54000, 4500, 14580, 1215,
				3285, 17280, 1440, 3060, 5, 10000));
		jobsList.add(new Job(56, "Firefighter", "Service", "Public Safety",
				"Law Enforcement/Public Safety", 42000, 3500, 11348, 945, 2555,
				13440, 1120, 2380, 5, 5000));
		jobsList.add(new Job(57, "Military(enlisted)", "Service",
				"Public Safety", "Law Enforcement/Public Safety", 57000, 4750,
				15390, 1283, 3467, 18240, 1520, 3230, 5, 0));
		jobsList.add(new Job(58, "Military(officer)", "Service",
				"Public Safety", "Law Enforcement/Public Safety", 72000, 6000,
				19440, 1620, 4380, 23040, 1173, 2493, 5, 0));
		jobsList.add(new Job(59, "Police/sheriff officer", "Service",
				"Public Safety", "Law Enforcement/Public Safety", 44000, 3666,
				11880, 990, 2676, 14080, 1173, 2493, 5, 5000));
		jobsList.add(new Job(60, "Private investigator", "Service",
				"Public Safety", "Law Enforcement/Public Safety", 37000, 3083,
				9990, 833, 2250, 11840, 986, 2103, 4, 5000));
		jobsList.add(new Job(61, "Security guard", "Service", "Public Safety",
				"Law Enforcement/Public Safety", 24000, 2000, 6480, 540, 1460,
				7680, 640, 1360, 2, 0));
		jobsList.add(new Job(62, "Court reporter", "Service", "Public Safety",
				"Legal ", 35173, 2931, 9450, 787, 2129, 11200, 933, 1983, 4,
				5000));
		jobsList.add(new Job(63, "Law clerk", "Service", "Public Safety",
				"Legal ", 31000, 2583, 8370, 698, 1885, 9920, 827, 1756, 3,
				5000));
		jobsList.add(new Job(64, "Lawyer", "Service", "Public Safety",
				"Legal ", 100000, 8333, 27000, 2250, 6083, 32000, 2667, 5666,
				5, 40000));
		jobsList.add(new Job(65, "Paralegal/legal secretary", "Service",
				"Public Safety", "Legal ", 42000, 3500, 11340, 945, 2555,
				13440, 1170, 2330, 5, 10000));
		jobsList.add(new Job(66, "Aircraft mechanic", "Service", "Engineering",
				"Mechanical/Engineering ", 61000, 5083, 16470, 1373, 3710,
				19520, 1627, 3456, 5, 10000));
		jobsList.add(new Job(67, "Auto mechanic", "Service", "Engineering",
				"Mechanical/Engineering ", 41000, 3416, 11070, 923, 2493,
				13120, 1093, 2323, 5, 5000));
		jobsList.add(new Job(68, "Machinist", "Service", "Engineering",
				"Mechanical/Engineering ", 43000, 3583, 11610, 968, 2615,
				13760, 1397, 2186, 5, 5000));
		jobsList.add(new Job(69, "Maintenance Wkr-Machinery", "Service",
				"Engineering", "Mechanical/Engineering ", 24000, 2000, 6480,
				540, 1460, 7680, 640, 1360, 2, 0));
		jobsList.add(new Job(70, "Plumber", "Service", "Engineering",
				"Mechanical/Engineering ", 50000, 4166, 13500, 1125, 3041,
				16000, 1333, 2833, 5, 5000));
		jobsList.add(new Job(71, "School bus driver", "Service", "Mechanical",
				"Mechanical/Engineering ", 28000, 2333, 7560, 630, 1703, 8960,
				747, 1586, 3, 0));
		jobsList.add(new Job(72, "Semi truck driver", "Service", "Mechanical",
				"Mechanical/Engineering ", 56000, 4666, 15120, 1260, 3406,
				17920, 1493, 3173, 5, 5000));
		jobsList.add(new Job(73, "Supervisor", "Service", "Mechanical",
				"Mechanical/Engineering ", 62000, 5166, 16740, 1395, 3771,
				19840, 1653, 3513, 5, 20000));
		jobsList.add(new Job(74, "Administrative assistant", "Service",
				"Business", "Office/Administrative Support ", 34000, 2833,
				9180, 765, 2068, 10880, 907, 1926, 3, 5000));
		jobsList.add(new Job(75, "Bank teller", "Service", "Business",
				"Office/Administrative Support ", 26000, 2166, 7020, 752, 1414,
				8320, 693, 1473, 3, 0));
		jobsList.add(new Job(76, "Bookkeeper", "Service", "Business",
				"Office/Administrative Support ", 36000, 3000, 9720, 810, 2190,
				11520, 960, 2040, 4, 0));
		jobsList.add(new Job(77, "Customer service rep", "Service", "Business",
				"Office/Administrative Support ", 28000, 2333, 7560, 630, 1703,
				8960, 747, 1593, 3, 0));
		jobsList.add(new Job(78, "Healthcare Support-Admin", "Service",
				"Business", "Office/Administrative Support ", 39000, 3258,
				10530, 878, 2372, 12486, 1040, 2210, 4, 5000));
		jobsList.add(new Job(79, "Office clerk", "Service", "Business",
				"Office/Administrative Support ", 25000, 2083, 6750, 563, 1520,
				8000, 667, 1416, 3, 0));
		jobsList.add(new Job(80, "Office manager", "Service", "Business",
				"Office/Administrative Support ", 40000, 3333, 10800, 900,
				2433, 12800, 1067, 2266, 5, 10000));
		jobsList.add(new Job(81, "Secretary/receptionist", "Service",
				"Business", "Office/Administrative Support ", 24000, 2000,
				6480, 540, 1460, 7680, 640, 1360, 2, 0));
		jobsList.add(new Job(82, "Travel Agent", "Service", "Business",
				"Office/Administrative Support ", 30000, 2500, 8100, 675, 1825,
				9600, 800, 1700, 3, 5000));
		jobsList.add(new Job(83, "Cosmetologist/barber", "Service", "",
				"Personal Care ", 34000, 2833, 9180, 765, 2068, 10880, 907,
				1926, 3, 5000));
		jobsList.add(new Job(84, "Massage therapist", "Service", "",
				"Personal Care ", 46000, 3833, 12420, 1035, 2798, 14720, 1227,
				2606, 5, 15000));
		jobsList.add(new Job(85, "Carpenter", "Production", "Engineering",
				"Production/Construction ", 32000, 2666, 8640, 720, 1946,
				10240, 853, 1813, 3, 5000));
		jobsList.add(new Job(86, "Construction helper", "Production",
				"Engineering", "Production/Construction ", 25000, 2083, 6750,
				563, 1520, 8000, 667, 1416, 3, 0));
		jobsList.add(new Job(87, "Electrician", "Production", "Engineering",
				"Production/Construction ", 44000, 3666, 11880, 990, 2676,
				14080, 1173, 2493, 5, 10000));
		jobsList.add(new Job(88, "HVAC", "Production", "Engineering",
				"Production/Construction ", 43000, 3583, 11610, 968, 2615,
				13760, 1147, 2436, 5, 10000));
		jobsList.add(new Job(89, "Laborer", "Production", "Engineering",
				"Production/Construction ", 29000, 2416, 7830, 653, 1763, 9280,
				773, 1643, 3, 0));
		jobsList.add(new Job(90, "Logistics-Shipping&Receiving", "Production",
				"Engineering", "Production/Construction ", 17000, 1416, 4590,
				383, 1033, 5440, 453, 963, 1, 0));
		jobsList.add(new Job(91, "Painter", "Production", "Engineering",
				"Production/Construction ", 33000, 2750, 8910, 743, 2667,
				10560, 880, 1870, 3, 0));
		jobsList.add(new Job(92, "Supervisor", "Production", "Engineering",
				"Production/Construction ", 58000, 4833, 15660, 1305, 3528,
				18560, 1547, 3286, 5, 10000));
		jobsList.add(new Job(93, "Welder", "Production", "Engineering",
				"Production/Construction ", 36000, 3000, 9720, 810, 2190,
				11520, 960, 2040, 4, 5000));
		jobsList.add(new Job(94, "Child care worker", "Service", "Education",
				"Social Service/Education ", 25000, 2083, 6750, 563, 1520,
				8000, 667, 1416, 3, 0));
		jobsList.add(new Job(95, "Pastor/Clergy", "Service", "Education",
				"Social Service/Education ", 38000, 3166, 10260, 855, 2311,
				12160, 1013, 2153, 4, 10000));
		jobsList.add(new Job(96, "Preschool teacher", "Service", "Education",
				"Social Service/Education ", 27000, 2225, 7290, 608, 1617,
				8640, 720, 1505, 3, 5000));
		jobsList.add(new Job(97, "Psychologist", "Service", "Education",
				"Social Service/Education ", 64000, 5333, 17280, 1440, 3893,
				20480, 1207, 4126, 5, 25000));
		jobsList.add(new Job(98, "Social worker", "Service", "Education",
				"Social Service/Education ", 40000, 3333, 10880, 900, 2636,
				12800, 1067, 2266, 5, 10000));
		jobsList.add(new Job(99, "Teacher", "Service", "Education",
				"Social Service/Education ", 45000, 3750, 12150, 1013, 2737,
				14400, 1200, 2550, 5, 10000));
		jobsList.add(new Job(100, "Teacher assistant", "Service", "Education",
				"Social Service/Education ", 35000, 2916, 9450, 785, 2131,
				11800, 983, 1933, 4, 5000));
		jobsList.add(new Job(101, "Athelete/sports competitor", "Service",
				"Entertainment", "Sports/Athletics ", 48000, 4000, 12960, 1080,
				2920, 15360, 1280, 2720, 5, 0));
		jobsList.add(new Job(102, "Athletic trainer", "Service",
				"Entertainment", "Sports/Athletics ", 42000, 3500, 11340, 945,
				2555, 13466, 1116, 2384, 5, 10000));
		jobsList.add(new Job(103, "Coaches", "Service", "Entertainment",
				"Sports/Athletics ", 35000, 2916, 9450, 788, 2128, 11200, 933,
				1983, 4, 5000));
		jobsList.add(new Job(104, "Fitness trainer", "Service",
				"Entertainment", "Sports/Athletics ", 31000, 2583, 8370, 698,
				1885, 9920, 827, 1756, 3, 5000));
		jobsList.add(new Job(105, "Umpire/offical", "Service", "Entertainment",
				"Sports/Athletics ", 32000, 2666, 8640, 720, 1946, 10240, 853,
				1813, 3, 5000));

		// Insert Jobs
		int rows = 0;
		for (Job job : jobsList) {
			insert(job);
			rows += 1;
		}

		return rows;
	} // -- end insertDefaultJobs() method
} // end JobsTableDatabaseAccessor class