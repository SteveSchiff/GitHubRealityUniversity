package gui.dialogs;

import gui.GuiInterface;
import gui.custom.RoundPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import lib.print.PrintAllUtilities;
import lib.print.PrintUtilities;
import obj.Group;
import obj.Job;
import obj.Survey;
import ctrl.Controller;

public class ViewSurvey extends JDialog implements GuiInterface {
	
	/*******************************
	 * Fields
	 * ***************************/
	private static ViewSurvey viewSurveyInstance;
	private Group group = Controller.getControllerInstance().getGroup();
	private Survey spouseSurvey;

	// Used to do calculations in Occupation/Income section */
	private double surveySalary;
	private double surveyMonthlyTaxes;
	private double surveyAnnualTaxes;
	private double surveyChildSupport;
	private double netIncome;
	private double spouseIncome;

	// Main panel
	private RoundPanel mainPanel = new RoundPanel();

	// the print panel
	private JFrame paperPrintFrame;

	// Five Information panels plus the button panel
	// at the bottom to hang the buttons on.
	private JPanel classInfoPanel = new JPanel();
	private JPanel studentInfoPanel = new JPanel();
	private JPanel famiyInfoPanel = new JPanel();
	private JPanel financialConsiderationsPanel = new JPanel();
	private JPanel occupationPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	// Class Information Labels
	private JLabel groupLabel = new JLabel();
	private JLabel groupInfoLabel = new JLabel();
	private JLabel teacherLabel = new JLabel();
	private JLabel teacherInfoLabel = new JLabel();
	private JLabel classPeriodLabel = new JLabel();
	private JLabel classPeriodInfoLabel = new JLabel();

	// Student Information Labels
	private JLabel fullNameLabel = new JLabel();
	private JLabel fullNameInfoLabel = new JLabel();
	private JLabel genderLabel = new JLabel();
	private JLabel genderInfoLabel = new JLabel();
	private JLabel gpaLabel = new JLabel();
	private JLabel gpaInfoLabel = new JLabel();
	private JLabel studentIDLabel = new JLabel();
	private JLabel studentIDInfoLabel = new JLabel();

	// Family Information Labels
	private JLabel maritalStatusLabel = new JLabel();
	private JLabel marriedInfoLabel = new JLabel();
	private JLabel spouseLabel = new JLabel();
	private JLabel spouseInfoLabel = new JLabel();
	private JLabel childrenLabel = new JLabel();
	private JLabel childrenInfoLabel = new JLabel();

	// Financial Considerations Labels
	private JLabel collegeLoansLabel = new JLabel();
	private JLabel collegeLoansInfoLabel = new JLabel();
	private JLabel creditScoreLabel = new JLabel();
	private JLabel creditScoreInfoLabel = new JLabel();
	private JLabel spousalIncomeLabel = new JLabel();
	private JLabel spousalIncomeInfoLabel = new JLabel();
	private JLabel childSupportLabel = new JLabel();
	private JLabel childSupportInfoLabel = new JLabel();

	// Occupation and Income Labels
	private JLabel occupationDescriptionLabel = new JLabel(); // 1st and 2nd row
	private JLabel emptySpaceLabel = new JLabel(); // 3rd row
	private JLabel occupationLabel = new JLabel();
	private JLabel occupationInfoLabel = new JLabel(); // 4th row
	private JLabel netIncomeLabel = new JLabel();
	private JLabel netIncomeInfoLabel = new JLabel(); // 5th row
	private JLabel checkbookEntryLabel = new JLabel();
	private JLabel checkbookEntryInfoLabel = new JLabel(); // 6th row
	private JLabel annualSalaryLabel = new JLabel();
	private JLabel annualSalaryInfoLabel = new JLabel(); // 4th row
	private JLabel annualTaxesLabel = new JLabel();
	private JLabel annualTaxesInfoLabel = new JLabel(); // 5th row
	private JLabel monthlySalaryLabel = new JLabel();
	private JLabel monthlySalaryInfoLabel = new JLabel(); // 6th row
	private JLabel monthlyTaxesLabel = new JLabel();
	private JLabel monthlyTaxesInfoLabel = new JLabel(); // 7th row

	// Buttons (for buttonPanel)
	private JButton closeButton;
	private JButton printButton;
	
	/************************
	 * End of fields list
	 ***********************/
	
