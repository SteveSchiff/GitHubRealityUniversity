package processingLibrary;

import java.util.List;

import obj.Group;
import obj.Survey;
import ctrl.Controller;

public class ProcessCreditScore {

	private Controller localControllerInstance = Controller.getControllerInstance();
	private Group group = localControllerInstance.getGroup();
	
	private List<Survey> surveysList = currentSurveysList(group);

	public List<Survey> doProcess() {
		
		System.out.println("Entering ProcessCreditScore.doProcess() method.");
		
		for (Survey survey : surveysList) {
			
				switch (survey.getGPA()) {
				case 1:
					if ( survey.getCreditCardUses() == 0 ) {
						survey.setCreditScore(550);
					}
					else {
						survey.setCreditScore(500);
					}
					break;
				case 2:
					if ( survey.getCreditCardUses() == 0 ) {
						survey.setCreditScore(600);
					}
					else {
						survey.setCreditScore(550);
					}
					break;
				case 3:
					if ( survey.getCreditCardUses() == 0 ) {
						survey.setCreditScore(625);
					}
					else {
						survey.setCreditScore(575);
					}
					break;
				case 4:
					if ( survey.getCreditCardUses() == 0 ) {
						survey.setCreditScore(650);
					}
					else {
						survey.setCreditScore(600);
					}
					break;
				case 5:
					if ( survey.getCreditCardUses() == 0 ) {
						survey.setCreditScore(675);
					}
					else {
						survey.setCreditScore(625);
					}
					break;
				case 6:
					if ( survey.getCreditCardUses() == 0 ) {
						survey.setCreditScore(700);
					}
					else {
						survey.setCreditScore(650);
					}
					break;
				} // end switch statement
				Controller.getControllerInstance().updateSQLSurvey(survey);
			
		} // end big for loop		
		
		System.out.println("Leaving ProcessCreditScore.doProcess() method.");
		System.out.println("-------------------------\n");
		return surveysList;
	} // end doProcess() method

	public List<Survey> currentSurveysList(Group group){
		
		List<Survey> survey;
		localControllerInstance.setGroup(group);
		localControllerInstance.setSQLselectWhereSurveysList(group);
		survey = localControllerInstance.getSurveysList();
		return survey;
	}

} // end ProcessCreditScore class
