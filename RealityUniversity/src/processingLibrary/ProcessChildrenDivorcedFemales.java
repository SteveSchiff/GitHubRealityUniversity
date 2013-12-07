package processingLibrary;

/***********************************************************************************************
 * This class retrieves surveys of divorced women with and without children. It then, 
 * based as much as possible on preference, assigns 40% of these women to have children and 60%
 * to not have children.
 ***********************************************************************************************/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ctrl.Controller;
import obj.Group;
import obj.Survey;

public class ProcessChildrenDivorcedFemales {

	private Controller localControllerInstance = Controller.getControllerInstance();
	private Group group = localControllerInstance.getGroup();
	
	private List<Survey> surveysList = currentSurveysList(group);

	private List<Survey> lstDivorcedFemales = new ArrayList<>();

	private List<Survey> lstDivWithChild = new ArrayList<>();

	private List<Survey> lstDivNoChild = new ArrayList<>();

	/**
	 * Counters to track number of surveys with/without kids for
	 * testing/verification
	 **/
//	private double countKids;
//	private double countNoKids;

	/** Married with children requirement. */
	private float divWithChildrenLimit = .4f; // target is 40% or .4
	private float divWithOutChildrenLimit = .6f; // target is 60% or .6

	/** Used to calculate %'s of women with/without kids **/
	private float actualChildrenRatio;
	private float actualNoChildrenRatio;

	/**
	 * Random generators for selecting random surveys and number of children to
	 * add to survey
	 **/
	private Random randomGenerator = new Random();
	private int randomInt;
	private int randomKids;

	public List<Survey> doProcess() {

		System.out.println("Entering ProcessChildrenDivorcedFemales.doProcess() method.");
		// Clear out our list and counters
		lstDivorcedFemales.clear();

		for (Survey survey : surveysList) {

			// Get surveys for all divorced women in group
			if (survey.getGender() == 1 && survey.getMaritalStatus() == 2) {
				lstDivorcedFemales.add(survey);

				// If survey has children add to list divorced women with
				// children
				// Count surveys with children
				if (survey.getChildren() > 0) {
					lstDivWithChild.add(survey);
				}
				else{
					lstDivNoChild.add(survey);
				}
			}// end if female and divorced

		} // end for loop

		// Get percentages of divorced women with and without children
		actualChildrenRatio = (float) (lstDivWithChild.size()) / lstDivorcedFemales.size();
		actualNoChildrenRatio = (float) (lstDivNoChild.size()) / lstDivorcedFemales.size();

		// If more than 40% of women have children we need to adjust down
		if (actualChildrenRatio > divWithChildrenLimit) {
			adjustChildrenDown();
		}
		// If more than 60% of women have NO children we need to adjust up
		else {
			if (actualNoChildrenRatio > divWithOutChildrenLimit) {
				adjustChildrenUp();
			}
		}// end else

		System.out.println("Leaving ProcessChildrenDivorcedFemales.doProcess() method.");
		System.out.println("-------------------------\n");
		return surveysList;
	} // end doProcess()
	
	/* ***************************************************** */

	public void adjustChildrenDown() {

		/*
		 * While our percentage of divorced WITH kids is too high we randomly
		 * select a divorced woman with kids and take her children away.
		 * (Yes, please let us know how that works out for ya!) :P
		 */
		while ((double) lstDivWithChild.size() / lstDivorcedFemales.size() > divWithChildrenLimit) {

			randomInt = randomGenerator.nextInt(lstDivWithChild.size());

			Survey survey = lstDivWithChild.get(randomInt);
			survey.setChildren(0);
			
			// enter directly to the database
			Controller.getControllerInstance().updateSQLSurvey(survey);
			lstDivWithChild.remove(survey);
			lstDivNoChild.add(survey);
		}
	} // end adjustChildrenDown()

	/* ***************************************************** */
	
	public void adjustChildrenUp() {

		/*
		 * While our percentage of divorced with NO kids is too high we randomly
		 * select a divorced woman with NO kids and give her children
		 */
		while ((double) lstDivNoChild.size() / lstDivorcedFemales.size() > divWithOutChildrenLimit) {
			randomInt = randomGenerator.nextInt(lstDivNoChild.size());

			Survey survey = lstDivNoChild.get(randomInt);

			// Randomly assign 1 or 2 kids
			randomKids = randomGenerator.nextInt(2) + 1;

			survey.setChildren(randomKids);
			
			// enter directly to the database
			Controller.getControllerInstance().updateSQLSurvey(survey);
			lstDivNoChild.remove(survey);
			lstDivWithChild.add(survey);
		}

	}// end adjustChildrenUp()
	
	public List<Survey> currentSurveysList(Group group){
		
		List<Survey> survey;
		localControllerInstance.setGroup(group);
		localControllerInstance.setSQLselectWhereSurveysList(group);
		survey = localControllerInstance.getSurveysList();
		return survey;
	}	

} // end class ProcessChildrenDivorcedFemales