	/************************
	 * Constructor - 1 of 2
	 ***********************/
	public ViewSurvey(Survey survey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("MAIN VIEWSURVEY CONSTRUCTOR WITH SURVEY PARAMETER");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all

		Job assignedJobInfo = Controller.getControllerInstance().getJob("id",
				Integer.toString(survey.getAssignedJob()));
		
		Job preferredJobInfo = Controller.getControllerInstance().getJob("id",
				Integer.toString(survey.getPreferredJob()));

		if (survey.getSpouse() > 0) {
			spouseSurvey = Controller.getControllerInstance().getSurvey("id",
					Integer.toString(survey.getSpouse()));
		}
		
		// for salary calculations
		/**
		 * Determines the value of the following
		 * class-wide fields:
		 * 
		 * surveySalary
		 * spouseIncome
		 * spousalIncomeInfoLabel
		 * annualTaxesInfoLabel
		 * monthlyTaxesInfoLabel
		 * surveyMonthlyTaxes
		 * surveyAnnualTaxes
		 * surveyChildSupport
		 * 
		 */
		processFinancialInformation(survey, assignedJobInfo);

		/*********************************
		 * Configurations
		 *********************************/
		setSize(580, 700);
		setResizable(true);

		/*********************************
		 * Configure Components
		 *********************************/
		setBackground(FRAME_BACKGROUND);

		/** Assemble Panel **/

		// Sub-panel borders
		Border blackBorder = BorderFactory.createLineBorder(Color.black, 1);

		//AORD Class Information Panel (1) - 3 sections
		// Class Information Panel (1) ************************************
		// ****************************************************************
		classInfoPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		classInfoPanel.setBorder(BorderFactory.createTitledBorder(blackBorder, "Class Information", 0, 0, BORDER_FONT, Color.black));
		classInfoPanel.setPreferredSize(new Dimension(560, 10));
		GridBagLayout classInfoPanelGridBagLayout = new GridBagLayout();
		classInfoPanelGridBagLayout.columnWidths = new int[] { 100, 0, 60, 0, 85, 0, 60, 0 };
		classInfoPanelGridBagLayout.rowHeights = new int[] { 0, 20, 0 };
		classInfoPanelGridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		classInfoPanelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		classInfoPanel.setLayout(classInfoPanelGridBagLayout);
		// Group Name - 1 ****************************************************
		groupLabel = new JLabel("RealityU Group: ");
		GridBagConstraints groupLabelConstraints = new GridBagConstraints();
		groupLabelConstraints.anchor = GridBagConstraints.WEST;
		groupLabelConstraints.insets = new Insets(0, 0, 10, 5);
		groupLabelConstraints.gridy = 0;
		groupLabelConstraints.gridx = 0;
		classInfoPanel.add(groupLabel, groupLabelConstraints);
		// ****************************************************************		
		groupInfoLabel = new JLabel(group.getName());		
		GridBagConstraints groupInfoLabelConstraints = new GridBagConstraints();
		groupInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		groupInfoLabelConstraints.insets = new Insets(0, 0, 10, 5);
		groupInfoLabelConstraints.gridy = 0;
		groupInfoLabelConstraints.gridx = 1;
		classInfoPanel.add(groupInfoLabel, groupInfoLabelConstraints);
		// Teacher Name - 2 ******************************************************		
		teacherLabel = new JLabel("Teacher: ");
		GridBagConstraints teacherLabelConstraints = new GridBagConstraints();
		teacherLabelConstraints.anchor = GridBagConstraints.WEST;
		teacherLabelConstraints.insets = new Insets(0, 0, 0, 5);
		teacherLabelConstraints.gridy = 1;
		teacherLabelConstraints.gridx = 0;
		classInfoPanel.add(teacherLabel, teacherLabelConstraints);
		// ****************************************************************		
		teacherInfoLabel = new JLabel(survey.getTeacher());
		GridBagConstraints teacherInfoLabelConstraints = new GridBagConstraints();
		teacherInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		teacherInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		teacherInfoLabelConstraints.gridy = 1;
		teacherInfoLabelConstraints.gridx = 1;
		classInfoPanel.add(teacherInfoLabel, teacherInfoLabelConstraints);
		// Class Period - 3 ********************************************************		
		classPeriodLabel = new JLabel("Class Period: ");
		GridBagConstraints classPeriodLabelConstraints = new GridBagConstraints();
		classPeriodLabelConstraints.anchor = GridBagConstraints.WEST;
		classPeriodLabelConstraints.insets = new Insets(0, 0, 0, 5);
		classPeriodLabelConstraints.gridy = 2;
		classPeriodLabelConstraints.gridx = 0;
		classInfoPanel.add(classPeriodLabel, classPeriodLabelConstraints);
		// ****************************************************************		
		classPeriodInfoLabel = new JLabel("" + survey.getCPeriod());
		GridBagConstraints classPeriodInfoLabelConstraints = new GridBagConstraints();
		classPeriodInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		classPeriodLabelConstraints.insets = new Insets(0, 0, 0, 5);
		classPeriodInfoLabelConstraints.gridy = 2;
		classPeriodInfoLabelConstraints.gridx = 1;
		classInfoPanel.add(classPeriodInfoLabel, classPeriodInfoLabelConstraints);
		// ****************************************************************			
		
		//AORD Student Information Panel (2) - 4 sections
		// Student Information Panel (2) ***********************************
		// *****************************************************************
		studentInfoPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		studentInfoPanel.setBorder(BorderFactory.createTitledBorder(blackBorder, "Student Information", 0, 0, BORDER_FONT, Color.black));
		studentInfoPanel.setPreferredSize(new Dimension(560, 10));
		GridBagLayout studentInfoGridBagLayout = new GridBagLayout();
		studentInfoGridBagLayout.columnWidths = new int[] { 73, 92, 6, 34, 120, 67, 52, 0}; // sum of all 8 is 344
		studentInfoGridBagLayout.rowHeights = new int[] { 20, 20, 0 };
		studentInfoGridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		studentInfoGridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		studentInfoPanel.setLayout(studentInfoGridBagLayout);		
		// Student name - 1 *******************************************************
		fullNameLabel = new JLabel("Name: ");
		GridBagConstraints fullNameLabelConstraints = new GridBagConstraints();
		fullNameLabelConstraints.anchor = GridBagConstraints.WEST;
		fullNameLabelConstraints.insets = new Insets(0, 0, 0, 5);
		fullNameLabelConstraints.gridy = 0;
		fullNameLabelConstraints.gridx = 0;
		studentInfoPanel.add(fullNameLabel, fullNameLabelConstraints);
		// *****************************************************************		
		fullNameInfoLabel = new JLabel(survey.getLName() + ", " + survey.getFName());
		GridBagConstraints fullNameLabelInfoConstraints = new GridBagConstraints();
		fullNameLabelInfoConstraints.anchor = GridBagConstraints.WEST;
		fullNameLabelInfoConstraints.insets = new Insets(0, 0, 0, 5);
		fullNameLabelInfoConstraints.gridy = 0;
		fullNameLabelInfoConstraints.gridx = 1;
		studentInfoPanel.add(fullNameInfoLabel, fullNameLabelInfoConstraints);		
		// Gender - 2 *******************************************************
		genderLabel = new JLabel("Gender: ");
		GridBagConstraints genderLabelConstraints = new GridBagConstraints();
		genderLabelConstraints.anchor = GridBagConstraints.WEST;
		genderLabelConstraints.insets = new Insets(0, 0, 0, 5);
		genderLabelConstraints.gridy = 1;
		genderLabelConstraints.gridx = 0;
		studentInfoPanel.add(genderLabel, genderLabelConstraints);
		// *****************************************************************		
		genderInfoLabel = new JLabel(determineGender(survey));
		GridBagConstraints genderLabelInfoConstraints = new GridBagConstraints();
		genderLabelInfoConstraints.anchor = GridBagConstraints.WEST;
		genderLabelInfoConstraints.insets = new Insets(0, 0, 0, 5);
		genderLabelInfoConstraints.gridy = 1;
		genderLabelInfoConstraints.gridx = 1;
		studentInfoPanel.add(genderInfoLabel, genderLabelInfoConstraints);	
		// Current GPA - 3 ***********************************************************
		gpaLabel = new JLabel("Current GPA: ");
		GridBagConstraints gpaLabelConstraints = new GridBagConstraints();
		gpaLabelConstraints.anchor = GridBagConstraints.WEST;
		gpaLabelConstraints.insets = new Insets(0, 0, 0, 5);
		gpaLabelConstraints.gridy = 2;
		gpaLabelConstraints.gridx = 0;
		studentInfoPanel.add(gpaLabel, gpaLabelConstraints);
		// *****************************************************************		
		gpaInfoLabel = new JLabel("" + ARR_GPA[survey.getGPA()]);
		GridBagConstraints gpaLabelInfoConstraints = new GridBagConstraints();
		gpaLabelInfoConstraints.insets = new Insets(0, 0, 0, 5);
		gpaLabelInfoConstraints.anchor = GridBagConstraints.WEST;
		gpaLabelInfoConstraints.gridy = 2;
		gpaLabelInfoConstraints.gridx = 1;
		studentInfoPanel.add(gpaInfoLabel, gpaLabelInfoConstraints);
		// Student Identifier - 4 **********************************************
		studentIDLabel = new JLabel("Student Identifier: ");
		GridBagConstraints studentIDLabelConstraints = new GridBagConstraints();
		studentIDLabelConstraints.anchor = GridBagConstraints.WEST;
		studentIDLabelConstraints.insets = new Insets(0, 0, 0, 5);
		studentIDLabelConstraints.gridy = 3;
		studentIDLabelConstraints.gridx = 0;
		studentInfoPanel.add(studentIDLabel, studentIDLabelConstraints);
		// *****************************************************************
		studentIDInfoLabel = new JLabel(Integer.toString(survey.getID()));
		GridBagConstraints studentIDLabelInfoConstraints = new GridBagConstraints();
		studentIDLabelInfoConstraints.anchor = GridBagConstraints.WEST;
		studentIDLabelInfoConstraints.insets = new Insets(0, 0, 0, 5);
		studentIDLabelInfoConstraints.gridy = 3;
		studentIDLabelInfoConstraints.gridx = 1;
		studentInfoPanel.add(studentIDInfoLabel, studentIDLabelInfoConstraints);
		// ************************************************

		//AORD Family Information Panel (3) - 3 sections
		// Family Information panel *****************************************
		// ******************************************************************
		famiyInfoPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		famiyInfoPanel.setBorder(BorderFactory.createTitledBorder(blackBorder, "Family Information", 0, 0, BORDER_FONT, Color.black));
		famiyInfoPanel.setPreferredSize(new Dimension(560, 10));
		GridBagLayout familyInfoPanelGridBagLayout = new GridBagLayout();
		familyInfoPanelGridBagLayout.columnWidths = new int[] { 100, 0, 60, 20, 60, 0, 60, 0 };
		familyInfoPanelGridBagLayout.rowHeights = new int[] { 20, 20, 0 };
		familyInfoPanelGridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		familyInfoPanelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		famiyInfoPanel.setLayout(familyInfoPanelGridBagLayout);
		// Marital Status - 1 *****************************************************
		maritalStatusLabel = new JLabel("MaritalStatus: ");
		GridBagConstraints maritalStatusLabelConstraints = new GridBagConstraints();
		maritalStatusLabelConstraints.anchor = GridBagConstraints.WEST;
		maritalStatusLabelConstraints.insets = new Insets(0, 0, 10, 5);
		maritalStatusLabelConstraints.gridy = 0;
		maritalStatusLabelConstraints.gridx = 0;
		famiyInfoPanel.add(maritalStatusLabel, maritalStatusLabelConstraints);
		// *******************************************************************
		if (survey.getMarried() == 0) {	marriedInfoLabel = new JLabel("Single");}
		else if (survey.getMarried() == 1) {marriedInfoLabel = new JLabel("Married");}
		else {marriedInfoLabel = new JLabel("Divorced");}
		GridBagConstraints maritalStatusInfoLabelConstraints = new GridBagConstraints();
		maritalStatusInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		maritalStatusInfoLabelConstraints.insets = new Insets(0, 0, 10, 5);
		maritalStatusInfoLabelConstraints.gridy = 0;
		maritalStatusInfoLabelConstraints.gridx = 1;
		famiyInfoPanel.add(marriedInfoLabel, maritalStatusInfoLabelConstraints);
		// Spouse - 2 ***************************************************************
		spouseLabel = new JLabel("Spouse: ");
		GridBagConstraints spouseLabelConstraints = new GridBagConstraints();
		spouseLabelConstraints.anchor = GridBagConstraints.WEST;
		spouseLabelConstraints.insets = new Insets(0, 0, 5, 5);
		spouseLabelConstraints.gridy = 1;
		spouseLabelConstraints.gridx = 0;
		famiyInfoPanel.add(spouseLabel, spouseLabelConstraints);
		// *************************************************************************
		if (survey.getSpouse() > 0) {
			Survey spouse = Controller.getControllerInstance().getSurvey("id", Integer.toString(survey.getSpouse()));
			spouseInfoLabel = new JLabel(spouse.getFName() + " " + spouse.getLName());
		} else {spouseInfoLabel = new JLabel("Unassigned");}
		GridBagConstraints spouseInfoLabelConstraints = new GridBagConstraints();
		spouseInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		spouseInfoLabelConstraints.insets = new Insets(0, 0, 5, 5);
		spouseInfoLabelConstraints.gridy = 1;
		spouseInfoLabelConstraints.gridx = 1;
		famiyInfoPanel.add(spouseInfoLabel, spouseInfoLabelConstraints);
		// Number of Children - 3 *******************************************************
		childrenLabel = new JLabel("No. of Children: ");
		GridBagConstraints childrenLabelConstraints = new GridBagConstraints();
		childrenLabelConstraints.anchor = GridBagConstraints.WEST;
		childrenLabelConstraints.insets = new Insets(0, 0, 0, 5);
		childrenLabelConstraints.gridy = 2;
		childrenLabelConstraints.gridx = 0;
		famiyInfoPanel.add(childrenLabel, childrenLabelConstraints);
		// *************************************************************************
		childrenInfoLabel = new JLabel(Integer.toString(survey.getChildren()));
		GridBagConstraints childrenInfoLabelConstraints = new GridBagConstraints();
		childrenInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		childrenInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		childrenInfoLabelConstraints.gridy = 2;
		childrenInfoLabelConstraints.gridx = 1;
		famiyInfoPanel.add(childrenInfoLabel, childrenInfoLabelConstraints);
		// ****************************************************************
		
		//AORD Financial Considerations Panel (4) - 4 sections
		// Financial Considerations panel ***********************************
		// ******************************************************************
		financialConsiderationsPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		financialConsiderationsPanel.setBorder(BorderFactory.createTitledBorder(blackBorder, "Financial Considerations", 0, 0, BORDER_FONT, Color.black));
		financialConsiderationsPanel.setPreferredSize(new Dimension(560, 10));
		GridBagLayout financialConsiderationsPanelGridBagLayout = new GridBagLayout();
		financialConsiderationsPanelGridBagLayout.columnWidths = new int[] { 73, 92, 6, 34, 120, 67, 52, 0}; //  original -> { 100, 0, 60, 20, 100, 0, 60, 0 }
		financialConsiderationsPanelGridBagLayout.rowHeights = new int[] { 20, 0 };
		financialConsiderationsPanelGridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		financialConsiderationsPanelGridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		financialConsiderationsPanel.setLayout(financialConsiderationsPanelGridBagLayout);
		// College Loan - 1 ***********************************************************
		collegeLoansLabel = new JLabel("College Loans: ");
		GridBagConstraints collegeLoansLabelConstraints = new GridBagConstraints();
		collegeLoansLabelConstraints.anchor = GridBagConstraints.WEST;
		collegeLoansLabelConstraints.insets = new Insets(0, 0, 0, 5);
		collegeLoansLabelConstraints.gridy = 0;
		collegeLoansLabelConstraints.gridx = 0;
		financialConsiderationsPanel.add(collegeLoansLabel, collegeLoansLabelConstraints);
		// *************************************************************************		
		collegeLoansInfoLabel = new JLabel("$ " + Double.toString(assignedJobInfo.getLoan()));
		GridBagConstraints collegeLoansInfoLabelConstraints = new GridBagConstraints();
		collegeLoansInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		collegeLoansInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		collegeLoansInfoLabelConstraints.gridy = 0;
		collegeLoansInfoLabelConstraints.gridx = 1;
		financialConsiderationsPanel.add(collegeLoansInfoLabel, collegeLoansInfoLabelConstraints);
		// Credit Score - 2 *************************************************************************		
		creditScoreLabel = new JLabel("Credit Score: ");
		GridBagConstraints creditScoreLabelConstraints = new GridBagConstraints();
		creditScoreLabelConstraints.anchor = GridBagConstraints.WEST;
		creditScoreLabelConstraints.insets = new Insets(0, 0, 0, 5);
		creditScoreLabelConstraints.gridy = 1;
		creditScoreLabelConstraints.gridx = 0;
		financialConsiderationsPanel.add(creditScoreLabel, creditScoreLabelConstraints);
		// *************************************************************************		
		creditScoreInfoLabel = new JLabel(Integer.toString(survey.getCreditScore()));
		GridBagConstraints creditScoreInfoLabelConstraints = new GridBagConstraints();
		creditScoreInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		creditScoreLabelConstraints.insets = new Insets(0, 0, 0, 5);
		creditScoreInfoLabelConstraints.gridy = 1;
		creditScoreInfoLabelConstraints.gridx = 1;
		financialConsiderationsPanel.add(creditScoreInfoLabel, creditScoreInfoLabelConstraints);
		// Spouse's Income - 3 ******************************************************************
		spousalIncomeLabel = new JLabel("Spousal Income: ");
		GridBagConstraints spousalIncomeLabelConstraints = new GridBagConstraints();
		spousalIncomeLabelConstraints.anchor = GridBagConstraints.WEST;
		spousalIncomeLabelConstraints.insets = new Insets(0, 0, 0, 5);
		spousalIncomeLabelConstraints.gridy = 2;
		spousalIncomeLabelConstraints.gridx = 0;
		financialConsiderationsPanel.add(spousalIncomeLabel, spousalIncomeLabelConstraints);
		// **********************************************************************
		spousalIncomeInfoLabel = new JLabel("N/A");
		GridBagConstraints spousalIncomeInfoLabelConstraints = new GridBagConstraints();
		spousalIncomeInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		spousalIncomeInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		spousalIncomeInfoLabelConstraints.gridy = 2;
		spousalIncomeInfoLabelConstraints.gridx = 1;
		financialConsiderationsPanel.add(spousalIncomeInfoLabel, spousalIncomeInfoLabelConstraints);
		// Child Support - 4 **********************************************************************
		childSupportLabel = new JLabel("Child Support: ");
		GridBagConstraints childSupportLabelConstraints = new GridBagConstraints();
		childSupportLabelConstraints.anchor = GridBagConstraints.WEST;
		childSupportLabelConstraints.insets = new Insets(0, 0, 0, 5);
		childSupportLabelConstraints.gridy = 3;
		childSupportLabelConstraints.gridx = 0;
		financialConsiderationsPanel.add(childSupportLabel, childSupportLabelConstraints);
		// **********************************************************************		
		childSupportInfoLabel = new JLabel("$ " + Double.toString(survey.getChildSupport()));
		GridBagConstraints childSupportInfoLabelConstraints = new GridBagConstraints();
		childSupportInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		childSupportInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		childSupportInfoLabelConstraints.gridy = 3;
		childSupportInfoLabelConstraints.gridx = 1;
		financialConsiderationsPanel.add(childSupportInfoLabel, childSupportInfoLabelConstraints);
		// **********************************************************************
		
		//AORD Occupation and Income Panel (5) - 7 sections
		// Occupation and Income panel ***************************************
		// *******************************************************************
		occupationPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		occupationPanel.setBorder(BorderFactory.createTitledBorder(blackBorder,	"Occupation and Income", 0, 0, BORDER_FONT, Color.black));
		occupationPanel.setPreferredSize(new Dimension(560, 100));
		GridBagLayout gbl_pnlOccupation = new GridBagLayout();
		gbl_pnlOccupation.columnWidths = new int[] { 160, 100, 30, 100, 100, 0, 0, 0 }; // 
		gbl_pnlOccupation.rowHeights = new int[] { 2, 0, 0, 0, 0, 0, 14, 0 };
		gbl_pnlOccupation.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlOccupation.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		occupationPanel.setLayout(gbl_pnlOccupation);
		// Occupation Description - 1 *******************************************
		occupationDescriptionLabel = new JLabel("<html>In your student survey you selected " + preferredJobInfo.getName()
						+ " as your occupation. <br /> Based on your current GPA your job was assigned as "
						+ assignedJobInfo.getName() + "</html>");
		GridBagConstraints occupationDescriptionPanelConstraints = new GridBagConstraints();
		occupationDescriptionPanelConstraints.anchor = GridBagConstraints.WEST;
		occupationDescriptionPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		occupationDescriptionPanelConstraints.insets = new Insets(0, 35, 0, 5);
		occupationDescriptionPanelConstraints.gridy = 0;
		occupationDescriptionPanelConstraints.gridx = 0;
		occupationDescriptionPanelConstraints.gridheight = 2;
		occupationDescriptionPanelConstraints.gridwidth = GridBagConstraints.REMAINDER;
		occupationPanel.add(occupationDescriptionLabel, occupationDescriptionPanelConstraints);
		// Empty Space Buffer Row ************************************************
		emptySpaceLabel = new JLabel("   ");
		GridBagConstraints emptySpaceLabelConstraints = new GridBagConstraints();
		emptySpaceLabelConstraints.anchor = GridBagConstraints.WEST;
		emptySpaceLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
		emptySpaceLabelConstraints.insets = new Insets(0, 0, 0, 5);
		emptySpaceLabelConstraints.gridy = 2;
		emptySpaceLabelConstraints.gridx = 0;
		emptySpaceLabelConstraints.gridwidth = GridBagConstraints.REMAINDER;
		occupationPanel.add(emptySpaceLabel, emptySpaceLabelConstraints);
		// Occupation - 2 ********************************************************
		occupationLabel = new JLabel("Occupation: ");
		GridBagConstraints occupationLabelConstraints = new GridBagConstraints();
		occupationLabelConstraints.anchor = GridBagConstraints.EAST;
		occupationLabelConstraints.insets = new Insets(0, 0, 0, 5);
		occupationLabelConstraints.gridy = 3;
		occupationLabelConstraints.gridx = 0;
		occupationPanel.add(occupationLabel, occupationLabelConstraints);
		// *******************************************************************
		occupationInfoLabel = new JLabel(assignedJobInfo.getName());
		GridBagConstraints occupationInfoLabelConstraints = new GridBagConstraints();
		occupationInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		occupationInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		occupationInfoLabelConstraints.gridy = 3;
		occupationInfoLabelConstraints.gridx = 1;
		occupationPanel.add(occupationInfoLabel, occupationInfoLabelConstraints);
		// Net Income - 3 *************************************************************		
		netIncomeLabel = new JLabel("Net Monthly Income: ");
		GridBagConstraints netIncomeLabelConstraints = new GridBagConstraints();
		netIncomeLabelConstraints.anchor = GridBagConstraints.EAST;
		netIncomeLabelConstraints.insets = new Insets(0, 0, 0, 5);
		netIncomeLabelConstraints.gridy = 5;
		netIncomeLabelConstraints.gridx = 0;
		occupationPanel.add(netIncomeLabel, netIncomeLabelConstraints);
		// **********************************************************************
		netIncomeInfoLabel = new JLabel("$ " + Double.toString(surveySalary - surveyMonthlyTaxes + surveyChildSupport));
		GridBagConstraints netIncomeInfoLabelConstraints = new GridBagConstraints();
		netIncomeInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		netIncomeInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		netIncomeInfoLabelConstraints.gridy = 5;
		netIncomeInfoLabelConstraints.gridx = 1;
		occupationPanel.add(netIncomeInfoLabel, netIncomeInfoLabelConstraints);
		// Checkbook Entry - 4 **********************************************************************
		checkbookEntryLabel = new JLabel("Checkbook Entry: ");
		GridBagConstraints checkbookEntryLabelConstraints = new GridBagConstraints();
		checkbookEntryLabelConstraints.anchor = GridBagConstraints.EAST;
		checkbookEntryLabelConstraints.insets = new Insets(0, 0, 0, 5);
		checkbookEntryLabelConstraints.gridy = 6;
		checkbookEntryLabelConstraints.gridx = 0;
		occupationPanel.add(checkbookEntryLabel, checkbookEntryLabelConstraints);
		// **********************************************************************
		checkbookEntryInfoLabel = new JLabel("$ "+ Double.toString(netIncome + spouseIncome));
		GridBagConstraints checkbookEntryInfoLabelConstraints = new GridBagConstraints();
		checkbookEntryInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		checkbookEntryLabelConstraints.insets = new Insets(0, 0, 0, 5);
		checkbookEntryInfoLabelConstraints.gridy = 6;
		checkbookEntryInfoLabelConstraints.gridx = 1;
		occupationPanel.add(checkbookEntryInfoLabel, checkbookEntryInfoLabelConstraints);
		
		// Annual Salary - 5 *********************************************************
		annualSalaryLabel = new JLabel("Annual Salary: ");
		GridBagConstraints annualSalaryLabelConstraints = new GridBagConstraints();
		annualSalaryLabelConstraints.anchor = GridBagConstraints.WEST;
		annualSalaryLabelConstraints.insets = new Insets(0, 0, 0, 5);
		annualSalaryLabelConstraints.gridy = 3;
		annualSalaryLabelConstraints.gridx = 3;
		occupationPanel.add(annualSalaryLabel, annualSalaryLabelConstraints);
		// *******************************************************************
		annualSalaryInfoLabel = new JLabel(FMT_CURRENCY.format(assignedJobInfo.getAnnGrossSal()));
		GridBagConstraints annualSalaryInfoLabelConstraints = new GridBagConstraints();
		annualSalaryInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		annualSalaryInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		annualSalaryInfoLabelConstraints.gridy = 3;
		annualSalaryInfoLabelConstraints.gridx = 4;
		occupationPanel.add(annualSalaryInfoLabel, annualSalaryInfoLabelConstraints);
		// Annual Taxes - 6 *************************************************************
		annualTaxesLabel = new JLabel("Annual Taxes : ");
		GridBagConstraints annualTaxesLabelConstraints = new GridBagConstraints();
		annualTaxesLabelConstraints.anchor = GridBagConstraints.WEST;
		annualTaxesLabelConstraints.insets = new Insets(0, 0, 0, 5);
		annualTaxesLabelConstraints.gridy = 4;
		annualTaxesLabelConstraints.gridx = 3;
		occupationPanel.add(annualTaxesLabel, annualTaxesLabelConstraints);
		// *******************************************************************
		annualTaxesInfoLabel = new JLabel(FMT_CURRENCY.format(surveyAnnualTaxes));
		GridBagConstraints annualTaxesInfoLabelConstraints = new GridBagConstraints();
		annualTaxesInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		annualTaxesInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		annualTaxesInfoLabelConstraints.gridy = 4;
		annualTaxesInfoLabelConstraints.gridx = 4;
		occupationPanel.add(annualTaxesInfoLabel, annualTaxesInfoLabelConstraints);
		// Monthly Salary - 7 *******************************************************************
		monthlySalaryLabel = new JLabel("Monthly Salary: ");
		GridBagConstraints monthlySalaryLabelConstraints = new GridBagConstraints();
		monthlySalaryLabelConstraints.anchor = GridBagConstraints.WEST;
		monthlySalaryLabelConstraints.insets = new Insets(0, 0, 0, 5);
		monthlySalaryLabelConstraints.gridy = 5;
		monthlySalaryLabelConstraints.gridx = 3;
		occupationPanel.add(monthlySalaryLabel, monthlySalaryLabelConstraints);
		// *******************************************************************
		monthlySalaryInfoLabel = new JLabel("$ " + Double.toString(assignedJobInfo.getMonGrossSal()));
		GridBagConstraints monthlySalaryInfoLabelConstraints = new GridBagConstraints();
		monthlySalaryInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		monthlySalaryInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		monthlySalaryInfoLabelConstraints.gridy = 5;
		monthlySalaryInfoLabelConstraints.gridx = 4;
		occupationPanel.add(monthlySalaryInfoLabel, monthlySalaryInfoLabelConstraints);
		// Monthly Taxes - 8 ****************************************************************
		monthlyTaxesLabel = new JLabel("Monthly Taxes: ");
		GridBagConstraints monthlyTaxesLabelConstraints = new GridBagConstraints();
		monthlyTaxesLabelConstraints.anchor = GridBagConstraints.WEST;
		monthlyTaxesLabelConstraints.insets = new Insets(0, 0, 0, 5);
		monthlyTaxesLabelConstraints.gridy = 6;
		monthlyTaxesLabelConstraints.gridx = 3;
		occupationPanel.add(monthlyTaxesLabel, monthlyTaxesLabelConstraints);
		// *************************************************************************
		monthlyTaxesInfoLabel = new JLabel(FMT_CURRENCY.format(surveyMonthlyTaxes));
		GridBagConstraints monthlyTaxesInfoLabelConstraints = new GridBagConstraints();
		monthlyTaxesInfoLabelConstraints.anchor = GridBagConstraints.WEST;
		monthlyTaxesInfoLabelConstraints.insets = new Insets(0, 0, 0, 5);
		monthlyTaxesInfoLabelConstraints.gridy = 6;
		monthlyTaxesInfoLabelConstraints.gridx = 4;
		occupationPanel.add(monthlyTaxesInfoLabel, monthlyTaxesInfoLabelConstraints);
		// ********************************************************************** 

		// Bottom/button panel
		// *******************************************************

		// Buttons 
		printButton = new JButton("Print");
		closeButton = new JButton("Close");

		buttonPanel = new JPanel();
		buttonPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		buttonPanel.add(printButton);
		buttonPanel.add(closeButton);

		// add functionality to print button
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println(methodName);
				// end of debugging statement set - 4 lines in all
			    
				if (ae.getSource() == printButton)
				paperFrameSetUp();
			}
		});

		// add functionality to close button
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println(methodName);
				// end of debugging statement set - 4 lines in all
			    
				if (ae.getSource() == closeButton)
					dispose();
			}// end listener
		});
		//***********************************************************************
		
		//************************************************************************
		// Assemble sub panels inside mainPanel, mainPanel inside our JDialog main
		getContentPane().add(mainPanel);
		mainPanel.setBackground(FRAME_BACKGROUND);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(classInfoPanel);
		mainPanel.add(studentInfoPanel);
		mainPanel.add(famiyInfoPanel);
		mainPanel.add(financialConsiderationsPanel);
		mainPanel.add(occupationPanel); 
		mainPanel.add(buttonPanel);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end first Constructor
	// end ViewSurvey constructor
	
	
	/************************
	 * Constructor - 2 of 2
	 ***********************/	
	public ViewSurvey(){		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("SECOND VIEWSURVEY CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		new ViewSurvey(null);    
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end second constructor
	
	/**
	 * Gets the single instance of Controller.
	 * 
	 * @return single instance of Controller
	 */
	public static ViewSurvey getViewSurveyInstance(Survey survey) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all

		// If we do not yet have an instance of this class
		// create a new one. Otherwise, return the instance.
		if (viewSurveyInstance == null) {
			viewSurveyInstance = new ViewSurvey(survey);
		}
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		return viewSurveyInstance;
	} // -- end getViewSurveyInstance() method
	

	public void paperPrintOneSurvey(Survey survey) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		// Calls print on our new frame
		JFrame localPaperFrame = paperFrameSetUp();
		PrintUtilities.printComponent(localPaperFrame);

		// get rid of our new frame
		localPaperFrame.dispose();
		this.dispose();
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end paperPrintSurvey() wrapper method
	
	// this method prints an on-screen preview of the survey as it would appear
	// on paper.
	public JFrame paperFrameSetUp() {	
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		/*
		 * Makes a new frame, adds all the view survey components and sets their
		 * backgrounds to white
		 */
		paperPrintFrame = new JFrame();
		paperPrintFrame.setBackground(Color.white);
		paperPrintFrame.setVisible(true);
		paperPrintFrame.setSize(580, 700);

		classInfoPanel.setBackground(Color.white); // set this to help id gui part
		studentInfoPanel.setBackground(Color.white);
		famiyInfoPanel.setBackground(Color.white);
		occupationPanel.setBackground(Color.white);
		financialConsiderationsPanel.setBackground(Color.white);
		mainPanel.setBackground(Color.white);
		mainPanel.remove(buttonPanel);
		paperPrintFrame.add(mainPanel);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	    
	    return paperPrintFrame;
	} // -- end paperFrameSetUp() method

	/**
	 * @param survey
	 * @param assignedJobInfo
	 * 
	 * surveySalary
	 * spousalIncomeInfoLabel
	 * spouseIncome
	 * annualTaxesInfoLabel
	 * monthlyTaxesInfoLabel
	 * surveyMonthlyTaxes
	 * surveyAnnualTaxes
	 * surveyChildSupport
	 * 
	 */
	private void processFinancialInformation(Survey survey, Job assignedJobInfo) {
		
		surveySalary = assignedJobInfo.getMonGrossSal();
		
		try {
			Job spouseJob = Controller.getControllerInstance().getJob("id",	Integer.toString(spouseSurvey.getAssignedJob()));
			survey.getSpouse();
			spousalIncomeInfoLabel.setText("$" + Double.toString(spouseJob.getMarAfterTax()));
			spouseIncome = spouseJob.getMarAfterTax();
		} catch (NullPointerException npe) {
			// Nothing to do
		}
		
		if (survey.getMarried() == 1) {
			annualTaxesInfoLabel.setText("$ " + Double.toString(assignedJobInfo.getSinAnnualTax()));
			surveyAnnualTaxes = assignedJobInfo.getSinAnnualTax();
		} else {
			annualTaxesInfoLabel.setText("$ " + Double.toString(assignedJobInfo.getMarAnnualTax()));
			surveyAnnualTaxes = assignedJobInfo.getMarAnnualTax();
		}
		if (survey.getMarried() == 1) {
			monthlyTaxesInfoLabel.setText("$ " + Double.toString(assignedJobInfo.getSinMonthlyTax()));
			surveyMonthlyTaxes = assignedJobInfo.getSinMonthlyTax();
		} else {
			monthlyTaxesInfoLabel.setText("$ " + Double.toString(assignedJobInfo.getMarMonthlyTax()));
			surveyMonthlyTaxes = assignedJobInfo.getMarMonthlyTax();
		}
		// for income calculation
		surveyChildSupport = survey.getChildSupport();
		// calculate net income for survey
		netIncome = surveySalary - surveyMonthlyTaxes + surveyChildSupport;
	} // end of processFinancialInformation() method
	
	private String determineGender(Survey survey){
		String gender = "";
		if (survey.getGender() == 0) {
			gender = "Male";
		} else {
			gender = "Female";
		}
		return gender;
	}


	
	public static void paperPrintSurveyList(List<Survey> listOfSurveys) {
		// TODO Auto-generated method stub
		JFrame[] localPrintFrames = new JFrame[listOfSurveys.size()];
		ViewSurvey[] viewSurveyInstances = null;
		
		for (int i = 0; i < listOfSurveys.size(); i++) {
			
			System.out.println(listOfSurveys.get(i));
			viewSurveyInstances[i] = ViewSurvey.getViewSurveyInstance(listOfSurveys.get(i));
//			localPrintFrames[i] = viewSurveyInstances[i].paperFrameSetUp();
		} // end for loop
		
//		PrintAllUtilities.printComponent(localPrintFrames);
	}
	
} // end class

