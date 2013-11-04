package gui.dialogs;

import gui.GuiInterface;
import gui.custom.StatusTip;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ctrl.Controller;

/**
 * The Class NewGroup extends from a JDialog and allows the user to create a new
 * Group.
 */
public class NewGroup extends JDialog implements GuiInterface {

	/**
	 * Constructor
	 * Shows the dialog for creating a new Group
	 */
	public NewGroup() {	
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("NEWGROUP CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		String groupName;
		groupName = (String) JOptionPane.showInputDialog(null, "Group Name:\n",
				"New Group", JOptionPane.PLAIN_MESSAGE, new ImageIcon(
						LG_NEW_GROUP), null, null);

		int maxLength = 50; // changed it from 12 (by Steve A. Schiff)
		// Get Name
		if ((groupName != null) && (groupName.length() > 0)) {

			while (groupName.length() > maxLength) {
				int status = JOptionPane.showConfirmDialog(null,
						"Group name can not be greater than " + maxLength
								+ " characters long.", "Error",
						JOptionPane.OK_CANCEL_OPTION);
				if (status == JOptionPane.OK_OPTION) {
					groupName = (String) JOptionPane.showInputDialog(null,
							"Group Name:\n", "New Group",
							JOptionPane.PLAIN_MESSAGE, new ImageIcon(
									LG_NEW_GROUP), null, null);
				} else {
					dispose();
					break;
				}
			}

			if (groupName.length() < maxLength) {
				// Make sure tables exist
//				Controller.getControllerInstance().checkExistenceOfSQLTables();

				// the addGroup() method checks uniqueness of group name and writes to database if name is unique
				// if unique, row will equal one and be greater than zero
				int rows = Controller.getControllerInstance().addGroup(groupName);

				if (rows > 0) {
					// Set the window to the new group
					Controller.getControllerInstance().setGroup(
							Controller.getControllerInstance()
									.getGroup("Name", groupName));
					Controller.getControllerInstance().refreshScreen();
				} else {
					new StatusTip("Group: " + groupName + " already exists!",
							LG_EXCEPTION);
				}
			}

		} // end the big if statement
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end constructor
} // end NewGroup class
