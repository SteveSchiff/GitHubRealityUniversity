package processingLibrary;

/***********************************************************************************************
 * This class retrieves surveys of divorced men with and without children. It then, 
 * based as much as possible on preference, assigns 40% of these men to have children and 60%
 * to not have children.
 ***********************************************************************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ctrl.Controller;
import obj.Group;
import obj.Survey;

public class ProcessChildrenDivorcedMales {

	private Controller localControllerInstance = Controller.getControllerInstance();
	private Group group = localControllerInstance.getGroup();
	
	private List<Survey> surveysList = currentSurveysList(group);

	private List<Survey> lstDivorcedMales = new ArrayList<>();

	private List<Survey> lstDivWithChild = new ArrayList<>();

	private List<Survey> lstDivNoChild = new ArrayList<>();

	/** Divorced with children requirement. */
	private float divWithChildrenLimitRatio = .4f; // target is 40% or .4
	private float divWithOutChildrenLimitRatio = .6f; // target is 60% or .6

	/** Used to calculate %'s of men with/without kids **/
	private float actualChildrenRatio;
	private float actualNoChildrenRatio;

	/**
	 * Random generators for selecting random surveys and number of children to
	 * add to survey
	 **/
	Random randomGenerator = new Random();
	int randomInt;
	int randomKids;

	public List<Survey> doProcess() {

		System.out.println("Entering ProcessChildrenDivorcedMales.doProcess() method.");
		// Clear out our list and counters
		lstDivorcedMales.clear();

		for (Survey survey : surveysList) {

			// Get surveys for all divorced men in group
			if ( (survey.getGender() == 0) && (survey.getMaritalStatus() == 2) ) {
				lstDivorcedMales.add(survey);

				// If survey has children add to list divorced men with children
				// Count surveys with children
				if (survey.getChildren() > 0) {
					lstDivWithChild.add(survey);
				}

				// If survey has NO children add to list divorced with no
				// children
				// Count surveys without children
				if (survey.getChildren() == 0) {
					lstDivNoChild.add(survey);
				}

			}// end if male and divorced

		} // end for loop

		// Get percentages of divorced men with and without children
		actualChildrenRatio = (float) lstDivWithChild.size() / lstDivorcedMales.size();
		actualNoChildrenRatio = (float) lstDivNoChild.size() / lstDivorcedMales.size();

		// If more than 40% of men have children we need to adjust down
		if (actualChildrenRatio > divWithChildrenLimitRatio) {
			adjustChildrenDown();
		}
		// If more than 60% of men have NO children we need to adjust up
		else {
			if (actualNoChildrenRatio > divWithOutChildrenLimitRatio) {
				adjustChildrenUp();
			}
		}// end else

		System.out.println("Leaving ProcessChildrenDivorcedMales.doProcess() method.");
		System.out.println("-------------------------\n");
		
		surveysList = currentSurveysList(group);
		return surveysList;
	} // end doProcess()

	public void adjustChildrenDown() {

		/*
		 * While our percentage of divorced WITH kids is too high we randomly
		 * select a divorced man with kids and take his children away ...darn
		 * DFACS
		 */
		while ((double) lstDivWithChild.size() / lstDivorcedMales.size() > divWithChildrenLimitRatio) {

			randomInt = randomGenerator.nextInt(lstDivWithChild.size());

			Survey survey = lstDivWithChild.get(randomInt);
			survey.setChildren(0);
			
			// enter directly to the database
			Controller.getControllerInstance().updateSQLSurvey(survey);
			lstDivWithChild.remove(survey);
			lstDivNoChild.add(survey);
		}

	} // end adjustChildrenDown()

	public void adjustChildrenUp() {

		/*
		 * While our percentage of divorced with NO kids is to high we randomly
		 * select a divorced man with NO kids and give him children ...Surprise!
		 */
		while ((double) lstDivNoChild.size() / lstDivorcedMales.size() > divWithOutChildrenLimitRatio) {
			randomInt = randomGenerator.nextInt(lstDivNoChild.size());
			Survey survey = lstDivNoChild.get(randomInt);

			// Randomly assign 1 or 2 kids
			randomKids = randomGenerator.nextInt(2) + 1;

			survey.setChildren(randomKids);
			
			// enter directly to the database
			Controller.getControllerInstance().updateSQLSurvey(survey);
			lstDivNoChild.remove(survey);
			lstDivWithChild.add(survey);
		} // end while loop
	}// end adjustChildrenUp()
	
	public List<Survey> currentSurveysList(Group group){
		
		List<Survey> survey;
		localControllerInstance.setGroup(group);
		localControllerInstance.setSQLselectWhereSurveysList(group);
		survey = localControllerInstance.getSurveysList();
		return survey;
	}

} // end class
