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
import obj.Survey;

public class ProcessChildrenDivorcedFemales {

	/** The list of surveys. */
	private List<Survey> surveysList = Controller.getControllerInstance()
			.getSurveysList();

	/** The list of divorced females. */
	private List<Survey> lstDivorcedFemales = new ArrayList<>();

	/** The list of divorced females that HAVE children. */
	private List<Survey> lstDivWithChild = new ArrayList<>();

	/** The list of divorced women who DO NOT have children */
	private List<Survey> lstDivNoChild = new ArrayList<>();

	/**
	 * Counters to track number of surveys with/without kids for
	 * testing/verification
	 **/
	private double countKids;
	private double countNoKids;

	/** Married with children requirement. */
	private double divWithChildrenLimit = .4; // target is 40% or .4
	private double divWithOutChildrenLimit = .6; // target is 60% or .6

	/** Used to calculate %'s of women with/without kids **/
	private double actualChildrenRatio;
	private double actualNoChildrenRatio;

	/**
	 * Random generators for selecting random surveys and number of children to
	 * add to survey
	 **/
	private Random randomGenerator = new Random();
	private int randomInt;
	private int randomKids;

	public List<Survey> doProcess() {

		// Clear out our list and counters
		lstDivorcedFemales.clear();
		countKids = 0;
		countNoKids = 0;

		for (Survey survey : surveysList) {

			// Get surveys for all divorced women in group
			if (survey.getGender() == 1 && survey.getMaritalStatus() == 2) {
				lstDivorcedFemales.add(survey);

				// If survey has children add to list divorced women with
				// children
				// Count surveys with children
				if (survey.getChildren() > 0) {
					lstDivWithChild.add(survey);
					
					countKids++;
//					countKids += survey.getChildren();
				}

				// If survey has NO children add to list divorced with no
				// children
				// Count surveys without children
				if (survey.getChildren() == 0) {
					lstDivNoChild.add(survey);
					countNoKids++;
				}
			}// end if female and divorced

		} // end for loop

		System.out.println("with kids " + countKids);
		System.out.println("with NO kids " + countNoKids);

		// Get percentages of divorced women with and without children
		actualChildrenRatio = (double) (lstDivWithChild.size()) / lstDivorcedFemales.size();
		actualNoChildrenRatio = (double) (lstDivNoChild.size()) / lstDivorcedFemales.size();

		System.out.println("% with children " + actualChildrenRatio);
		System.out.println("% withOUt children " + actualNoChildrenRatio);

		// If more than 40% of women have children we need to adjust down
		if (actualChildrenRatio > divWithChildrenLimit) {
			System.out.println("too many kids");
			adjustChildrenDown();
		}
		// If more than 60% of women have NO children we need to adjust up
		else {
			if (actualNoChildrenRatio > divWithOutChildrenLimit) {
				System.out.println("need MORE kids");
				adjustChildrenUp();
			}
		}// end else

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

			countKids--;
			countNoKids++;
		}

		System.out.println("end with kids " + countKids);
		System.out.println("end no kids " + countNoKids);

	} // end adjustChildrenDown()

	/* ***************************************************** */
	
	public void adjustChildrenUp() {

		/*
		 * While our percentage of divorced with NO kids is too high we randomly
		 * select a divorced woman with NO kids and give her children
		 */
		while ((double) lstDivNoChild.size() / lstDivorcedFemales.size() > divWithOutChildrenLimit) {
			System.out.println((double) lstDivNoChild.size() / lstDivorcedFemales.size());
			randomInt = randomGenerator.nextInt(lstDivNoChild.size());

			System.out.println("begin " + countNoKids);
			Survey survey = lstDivNoChild.get(randomInt);

			// Randomly assign 1 or 2 kids
			randomKids = randomGenerator.nextInt(2) + 1;

			survey.setChildren(randomKids);
			
			// enter directly to the database
			Controller.getControllerInstance().updateSQLSurvey(survey);
			lstDivNoChild.remove(survey);
			lstDivWithChild.add(survey);

			countNoKids--;
			countKids++;

		}

		System.out.println("end with kids " + countKids);
		System.out.println("end no kids " + countNoKids);

	}// end adjustChildrenUp()

} // end class ProcessChildrenDivorcedFemales
