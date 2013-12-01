package ctrl;

import gui.GuiInterface;
import gui.GuiMain;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import obj.Group;
import obj.Job;
import obj.Survey;
import processingLibrary.ProcessChildren;
import processingLibrary.ProcessChildrenDivorcedFemales;
import processingLibrary.ProcessChildrenDivorcedMales;
import processingLibrary.ProcessCreditScore;
import processingLibrary.ProcessCustodyChildSupport;
import processingLibrary.ProcessJobs;
import processingLibrary.ProcessMarried;
import databaseAccessors.GroupsTableDatabaseAccessor;
import databaseAccessors.JobsTableDatabaseAccessor;
import databaseAccessors.SurveysTableDatabaseAccessor;

/**
 * Hosts constants and methods that are available to all other controllers.
 */
public class Controller implements GuiInterface {

	/*******************************
	 * Fields
	 *******************************/

	/** An instance of the Controller. */
	private static Controller controllerInstance = null;

	protected static GroupsTableDatabaseAccessor groupsTableDatabaseAccessor = new GroupsTableDatabaseAccessor();

	protected static JobsTableDatabaseAccessor jobsTableDatabaseAccessor = new JobsTableDatabaseAccessor();

	protected static SurveysTableDatabaseAccessor surveysTableDatabaseAccessor = new SurveysTableDatabaseAccessor();

	/** The jobs map. Used to populate jobs and job categories */
	private Map<String, List<Job>> mapJobs;

	/** The current group. */
	private Group group;

	private List<Group> listOfGroups = new ArrayList<>();

	private List<Survey> listOfSurveys = new ArrayList<>();

	private List<Job> listOfJobs = new ArrayList<>();
	
	private List<Job> listOfNewJobs = new ArrayList<>();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {

	} // ****end constructor****

	/**
	 * Gets the single instance of Controller.
	 * 
	 * @return single instance of Controller
	 */
	public static Controller getControllerInstance() {

		// If we do have an instance of this controller, simply return it.
		// Otherwise, create one.
		if (controllerInstance != null) {
			return controllerInstance;
		} else {
			controllerInstance = new Controller();
			return controllerInstance;
		}
	} // end of Controller static method

	/**
	 *  THE HEART AND SOUL of all the calculations.
	 * Process the group.
	 */
	public void processGroup() {
		
		System.out.println("getGroupsList.size() = " + getSurveysList().size() + "\n");
		// no point in doing all the heavy-duty processing if the
		// survey group is less than 20 in size.
		if (getSurveysList().size() > 19) {
			 listOfSurveys = new ProcessMarried().doProcess();
			 listOfSurveys = new ProcessCreditScore().doProcess();
			 listOfSurveys = new ProcessJobs().doProcess();
			 listOfSurveys = new ProcessChildren().doProcess();
			 listOfSurveys = new ProcessChildrenDivorcedFemales().doProcess();
			 listOfSurveys = new ProcessChildrenDivorcedMales().doProcess();
			 listOfSurveys = new ProcessCustodyChildSupport().doProcess();
			
		} else {
			 listOfSurveys = new ProcessCreditScore().doProcess();
			 listOfSurveys = new ProcessJobs().doProcess();
		}

		refreshScreen();

	} // end processGroup method

	/**
	 * Refresh screen.
	 */
	public void refreshScreen() {
		listOfSurveys.clear();
		GuiMain.getGUIMainInstance().drawWindow();
	} // -- end of refreshScreen() method

	/**
	 * Gets the frame of the application currently in memory.
	 * 
	 * @return the frame
	 */
	public JFrame getFrame() {
		return GuiMain.getGUIMainInstance().getBigDaddyFrame();
	} // end getFrame() method

	/**
	 * Gets the teacher names.
	 * 
	 * @return a List of Strings (Teacher Names)
	 */
	public List<String> getTeacherNamesList() {

		// Get All Surveys
		List<Survey> surveysList = getSurveysList();
		List<String> teachersList = new ArrayList<>();

		// Return individual teacher names
		for (Survey survey : surveysList) {
			if (teachersList.size() > 0) {
				// Add it to the list if it's not already in it.
				if (!teachersList.contains(survey.getTeacher())) {
					teachersList.add(survey.getTeacher());
				}
			} else {
				// First entry
				teachersList.add("none");
				teachersList.add(survey.getTeacher());
			}
		} // end for loop
		return teachersList;
	} // -- getTeacherNamesList() method

