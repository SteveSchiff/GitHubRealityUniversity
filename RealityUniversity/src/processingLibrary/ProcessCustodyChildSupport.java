package processingLibrary;

import ctrl.Controller;

/*********************************************************************************************
 * This class selects divorced men and women with children. It then, based on the individuals 
 * income, sets 10% of males to receive child support payment, and the rest to pay child support.
 * 75% of women are set to receive child support, and the rest to pay child support.
 * Percentages will vary slightly based on group size. The larger the group, the more accurate
 * percentages will be. The assignment of negative child support denotes that an individual is a
 * non-custodial parent paying child support. A positive assignment denotes individual is a 
 * custodial parent receiving child support.
 **********************************************************************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ctrl.Controller;
import obj.Group;
import obj.Job;
import obj.Survey;

public class ProcessCustodyChildSupport {

	Group group = Controller.getControllerInstance().getGroup();
	
	/** The list of surveys. */
	// Gets the list directly from the database
	private List<Survey> surveysList = currentSurveysList(group);
	

	/** The list of divorced males that HAVE children. */
	private List<Survey> lstDivMalesWithChild = new ArrayList<>();

	/** The list of divorced females that HAVE children. */
	private List<Survey> lstDivFemalesWithChild = new ArrayList<>();

	/** Random generators */
	Random randomGenerator = new Random();
	int randomMale;
	int randomFemale;

	public List<Survey> doProcess() {

		// This can be uncommented. It will set childSupport to '0' for all
		// surveys in group
		for (Survey empty : surveysList) {
			if (empty.getChildSupport() != 0)
				empty.setChildSupport(0);
		}
		System.out.println("Reset child support to zero for re-processing");

		// Populate the "divorced with children" lists for
		// both women and men.
		for (Survey survey : surveysList) {

			if (survey.getMaritalStatus() == 2 && survey.getChildren() > 0) {
				// Get surveys for all divorced men with children in group
				if (survey.getGender() == 0) {
					lstDivMalesWithChild.add(survey);
				}
				// Get surveys for divorced women with children
				if (survey.getGender() == 1) {
					lstDivFemalesWithChild.add(survey);
				}
			} // end if
		} // end for survey

		System.out.println("ChildSupport/Custody \n -----------------");
		System.out.println("Divorced males: " + lstDivMalesWithChild.size());
		System.out.println("Divorced females : "
				+ lstDivFemalesWithChild.size());

		// if the list of divorced men with children
		// is less than 10, don't even bother trying
		// to determine if any of them get child support.
		if( lstDivMalesWithChild.size() >= 10 ){
			
			setChildSupportMale();			
		}
		
		setChildSupportFemale();
		
		surveysList = currentSurveysList(group);
		return surveysList;

	} // end doProcess()

	// *****************************************
	
	// This method is only activated if the
	// group of divorced males is at least 10.
	public void setChildSupportMale() {
		
		// temporary survey list so that the original one
		// doesn't get modified as it's being run through
		// the while loop below
		List<Survey> tempList = lstDivMalesWithChild;
		
		// initialize for while statement that follows
		int i = 0;

		// While less than 10% of men receive child support
		while ( i < (int)(lstDivMalesWithChild.size() * .1) ) {

			randomMale = randomGenerator.nextInt(lstDivMalesWithChild.size());
			// Get a random male with children
			Survey survey = tempList.get(randomMale);
			// Get job info for survey
			Job jobInfo = Controller.getControllerInstance().getJob("id", Integer.toString(survey.getAssignedJob()));
			
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();
			// Assign child support based on income and number of children
			if (survey.getChildren() == 1) {
				survey.setChildSupport(.0116 * salary);
				
				// write the change directly to the database
				Controller.getControllerInstance().updateSQLSurvey(survey);
				tempList.remove(survey);
			}
			if (survey.getChildren() == 2) {
				survey.setChildSupport(.0174 * salary);
				
				// write the change directly to the database
				Controller.getControllerInstance().updateSQLSurvey(survey);
				tempList.remove(survey);
			}

			i++;
			System.out.println("child support: " + survey.getChildSupport());

		} // end while loop
		
		// the men left over that don't get child support
		// has a changed list that must be pruned.
		lstDivMalesWithChild = tempList;

		// Set all other males to pay child support based on their income
		for (Survey survey2 : lstDivMalesWithChild) {

			Job jobInfo = Controller.getControllerInstance().getJob("id",
					Integer.toString(survey2.getAssignedJob()));
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();

			if (survey2.getChildSupport() == 0) {
				if (survey2.getChildren() == 1) {
					survey2.setChildSupport(-(.0116 * salary));
					
					// write the change directly to the database
					Controller.getControllerInstance().updateSQLSurvey(survey2);
				} // end first inner if block
				if (survey2.getChildren() >= 2) {
					survey2.setChildSupport(-(.0174 * salary));
					
					// write the change directly to the database
					Controller.getControllerInstance().updateSQLSurvey(survey2);
				} // end second inner if block
			} // end outer if block
		} // end for loop
	} // end setChildSupportMale() method

	// *****************************************
	
	public void setChildSupportFemale() {
		
		float i = 1;
		int numberOfDivorcedFemales = lstDivFemalesWithChild.size();
		List<Survey> listOfFemalesNotReceivingChildSupport = lstDivFemalesWithChild;
		
		// ******************************************************
		
		// While less than 75% of women receive child support
		while (i < numberOfDivorcedFemales * .75) {

			randomFemale = randomGenerator.nextInt(listOfFemalesNotReceivingChildSupport.size());
			
			// Get a random female with children
			Survey survey = listOfFemalesNotReceivingChildSupport.get(randomFemale);

			// Get job information from survey
			Job jobInfo = Controller.getControllerInstance().getJob("id",
					Integer.toString(survey.getAssignedJob()));
			
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();
			
			// Assign child support based on income and number of children
			if (survey.getChildren() == 1) {
				survey.setChildSupport(.0116 * salary);
				
				// write the change directly to the database
				Controller.getControllerInstance().updateSQLSurvey(survey);
				listOfFemalesNotReceivingChildSupport.remove(survey);
			}
			if (survey.getChildren() >= 2) {
				survey.setChildSupport(.0174 * salary);
				
				// write the change directly to the database
				Controller.getControllerInstance().updateSQLSurvey(survey);
				listOfFemalesNotReceivingChildSupport.remove(survey);
			}
			i++;

			System.out.println("i = : " + i);
			System.out.println("child support/Female: "
					+ survey.getChildSupport());
		} // end while loop
		
		// ******************************************************
		
		// the women left over that don't get child support
		// has a changed list that must be pruned.
		lstDivFemalesWithChild = listOfFemalesNotReceivingChildSupport;

		// For remaining women, assign child support payment based on income
		for (Survey survey2 : lstDivFemalesWithChild) {

			Job jobInfo = Controller.getControllerInstance().getJob("id",
					Integer.toString(survey2.getAssignedJob()));
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();

			if (survey2.getChildSupport() == 0) {
				if (survey2.getChildren() == 1) {
					survey2.setChildSupport(-(.0116 * salary));
					
					// write the change directly to the database
					Controller.getControllerInstance().updateSQLSurvey(survey2);
					System.out.println("Came from neg.: "
							+ survey2.getChildSupport());
				} // end first inner if block
				if (survey2.getChildren() >= 2) {
					survey2.setChildSupport(-(.0174 * salary));
					
					// write the change directly to the database
					Controller.getControllerInstance().updateSQLSurvey(survey2);
					System.out.println("Came from neg.: "
							+ survey2.getChildSupport());
				} // end second inner if block
			} // end outer if block
		} // end for loop
	} // end setChildSupportFemale() method

	// *****************************************
	
	// this method actually taps directly into the database
	// to get the values of the various surveys
	public List<Survey> currentSurveysList(Group group){
		
		List<Survey> survey;
		Controller.getControllerInstance().setGroup(group);
		Controller.getControllerInstance().setSQLselectWhereSurveysList(group);
		survey = Controller.getControllerInstance().getSurveysList();
		return survey;
	}
} // end class

