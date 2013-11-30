package processingLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ctrl.Controller;
import obj.Group;
import obj.Survey;

/**
 * The Class ProcessMarried processes the list of surveys for married, divorced,
 * and single surveys as well as sets spouses for married surveys.
 */
public class ProcessMarried {


	private Controller localControllerInstance = Controller.getControllerInstance();
	
	private Group group = localControllerInstance.getGroup();
	
	private List<Survey> listOfSurveys = localControllerInstance.getSurveysList();

	private List<Survey> listOfMales = new ArrayList<>();

	private List<Survey> listOfFemales = new ArrayList<>();
	
	private List<Survey> listOfSingleMales = new ArrayList<>();
	
	private List<Survey> listOfSingleFemales = new ArrayList<>();

	private List<Survey> listOfMarriedMales = new ArrayList<>();

	private List<Survey> listOfMarriedFemales = new ArrayList<>();

	private List<Survey> listOfDivorcedMales = new ArrayList<>();

	private List<Survey> listOfDivorcedFemales = new ArrayList<>();

	private double marriedRequirementRatio = .4f; // 40%

	private double divorcedRequirementRatio = .35f; // 35%

	/**
	 * Do processing.
	 * 
	 * @return the list of surveys after being processed
	 */
	public List<Survey> doProcess() {

		System.out.println("Entering ProcessMarried.doProcess() method.");
		// populate the various survey lists from main survey list
		
		clearLists();
		
		for (Survey survey : listOfSurveys) {

			// Populate gender lists
			if (survey.getGender() == 0) {// Male
				listOfMales.add(survey);
				survey.setSpouse(0); // clear the list for processing
				localControllerInstance.updateSQLSurvey(survey);
			}
			else {// Female
				listOfFemales.add(survey);
				survey.setSpouse(0); // clear the list for processing
				localControllerInstance.updateSQLSurvey(survey);
			}

			// Populate married lists
			if (survey.getMaritalStatus() == 1) {
				if (survey.getGender() == 0) // Married Male
					listOfMarriedMales.add(survey);
				else // Married Female
					listOfMarriedFemales.add(survey);
			} // end if married code block
			
			// Populate single lists
			if (survey.getMaritalStatus() == 0) {
				if (survey.getGender() == 0) // Single Male
					listOfSingleMales.add(survey);
				else  // Single Female
					listOfSingleFemales.add(survey);
			} // end if single code block

			// Populate divorced lists
			if (survey.getMaritalStatus() == 2) {
				if (survey.getGender() == 0) // Divorced Male
					listOfDivorcedMales.add(survey);
				if (survey.getGender() == 1) // Divorced Female
					listOfDivorcedFemales.add(survey);
			} // end if divorced code block
		} // end for loop

		// Remove this block to randomize married regardless of preferred
		setMarriedMales();
		setMarriedFemales();

		// Set spouses
		setSpouses();

		// Make sure we have 35% of the group divorced
		setDivorcedMales();
		setDivorcedFemales();

		System.out.println("Leaving ProcessMarried.doProcess() method.");
		System.out.println("-------------------------\n");
		
		listOfSurveys = currentSurveysList(group);
		return listOfSurveys;
	} // end doProcess() method

	/**
	 * Loop through males and make some married.
	 */
	public void setMarriedMales() {

		Random random = new Random();
		
		float actualMarriedMalesRatio = ((float)listOfMarriedMales.size()) / listOfMales.size();

		if (actualMarriedMalesRatio < marriedRequirementRatio) {			
			while (actualMarriedMalesRatio < marriedRequirementRatio) {

				Survey survey = listOfSingleMales.get(random.nextInt(listOfSingleMales.size()));

				survey.setMaritalStatus(1);
				localControllerInstance.updateSQLSurvey(survey);
				listOfSingleMales.remove(survey);
				listOfMarriedMales.add(survey);
				actualMarriedMalesRatio = ((float)listOfMarriedMales.size()) / listOfMales.size();
			} // end while
		} // end if code block
		else {			
			while (actualMarriedMalesRatio > marriedRequirementRatio) {
				
				Survey survey = listOfMarriedMales.get(random.nextInt(listOfMarriedMales.size()));
				
				survey.setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedMales.remove(survey);
				listOfSingleMales.add(survey);
				actualMarriedMalesRatio = ((float)listOfMarriedMales.size()) / listOfMales.size();				
			} // end while
		} // end else code block
	} // end of setMarriedMales method