	public void openHelp() {

		try {
			Desktop.getDesktop().open(new File("help/index.html"));
		} catch (IOException eio) {
			eio.printStackTrace();
		}
	} // -- end openHelp() method

	/**
	 * Gets Job categories.
	 * 
	 * @return a List of Strings containing job categories
	 */
	public List<String> getJobCategoriesList() {

		List<String> lstCategories = new ArrayList<>();
		lstCategories.addAll(mapJobs.keySet());

		return lstCategories;
	} // end getJobCategoriesList() method

	/**
	 * Gets the job industries.
	 * 
	 * @return a List of Strings (Job Industries)
	 */
	public List<String> getJobIndustriesList() {

		// Get All Jobs
		List<Job> lstJobs = getJobsList();
		List<String> lstIndustries = new ArrayList<>();

		// Return individual job industry
		for (Job job : lstJobs) {
			if (lstIndustries.size() > 0) {
				// Add it to the list if it's not already in it.
				if (!lstIndustries.contains(job.getIndustry())) {
					lstIndustries.add(job.getIndustry());
				}
			} else {
				// First entry
				lstIndustries.add(job.getIndustry());
			}
		}

		// Add industries that are in jobs that haven't been saved
		if (listOfNewJobs.size() > 0) {
			for (Job job : listOfNewJobs) {
				// Add it to the list if it's not already in it.
				if (!lstIndustries.contains(job.getIndustry())) {
					lstIndustries.add(job.getIndustry());
				} else {
					// First entry
					lstIndustries.add(job.getIndustry());
				}
			}
		}

		if (lstIndustries.size() > 0) {

			return lstIndustries;
		} else {
			List<String> emptyList = new ArrayList<>();
			emptyList.add("");

			return emptyList;
		}

	} // -- end getJobIndustriesList() method

	/**
	 * Gets the job types.
	 * 
	 * @return a List of Strings (Job Types)
	 */
	public List<String> getJobTypesList() {

		// Get All Jobs
		List<Job> lstJobs = getJobsList();
		List<String> lstTypes = new ArrayList<>();

		// Return individual job names
		for (Job job : lstJobs) {
			if (lstTypes.size() > 0) {
				// Add it to the list if it's not already in it.
				if (!lstTypes.contains(job.getType())) {
					lstTypes.add(job.getType());
				}
			} else {
				// First entry
				lstTypes.add(job.getType());
			}
		}

		// Add types that are in jobs that haven't been saved
		if (listOfNewJobs.size() > 0) {
			for (Job job : listOfNewJobs) {
				// Add it to the list if it's not already in it.
				if (!lstTypes.contains(job.getType())) {
					lstTypes.add(job.getType());
				} else {
					// First entry
					lstTypes.add(job.getType());
				}
			}
		}

		if (lstTypes.size() > 0) {

			return lstTypes;
		} else {
			List<String> emptyList = new ArrayList<>();
			emptyList.add("");

			return emptyList;
		}

	} // -- end getJobTypesList() method

	/**
	 * Gets the jobs for each category.
	 * 
	 * @param category
	 *            : The job category to search
	 * @return a List of Strings (Job Names)
	 */
	public List<String> getJobsByCategoryList(String category) {

		List<Job> lstJobs = mapJobs.get(category);
		List<String> lstJobNames = new ArrayList<>();

		for (Job job : lstJobs) {
			lstJobNames.add(job.getName());
		}

		return lstJobNames;
	} // end getJobsListInCategory() method

	/**
	 * Get a job from memory.
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
	 *            : The term to look for in the table.
	 * @return a Job object.
	 */
	public Job getJob(String search, String criteria) { // ending brace on line
														// 374

		Job job = new Job();

		if (!this.listOfJobs.isEmpty()) {
			try {
				for (int i = 0; i < listOfJobs.size(); i++) {
					job = listOfJobs.get(i);
					switch (search) {
					case "ID":
					case "id":
						if (job.getID() == Integer.parseInt(criteria)) {

							return job;
						}
						break;
					case "Name":
					case "name":
						if (job.getName().equalsIgnoreCase(criteria)) {

							return job;
						}
						break;
					}

				}
			} catch (NullPointerException npe) {

				return job;
			}
		}

		return job;
	} // -- end getJob() method ******************************

	/**
	 * Gets the jobs.
	 * 
	 * @return the jobs
	 */
	public List<Job> getJobsList() {

		return listOfJobs;
	} // -- end getJobsList() method

