package processingLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ctrl.Controller;
import obj.Survey;

/**
 * The Class ProcessMarried processes the list of surveys for married, divorced,
 * and single surveys as well as sets spouses for married surveys.
 */
public class ProcessMarried {

	/** The list of surveys. */
	private List<Survey> listOfSurveys = Controller.getControllerInstance()
			.getSurveysList();

	/** The list of males. */
	private List<Survey> listOfMales = new ArrayList<>();

	/** The list of females. */
	private List<Survey> listOfFemales = new ArrayList<>();

	/** The list of married males. */
	private List<Survey> listOfMarriedMales = new ArrayList<>();

	/** The list of married females. */
	private List<Survey> listOfMarriedFemales = new ArrayList<>();

	/** The list of divorced males. */
	private List<Survey> listOfDivorcedMales = new ArrayList<>();

	/** The list of divorced females. */
	private List<Survey> listOfDivorcedFemales = new ArrayList<>();

	/** The married requirement. */
	// private double marriedRequirementRatio = .4 * 100f; // 40%
	private double marriedRequirementRatio = .4f; // 40%

	/** The divorced requirement. */
	// private double divorcedRequirementRatio = .35 * 100f; // 35%
	private double divorcedRequirementRatio = .35f; // 35%

	/** The actual number of married surveys. */
	// a ratio value?
	private float actualMarried;

	/** The actual number of divorced surveys. */
	// a ratio value?
	private float actualDivorced;

	/**
	 * Do processing.
	 * 
	 * @return the list of surveys after being processed
	 */
	public List<Survey> doProcess() {

		// populate the various survey lists from main survey list
		for (Survey survey : listOfSurveys) {

			// Populate gender lists
			if (survey.getGender() == 0) // Male
				listOfMales.add(survey);
			if (survey.getGender() == 1) // Female
				listOfFemales.add(survey);

			// Populate married lists (based on income)
			if (survey.getMaritalStatus() == 1) {
				if (survey.getGender() == 0) // Married Male
					listOfMarriedMales.add(survey);
				if (survey.getGender() == 1) // Married Female
					listOfMarriedFemales.add(survey);
			}

			// Populate divorced lists
			if (survey.getMaritalStatus() == 2) {
				if (survey.getGender() == 0) // Divorced Male
					listOfDivorcedMales.add(survey);
				if (survey.getGender() == 1) // Divorced Female
					listOfDivorcedFemales.add(survey);
			}
		} // end for loop

		// Make sure we have 40% of the group married
		// an ordinary set method
		setActualMarried( (float)(listOfMarriedMales.size() + listOfMarriedFemales.size()) / listOfSurveys.size() );
		
		// Make sure we have 35% of the group divorced
		// an ordinary set method
		setActualDivorced( (float)(listOfDivorcedMales.size() + listOfDivorcedFemales.size()) / listOfSurveys.size() );

		// Remove this block to randomize married regardless of preferred
		setMarriedMales();
		setMarriedFemales();

		// Set spouses
		setSpouses();

		// Make sure we have 35% of the group divorced
		setDivorcedMales();
		setDivorcedFemales();

		return listOfSurveys;
	} // end doProcess() method

	public void setActualMarried(float actualMarried) {

		this.actualMarried = actualMarried;
	}

	public void setActualDivorced(float actualDivorced) {

		this.actualDivorced = actualDivorced;
	}

	/**
	 * Loop through males and make some married.
	 */
	// TODO This is where the application was freezing up
	// based on the method tracers I inserted into the code.
	public void setMarriedMales() {
		
		System.out.println("\nEntering setMarriedMales() method.");
		Controller localControllerInstance = Controller.getControllerInstance();
		
		// debug sysout statement
//		System.out.println("married RequirementRatio is " + marriedRequirementRatio);

		Random rndMarMale = new Random();
		
		float actualMarriedMalesRatio = ((float)listOfMarriedMales.size()) / listOfMales.size();
		
//		System.out.println("listOfMarriedMales.size() is " + listOfMarriedMales.size());
//		System.out.println("listOfMales().size() is " + listOfMales.size());

		// first we test to make sure we have enough  married males
		while (actualMarriedMalesRatio < marriedRequirementRatio) {
			
			Survey survey = listOfMales.get(rndMarMale.nextInt(listOfMales.size()));
			
			if (survey.getMaritalStatus() == 0) {
				survey.setMaritalStatus(1);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedMales.add(survey);
				actualMarriedMalesRatio = ((float)listOfMarriedMales.size()) / listOfMales.size();
			} // end if
		} // end while
		
		// debug sysout statement set
		System.out.println("");
		System.out.println("Generated actualMarriedMales variable. It is "	+ actualMarriedMalesRatio);
		System.out.println("rndMarMale from new Random() is a number type of "	+ rndMarMale);
		System.out.println("marriedRequirementRatio is " + marriedRequirementRatio);
		// end of debug statements

		// then we test to make sure we don't have too many!
		while (actualMarriedMalesRatio > marriedRequirementRatio) {
			
			Survey survey = listOfMales.get(rndMarMale.nextInt(listOfMales.size()));
			
			if (survey.getMaritalStatus() == 1) {
				survey.setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedMales.remove(survey);
				actualMarriedMalesRatio = ((float)listOfMarriedMales.size()) / listOfMales.size();
			} // end if
		} // end while
		System.out.println("Number of married males is " + listOfMarriedMales.size());
		System.out.println("Leaving setMarriedMales() method.");
	} // end of setMarriedMales method

