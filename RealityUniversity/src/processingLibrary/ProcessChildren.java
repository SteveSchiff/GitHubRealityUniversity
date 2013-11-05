package processingLibrary;

/****************************************************************************************************
 * This class retrieves the surveys of married women with and without children.
 * It then, based as much as possible on preference, assigns 50% of married women and their spouses
 *  to have children and 50% to NOT have children. 
 ****************************************************************************************************/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ctrl.Controller;
import obj.Survey;

public class ProcessChildren {

	/** The list of surveys. */
	private List<Survey> surveysList = Controller.getControllerInstance()
			.getSurveysList();

	/** The list of married females. */
	private List<Survey> marriedFemalesList = new ArrayList<>();

	/** The list of married females that HAVE children. */
	private List<Survey> marriedFemalesWithChildrenList = new ArrayList<>();

	/** The list of married women who DO NOT have children */
	private List<Survey> marriedFemalesWithNoChildrenList = new ArrayList<>();

	/** Used to set male spouse information the same as their wife **/
	private Survey spouseSurvey;

	/**
	 * Counters to track number of surveys with/without kids for
	 * testing/verification
	 **/
	private int countKids;
	private int countNoKids;

	/** Married with children requirement. */
	private double marriedWithChildrenLimitRatio = .5; // target is 50% or .5

	/** Used to calculate %'s of women with/without kids **/
	private double actualChildrenRatio;
	private double actualNoChildrenRatio;

	/**
	 * Random generators for selecting random surveys and number of children to
	 * add to survey
	 **/
	private Random randomGenerator = new Random();
	private int randomInteger;
	private int randomKids;

	public List<Survey> doProcess() {

		System.out.println(methodName);

		// Clear out our list and counters
		marriedFemalesList.clear();
		countKids = 0;
		countNoKids = 0;

		for (Survey survey : surveysList) {

			// Get surveys for all married women in group
			if (survey.getGender() == 1 && survey.getMarried() == 1) {
				marriedFemalesList.add(survey);

				// If survey has children add to list married women with
				// children
				// Count surveys with children
				if (survey.getChildren() > 0) {
					marriedFemalesWithChildrenList.add(survey);

					// make sure the spouse has the same number of kids
					spouseSurvey = Controller.getControllerInstance()
							.getSurvey("id",
									Integer.toString(survey.getSpouse()));
					spouseSurvey.setChildren(survey.getChildren());

					countKids++;
				}

				// If survey has NO children add to list married with no
				// children
				// Count surveys without children
				if (survey.getChildren() == 0) {
					marriedFemalesWithNoChildrenList.add(survey);

					// Make sure the spouse has NO kids as well
					spouseSurvey = Controller.getControllerInstance()
							.getSurvey("id",
									Integer.toString(survey.getSpouse()));
					spouseSurvey.setChildren(survey.getChildren());

					countNoKids++;
				}

			}// end if female and married

		} // end for loop

		System.out.println("with kids " + countKids);
		System.out.println("with NO kids " + countNoKids);

		// Get percentages of married women with and without children
		actualChildrenRatio = (double) marriedFemalesWithChildrenList.size()
				/ marriedFemalesList.size();
		actualNoChildrenRatio = (double) marriedFemalesWithNoChildrenList
				.size() / marriedFemalesList.size();

		System.out.println("% with children " + actualChildrenRatio);
		System.out.println("% withOUt children " + actualNoChildrenRatio);

		// If more than 50% of women have children we need to adjust down
		if (actualChildrenRatio > marriedWithChildrenLimitRatio) {
			System.out.println("too many kids");
			adjustChildrenDown();
		}
		// If more than 50% of women have NO children we need to adjust up
		else {
			if (actualNoChildrenRatio > marriedWithChildrenLimitRatio) {
				System.out.println("need MORE kids");
				adjustChildrenUp();
			}
		}// end else

		return surveysList;

	} // end doProcess()

	public void adjustChildrenDown() {

		System.out.println(methodName);

		/*
		 * While our percentage of married WITH kids is too high we randomly
		 * select married women with kids and take her children away
		 */

		while ((double) marriedFemalesWithChildrenList.size()
				/ marriedFemalesList.size() > marriedWithChildrenLimitRatio) {

			randomInteger = randomGenerator
					.nextInt(marriedFemalesWithChildrenList.size());

			Survey survey = marriedFemalesWithChildrenList.get(randomInteger);
			survey.setChildren(0);
			marriedFemalesWithChildrenList.remove(survey);

			// Make sure her spouse has no kids as well
			spouseSurvey = Controller.getControllerInstance().getSurvey("id",
					Integer.toString(survey.getSpouse()));
			spouseSurvey.setChildren(0);

			countKids--;
			countNoKids++;
		}

		System.out.println("end with kids " + countKids);
		System.out.println("end no kids " + countNoKids);

	} // end adjustChildrenDown()

	public void adjustChildrenUp() {

		System.out.println(methodName);

		/*
		 * While our percentage of married with NO kids is to high we randomly
		 * select a married women with NO kids and give her children
		 */

		while ((double) marriedFemalesWithNoChildrenList.size()
				/ marriedFemalesList.size() > marriedWithChildrenLimitRatio) {
			System.out.println(countNoKids / marriedFemalesList.size());
			randomInteger = randomGenerator
					.nextInt(marriedFemalesWithNoChildrenList.size());

			System.out.println("begin "
					+ marriedFemalesWithNoChildrenList.size()/* countNoKids */);
			Survey survey = marriedFemalesWithNoChildrenList.get(randomInteger);

			// Randomly assign 1 or 2 kids
			randomKids = randomGenerator.nextInt(2) + 1;

			survey.setChildren(randomKids);
			marriedFemalesWithNoChildrenList.remove(survey);

			// Make sure her spouse has the same number of children
			spouseSurvey = Controller.getControllerInstance().getSurvey("id",
					Integer.toString(survey.getSpouse()));
			spouseSurvey.setChildren(randomKids);

			countNoKids--;
			countKids++;

		}

		System.out.println("end with kids " + countKids);
		System.out.println("end no kids " + countNoKids);

	}// end adjustChildrenUp()

} // end class