	/**
	 * Get a list of jobs.
	 * 
	 * @param search
	 *            : The property of the object to look for. <br />
	 * @param criteria
	 *            : The value of the property being searched.<br />
	 *            <ul>
	 *            <strong>MUST be a String!</strong> -
	 *            <i>Integer.toString(int))</i>
	 *            <li>ID</li>
	 *            <li>Name</li>
	 *            <li>GPA</li>
	 *            <li>Category</li>
	 *            <li>Industry</li>
	 *            <li>Type</li>
	 *            </ul>
	 * @return a list of Job objects.
	 */
	public List<Job> searchJobsList(String search, String criteria) {

		List<Job> matchingCriteriaJobsList = new ArrayList<>();

		if (!listOfJobs.isEmpty()) {
			try {
				for (int i = 0; i < listOfJobs.size(); i++) {
					Job job = listOfJobs.get(i);
					switch (search) {
					case "ID":
					case "id":
						if (job.getID() == Integer.parseInt(criteria)) {
							matchingCriteriaJobsList.add(job);
						}
						break;
					case "Name":
					case "name":
						if (job.getName().equalsIgnoreCase(criteria)) {
							matchingCriteriaJobsList.add(job);
						}
						break;
					case "GPA":
					case "gpa":
						if (job.getGPA() == Integer.parseInt(criteria)) {
							matchingCriteriaJobsList.add(job);
						}
						break;
					case "Category":
					case "category":
						if (job.getCategory().equalsIgnoreCase(criteria)) {
							matchingCriteriaJobsList.add(job);
						}
						break;
					case "Industry":
					case "industry":
						if (job.getIndustry().equalsIgnoreCase(criteria)) {
							matchingCriteriaJobsList.add(job);
						}
						break;
					case "Type":
					case "type":
						if (job.getType().equalsIgnoreCase(criteria)) {
							matchingCriteriaJobsList.add(job);
						}
						break;
					}
				} // end for loop
				if (matchingCriteriaJobsList.size() > 0) {
					return matchingCriteriaJobsList;
				}
			} catch (NullPointerException npe) {

				// Hopefully this value will never be returned
				return listOfJobs;
			}
		} // end if statement

		return listOfJobs;
	} // -- end searchJobsList() method

	/**
	 * Gets the group in memory.
	 * 
	 * @return the current Group object.
	 */
	public Group getGroup() {

		return group;
	}

	/**
	 * Goes through all of the groups currently in memory and returns a Group
	 * based on search criteria
	 * 
	 * @param search
	 *            : The property of the object to look for. <br />
	 * @param criteria
	 *            : The term to look for in the column.<br />
	 *            <ul>
	 *            <strong>MUST be a String!</strong> -
	 *            <i>Integer.toString(int))</i>
	 *            <li>ID</li>
	 *            <li>Name</li>
	 *            </ul>
	 * @return
	 * @return Returns a Group object.
	 */
	public Group getGroup(String search, String criteria) {

		Group group = new Group();

		if (!this.listOfGroups.isEmpty()) {
			try {
				for (int i = 0; i < listOfGroups.size(); i++) {
					group = listOfGroups.get(i);
					switch (search) {
					case "ID":
					case "id":
						if (group.getID() == Integer.parseInt(criteria)) {
							return group;
						}
						break;
					case "Name":
					case "name":
						if (group.getName().equalsIgnoreCase(criteria)) {
							return group;
						}
						break;
					}

				}
			} catch (NullPointerException npe) {

				return group;
			}
		} // end if

		return group;
	} // end getGroup method

	/**
	 * Gets all of the groups currently in memory.
	 * 
	 * @return all of the groups
	 */
	public List<Group> getGroupsList() {

		setSQLselectGroupsList();
		return listOfGroups;
	} // end getGroupsList() method

	/**
	 * Sets the current Group object.
	 * 
	 * @param group
	 *            the new group
	 */
	public void setGroup(Group group) {

		this.group = group;
		
		setSQLselectWhereSurveysList(group);

	} // end setGroup method

