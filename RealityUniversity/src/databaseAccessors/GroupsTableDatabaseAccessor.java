package databaseAccessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import obj.Group;

/**
 * The Class GroupsDAO retrieves data from the "groups" table.
 */
public class GroupsTableDatabaseAccessor implements DatabaseAccessorInterface {

	private Date currentDateTime = new Date();

	/**
	 * Gets a List of groups by s criteria.
	 * 
	 * @param column
	 *            : The column name in the table to look for.
	 * @param search
	 *            : The term to look for in the column.
	 * @return Returns a List of Group objects.
	 */
	public List<Group> select(String column, String search) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		List<Group> lstGroups = new ArrayList<>();

		String sql = "";
		String strWhere = "";

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			if (column != null) {
				strWhere = "WHERE " + column + " = " + search;
			}
			sql = "SELECT * FROM groups " + strWhere;
			stmt = conn.prepareStatement(sql);

			// Get ResultSet by Column Name
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Group group = new Group();

				group.setID(rs.getInt("id"));
				group.setName(rs.getString("name"));
				group.setCreated(rs.getDate("created"));
				group.setModified(rs.getDate("modified"));

				lstGroups.add(group);
			}

			// Close ResultSet, Query, and Database Connection
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			System.out.println("JDBC Error. Current DB: " + DATABASE);
		} catch (Exception ex) {
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

		return lstGroups;
	}

	// end of select method

	/**
	 * Update.
	 * 
	 * @param group
	 *            : the Group being updated
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int update(Group group) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "UPDATE groups SET name=?, modified=?" + "WHERE id=?";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, group.getName());
			stmt.setDouble(2, currentDateTime.getTime());
			// stmt.setDate(2, (java.sql.Date) group.getModified());
			stmt.setInt(3, group.getID());

			rows = stmt.executeUpdate();

			// Close Query and Database Connection
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception ex) {
			// Handle Errors for Class
			ex.printStackTrace();
		} finally {

			// Close Resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // End Finally Try/Catch
		} // End Try/Catch

		return rows;
	}

	// end of update method

	// end of update method

	/**
	 * Insert a new Group.
	 * 
	 * @param groupName
	 *            : The group's name
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int insert(String groupName) {

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;

		int rows = 0;

		try {

			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql = "INSERT INTO groups (name, created)" + "VALUES (?,?)";

			stmt = conn.prepareStatement(sql);

			stmt.setString(1, groupName);
			stmt.setDouble(2, currentDateTime.getTime());

			rows = stmt.executeUpdate();

			// Close Query and Database Connection
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception ex) {
			// Handle Errors for Class
			ex.printStackTrace();
		} finally {

			// Close Resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // End Finally Try/Catch
		} // End Try/Catch

		return rows;
	} // -- end insert() method

	public int delete(Group group) { // also deletes surveys that are in group

		// Variable Declarations
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			// Connect to Database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DATABASE, USER, PASS);

			// Create SQL Statement
			String sql2 = "DELETE FROM surveys WHERE GroupID = ?";
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, group.getID());

			// Execute SQL Statement
			rows = stmt.executeUpdate();

			stmt.close();

			// Create SQL Statement
			String sql = "DELETE FROM groups WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, group.getID());

			// Execute SQL Statement
			rows = stmt.executeUpdate();

			// Close Query and Database Connection
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception ex) {
			// Handle Errors for Class
			ex.printStackTrace();
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
	} // end of delete method

	@Override
	public int createTable() {

		System.out
				.println("--This method returns an integer containing the number of rows");
		System.out.println("--that were created.");

		if (!doesGroupsTableExist()) {
			JOptionPane
					.showMessageDialog(null,
							"Error: Table 'groups' does not exist! A new table will be created now.");

			// Variable Declarations
			Connection conn = null;
			PreparedStatement stmt = null;

			int rows = 0;

			try {

				// Connect to Database
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DATABASE, USER, PASS);

				// Create SQL Statement
				String sql = "CREATE TABLE IF NOT EXISTS groups"
						+ "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
						+ "'name' VARCHAR NOT NULL,"
						+ "'created' DATETIME NOT NULL,"
						+ "'modified' DATETIME)";

				stmt = conn.prepareStatement(sql);
				rows = stmt.executeUpdate();

				// Close Query and Database Connection
				stmt.close();
				conn.close();

			} catch (SQLException se) {
				// Handle Errors for JDBC
				se.printStackTrace();
			} catch (Exception ex) {
				// Handle Errors for Class
				ex.printStackTrace();
			} finally {

				// Close Resources
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}

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
	} // end of createTable method

	public boolean doesGroupsTableExist() {
		// the next few statements are for debugging purposes only

		System.out
				.println("--This method returns a boolean and ONLY checks existence of groups table");

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
				if (tName.equals("groups")) {

					return true;
				}
			}

		} catch (SQLException se) {
			// Handle Errors for JDBC
			se.printStackTrace();
		} catch (Exception ex) {
			// Handle Errors for Class
			ex.printStackTrace();
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
	}
	// end of checkTable method
}
