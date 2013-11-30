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
import obj.Group;
import obj.Survey;

public class ProcessChildren {

	Controller localControllerInstance = Controller.getControllerInstance();
	Group group = localControllerInstance.getGroup();
	
	private List<Survey> surveysList = currentSurveysList(group);

	private List<Survey> marriedFemalesList = new ArrayList<>();

	private List<Survey> marriedFemalesWithChildrenList = new ArrayList<>();

	private List<Survey> marriedFemalesWithNoChildrenList = new ArrayList<>();

	private Survey spouseSurvey;

	private float marriedWithChildrenLimitRatio = .5f; // target is 50% or .5

	private float actualChildrenRatio;
	private float actualNoChildrenRatio;

	/**
	 * Random generators for selecting random surveys and number of children to
	 * add to survey
	 **/
	private Random randomGenerator = new Random();
	private int randomInteger;
	private int randomKids;

	public List<Survey> doProcess() {

		System.out.println("Entering ProcessChildren.doProcess() method.");
		// Clear out our list and counters
		marriedFemalesList.clear();

		for (Survey survey : surveysList) {

			// Get surveys for all married women in group
			if (survey.getGender() == 1 && survey.getMaritalStatus() == 1) {
				marriedFemalesList.add(survey);

				// If survey has children add to list married women with
				// children
				// Count surveys with children
				if (survey.getChildren() > 0) {
					marriedFemalesWithChildrenList.add(survey);

					// make sure the spouse has the same number of kids
					spouseSurvey = Controller.getControllerInstance().getSurvey("id", 
							Integer.toString(survey.getSpouse()));
					spouseSurvey.setChildren(survey.getChildren());
				}

				// If survey has NO children add to list married with no
				// children
				// Count surveys without children
				if (survey.getChildren() == 0) {
					marriedFemalesWithNoChildrenList.add(survey);

					// Make sure the spouse has NO kids as well
					spouseSurvey = Controller.getControllerInstance().getSurvey("id",
									Integer.toString(survey.getSpouse()));
					spouseSurvey.setChildren(survey.getChildren());
				}
			}// end if female and married
		} // end for loop

		// Get percentages of married women with and without children
		actualChildrenRatio = (float) marriedFemalesWithChildrenList.size()
				/ marriedFemalesList.size();
		actualNoChildrenRatio = (float) marriedFemalesWithNoChildrenList
				.size() / marriedFemalesList.size();

		// If more than 50% of women have children we need to adjust down
		if (actualChildrenRatio > marriedWithChildrenLimitRatio) {
			adjustChildrenDown();
		}
		// If more than 50% of women have NO children we need to adjust up
		else {
			if (actualNoChildrenRatio > marriedWithChildrenLimitRatio) {
				adjustChildrenUp();
			}
		}// end else

		System.out.println("Leaving ProcessChildren.doProcess() method.");
		System.out.println("-------------------------\n");
		return surveysList;

	} // end doProcess()

	public void adjustChildrenDown() {

		/*
		 * While our percentage of married WITH kids is too high we randomly
		 * select married women with kids and take her children away.
		 * ^Let us know how that works out, huh?  ;-)
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
		} // end while loop

	} // end adjustChildrenDown()

	public void adjustChildrenUp() {
		
		/*
		 * While our percentage of married with NO kids is too high we randomly
		 * select a married women with NO kids and give her children
		 */
		while ((double) marriedFemalesWithNoChildrenList.size()
				/ marriedFemalesList.size() > marriedWithChildrenLimitRatio) {
			randomInteger = randomGenerator.nextInt(marriedFemalesWithNoChildrenList.size());

			Survey survey = marriedFemalesWithNoChildrenList.get(randomInteger);

			// Randomly assign 1 or 2 kids
			randomKids = randomGenerator.nextInt(2) + 1;

			survey.setChildren(randomKids);
			marriedFemalesWithNoChildrenList.remove(survey);

			// Make sure her spouse has the same number of children
			spouseSurvey = localControllerInstance.getSurvey("id", Integer.toString(survey.getSpouse()));
			spouseSurvey.setChildren(randomKids);
		} // end while loop
	}// end adjustChildrenUp()
	
	public List<Survey> currentSurveysList(Group group){
		
		List<Survey> surveysList;
		localControllerInstance.setGroup(group);
		localControllerInstance.setSQLselectWhereSurveysList(group);
		surveysList = localControllerInstance.getSurveysList();
		return surveysList;
	}

} // end class
