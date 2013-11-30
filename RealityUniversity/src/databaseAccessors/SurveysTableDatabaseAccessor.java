package databaseAccessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import obj.Survey;
import ctrl.Controller;

/**
 * The Class SurveysDAO retrieves data from the "surveys" table.
 */
public class SurveysTableDatabaseAccessor implements DatabaseAccessorInterface {

	/**
	 * Search for Surveys
	 * 
	 * @param where
	 *            : The column name in the table to look for.
	 * @param criteria
	 *            : The term to look for in the column.
	 * @param groupBy
	 *            : If you want to GROUPBY anything.
	 * 
	 * @return Returns a List of Survey objects.
	 */
	// The 'where' parameter could be "id" or "fname" or "lname" or "gpa" or "gender"
	// or "cperiod" or "teacher" or "groupID" or "education" or etc.
	public List<Survey> select(String where, String criteria, String groupBy) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		List<Survey> surveysList = new ArrayList<>();
		String sql = "", strWhere = "", strGroupBy = "";

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			if (where != null)
				strWhere = " WHERE " + where + " = " + criteria;
			if (groupBy != null)
				strGroupBy = " GROUP BY " + groupBy;

			sql = "SELECT * FROM surveys " + strWhere + strGroupBy;
			stmt = conn.prepareStatement(sql);

			// Get ResultSet by Column Name
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Survey survey = new Survey();

				survey.setID(rs.getInt("id"));
				survey.setFName(rs.getString("fName"));
				survey.setLName(rs.getString("lName"));
				survey.setGPA(rs.getInt("gpa"));
				survey.setGender(rs.getInt("gender"));
				survey.setCPeriod(rs.getInt("cPeriod"));
				survey.setTeacher(rs.getString("teacher"));
				survey.setGroupID(rs.getInt("groupID"));
				survey.setEducation(rs.getInt("education"));
				survey.setPreferredJob(rs.getInt("prefJob"));
				survey.setAssignedJob(rs.getInt("job"));
				survey.setMaritalStatus(rs.getInt("married"));
				survey.setSpouse(rs.getInt("spouse"));
				survey.setChildren(rs.getInt("children"));
				survey.setCreditCards(rs.getInt("cCards"));
				survey.setCreditCardUses(rs.getInt("cCardUses"));
				survey.setGroceries(rs.getString("groceries"));
				survey.setClothing(rs.getString("clothing"));
				survey.setHome(rs.getString("home"));
				survey.setVehicle(rs.getString("vehicle"));
				survey.setChildSupport(rs.getDouble("childSupport"));
				survey.setCreditScore(rs.getInt("creditScore"));

