package ctrl;

import gui.ToolBar;
import gui.GuiInterface;
import gui.dialogs.EditSurvey;
import gui.dialogs.ViewSurvey;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import obj.Group;
import obj.Job;
import obj.Survey;

public class ArchivedControllerMethods implements GuiInterface {
	
	/**
	 * *******************************************
	 * This class was made merely to mothball
	 * unneeded methods from the Controller class.
	 * *******************************************
	 *
	 **/
	


	private List<Survey> listOfNewSurveys;
	private List<Survey> listOfDeletedSurveys;
	private List<Job> listOfNewJobs;
	private List<Group> listOfGroups;
	private List<Job> listOfDeletedJobs;
	private List<Survey> listOfSurveys;
	private List<Survey> listOfUpdatedSurveys;
	private boolean shouldItBeProcessed;

	/**
	 * Adds the survey in memory.
	 * 
	 * @param survey
	 *            the survey
	 */
	public void addToListOfNewSurveys(Survey survey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		// lstSurveys.add(survey);
		listOfNewSurveys.add(survey);
		ToolBar.getToolBarInstance().setSaveEnabled(true);
		refreshScreen();	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end addToListOfNewSurveys() method
	
	//
	// ==============================================
	// ==============================================
	//
	
	public List<Survey> getDeletedSurveysList() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all	
		
		if (listOfDeletedSurveys.size() > 0) {	    
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return listOfDeletedSurveys;
		}
		List<Survey> lstBlank = new ArrayList<>();	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		return lstBlank;
	}
	
	//
	// ==============================================
	// ==============================================
	//
	