	/**
	 * Adds the group in memory.
	 * 
	 * @param groupName
	 *            the name of the group being added
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int addGroup(String groupName) { // checks uniqueness of group name
											// and writes to database if name is
											// unique

		boolean exists = false;

		for (Group group : listOfGroups) {
			if (group.getName().equalsIgnoreCase(groupName)) {
				exists = true;
			}
		}

		if (!exists) {
			// writes the new group to database
			insertSQLGroup(groupName);
			// refreshes the group list directly from database after the
			// previous write
			setSQLselectGroupsList();
			return 1;
		} else {
			return 0;
		}
	} // -- end addGroup() method

	/**
	 * Get a survey.
	 * 
	 * @param column
	 *            : The column in the table to look for. <br />
	 * @param search
	 *            : The term to look for in the table.
	 *            <ul>
	 *            <strong>MUST be a String!</strong> -
	 *            <i>Integer.toString(int))</i>
	 *            <li>ID</li>
	 *            <li>FName</li>
	 *            <li>LName</li>
	 *            </ul>
	 * @return a Survey object.
	 */
	public Survey getSurvey(String column, String search) {

		Survey survey = new Survey();

		if (!this.listOfSurveys.isEmpty()) {
			try {
				for (int i = 0; i < listOfSurveys.size(); i++) {
					survey = listOfSurveys.get(i);
					switch (column) {
					case "ID":
					case "id":
						if (survey.getID() == Integer.parseInt(search)) {
							return survey;
						}
						break;
					case "LName":
					case "lname":
						if (survey.getLName().equalsIgnoreCase(search)) {
							return survey;
						}
						break;
					case "FName":
					case "fname":
						if (survey.getLName().equalsIgnoreCase(search)) {
							return survey;
						}
						break;
					}
				}
			} catch (NullPointerException npe) {
				return survey;
			}
		}
		return survey;
	} // -- end getSurvey() method

	/**
	 * Gets the surveys.
	 * 
	 * @return a List of Survey objects for the current group
	 */
	public List<Survey> getSurveysList() {
		if (listOfSurveys.size() > 0) {
			return listOfSurveys;
		}
		List<Survey> lstBlank = new ArrayList<>();

		return lstBlank;
	} // -- end getSurveysList() method

	/**
	 * Get a list of surveys.
	 * 
	 * @param column
	 *            : The column in the table to look for. <br />
	 * 
	 * @param search
	 *            : The term to look for in the table.
	 *            <ul>
	 *            <li>ID (i.e. <i>Integer.toString(parameter))</i></li>
	 *            <li>FName</li>
	 *            <li>LName</li>
	 *            <li>GroupID (i.e. <i>Integer.toString(parameter))</i></li>
	 *            </ul>
	 * @return a List of Survey objects.
	 */
	public List<Survey> getSurveysList(String column, String search) {

		List<Survey> lstRSurveys = new ArrayList<>();

		if (!listOfSurveys.isEmpty()) {
			try {
				for (int i = 0; i < listOfSurveys.size(); i++) {
					Survey survey = listOfSurveys.get(i);
					switch (column) {
					case "ID":
					case "id":
						if (survey.getID() == Integer.parseInt(search)) {
							lstRSurveys.add(survey);
						}
						break;
					case "LName":
					case "lname":
						if (survey.getLName().equalsIgnoreCase(search)) {
							lstRSurveys.add(survey);
						}
						break;
					case "GroupID":
					case "groupid":
						if (survey.getGroupID() == Integer.parseInt(search)) {
							lstRSurveys.add(survey);
						}
						break;
					case "FName":
					case "fname":
						if (survey.getLName().equalsIgnoreCase(search)) {
							lstRSurveys.add(survey);
						}
						break;
					}
				}
				if (lstRSurveys.size() > 0) {
					return lstRSurveys;
				}
			} catch (NullPointerException npe) {
				return listOfSurveys;
			} // end try-catch block
		}
		return listOfSurveys;
	} // end getSurveys method

	/**
	 * Sets the groups list.
	 */
	public void setSQLselectGroupsList() { // SQL accessor caller method
		listOfGroups = groupsTableDatabaseAccessor.select(null, null);
		
	} // -- end setSQLselectGroupsList() method

	/**
	 * Sets the jobs list.
	 */
	public void setSQLselectAllJobsList() { // SQL accessor caller method
		// gives a list of all jobs in the database
		listOfJobs = jobsTableDatabaseAccessor.select(null, null);
	} // -- end setSQLselectJobsList() method

	/**
	 * Sets the surveys list.
	 * 
	 * @param group
	 *            : the group containing the surveys
	 */
	public void setSQLselectWhereSurveysList(Group group) { // SQL accessor
															// caller method

		if (group != null) {
			listOfSurveys = surveysTableDatabaseAccessor.select("groupID",
					String.valueOf(group.getID()), null);
		}
	} // -- end seSQLselectWhereSurveysList() method
	
	