				surveysList.add(survey);
			}

			// Close ResultSet, Query, and Database Connection
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			System.out.println("JDBC Error: SurveysDAO");
		} catch (Exception e) {
			// Handle Errors for Class
			System.out.println("Class Error: SurveysDAO");
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

		return surveysList;
	} // -- end select() method

	/**
	 * Update a Survey.
	 * 
	 * @param survey
	 *            : the Survey being updated
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int update(Survey survey) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "UPDATE surveys SET "
					+ "fName=?, lName=?, gpa=?, gender=?, " // 1-4
					+ "cPeriod=?, teacher=?, groupID=?, education=?, prefJob=?, job=?, married=?, " // 5-11
					+ "spouse=?, children=?, cCards=?, cCardUses=?, groceries=?, clothing=?, home=?," // 12-18
					+ "vehicle=?, childSupport=?, creditScore=? WHERE id=?"; // 19-22

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, survey.getFName());
			stmt.setString(2, survey.getLName());
			stmt.setInt(3, survey.getGPA());
			stmt.setInt(4, survey.getGender());
			stmt.setInt(5, survey.getCPeriod());
			stmt.setString(6, survey.getTeacher());
			stmt.setInt(7, survey.getGroupID());
			stmt.setInt(8, survey.getEducation());
			stmt.setInt(9, survey.getPreferredJob());
			stmt.setInt(10, survey.getAssignedJob());
			stmt.setInt(11, survey.getMaritalStatus());
			stmt.setInt(12, survey.getSpouse());
			stmt.setInt(13, survey.getChildren());
			stmt.setInt(14, survey.getCreditCards());

			if (survey.getCreditCards() == 0) {
				stmt.setInt(15, 0);
			}

			stmt.setInt(15, survey.getCreditCardUses());
			stmt.setString(16, survey.getGroceries());
			stmt.setString(17, survey.getClothing());
			stmt.setString(18, survey.getHome());
			stmt.setString(19, survey.getVehicle());
			stmt.setDouble(20, survey.getChildSupport());
			stmt.setDouble(21, survey.getCreditScore());
			stmt.setInt(22, survey.getID());

			rows = stmt.executeUpdate();

			if (rows > 0) {
				Controller.getControllerInstance().updateSQLGroup(null);
			}

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
	 * Insert a new Survey.
	 * 
	 * @param survey
	 *            : The survey being inserted
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int insert(Survey survey) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "INSERT INTO surveys (fName, lName, gpa, gender, "
					+ "cPeriod, teacher, groupID, education, prefJob, job, married, "
					+ "spouse, children, cCards, cCardUses, groceries, clothing, home,"
					+ "vehicle, childSupport, creditScore)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, survey.getFName());
			stmt.setString(2, survey.getLName());
			stmt.setInt(3, survey.getGPA());
			stmt.setInt(4, survey.getGender());
			stmt.setInt(5, survey.getCPeriod());
			stmt.setString(6, survey.getTeacher());
			stmt.setInt(7, survey.getGroupID());
			stmt.setInt(8, survey.getEducation());
			stmt.setInt(9, survey.getPreferredJob());
			stmt.setInt(10, survey.getAssignedJob());
			stmt.setInt(11, survey.getMaritalStatus());
			stmt.setInt(12, survey.getSpouse());
			stmt.setInt(13, survey.getChildren());
			stmt.setInt(14, survey.getCreditCards());

			if (survey.getCreditCards() == 0) {
				stmt.setString(15, null);
			} else {
				stmt.setInt(15, survey.getCreditCardUses());
			}

			stmt.setString(16, survey.getGroceries());
			stmt.setString(17, survey.getClothing());
			stmt.setString(18, survey.getHome());
			stmt.setString(19, survey.getVehicle());
			stmt.setDouble(20, survey.getChildSupport());
			stmt.setDouble(21, survey.getCreditScore());

			rows = stmt.executeUpdate();

			if (rows > 0) {
				Controller.getControllerInstance().updateSQLGroup(null);
			}

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
	 * Delete a Survey.
	 * 
	 * @param survey
	 *            : the survey being deleted
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int delete(Survey survey) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "DELETE FROM surveys WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, survey.getID());

			// Execute SQL Statement
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
	} // -- end delete() method

	public int createTable() {

		if (!doesSurveysTableExist()) {
			JOptionPane
					.showMessageDialog(null,
							"Error: Table 'surveys' does not exist! A new table will be created now.");

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;

			int rows = 0;

			try {

				// Connect to Database
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DATABASE, USER, PASS);

				// Create SQL Statement
				String sql = "CREATE TABLE IF NOT EXISTS 'surveys'"
						+ "('id' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
						+ "'fName' VARCHAR NOT NULL ,"
						+ "'lName' VARCHAR NOT NULL ,"
						+ "'gpa' INTEGER NOT NULL ,"
						+ "'gender' INTEGER NOT NULL ,"
						+ "'cPeriod' INTEGER  NOT NULL ,"
						+ "'teacher' VARCHAR NOT NULL ,"
						+ "'groupID' INTEGER NOT NULL ,"
						+ "'education' INTEGER  NOT NULL ,"
						+ "'prefJob' INTEGER NOT NULL ," + "'job' INTEGER ,"
						+ "'married' INTEGER NOT NULL ,"
						+ "'spouse' INTEGER NOT NULL, "
						+ "'children' INTEGER NOT NULL ,"
						+ "'cCards' INTEGER NOT NULL ,"
						+ "'cCardUses' INTEGER ," + "'groceries' DOUBLE ,"
						+ "'clothing' DOUBLE ," + "'home' DOUBLE ,"
						+ "'vehicle' DOUBLE ," + "'childSupport' DOUBLE ,"
						+ "'creditScore' INTEGER)";

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
		} else {

			return 0;
		}
	} // -- end createTable() method

	public boolean doesSurveysTableExist() {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "SELECT name FROM sqlite_master";
			stmt = conn.prepareStatement(sql);

			// Get ResultSet by Column Name
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String tName = rs.getString("name");
				if (tName.equals("surveys")) {

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
	} // -- end doesSurveystableExist() method

} // End of Class
