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
			if (survey.getMarried() == 1) {
				if (survey.getGender() == 0) // Married Male
					listOfMarriedMales.add(survey);
				if (survey.getGender() == 1) // Married Female
					listOfMarriedFemales.add(survey);
			}

			// Populate divorced lists
			if (survey.getMarried() == 2) {
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
		System.out.println("married RequirementRatio is " + marriedRequirementRatio);

		Random rndMarMale = new Random();
		
		float actualMarriedMales = ((float)listOfMarriedMales.size()) / listOfMales.size();
		System.out.println("listOfMarriedMales.size() is " + listOfMarriedMales.size());
		System.out.println("listOfMales().size() is " + listOfMales.size());

		// first we test to make sure we have enough  married males
		while (actualMarriedMales < marriedRequirementRatio) {
			
			Survey survey = listOfMales.get(rndMarMale.nextInt(listOfMales.size()));
			
			if (survey.getMarried() == 0) {
				survey.setMarried(1);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedMales.add(survey);
				actualMarriedMales = ((float)listOfMarriedMales.size()) / listOfMales.size();
			} // end if
		} // end while
		
		// debug sysout statement set
		System.out.println("");
		System.out.println("Generated actualMarriedMales variable. It is "	+ actualMarriedMales);
		System.out.println("rndMarMale from new Random() is a number type of "	+ rndMarMale);
		System.out.println("marriedRequirementRatio is " + marriedRequirementRatio);
		// end of debug statements

		// then we test to make sure we don't have too many!
		while (actualMarriedMales > marriedRequirementRatio) {
			
			Survey survey = listOfMales.get(rndMarMale.nextInt(listOfMales.size()));
			
			if (survey.getMarried() == 1) {
				survey.setMarried(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedMales.remove(survey);
				actualMarriedMales = ((float)listOfMarriedMales.size()) / listOfMales.size();
			} // end if
		} // end while
		System.out.println("Leaving setMarriedMales() method.");
	} // end of setMarriedMales method

	/**
	 * Loop through females and make some married.
	 */
	public void setMarriedFemales() {
		
		System.out.println("\nEntering setMarriedFemales() method.");
		Controller localControllerInstance = Controller.getControllerInstance();

		Random rndMarFemale = new Random();
		
		float actualMarriedFemales = ((float)listOfMarriedFemales.size()) / listOfFemales.size();

		// first we test to make sure we have enough married females
		while (actualMarriedFemales < marriedRequirementRatio) {
			
			Survey survey = listOfFemales.get(rndMarFemale.nextInt(listOfFemales.size()));
			
			if (survey.getMarried() == 0) {
				survey.setMarried(1);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedFemales.add(survey);
				actualMarriedFemales = ((float)listOfMarriedFemales.size()) / listOfFemales.size();
			} // end if
		} // end while
		
		// then we test to make sure we don't have too many!
		while (actualMarriedFemales > marriedRequirementRatio) {
			
			Survey survey = listOfFemales.get(rndMarFemale.nextInt(listOfFemales.size()));
			
			if (survey.getMarried() == 1) {
				survey.setMarried(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfMarriedFemales.remove(survey);
				actualMarriedFemales = ((float)listOfMarriedFemales.size()) / listOfFemales.size();
			} // end if
		} // end while
		System.out.println("Leaving setMarriedFemales() method.");
	} // end setMarried Females method

	/**
	 * Sets the spouses for the group.
	 */
	public void setSpouses() {
		
		System.out.println("\nEntering setSpouses() method.");

		Random rndSpouse = new Random();
		// TODO: Try to match similar income

		List<Survey> surveys;
		List<Survey> spouseSurveys;

		if (listOfMarriedFemales.size() > listOfMarriedMales.size()) {
			surveys = listOfMarriedFemales;
			spouseSurveys = listOfMarriedMales;
		} else {
			surveys = listOfMarriedMales;
			spouseSurveys = listOfMarriedFemales;
		}

		for (int i = 0; i < surveys.size(); i++) {

			if (listOfMarriedMales.size() > 0
					&& listOfMarriedFemales.size() > 0) {

				Survey survey = surveys.get(i);
				if (!checkSpouse(survey) && survey.getMarried() != 2) {

					try {
						// Set a spouse
						Survey spouse = spouseSurveys.get(rndSpouse
								.nextInt(spouseSurveys.size()));

						// Find an eligible spouse
						while (checkSpouse(spouse) && spouseSurveys.size() > 1) {

							// If spouse is already married, remove from
							// eligible spouses
							if (checkSpouse(spouse))
								spouseSurveys.remove(spouse);

							spouse = spouseSurveys.get(rndSpouse
									.nextInt(spouseSurveys.size()));

						}

						if (spouse != null) {

							// Remove the old surveys
							listOfSurveys.remove(survey);
							listOfSurveys.remove(spouse);

							// Make the 2 spouses to each other
							survey.setSpouse(spouse.getID());
							spouse.setSpouse(survey.getID());

							// Add the new surveys

							listOfSurveys.add(survey);
							listOfSurveys.add(spouse);
						}
					} catch (IndexOutOfBoundsException iobe) {
						break;
					}
				} // CheckSpouse
			}
		} // For Loop
		System.out.println("Leaving setSpouses() method.");
	} // end setSpouses() method

	/**
	 * Loop through males and make some divorced.
	 */
	public void setDivorcedMales() {
		
		System.out.println("\nEntering setDivorcedMales() method.");
		Controller localControllerInstance = Controller.getControllerInstance();

		Random rndDivMale = new Random();
		float actualDivorcedMales = (float)(listOfDivorcedMales.size()) / listOfMales.size();
		System.out.println("actualDivorcedMales is " + actualDivorcedMales);

		// first we test to make sure we have enough divorced males
		while (actualDivorcedMales < divorcedRequirementRatio) {
			Survey survey = listOfMales.get(rndDivMale.nextInt(listOfMales.size()));
			if (survey.getMarried() == 0) {
				survey.setMarried(2);
				localControllerInstance.updateSQLSurvey(survey);
				listOfDivorcedMales.add(survey);
				actualDivorcedMales = (float)(listOfDivorcedMales.size()) / listOfMales.size();
				System.out.println("actualDivorcedMales is " + actualDivorcedMales + " first while");
			}
		}
		
		// then we test to make sure we don't have too many!
		while (actualDivorcedMales > divorcedRequirementRatio) {
			Survey survey = listOfMales.get(rndDivMale.nextInt(listOfMales.size()));
			if (survey.getMarried() == 2) {
				survey.setMarried(0);
				localControllerInstance.updateSQLSurvey(survey);
				listOfDivorcedMales.remove(survey);
				actualDivorcedMales = (float)(listOfDivorcedMales.size()) / listOfMales.size();
				System.out.println("actualDivorcedMales is " + actualDivorcedMales + " second while");
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
		float actualDivorcedFemales = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();
		System.out.println("actualDivorcedFemales is " + actualDivorcedFemales);

		// first we check to make sure we have enough divorced females
		while (actualDivorcedFemales < divorcedRequirementRatio) {
			Survey survey = listOfFemales.get(rndDivFemale.nextInt(listOfFemales.size()));
			if (survey.getMarried() == 0) {
				survey.setMarried(2);
				localControllerInstance.updateSQLSurvey(survey);
				listOfDivorcedFemales.add(survey);
				actualDivorcedFemales = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();
				System.out.println("actualDivorcedFemales is " + actualDivorcedFemales + " first while");
			} // end if
		} // end while
		
		// then we make sure we don't have too many!
		while (actualDivorcedFemales > divorcedRequirementRatio) {
			Survey survey = listOfFemales.get(rndDivFemale.nextInt(listOfFemales.size()));
			if (survey.getMarried() == 2) {
				survey.setMarried(0);
				listOfDivorcedFemales.remove(survey);
				localControllerInstance.updateSQLSurvey(survey);
				actualDivorcedFemales = (float)(listOfDivorcedFemales.size()) / listOfFemales.size();
				System.out.println("actualDivorcedFemales is " + actualDivorcedFemales + " second while");
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
	public boolean checkSpouse(Survey survey) {

		if (survey.getSpouse() > 0)
			return true;
		return false;
	}

	public float getActualMarried() {

		return actualMarried;
	}

	public float getActualDivorced() {

		return actualDivorced;
	}
} // end class
