package orphanedClasses;

import gui.GuiInterface;
import gui.ToolBar;
import gui.dialogs.EditSurvey;
import gui.dialogs.ViewSurvey;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import obj.Group;
import obj.Job;
import obj.Survey;

public class ArchivedControllerMethods implements GuiInterface {

	/**
	 * ******************************************* This class was made merely to
	 * mothball unneeded methods from the Controller class.
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
	
	/**
	 * Adds the survey in memory.
	 * 
	 * @param survey
	 *            the survey
	 */
	public void addToListOfNewSurveys(Survey survey) {

		// lstSurveys.add(survey);
		listOfNewSurveys.add(survey);
		ToolBar.getToolBarInstance().setSaveEnabled(true);
		refreshScreen();

	} // -- end addToListOfNewSurveys() method

	//
	// ==============================================
	// ==============================================
	//

	public List<Survey> getDeletedSurveysList() {

		if (listOfDeletedSurveys.size() > 0) {

			return listOfDeletedSurveys;
		}
		List<Survey> lstBlank = new ArrayList<>();

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

		if (listOfNewJobs.size() > 0)
			return listOfNewJobs;
		List<Job> lstBlank = new ArrayList<>();

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

		if (listOfNewSurveys.size() > 0) {

			return listOfNewSurveys;
		}
		List<Survey> lstBlank = new ArrayList<>();

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

					return lstSearchedGroups;
				}
			} catch (NullPointerException npe) {

				return listOfGroups;
			}
		}
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

		int response = JOptionPane
				.showConfirmDialog(
						null,
						new String(
								"Are you sure you want to quit?\nAny work that is not saved will be lost!"),
						"Exit Application", JOptionPane.YES_NO_OPTION, 0,
						new ImageIcon(LG_EXIT));

		if (response == JOptionPane.YES_OPTION)

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

		if (listOfNewJobs.size() > 0) {

			return true;
		}
		if (listOfDeletedJobs.size() > 0) {

			return true;
		}

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

		new EditSurvey(survey);

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

		ViewSurvey vSurvey = new ViewSurvey(survey);
		vSurvey.setVisible(true);

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

		if (listOfNewSurveys.contains(survey)) {
			listOfNewSurveys.remove(survey);
		}

		listOfDeletedSurveys.add(survey);
		listOfSurveys.remove(survey);
		gui.ToolBar.getToolBarInstance().setSaveEnabled(true);
		refreshScreen();

	} // -- end removeSurveyFromListOfNewSurveys() method

	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Destroy the listOfDeletedSurveys list.
	 */
	public void destroyListOfDeletedSurveys() {

		listOfDeletedSurveys.clear();
		ToolBar.getToolBarInstance().setSaveEnabled(false); // possibly unneeded
		// isGroupChanged(); // possibly unneeded

	} // -- end destroyListOfDeletedSurveys() method

	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Destroy the newSurveys list.
	 */
	public void destroyListOfNewSurveys() {

		listOfNewSurveys.clear();
		ToolBar.getToolBarInstance().setSaveEnabled(false); // possibly unneeded
		// isGroupChanged(); // possibly unneeded

	} // -- end destroyListOfNewSurveys() method

	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Destroy updated surveys.
	 */
	public void destroyListOfUpdatedSurveys() {

		listOfUpdatedSurveys.clear();
		ToolBar.getToolBarInstance().setSaveEnabled(false); // possibly unneeded
		// isGroupChanged(); // possibly unneeded

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

		listOfUpdatedSurveys.add(newSurvey);
		listOfSurveys.remove(oldSurvey);
		listOfSurveys.add(newSurvey);

		gui.ToolBar.getToolBarInstance().setSaveEnabled(true);

	} // -- end of updateSurvey() method

	//
	// ==============================================
	// ==============================================
	//

	/**
	 * Process group.
	 */
	// TODO processGroup method - part of problem chain method call
	// <<< lblProcess.addMouseListener(new MouseListener() {
	// public void mouseClicked(MouseEvent arg0) >>>
	// in the GroupInfoUpperRightSidePanel class.
	// method
	public void processGroup() {

	} // -- end processGroup() method

	public void printSurvey(Survey survey) {

		ViewSurvey viewSurvey = new ViewSurvey(survey);
		viewSurvey.paperPrintOneSurvey(survey);

	} // -- end printSurvey() method

	private void refreshScreen() {
		// TODO Auto-generated method stub

	}

}