	/**
	 * Loop through females and make some married.
	 */
	public void setMarriedFemales() {
		
		Controller localControllerInstance = Controller.getControllerInstance();

		Random rndMarFemale = new Random();
		
		float actualMarriedFemalesRatio = ((float)listOfMarriedFemales.size()) / listOfFemales.size();

		if (actualMarriedFemalesRatio < marriedRequirementRatio) {
			while (actualMarriedFemalesRatio < marriedRequirementRatio) {

				Survey survey = listOfSingleFemales.get(rndMarFemale.nextInt(listOfSingleFemales.size()));

				survey.setMaritalStatus(1);
				localControllerInstance.updateSQLSurvey(survey);
				listOfSingleFemales.remove(survey);
				listOfMarriedFemales.add(survey);
				actualMarriedFemalesRatio = ((float) listOfMarriedFemales.size()) / listOfFemales.size();
			} // end while
		} // end if code block
		else {
			while (actualMarriedFemalesRatio > marriedRequirementRatio) {
				
				Survey survey = listOfMarriedFemales.get(rndMarFemale.nextInt(listOfMarriedFemales.size()));
				
				survey.setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedFemales.remove(survey);
				listOfSingleFemales.add(survey);
				actualMarriedFemalesRatio = ((float)listOfMarriedFemales.size()) / listOfFemales.size();
			} // end while
		} // end else code block
	} // end setMarried Females method

	// ************************************************
	// ************************************************
	
	/**
	 * Sets the spouses for the group.
	 */
	public void setSpouses() {
		// TODO: Try to match similar income (Still to be done)
		
		Random randomSpouse = new Random();
		
		List<Survey> listOfAvailableMarriedMales = listOfMarriedMales;
		List<Survey> listOfAvailableMarriedFemales = listOfMarriedFemales;

		if( (listOfAvailableMarriedMales.size() > 0) && (listOfAvailableMarriedFemales.size() > 0) ) {
		
			for (int i = 0; i < listOfAvailableMarriedMales.size(); i++) {
				
				Survey marriedMale = listOfAvailableMarriedMales.get(i);
				
				if ( listOfAvailableMarriedFemales.size() >= 1 ) {
					
					// Set a spouse from random choice of available married pool
					Survey marriedFemale = listOfAvailableMarriedFemales
							.get(randomSpouse.nextInt(listOfAvailableMarriedFemales.size()));

					marriedMale.setSpouse(marriedFemale.getID());
					localControllerInstance.updateSQLSurvey(marriedMale);
					marriedFemale.setSpouse(marriedMale.getID());
					localControllerInstance.updateSQLSurvey(marriedFemale);
					
					listOfAvailableMarriedMales.remove(marriedMale);
					listOfAvailableMarriedFemales.remove(marriedFemale);
					i--; // this will keep the next one from being skipped over in the array shift
				} // end if code block
				else { break; } // no point in looping through the rest if no more eligible females
			} // end for Loop
			
			// Set whatever spouseless marriages that are left over to
			// marital status of single.
			for (int i = 0; i < listOfAvailableMarriedMales.size(); i++ ) {
				
				listOfAvailableMarriedMales.get(i).setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(listOfAvailableMarriedMales.get(i));
				listOfMarriedMales.remove(listOfAvailableMarriedMales.get(i));
				listOfSingleMales.add(listOfAvailableMarriedMales.get(i));								
			} // end male outer loop
			
			for (int i = 0; i < listOfAvailableMarriedFemales.size(); i++ ) {
				
				listOfAvailableMarriedFemales.get(i).setMaritalStatus(0);				
				localControllerInstance.updateSQLSurvey(listOfMarriedFemales.get(i));
				listOfMarriedFemales.remove(listOfAvailableMarriedFemales.get(i));
				listOfSingleFemales.add(listOfAvailableMarriedFemales.get(i));
			} // end female outer loop			
		} // end big if code block
		
		// ****************************************************************
		// else if only the marriedMalesWithoutSpouseList > 0 to start with
		// ****************************************************************
		else if ( (listOfAvailableMarriedMales.size() > 0)
						&& (listOfAvailableMarriedFemales.size() == 0) ) {
			
			// Set whatever spouseless marriages that are left over to
			// marital status of single.
			for (int i = 0; i < listOfAvailableMarriedMales.size(); i++ ) {
				
				listOfAvailableMarriedMales.get(i).setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(listOfAvailableMarriedMales.get(i));
				listOfMarriedMales.remove(listOfAvailableMarriedMales.get(i));
				listOfSingleMales.add(listOfAvailableMarriedMales.get(i));				
			} // end male loop			
		} // end the else if part of the code block for the males
		
		// ****************************************************************
		// else if only the marriedFemalesWithoutSpouseList > 0 to start with
		// ****************************************************************
		else if ( (listOfAvailableMarriedMales.size() == 0)
						&& (listOfAvailableMarriedFemales.size() > 0) ) {
			
			for (int i = 0; i < listOfAvailableMarriedFemales.size(); i++ ) {
				
				listOfAvailableMarriedFemales.get(i).setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(listOfAvailableMarriedFemales.get(i));
				listOfMarriedFemales.remove(listOfAvailableMarriedFemales.get(i));
				listOfSingleFemales.add(listOfAvailableMarriedFemales.get(i));				
			} // end female loop			
		} // end the else part of the code block for the females		
	} // end setSpouses() method