	/**
	 * Loop through females and make some married.
	 */
	public void setMarriedFemales() {
		
		System.out.println("\nEntering setMarriedFemales() method.");
		Controller localControllerInstance = Controller.getControllerInstance();

		Random rndMarFemale = new Random();
		
		float actualMarriedFemalesRatio = ((float)listOfMarriedFemales.size()) / listOfFemales.size();

		// first we test to make sure we have enough married females
		while (actualMarriedFemalesRatio < marriedRequirementRatio) {
			
			Survey survey = listOfFemales.get(rndMarFemale.nextInt(listOfFemales.size()));
			
			if (survey.getMaritalStatus() == 0) {
				survey.setMaritalStatus(1);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedFemales.add(survey);
				actualMarriedFemalesRatio = ((float)listOfMarriedFemales.size()) / listOfFemales.size();
			} // end if
		} // end while
		
		// then we test to make sure we don't have too many!
		while (actualMarriedFemalesRatio > marriedRequirementRatio) {
			
			Survey survey = listOfFemales.get(rndMarFemale.nextInt(listOfFemales.size()));
			
			if (survey.getMaritalStatus() == 1) {
				survey.setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedFemales.remove(survey);
				actualMarriedFemalesRatio = ((float)listOfMarriedFemales.size()) / listOfFemales.size();
			} // end if
		} // end while
		System.out.println("Number of married females is " + listOfMarriedFemales.size());
		System.out.println("Leaving setMarriedFemales() method.");
	} // end setMarried Females method

	// ************************************************
	// ************************************************
	
