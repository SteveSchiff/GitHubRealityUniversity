package gui.dialogs;

import gui.GuiInterface;
import gui.MenuBar;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import obj.Group;
import ctrl.Controller;

/**
 * The Class OpenGroup extends from a JDialog and allows the user to open an
 * existing Group.
 */
public class OpenGroup extends JDialog implements GuiInterface {

	/**
	 * Shows the dialog for opening an existing Group
	 */
	public OpenGroup() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("OPENGROUP CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all

		String groupName = null;
		int response = -1;

		List<Group> groupsList = Controller.getControllerInstance().getGroupsList();
		String[] arrGroupNames = new String[groupsList.size()];

		for (int i = 0; i < groupsList.size(); i++) {
			arrGroupNames[i] = groupsList.get(i).getName();
		}

		if (groupsList.size() > 0) {
			groupName = (String) JOptionPane.showInputDialog(null,
					"Open Group:\n", "Open Group", JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(LG_OPEN_GROUP), arrGroupNames, null);

			// Get Name
			if (groupName != null)
				doOpenGroup(groupName);

		} // End If (lstGroups.size() > 0)
		else {
			response = JOptionPane.showConfirmDialog(null,
					"No groups have been created.\n"
							+ "Would you like to create one now?",
					"Open Group", JOptionPane.YES_NO_OPTION);

			if (response == JOptionPane.YES_OPTION) {
				new NewGroup();
			}
		} // end else block
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	}// End Constructor

	public void doOpenGroup(String groupName) {		
		// the next few statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//because 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set
	    
		// Make sure tables exist
		//Controller.getInstance().checkExistenceOfTables();

		// Set the new window environment
	    // debugging statement
	    System.out.println("\n*** start of three methods from doOpenGroup method ***\n");
	    
		Controller.getControllerInstance().setGroup(Controller.getControllerInstance().getGroup("name", groupName));
				
		MenuBar.getMenuBarInstance().setDeleteGroup(true);
		Controller.getControllerInstance().refreshScreen();
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end doOpenGroup() method
} // End Class