	/**
	 * Loop through males and make some divorced.
	 */
	public void setDivorcedMales() {

		Random rndDivMale = new Random();
		float actualDivorcedMalesRatio = (float)(listOfDivorcedMales.size()) / listOfMales.size();

		
		if (actualDivorcedMalesRatio < divorcedRequirementRatio) {
			while (actualDivorcedMalesRatio < divorcedRequirementRatio) {
				Survey survey = listOfSingleMales.get(rndDivMale.nextInt(listOfSingleMales.size()));
				survey.setMaritalStatus(2);
				localControllerInstance.updateSQLSurvey(survey);
				listOfSingleMales.remove(survey);
				listOfDivorcedMales.add(survey);
				actualDivorcedMalesRatio = (float) (listOfDivorcedMales.size()) / listOfMales.size();
			} // end while loop
		} // end if code block
		else {
			while (actualDivorcedMalesRatio > divorcedRequirementRatio) {
				Survey survey = listOfMales.get(rndDivMale.nextInt(listOfMales.size()));
				if (survey.getMaritalStatus() == 2) {
					survey.setMaritalStatus(0);
					localControllerInstance.updateSQLSurvey(survey);
					listOfDivorcedMales.remove(survey);
					listOfSingleMales.add(survey);
					actualDivorcedMalesRatio = (float)(listOfDivorcedMales.size()) / listOfMales.size();
				} // end if
			} // end while
		} // end else code block
	} // end setDivorcedMales() method

	/**
	 * Loop through females and make some divorced.
	 */
	public void setDivorcedFemales() {
		Controller localControllerInstance = Controller.getControllerInstance();

		Random rndDivFemale = new Random();
		float actualDivorcedFemalesRatio = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();

		
		if (actualDivorcedFemalesRatio < divorcedRequirementRatio) {
			while (actualDivorcedFemalesRatio < divorcedRequirementRatio) {
				Survey survey = listOfFemales.get(rndDivFemale
						.nextInt(listOfFemales.size()));
				if (survey.getMaritalStatus() == 0) {
					survey.setMaritalStatus(2);
					localControllerInstance.updateSQLSurvey(survey);
					listOfSingleFemales.remove(survey);
					listOfDivorcedFemales.add(survey);
					actualDivorcedFemalesRatio = (float) (listOfDivorcedFemales.size()) / listOfFemales.size();
				} // end if
			} // end while
		} // end if code block
		else {
			while (actualDivorcedFemalesRatio > divorcedRequirementRatio) {
				Survey survey = listOfFemales.get(rndDivFemale.nextInt(listOfFemales.size()));
				if (survey.getMaritalStatus() == 2) {
					survey.setMaritalStatus(0);
					listOfDivorcedFemales.remove(survey);
					listOfSingleFemales.add(survey);
					localControllerInstance.updateSQLSurvey(survey);
					actualDivorcedFemalesRatio = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();
				} // end if
			} // end while
		} // end else code block
	} // end setDivorcedFemales() method

	/**
	 * Check spouse.
	 * 
	 * @param survey
	 *            : the survey being checked
	 * @return true, if has spouse
	 */
	public boolean hasSpouse(Survey survey) {

		if (survey.getSpouse() > 0){
			return true;
		}
		else {
			return false;
		}
	} // end hasSpouse() method
	
	public List<Survey> currentSurveysList(Group group){
		
		List<Survey> survey;
		localControllerInstance.setGroup(group);
		localControllerInstance.setSQLselectWhereSurveysList(group);
		survey = localControllerInstance.getSurveysList();
		return survey;
	}
	
	private void clearLists() {
		listOfMales.clear();
		listOfFemales.clear();
		listOfSingleMales.clear();
		listOfSingleFemales.clear();
		listOfMarriedMales.clear();
		listOfMarriedFemales.clear();
		listOfDivorcedMales.clear();
		listOfDivorcedFemales.clear();
	}
} // end class
