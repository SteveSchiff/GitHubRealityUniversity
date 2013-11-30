package processingLibrary;

import java.util.List;

import obj.Survey;
import ctrl.Controller;

public class ProcessCreditScore {

	private List<Survey> lstSurveys = Controller.getControllerInstance()
			.getSurveysList();

	public List<Survey> doProcess() {
		System.out.println("Entering ProcessCreditScore.doProcess() method.");
		for (Survey survey : lstSurveys) {
			// for surveys that do not have credit cards
			if (survey.getCreditCards() == 0) {
				switch (survey.getGPA()) {
				case 1:
					survey.setCreditScore(550);
					break;
				case 2:
					survey.setCreditScore(600);
					break;
				case 3:
					survey.setCreditScore(625);
					break;
				case 4:
					survey.setCreditScore(650);
					break;
				case 5:
					survey.setCreditScore(675);
					break;
				case 6:
					survey.setCreditScore(700);
					break;
				} // end switch statement
				Controller.getControllerInstance().updateSQLSurvey(survey);
			} // outer if

			// TODO: the assumption in this logic is credit card use is
			// "emergency only"
			// TODO: if "emergency only" does not write a "0" to the database
			// TODO: survey.getCCardUses()==0 needs to be changed in the first
			// if statement
			// these comments will self destruct
			// for surveys that have credit cards for emergency only
			if ((survey.getCreditCards() == 1 && survey.getCreditCardUses() == 0)) {
				switch (survey.getGPA()) {
				case 0:
					survey.setCreditScore(550);
					break;
				case 1:
					survey.setCreditScore(550);
					break;
				case 2:
					survey.setCreditScore(600);
					break;
				case 3:
					survey.setCreditScore(625);
					break;
				case 4:
					survey.setCreditScore(650);
					break;
				case 5:
					survey.setCreditScore(675);
					break;
				case 6:
					survey.setCreditScore(700);
					break;
				} // end switch statement
				Controller.getControllerInstance().updateSQLSurvey(survey);
			} // end if

			// any other combinations get decreased credit scores
			else {
				switch (survey.getGPA()) {
				case 0:
					survey.setCreditScore(500);
					break;
				case 1:
					survey.setCreditScore(500);
					break;
				case 2:
					survey.setCreditScore(550);
					break;
				case 3:
					survey.setCreditScore(575);
					break;
				case 4:
					survey.setCreditScore(600);
					break;
				case 5:
					survey.setCreditScore(625);
					break;
				case 6:
					survey.setCreditScore(650);
					break;
				} // end switch statement
				Controller.getControllerInstance().updateSQLSurvey(survey);
			} // end else block
		} // end big for loop
		System.out.println("Leaving ProcessCreditScore.doProcess() method.");
		System.out.println("-------------------------\n");
		return lstSurveys;
	} // end doProcess() method
} // end ProcessCreditScore class