	/**
	 * Sets the spouses for the group.
	 */
	public void setSpouses() {
		
		System.out.println("\nEntering setSpouses() method.");
		System.out.println("There are " + listOfMarriedFemales.size() + " females.");
		System.out.println("There are " + listOfMarriedMales.size() + " males.");

		Random randomSpouse = new Random();
		// TODO: Try to match similar income

		List<Survey> marriedMalesWithoutAssignedSpouseList = listOfMarriedMales;
		List<Survey> marriedFemalesWithoutAssignedSpouseList = listOfMarriedFemales;
		
		// These next two values are temporarily needed in oder to
		// prune the available married list without affecting it while
		// the loop is running.  After the looping is through, then the
		// temporary values will be used to reset the main married lists.
		List<Survey> tempMarriedMalesWithoutAssignedSpouseList = marriedMalesWithoutAssignedSpouseList;
		List<Survey> tempMarriedFemalesWithoutAssignedSpouseList = marriedFemalesWithoutAssignedSpouseList;
		
		// First we need to loop through the two lists to winnow out
		// the marrieds that already have an assigned spouse from previous
		// survey group processing run-throughs.
		for (int i = 0; i < marriedMalesWithoutAssignedSpouseList.size(); i++) {
			
			Survey marriedMale = marriedMalesWithoutAssignedSpouseList.get(i);
			
			if (hasSpouse(marriedMale)) {
				tempMarriedMalesWithoutAssignedSpouseList.remove(i);
			}
		} // end married male for loop
		
		for (int i = 0; i < marriedFemalesWithoutAssignedSpouseList.size(); i++) {
			
			Survey marriedFemale = marriedFemalesWithoutAssignedSpouseList.get(i);
			
			if (hasSpouse(marriedFemale)) {
				tempMarriedFemalesWithoutAssignedSpouseList.remove(i);
			}
		} // end married female for loop
		
		// Now we use the temporary married lists to reset the married lists
		// that we really want to work with - the ones that have no assigned
		// spouse yet.
		marriedMalesWithoutAssignedSpouseList = tempMarriedMalesWithoutAssignedSpouseList;
		marriedFemalesWithoutAssignedSpouseList = tempMarriedFemalesWithoutAssignedSpouseList;
		
		// *********************************************************************
		// Now that we've winnowed both married lists down to only the ones
		// that are married with no assigned spouses, we can start the assigning
		// *********************************************************************
		if( (marriedMalesWithoutAssignedSpouseList.size() > 0) && (marriedFemalesWithoutAssignedSpouseList.size() > 0) ) {
		
			for (int i = 0; i < marriedMalesWithoutAssignedSpouseList.size(); i++) {
				
				Survey marriedMale = marriedMalesWithoutAssignedSpouseList.get(i);
				
				if ( marriedFemalesWithoutAssignedSpouseList.size() >= 1 ) {
					
					// Set a spouse from random choice of available married pool
					Survey marriedFemale = marriedFemalesWithoutAssignedSpouseList.get(randomSpouse
							.nextInt(marriedFemalesWithoutAssignedSpouseList.size()));

					marriedMale.setSpouse(marriedFemale.getID());
					marriedFemale.setSpouse(marriedMale.getID());
					
					// must use the temp list for the married males so the loop through
					// is not broken during the whole process
					tempMarriedMalesWithoutAssignedSpouseList.remove(marriedMale);
					marriedFemalesWithoutAssignedSpouseList.remove(marriedFemale);
				} // if marriedFemale had no assigned spouse
				else {
					// no sense in continuing the looping if there are
					// no more available partners
					marriedMalesWithoutAssignedSpouseList = tempMarriedMalesWithoutAssignedSpouseList;
					break;
				}				
			} // end for Loop
			
			// Set whatever spouseless marriages that are left over to
			// marital status of single.
			for (int i = 0; i < marriedMalesWithoutAssignedSpouseList.size(); i++ ) {
				marriedMalesWithoutAssignedSpouseList.get(i).setMaritalStatus(0);
				
				// write to database
				Controller.getControllerInstance().updateSQLSurvey(marriedMalesWithoutAssignedSpouseList.get(i));
				
				// Now we must loop through the original married males list to
				// remove this newly single male from it.
				for(int j = 0; j < listOfMarriedMales.size(); j++) {
					
					if ( listOfMarriedMales.get(j) == marriedMalesWithoutAssignedSpouseList.get(i) ) {
						listOfMarriedMales.remove(j);
						break; // move on to the next male without a spouse since we've got a match
					}
				} // end inner loop
			} // end male outer loop
			
			for (int i = 0; i < marriedFemalesWithoutAssignedSpouseList.size(); i++ ) {
				marriedFemalesWithoutAssignedSpouseList.get(i).setMaritalStatus(0);
				
				// write to database
				Controller.getControllerInstance().updateSQLSurvey(marriedFemalesWithoutAssignedSpouseList.get(i));
				
				// Now we must loop through the original married males list to
				// remove this newly single male from it.
				for(int j = 0; j < listOfMarriedFemales.size(); j++) {
					
					if ( listOfMarriedFemales.get(j) == marriedFemalesWithoutAssignedSpouseList.get(i) ) {
						listOfMarriedFemales.remove(j);
						break; // move on to the next female without a spouse since we've got a match
					}
				} // end inner loop
			} // end female outer loop			
		} // end big if code block
		
		// ****************************************************************
		// else if only the marriedMalesWithoutSpouseList > 0 to start with
		// ****************************************************************
		else if ( marriedMalesWithoutAssignedSpouseList.size() > 0 ) {
			
			// Set whatever spouseless marriages that are left over to
			// marital status of single.
			for (int i = 0; i < marriedMalesWithoutAssignedSpouseList.size(); i++ ) {
				marriedMalesWithoutAssignedSpouseList.get(i).setMaritalStatus(0);
				
				// write to database
				Controller.getControllerInstance().updateSQLSurvey(marriedMalesWithoutAssignedSpouseList.get(i));
				
				// Now we must loop through the original married males list to
				// remove this newly single male from it.
				for(int j = 0; j < listOfMarriedMales.size(); j++) {
					
					if ( listOfMarriedMales.get(j) == marriedMalesWithoutAssignedSpouseList.get(i) ) {
						listOfMarriedMales.remove(j);
						break; // move on to the next male without a spouse since we've got a match
					}
				} // end inner loop
			} // end male outer loop			
		} // end the else if part of the code block for the males
		
		// ****************************************************************
		// else if only the marriedFemalesWithoutSpouseList > 0 to start with
		// ****************************************************************
		else {
			
			for (int i = 0; i < marriedFemalesWithoutAssignedSpouseList.size(); i++ ) {
				marriedFemalesWithoutAssignedSpouseList.get(i).setMaritalStatus(0);
				
				// write to database
				Controller.getControllerInstance().updateSQLSurvey(marriedFemalesWithoutAssignedSpouseList.get(i));
				
				// Now we must loop through the original married males list to
				// remove this newly single male from it.
				for(int j = 0; j < listOfMarriedFemales.size(); j++) {
					
					if ( listOfMarriedFemales.get(j) == marriedFemalesWithoutAssignedSpouseList.get(i) ) {
						listOfMarriedFemales.remove(j);
						break; // move on to the next female without a spouse since we've got a match
					}
				} // end inner loop
			} // end female outer loop			
		} // end the else part of the code block for the females
		
		System.out.println("Leaving setSpouses() method.");
	} // end setSpouses() method

