package processingLibrary;

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
import obj.Job;
import obj.Survey;

public class ProcessCustodyChildSupport {

	/** The list of surveys. */
	private List<Survey> surveysList = Controller.getControllerInstance().getSurveysList();

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

		setChildSupportMale();
		setChildSupportFemale();

		return surveysList;

	} // end doProcess()

	public void setChildSupportMale() {

		// set 'i' to '1' for groups of 10 or more. If 'i' is not '0' no male in
		// a group under 10 will receive child support
		int i = 0;

		// While less than 10% of men receive child support
		while (i <= lstDivMalesWithChild.size() * .1) {

			randomMale = randomGenerator.nextInt(lstDivMalesWithChild.size());
			// Get a random male with children
			Survey survey = lstDivMalesWithChild.get(randomMale);
			// Get job info for survey
			Job jobInfo = Controller.getControllerInstance().getJob("id",
					Integer.toString(survey.getAssignedJob()));
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();
			// Assign child support based on income and number of children
			if (survey.getChildren() == 1) {
				survey.setChildSupport(.0116 * salary);
				lstDivMalesWithChild.remove(survey);
			}
			if (survey.getChildren() == 2) {
				survey.setChildSupport(.0174 * salary);
				lstDivMalesWithChild.remove(survey);
			}

			i++;
			System.out.println("child support: " + survey.getChildSupport());

		} // emd while

		// Set all other males to pay child support based on their income
		for (Survey survey2 : lstDivMalesWithChild) {

			Job jobInfo = Controller.getControllerInstance().getJob("id",
					Integer.toString(survey2.getAssignedJob()));
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();

			if (survey2.getChildSupport() == 0) {
				if (survey2.getChildren() == 1) {
					survey2.setChildSupport(-(.0116 * salary));
				}
				if (survey2.getChildren() == 2) {
					survey2.setChildSupport(-(.0174 * salary));
				}
			}
		}

	} // end setChildSupportMale()

	public void setChildSupportFemale() {

		float i = 0;
		int numFemales = lstDivFemalesWithChild.size();

		// While less than 75% of women receive child support
		while (i < numFemales * .75) {

			randomFemale = randomGenerator.nextInt(lstDivFemalesWithChild
					.size());
			// Get a random female with children
			Survey survey = lstDivFemalesWithChild.get(randomFemale);

			// Get job information from survey
			Job jobInfo = Controller.getControllerInstance().getJob("id",
					Integer.toString(survey.getAssignedJob()));
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();
			// Assign child support based on income and number of children
			if (survey.getChildren() == 1) {
				survey.setChildSupport(.0116 * salary);
				lstDivFemalesWithChild.remove(survey);
			}
			if (survey.getChildren() == 2) {
				survey.setChildSupport(.0174 * salary);
				lstDivFemalesWithChild.remove(survey);
			}
			i++;

			System.out.println("i = : " + i);
			System.out.println("child support/Female: "
					+ survey.getChildSupport());
		}

		// For remaining women, assign child support payment based on income
		for (Survey survey2 : lstDivFemalesWithChild) {

			Job jobInfo = Controller.getControllerInstance().getJob("id",
					Integer.toString(survey2.getAssignedJob()));
			// TODO: do we need to take tax out of this equation?
			double salary = jobInfo.getAnnGrossSal();

			if (survey2.getChildSupport() == 0) {
				if (survey2.getChildren() == 1) {
					survey2.setChildSupport(-(.0116 * salary));
					System.out.println("Came from neg.: "
							+ survey2.getChildSupport());
				}
				if (survey2.getChildren() == 2) {
					survey2.setChildSupport(-(.0174 * salary));
					System.out.println("Came from neg.: "
							+ survey2.getChildSupport());
				}

			}
		}

	} // end setChildSupportFemale()
} // end class

