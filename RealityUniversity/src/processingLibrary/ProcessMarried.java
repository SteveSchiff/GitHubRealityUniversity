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
	private float actualMarried;

	/** The actual number of divorced surveys. */
	private float actualDivorced;

	/**
	 * Do processing.
	 * 
	 * @return the list of surveys after being processed
	 */
	public List<Survey> doProcess() {

		for (Survey survey : listOfSurveys) {

			// Populate gender lists
			if (survey.getGender() == 0) // Male
				listOfMales.add(survey);
			if (survey.getGender() == 1) // Female
				listOfFemales.add(survey);

			// Populate single list (based on single/no kids)

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
		}

		// Make sure we have 40% of the group married
		setActualMarried((listOfMarriedMales.size() + listOfMarriedFemales
				.size()) * (100f / listOfSurveys.size()));
		// Make sure we have 35% of the group divorced
		setActualDivorced((listOfDivorcedMales.size() + listOfDivorcedFemales
				.size()) * (100f / listOfSurveys.size()));

		// Remove this block to randomize married regardless of preferred
		setMarriedMales();
		setMarriedFemales();

		// Set spouses
		setSpouses();

		// Make sure we have 35% of the group divorced

		setDivorcedMales();
		setDivorcedFemales();

		return listOfSurveys;
	}

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

	/**
	 * Loop through males and make some married.
	 */
	// TODO This is where the application was freezing up
	// based on the method tracers I inserted into the code.
	public void setMarriedMales() {

		// debug sysout statement
		System.out.println("married RequirementRatio is "
				+ marriedRequirementRatio);

		Random rndMarMale = new Random();
		// debug sysout statement
		System.out
				.println("Generated Random() number and assigned to rndMarMale variable.\n");
		float actualMarriedMales = listOfMarriedMales.size()
				* (100f / listOfSurveys.size());
		// XXX Main debugging to unfreeze app
		// debug sysout statement set
		System.out.println("");
		System.out.println("Generated actualMarriedMales variable. It is "
				+ actualMarriedMales);
		System.out.println("rndMarMale from new Random() is a number type of "
				+ rndMarMale);
		System.out.println("marriedRequirementRatio is "
				+ marriedRequirementRatio);
		// end of debug statements

		while (actualMarriedMales < (marriedRequirementRatio / 2)) {
			Survey survey = listOfMales.get(rndMarMale.nextInt(listOfMales
					.size()));
			if (survey.getMarried() == 0) {
				survey.setMarried(1);
				listOfMarriedMales.add(survey);
				listOfMales.remove(survey);
				actualMarriedMales = listOfMarriedMales.size()
						* (100f / listOfSurveys.size());
			} // end if
				// debug sysout statement
				// System.out.println("actualMarriedMales < (marriedRequirementRatio / 2)");
		} // end while
			// debug sysout statement set
		System.out.println("");
		System.out.println("Generated actualMarriedMales variable. It is "
				+ actualMarriedMales);
		System.out.println("rndMarMale from new Random() is a number type of "
				+ rndMarMale);
		System.out.println("marriedRequirementRatio is "
				+ marriedRequirementRatio);
		// end of debug statements

		while (actualMarriedMales > (marriedRequirementRatio / 2)) {
			Survey survey = listOfMales.get(rndMarMale.nextInt(listOfMales
					.size()));
			if (survey.getMarried() == 1) {
				survey.setMarried(0);
				listOfMarriedMales.remove(survey);
				listOfMales.add(survey);
				actualMarriedMales = listOfMarriedMales.size()
						* (100f / listOfSurveys.size());
			} // end if
				// debug sysout statement
				// System.out.println("actualMarriedMales > (marriedRequirementRatio / 2)");
		} // end while

	} // end of setMarriedMales method

	/**
	 * Loop through females and make some married.
	 */
	public void setMarriedFemales() {

		Random rndMarFemale = new Random();
		float actualMarriedFemales = listOfMarriedFemales.size()
				* (100f / listOfSurveys.size());

		while (actualMarriedFemales < (marriedRequirementRatio / 2)) {
			Survey survey = listOfFemales.get(rndMarFemale
					.nextInt(listOfFemales.size()));
			if (survey.getMarried() == 0) {
				survey.setMarried(1);
				listOfMarriedFemales.add(survey);
				listOfFemales.remove(survey);
				actualMarriedFemales = listOfMarriedFemales.size()
						* (100f / listOfSurveys.size());
			}
		}
		while (actualMarriedFemales > (marriedRequirementRatio / 2)) {
			Survey survey = listOfFemales.get(rndMarFemale
					.nextInt(listOfFemales.size()));
			if (survey.getMarried() == 1) {
				survey.setMarried(0);
				listOfMarriedFemales.remove(survey);
				listOfFemales.add(survey);
				actualMarriedFemales = listOfMarriedFemales.size()
						* (100f / listOfSurveys.size());
			}
		}
	}

	/**
	 * Loop through males and make some divorced.
	 */
	public void setDivorcedMales() {

		Random rndDivMale = new Random();
		float actualDivorcedMales = listOfDivorcedMales.size()
				* (100f / listOfSurveys.size());

		while (actualDivorcedMales < (divorcedRequirementRatio / 2)) {
			Survey survey = listOfMales.get(rndDivMale.nextInt(listOfMales
					.size()));
			if (survey.getMarried() == 0) {
				survey.setMarried(2);
				listOfDivorcedMales.add(survey);
				listOfMales.remove(survey);
				actualDivorcedMales = listOfDivorcedMales.size()
						* (100f / listOfSurveys.size());
			}
		}
		while (actualDivorcedMales > (divorcedRequirementRatio / 2)) {
			Survey survey = listOfMales.get(rndDivMale.nextInt(listOfMales
					.size()));
			if (survey.getMarried() == 2) {
				survey.setMarried(0);
				listOfDivorcedMales.remove(survey);
				listOfMales.add(survey);
				actualDivorcedMales = listOfDivorcedMales.size()
						* (100f / listOfSurveys.size());
			}
		}
	}

	/**
	 * Loop through females and make some divorced.
	 */
	public void setDivorcedFemales() {

		Random rndDivFemale = new Random();
		float actualDivorcedFemales = listOfDivorcedFemales.size()
				* (100f / listOfSurveys.size());

		while (actualDivorcedFemales < (divorcedRequirementRatio / 2)) {
			Survey survey = listOfFemales.get(rndDivFemale
					.nextInt(listOfFemales.size()));
			if (survey.getMarried() == 0) {
				survey.setMarried(2);
				listOfDivorcedFemales.add(survey);
				listOfFemales.remove(survey);
				actualDivorcedFemales = listOfDivorcedFemales.size()
						* (100f / listOfSurveys.size());
			}
		}
		while (actualDivorcedFemales > (divorcedRequirementRatio / 2)) {
			Survey survey = listOfFemales.get(rndDivFemale
					.nextInt(listOfFemales.size()));
			if (survey.getMarried() == 2) {
				survey.setMarried(0);
				listOfDivorcedFemales.remove(survey);
				listOfFemales.add(survey);
				actualDivorcedFemales = listOfDivorcedFemales.size()
						* (100f / listOfSurveys.size());
			}
		}
	}

	/**
	 * Sets the spouses for the group.
	 */
	public void setSpouses() {

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
	}

	public float getActualMarried() {

		return actualMarried;
	}

	public void setActualMarried(float actualMarried) {

		this.actualMarried = actualMarried;
	}

	public float getActualDivorced() {

		return actualDivorced;
	}

	public void setActualDivorced(float actualDivorced) {

		this.actualDivorced = actualDivorced;
	}
}