	/**
	 * Loop through males and make some divorced.
	 */
	public void setDivorcedMales() {
		
		System.out.println("\nEntering setDivorcedMales() method.");
		Controller localControllerInstance = Controller.getControllerInstance();

		Random rndDivMale = new Random();
		float actualDivorcedMalesRatio = (float)(listOfDivorcedMales.size()) / listOfMales.size();
		System.out.println("actualDivorcedMales is " + actualDivorcedMalesRatio);

		// first we test to make sure we have enough divorced males
		while (actualDivorcedMalesRatio < divorcedRequirementRatio) {
			Survey survey = listOfMales.get(rndDivMale.nextInt(listOfMales.size()));
			if (survey.getMaritalStatus() == 0) {
				survey.setMaritalStatus(2);
				localControllerInstance.updateSQLSurvey(survey);
				listOfDivorcedMales.add(survey);
				actualDivorcedMalesRatio = (float)(listOfDivorcedMales.size()) / listOfMales.size();
				System.out.println("actualDivorcedMales is " + actualDivorcedMalesRatio + " first while");
			}
		}
		
		// then we test to make sure we don't have too many!
		while (actualDivorcedMalesRatio > divorcedRequirementRatio) {
			Survey survey = listOfMales.get(rndDivMale.nextInt(listOfMales.size()));
			if (survey.getMaritalStatus() == 2) {
				survey.setMaritalStatus(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfDivorcedMales.remove(survey);
				actualDivorcedMalesRatio = (float)(listOfDivorcedMales.size()) / listOfMales.size();
				System.out.println("actualDivorcedMales is " + actualDivorcedMalesRatio + " second while");
			} // end if
		} // end while
		System.out.println("Leaving setDivorcedMales() method.");
	} // end setDivorcedMales() method

	/**
	 * Loop through females and make some divorced.
	 */
	public void setDivorcedFemales() {
		
		System.out.println("\nEntering setDivorcedFemales() method.");
		Controller localControllerInstance = Controller.getControllerInstance();

		Random rndDivFemale = new Random();
		float actualDivorcedFemalesRatio = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();
		System.out.println("actualDivorcedFemales is " + actualDivorcedFemalesRatio);

		// first we check to make sure we have enough divorced females
		while (actualDivorcedFemalesRatio < divorcedRequirementRatio) {
			Survey survey = listOfFemales.get(rndDivFemale.nextInt(listOfFemales.size()));
			if (survey.getMaritalStatus() == 0) {
				survey.setMaritalStatus(2);
				localControllerInstance.updateSQLSurvey(survey);
				listOfDivorcedFemales.add(survey);
				actualDivorcedFemalesRatio = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();
				System.out.println("actualDivorcedFemales is " + actualDivorcedFemalesRatio + " first while");
			} // end if
		} // end while
		
		// then we make sure we don't have too many!
		while (actualDivorcedFemalesRatio > divorcedRequirementRatio) {
			Survey survey = listOfFemales.get(rndDivFemale.nextInt(listOfFemales.size()));
			if (survey.getMaritalStatus() == 2) {
				survey.setMaritalStatus(0);
				listOfDivorcedFemales.remove(survey);
				localControllerInstance.updateSQLSurvey(survey);
				actualDivorcedFemalesRatio = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();
				System.out.println("actualDivorcedFemales is " + actualDivorcedFemalesRatio + " second while");
			} // end if
		} // end while
		System.out.println("Leaving setDivorcedFemales() method.");
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
	}

	public float getActualMarried() {

		return actualMarried;
	}

	public float getActualDivorced() {

		return actualDivorced;
	}
} // end class