	/**
	 * Updates current Group<br>
	 * .
	 * 
	 * @param uGroup
	 *            : If you want to update a specific group, pass it in.
	 *            Otherwise it updates current group.
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int updateSQLGroup(Group uGroup) { // SQL accessor caller method

		Group group;
		if (uGroup == null) {
			group = getGroup();
		} else {
			group = uGroup;
		}

		group.setModified(new Date());

		int success = groupsTableDatabaseAccessor.update(group);

		return success;
	} // -- end updateSQLGroup() method

	/**
	 * Alter a job in the DataAccessor.
	 * 
	 * @param job
	 *            the job being altered
	 * @return whether or not altering the job was successful
	 */
	public int updateSQLJob(Job job) { // SQL accessor caller method

		int result = 0;
		result = jobsTableDatabaseAccessor.update(job);

		return result;
	} // -- end updateSQLJob() method

	/**
	 * Alter a survey in the DAO.
	 * 
	 * @param survey
	 *            the survey being altered
	 * @return whether or not altering the survey was successful
	 */
	public int updateSQLSurvey(Survey survey) { // SQL accessor caller method

		int result = 0;
		result = surveysTableDatabaseAccessor.update(survey);

		return result;
	} // -- end updateSQLSurvey() method

	/**
	 * Adds a Group in the databaseAccessors.
	 * 
	 * @param groupName
	 *            : The group's name.
	 * @return Returns an integer:<br>
	 *         0: Failure<br>
	 *         1: Success
	 */
	public int insertSQLGroup(String groupName) { // SQL accessor caller method

		int success = groupsTableDatabaseAccessor.insert(groupName);
		listOfGroups.add(groupsTableDatabaseAccessor.select("name",
				"'" + groupName + "'").get(0));

		return success;
	} // -- end insertGroup() method

	/**
	 * Adds a new job in the DAO.
	 * 
	 * @param job
	 *            the job
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int insertSQLJob(Job job) { // SQL accessor caller method

		int result = 0;
		jobsTableDatabaseAccessor.insert(job);

		return result;
	} // -- end insertJob() method

	/**
	 * Adds a new survey to the Group in the DAO.
	 * 
	 * @param survey
	 *            the survey
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public int insertSQLSurvey(Survey survey) { // SQL accessor caller method

		int result = 0;
		surveysTableDatabaseAccessor.insert(survey);

		return result;
	} // -- end insertSurvey() method

	/**
	 * Remove group from database.
	 * 
	 * @param group
	 *            the group being removed
	 */
	public void deleteSQLGroup() { // SQL accessor caller method

		groupsTableDatabaseAccessor.delete(group);
		setSQLselectGroupsList();
		setGroup(null);
		refreshScreen();
	} // -- end deleteSQLGroup() method

	/**
	 * Delete a job in the databaseAccessors.
	 * 
	 * @param job
	 *            the job being deleted
	 * @return whether or not deleted the job was successful
	 */
	public int deleteSQLJob(Job job) { // SQL accessor caller method

		int result = 0;
		jobsTableDatabaseAccessor.delete(job);

		return result;
	} // -- end deleteSQLJob() method

	/**
	 * Delete a survey using the databaseAccessors methods
	 * 
	 * @param survey
	 *            the survey being deleted
	 * @return whether or not deleted the survey was successful
	 */
	public int deleteSQLSurvey(Survey survey) { // SQL accessor caller method

		int result = 0;
		surveysTableDatabaseAccessor.delete(survey);

		return result;
	} // -- end deleteSQLSurvey() method

	/**
	 * Check tables.
	 */
	public void checkExistenceOfSQLTables() { // SQL accessor caller method

		if (!groupsTableDatabaseAccessor.doesGroupsTableExist())
			groupsTableDatabaseAccessor.createTable();
		else
			setSQLselectGroupsList(); // decomment after commit 10/01/2013
		if (!jobsTableDatabaseAccessor.doesJobsTableExist())
			jobsTableDatabaseAccessor.createTable();
		else
			setSQLselectAllJobsList(); // decomment after commit 10/01/2013
		if (!surveysTableDatabaseAccessor.doesSurveysTableExist())
			surveysTableDatabaseAccessor.createTable();

		mapJobs = jobsTableDatabaseAccessor.getJobsByCategory();
	} // -- end checkExistenceOfTables() method
	
} // end Controller class