	/**
	 * Gets jobs that have been added, but not yet saved
	 * 
	 * @return a List of jobs
	 */
	public List<Job> getNewJobsList() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		if (listOfNewJobs.size() > 0)
			return listOfNewJobs;
		List<Job> lstBlank = new ArrayList<>();	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		return lstBlank;
	} // -- end getNewJobsList() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Gets surveys that have been added, but not yet saved
	 * 
	 * @return a List of surveys
	 */
	public List<Survey> getNewSurveysList() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		if (listOfNewSurveys.size() > 0) {	    
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return listOfNewSurveys;
		}
		List<Survey> lstBlank = new ArrayList<>();	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		return lstBlank;
	} // -- end getNewSurveysList() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Goes through the list of groups currently in memory and returns a list of
	 * groups based on search criteria.
	 * 
	 * @param search
	 *            : The property of the object to look for. <br />
	 *            <ul>
	 *            <strong>MUST be a String!</strong> -
	 *            <i>Integer.toString(int))</i>
	 *            <li>ID</li>
	 *            <li>Name</li>
	 *            </ul>
	 * @param criteria
	 *            : The term to look for in the column.
	 * @return Returns a list of Group objects.
	 */
	public List<Group> getGroupsList(String search, String criteria) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		List<Group> lstSearchedGroups = new ArrayList<>();

		if (!this.listOfGroups.isEmpty()) {
			try {
				for (int i = 0; i < listOfGroups.size(); i++) {
					switch (search) {
					case "ID":
						if (listOfGroups.get(i).getID() == Integer
								.parseInt(criteria)) {
							lstSearchedGroups.add(listOfGroups.get(i));
						}
						break;
					case "Name":
						if (listOfGroups.get(i).getName()
								.equalsIgnoreCase(criteria)) {
							lstSearchedGroups.add(listOfGroups.get(i));
						}
						break;
					}
				}
				if (lstSearchedGroups.size() > 0) {	    
					
					// the next statement is for debugging purposes only
				    System.out.println("\n---Leaving " + methodName);
					// end of debugging statement set
					return lstSearchedGroups;
				}
			} catch (NullPointerException npe) {	    
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
				return listOfGroups;
			}
		}	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName + "\nThis is a BAD return!");
		// end of debugging statement set

		// Should not reach this EVER
		return null;
	} // -- end getGroupsList() method
	
	//
	// ==============================================
	// ==============================================
	//
	
	/**
	 * Close the application and removes it from memory.
	 */
	public void closeApplication() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all		

		int response = JOptionPane
				.showConfirmDialog(
						null,
						new String(
								"Are you sure you want to quit?\nAny work that is not saved will be lost!"),
						"Exit Application", JOptionPane.YES_NO_OPTION, 0,
						new ImageIcon(LG_EXIT));

		if (response == JOptionPane.YES_OPTION)	    
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			System.exit(0);
	} // -- end closeApplication() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Checks if job has been modified.
	 * 
	 * @return true, if is job changed
	 */
	public boolean isJobChanged() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		if (listOfNewJobs.size() > 0) {    
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return true;
		}
		if (listOfDeletedJobs.size() > 0) {    
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return true;
		}    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set

		return false;
	} // -- end isJobChanged() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Open edit survey.
	 * 
	 * @param survey
	 *            : the survey being edited
	 */
	public void openEditSurvey(Survey survey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		new EditSurvey(survey);    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end openEditSurvey() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Open view survey.
	 * 
	 * @param survey
	 *            : the survey being opened
	 */
	public void openViewSurvey(Survey survey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		ViewSurvey vSurvey = new ViewSurvey(survey);
		vSurvey.setVisible(true);    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end openViewSurvey() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Removes the survey.
	 * 
	 * @param survey
	 *            the survey
	 */
	public void removeSurveyFromListOfNewSurveys(Survey survey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		if (listOfNewSurveys.contains(survey)) {
			listOfNewSurveys.remove(survey);
		}

		listOfDeletedSurveys.add(survey);
		listOfSurveys.remove(survey);
		gui.ToolBar.getToolBarInstance().setSaveEnabled(true);
		refreshScreen();
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end removeSurveyFromListOfNewSurveys() method
	
	//
	// ==============================================
	// ==============================================
	//
	
	/**
	 * Destroy the listOfDeletedSurveys list.
	 */
	public void destroyListOfDeletedSurveys() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		listOfDeletedSurveys.clear();
		ToolBar.getToolBarInstance().setSaveEnabled(false); // possibly unneeded
		//isGroupChanged(); // possibly unneeded	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end destroyListOfDeletedSurveys() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Destroy the newSurveys list.
	 */
	public void destroyListOfNewSurveys() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		listOfNewSurveys.clear();
		ToolBar.getToolBarInstance().setSaveEnabled(false); // possibly unneeded
		//isGroupChanged(); // possibly unneeded	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end destroyListOfNewSurveys() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Destroy updated surveys.
	 */
	public void destroyListOfUpdatedSurveys() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		listOfUpdatedSurveys.clear();
		ToolBar.getToolBarInstance().setSaveEnabled(false); // possibly unneeded
		//isGroupChanged(); // possibly unneeded	    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end destroyListOfUpdatedSurveys() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Delete a group in the DatabaseAccess.
	 * 
	 * @param group
	 *            the group being deleted
	 * @return whether or not deleted the survey was successful
	 */
	// private void deleteGroup(Group group) {
	// jobsDAO.delete(group.getID());
	// lstJobs.remove(group);
	// Controller.getInstance().refreshScreen();
	// }
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Delete a job in the DAO..
	 * 
	 */
	// private void deleteJob(Job job) {
	// jobsDAO.delete(job.getID());
	// lstJobs.remove(job);
	// Controller.getInstance().refreshScreen();
	// }
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Update survey.
	 * 
	 * @param oldSurvey
	 *            : the old version of the survey being updated
	 * @param newSurvey
	 *            : the new version of the survey being updated
	 * 
	 */
	public void updateSurvey(Survey oldSurvey, Survey newSurvey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		listOfUpdatedSurveys.add(newSurvey);
		listOfSurveys.remove(oldSurvey);
		listOfSurveys.add(newSurvey);

		gui.ToolBar.getToolBarInstance().setSaveEnabled(true);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end of updateSurvey() method
	
	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Process group.
	 */
	//TODO processGroup method - part of problem chain method call
	// <<< lblProcess.addMouseListener(new MouseListener() {
	//     public void mouseClicked(MouseEvent arg0) >>>
	// in the GroupInfoUpperRightSidePanel class.
	// method
	public void processGroup() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
//		gui.ToolBar.getToolBarInstance().setSaveEnabled(true);
		shouldItBeProcessed = true;
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end processGroup() method

	public void printSurvey(Survey survey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		ViewSurvey viewSurvey = new ViewSurvey(survey);
		viewSurvey.paperPrintOneSurvey(survey);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end printSurvey() method
 



	private void refreshScreen() {
		// TODO Auto-generated method stub
		
	}


}
