package processingLibrary;

import java.util.List;

import obj.Survey;
import ctrl.Controller;

public class ProcessCreditScore {

	private List<Survey> lstSurveys = Controller.getControllerInstance()
			.getSurveysList();

	public List<Survey> doProcess() {

		System.out.println(methodName);

		for (Survey survey : lstSurveys) {
			// for surveys that do not have credit cards
			if (survey.getCreditCards() == 0) {
				// set credit scores as follows based on gpa
				if (survey.getGPA() == 1) {
					survey.setCreditScore(550);
				}
				if (survey.getGPA() == 2) {
					survey.setCreditScore(600);
				}
				if (survey.getGPA() == 3) {
					survey.setCreditScore(625);
				}
				if (survey.getGPA() == 4) {
					survey.setCreditScore(650);
				}
				if (survey.getGPA() == 5) {
					survey.setCreditScore(675);
				}
				if (survey.getGPA() == 6) {
					survey.setCreditScore(700);
				}
			}

			// TODO: the assumption in this logic is credit card use is
			// "emergency only"
			// TODO: if "emergency only" does not write a "0" to the database
			// TODO: survey.getCCardUses()==0 needs to be changed in the first
			// if statement
			// these comments will self destruct
			// for surveys that have credit cards for emergency only
			if (survey.getCreditCards() == 1 && survey.getCreditCardUses() == 0) {
				// set credit scores as follows based on gpa
				if (survey.getGPA() == 0) {
					survey.setCreditScore(550);
				}
				if (survey.getGPA() == 1) {
					survey.setCreditScore(550);
				}
				if (survey.getGPA() == 2) {
					survey.setCreditScore(600);
				}
				if (survey.getGPA() == 3) {
					survey.setCreditScore(625);
				}
				if (survey.getGPA() == 4) {
					survey.setCreditScore(650);
				}
				if (survey.getGPA() == 5) {
					survey.setCreditScore(675);
				}
				if (survey.getGPA() == 6) {
					survey.setCreditScore(700);
				}
			}

			// any other combinations get decreased credit scores
			else {
				// set credit scores as follows based on gpa
				if (survey.getGPA() == 0) {
					survey.setCreditScore(500);
				}
				if (survey.getGPA() == 1) {
					survey.setCreditScore(500);
				}
				if (survey.getGPA() == 2) {
					survey.setCreditScore(550);
				}
				if (survey.getGPA() == 3) {
					survey.setCreditScore(575);
				}
				if (survey.getGPA() == 4) {
					survey.setCreditScore(600);
				}
				if (survey.getGPA() == 5) {
					survey.setCreditScore(625);
				}
				if (survey.getGPA() == 6) {
					survey.setCreditScore(650);
				}

				System.out.println(lstSurveys);

			}
		}
		return lstSurveys;
	}

}
