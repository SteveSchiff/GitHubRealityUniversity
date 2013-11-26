package gui;

import gui.custom.StatusTip;
import gui.custom.RoundPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import obj.Group;
import obj.Survey;

import org.netbeans.validation.api.builtin.stringvalidation.StringValidators;
import org.netbeans.validation.api.ui.swing.SwingValidationGroup;
import org.netbeans.validation.api.ui.swing.ValidationPanel;

import ctrl.Controller;

/**
 * The Class NewSurvey accepts user input into a survey so that it can be
 * inserted into the database.
 */
public class NewSurveyPanel extends JPanel implements GuiInterface {

	private Group group;
	private ValidationPanel surveyValidationPanel = new ValidationPanel();

	private Font newSurveyFont = new Font("sansserif", Font.PLAIN, 12);

	// Text Fields
	private JTextField firstNameTextField = new JTextField(11);
	private JTextField lastNameTextField = new JTextField(11);

	// Combo Boxes
	private List<String> jobCategoriesList = Controller.getControllerInstance()
			.getJobCategoriesList();
	private List<String> listOfJobsInCategory = Controller
			.getControllerInstance().getJobsByCategoryList(
					jobCategoriesList.get(0));

	private JComboBox periodComboBox = new JComboBox(ARR_PERIOD);
	private JComboBox educationComboBox = new JComboBox(ARR_EDUCATION);
	private JComboBox GPAComboBox = new JComboBox(ARR_GPA);
	private JComboBox childrenCountComboBox = new JComboBox(ARR_CHILDREN_COUNT);
	private JComboBox creditCardUsesComboBox = new JComboBox(ARR_CCARD_USES);
	private JComboBox teacherComboBox = new JComboBox(new DefaultComboBoxModel(
			Controller.getControllerInstance().getTeacherNamesList().toArray()));
	private JComboBox preferredJobCategoryComboBox = new JComboBox(
			new DefaultComboBoxModel(jobCategoriesList.toArray()));
	private JComboBox preferredJobComboBox = new JComboBox(
			new DefaultComboBoxModel(listOfJobsInCategory.toArray()));

	// Radio Buttons
	private JRadioButton maleGenderRadioButton = new JRadioButton("Male");
	private JRadioButton femaleGenderRadioButton = new JRadioButton("Female");
	private JRadioButton yesMarriedRadioButton = new JRadioButton("Yes");
	private JRadioButton noMarriedRadioButton = new JRadioButton("No");
	private JRadioButton yesChildrenRadioButton = new JRadioButton("Yes");
	private JRadioButton noChildrenRadioButton = new JRadioButton("No");
	private JRadioButton yesCreditCardsRadioButton = new JRadioButton("Yes");
	private JRadioButton noCreditCardsRadioButton = new JRadioButton("No");

	// Radio Button Groups
	private ButtonGroup genderButtonGroup = new ButtonGroup();
	private ButtonGroup marriedButtonGroup = new ButtonGroup();
	private ButtonGroup childrenButtonGroup = new ButtonGroup();
	private ButtonGroup creditCardsButtonGroup = new ButtonGroup();

	private RoundPanel contentPanel = new RoundPanel();
	private JPanel footerPanel = new JPanel();
	private JButton saveSurveyButton = new JButton(); // The "Save Survey"
														// button at lower right
	private JButton resetButton = new JButton();

	// This was done to insert validation routines into the textboxes
	private SwingValidationGroup surveyValidationPanelGroup = surveyValidationPanel
			.getValidationGroup();

	/**
	 * Constructor.
	 */
	public NewSurveyPanel() {
	    
		setBackground(FRAME_BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		try {
			group = Controller.getControllerInstance().getGroup();

			footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			footerPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
			footerPanel.add(resetButton);
			footerPanel.add(saveSurveyButton);
			
			contentPanel.setBackground(PANEL_BACKGROUNDTAN);
			contentPanel.setLayout(new BorderLayout(0, 0));
			
			surveyValidationPanel.setBorder(null);
			surveyValidationPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

			// Putting the various panels and components together
			/**
			 * The content component hierarchy
			 * 
			 *   I. This
			 *   	A. contentPanel
			 *   		1. drawHeader()
			 *   		2. surveyValidationPanel
			 *   			a. drawSurvey()
			 *   		3. footerPanel
			 */
			add(this.contentPanel);
			contentPanel.add(drawHeaderPanel(), BorderLayout.NORTH); // top
			surveyValidationPanel.setInnerComponent(drawSurveyPanel());		
			contentPanel.add(surveyValidationPanel, BorderLayout.CENTER); // middle
			contentPanel.add(footerPanel, BorderLayout.SOUTH); // bottom
			defaultValues();

			// Validation routine
			String pattern = "^[a-zA-Z0-9‰ˆ¸ƒ÷‹]*$";

			surveyValidationPanelGroup.add(firstNameTextField, StringValidators.regexp(pattern,
					"First Name: Symbols may not be used.", false),
					StringValidators.REQUIRE_NON_EMPTY_STRING,
					StringValidators.NO_WHITESPACE,
					StringValidators.MAY_NOT_START_WITH_DIGIT, StringValidators
							.maxLength(15), StringValidators.minLength(1));
			
			surveyValidationPanelGroup.add(lastNameTextField, StringValidators.regexp(pattern,
					"Last Name: Symbols may not be used.", false),
					StringValidators.REQUIRE_NON_EMPTY_STRING,
					StringValidators.NO_WHITESPACE,
					StringValidators.MAY_NOT_START_WITH_DIGIT, StringValidators
							.maxLength(15), StringValidators.minLength(2));
			
			surveyValidationPanelGroup.add(teacherComboBox, StringValidators.regexp(pattern,
					"Teacher: Symbols may not be used.", false),
					StringValidators.REQUIRE_NON_EMPTY_STRING,
					StringValidators.NO_WHITESPACE,
					StringValidators.MAY_NOT_START_WITH_DIGIT, StringValidators
							.maxLength(15), StringValidators.minLength(2));
			// end of validation routine
			
			//TODO DONE Construct the reset/clear button here
			
			//XXX ******** Button on lower right hand corner **********
			resetButton.setToolTipText("Clears survey entries");
			resetButton.setText("Clear");
			resetButton.setFont(FNT_BIG_AND_BOLD);
			resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				    
					Controller.getControllerInstance().refreshScreen();					
				} // -- end actionPerformed() method
			});
			
			// end of reset/clear button construction code block
			
			//XXX ******** Button on lower right hand corner **********
			saveSurveyButton.setToolTipText("Add Survey to group: "
					+ group.getName());
			saveSurveyButton.setText("Save Survey");
			saveSurveyButton.setFont(FNT_BIG_AND_BOLD);
			saveSurveyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (surveyValidationPanel.isFatalProblem()) {
						new StatusTip(
								"Error: Could not add survey. Please recheck form values.",
								LG_EXCEPTION);
					} else {
						// Custom Validator
						if(validateSurvey()){
							Controller.getControllerInstance().insertSQLSurvey(addSurvey());
							Controller.getControllerInstance().refreshScreen();
							defaultValues();
						} else {
							new StatusTip("Error: Enter GPA", LG_EXCEPTION);
						}
					}					
				} // -- end actionPerformed() method
			});
			
		} catch (NullPointerException npe) {
			}
	} // end constructor *******************

	public JPanel drawHeaderPanel() {

		JPanel headerPanel = new JPanel();

		JLabel logoLabel = new JLabel("Logo");
		logoLabel = new JLabel(new ImageIcon(MISC_01), JLabel.LEFT);
		headerPanel.add(logoLabel);

		headerPanel.setLayout(new GridLayout(0, 2));

		JLabel groupLabel = new JLabel(group.getName(), JLabel.RIGHT);

		headerPanel.setBorder(new EmptyBorder(15, 5, 15, 5));
		headerPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		groupLabel.setFont(FNT_SURVEY_TEXT);
		groupLabel.setForeground(CLR_SURVEY_TEXT);
		groupLabel.setBorder(new EmptyBorder(5, 5, 5, 25));

		headerPanel.add(groupLabel);

		return headerPanel;
	} // -- end drawHeader(0 method

	public JPanel drawSurveyPanel() {

		JPanel surveyPanel = new JPanel();

		// Main Panel
		GridBagLayout surveyPanelGridBagLayout = new GridBagLayout();
		surveyPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		surveyPanel.setBorder(new TitledBorder(null, "New Survey", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		surveyPanelGridBagLayout.columnWidths = new int[] { 78, 31, 49, 94, 53, 0, 0 };
		surveyPanelGridBagLayout.rowHeights = new int[] { 23, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0 };
		surveyPanelGridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		surveyPanelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		surveyPanel.setLayout(surveyPanelGridBagLayout);

		// Class Period
		JLabel classPeriodLabel = new JLabel("Class Period: ", SwingConstants.RIGHT);
		classPeriodLabel.setFont(newSurveyFont);
		GridBagConstraints classPeriodLabelGridBagConstraint = new GridBagConstraints();
		classPeriodLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		classPeriodLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		classPeriodLabelGridBagConstraint.gridx = 0;
		classPeriodLabelGridBagConstraint.gridy = 0;
		surveyPanel.add(classPeriodLabel, classPeriodLabelGridBagConstraint);
		// ------
		periodComboBox.setName("Class Period"); // JComboBox
		GridBagConstraints periodComboBoxGridBagConstraint = new GridBagConstraints();
		periodComboBoxGridBagConstraint.anchor = GridBagConstraints.WEST;
		periodComboBoxGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		periodComboBoxGridBagConstraint.gridx = 1;
		periodComboBoxGridBagConstraint.gridy = 0;
		surveyPanel.add(periodComboBox, periodComboBoxGridBagConstraint);

		// Teacher
		JLabel teacherLabel = new JLabel("Teacher: ", SwingConstants.RIGHT);
		teacherLabel.setFont(newSurveyFont);
		GridBagConstraints teacherLabelGridBagConstraint = new GridBagConstraints();
		teacherLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		teacherLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		teacherLabelGridBagConstraint.gridx = 3;
		teacherLabelGridBagConstraint.gridy = 0;
		surveyPanel.add(teacherLabel, teacherLabelGridBagConstraint);
		// ------
		teacherComboBox.setName("Teacher"); // JComboBox
		teacherComboBox.setEditable(true); // JComboBox
		GridBagConstraints teacherComboBoxGridBagConstraint = new GridBagConstraints();
		teacherComboBoxGridBagConstraint.gridwidth = 2;
		teacherComboBoxGridBagConstraint.anchor = GridBagConstraints.WEST;
		teacherComboBoxGridBagConstraint.insets = new Insets(0, 0, 5, 0);
		teacherComboBoxGridBagConstraint.gridx = 4;
		teacherComboBoxGridBagConstraint.gridy = 0;
		surveyPanel.add(teacherComboBox, teacherComboBoxGridBagConstraint);

		// First Name
		JLabel firstNameLabel = new JLabel("First Name: ", SwingConstants.RIGHT);
		firstNameLabel.setFont(newSurveyFont);
		GridBagConstraints firstNameLabelGridBagConstraint = new GridBagConstraints();
		firstNameLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		firstNameLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		firstNameLabelGridBagConstraint.gridx = 0;
		firstNameLabelGridBagConstraint.gridy = 1;
		surveyPanel.add(firstNameLabel, firstNameLabelGridBagConstraint);
		// ------
		firstNameTextField.setName("FirstName");
		GridBagConstraints firstNameTextfieldGridBagConstraint = new GridBagConstraints();
		firstNameTextfieldGridBagConstraint.gridwidth = 2;
		firstNameTextfieldGridBagConstraint.anchor = GridBagConstraints.WEST;
		firstNameTextfieldGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		firstNameTextfieldGridBagConstraint.gridx = 1;
		firstNameTextfieldGridBagConstraint.gridy = 1;
		surveyPanel.add(firstNameTextField, firstNameTextfieldGridBagConstraint);

		// Last Name
		JLabel lastNameLabel = new JLabel("Last Name: ", SwingConstants.RIGHT);
		lastNameLabel.setFont(newSurveyFont);
		GridBagConstraints gbc_lblLName = new GridBagConstraints();
		gbc_lblLName.anchor = GridBagConstraints.WEST;
		gbc_lblLName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLName.gridx = 3;
		gbc_lblLName.gridy = 1;
		surveyPanel.add(lastNameLabel, gbc_lblLName);
		// ------
		lastNameTextField.setName("Last Name");
		GridBagConstraints lastNameTextfieldGridBagConstraint = new GridBagConstraints();
		lastNameTextfieldGridBagConstraint.gridwidth = 2;
		lastNameTextfieldGridBagConstraint.anchor = GridBagConstraints.WEST;
		lastNameTextfieldGridBagConstraint.insets = new Insets(0, 0, 5, 0);
		lastNameTextfieldGridBagConstraint.gridx = 4;
		lastNameTextfieldGridBagConstraint.gridy = 1;
		surveyPanel.add(lastNameTextField, lastNameTextfieldGridBagConstraint);

		// Gender
		JLabel genderLabel = new JLabel("Gender: ", SwingConstants.RIGHT);
		genderLabel.setFont(newSurveyFont);
		GridBagConstraints genderLabelGridBagConstraint = new GridBagConstraints();
		genderLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		genderLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		genderLabelGridBagConstraint.gridx = 0;
		genderLabelGridBagConstraint.gridy = 2;
		surveyPanel.add(genderLabel, genderLabelGridBagConstraint);
		// ------
		genderButtonGroup.add(maleGenderRadioButton);
		GridBagConstraints maleGenderRadioButtonGridBagConstraint = new GridBagConstraints();
		maleGenderRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		maleGenderRadioButtonGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		maleGenderRadioButtonGridBagConstraint.gridx = 1;
		maleGenderRadioButtonGridBagConstraint.gridy = 2;
		surveyPanel.add(maleGenderRadioButton, maleGenderRadioButtonGridBagConstraint);
		// ------
		genderButtonGroup.add(femaleGenderRadioButton);
		GridBagConstraints femaleGenderRadioButtonGridBagConstraint = new GridBagConstraints();
		femaleGenderRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		femaleGenderRadioButtonGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		femaleGenderRadioButtonGridBagConstraint.gridx = 2;
		femaleGenderRadioButtonGridBagConstraint.gridy = 2;
		surveyPanel.add(femaleGenderRadioButton, femaleGenderRadioButtonGridBagConstraint);

		// GPA
		JLabel gpaLabel = new JLabel("Current GPA: ", SwingConstants.RIGHT);
		gpaLabel.setFont(newSurveyFont);
		GridBagConstraints gbc_lblGPA = new GridBagConstraints();
		gbc_lblGPA.anchor = GridBagConstraints.WEST;
		gbc_lblGPA.insets = new Insets(0, 0, 5, 5);
		gbc_lblGPA.gridx = 3;
		gbc_lblGPA.gridy = 2;
		surveyPanel.add(gpaLabel, gbc_lblGPA);
		// ------
		GridBagConstraints gbc_cboGPA = new GridBagConstraints();
		gbc_cboGPA.gridwidth = 2;
		gbc_cboGPA.anchor = GridBagConstraints.WEST;
		gbc_cboGPA.insets = new Insets(0, 0, 5, 0);
		gbc_cboGPA.gridx = 4;
		gbc_cboGPA.gridy = 2;
		surveyPanel.add(GPAComboBox, gbc_cboGPA);

		// Education
		JLabel plannedEducationLabel = new JLabel("Planned Education:  ", SwingConstants.RIGHT);
		plannedEducationLabel.setFont(newSurveyFont);
		GridBagConstraints educationLabelGridBagConstraint = new GridBagConstraints();
		educationLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		educationLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		educationLabelGridBagConstraint.gridx = 0;
		educationLabelGridBagConstraint.gridy = 3;
		surveyPanel.add(plannedEducationLabel, educationLabelGridBagConstraint);
		// ------
		GridBagConstraints gbc_cboEducation = new GridBagConstraints();
		gbc_cboEducation.gridwidth = 3;
		gbc_cboEducation.anchor = GridBagConstraints.WEST;
		gbc_cboEducation.insets = new Insets(0, 0, 5, 5);
		gbc_cboEducation.gridx = 1;
		gbc_cboEducation.gridy = 3;
		surveyPanel.add(educationComboBox, gbc_cboEducation);

		// Occupation category
		JLabel preferredOccupationLabel = new JLabel("Preferred Occupation: ");
		preferredOccupationLabel.setFont(newSurveyFont);
		GridBagConstraints preferredOccupationLabelGridBagConstraint = new GridBagConstraints();
		preferredOccupationLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		preferredOccupationLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		preferredOccupationLabelGridBagConstraint.gridx = 0;
		preferredOccupationLabelGridBagConstraint.gridy = 5;
		surveyPanel.add(preferredOccupationLabel, preferredOccupationLabelGridBagConstraint);
		// ------
		preferredJobCategoryComboBox.setSize(150, 20);
		GridBagConstraints preferredJobCategoryComboBoxGridBagConstraint = new GridBagConstraints();
		preferredJobCategoryComboBoxGridBagConstraint.gridwidth = 5;
		preferredJobCategoryComboBoxGridBagConstraint.anchor = GridBagConstraints.WEST;
		preferredJobCategoryComboBoxGridBagConstraint.insets = new Insets(0, 0, 5, 0);
		preferredJobCategoryComboBoxGridBagConstraint.gridx = 1;
		preferredJobCategoryComboBoxGridBagConstraint.gridy = 5;
		surveyPanel.add(preferredJobCategoryComboBox, preferredJobCategoryComboBoxGridBagConstraint);

		// Specific job
		preferredJobComboBox.setSize(150, 20);
		GridBagConstraints preferredJobComboBoxGridBagConstraint = new GridBagConstraints();
		preferredJobComboBoxGridBagConstraint.gridwidth = 5;
		preferredJobComboBoxGridBagConstraint.anchor = GridBagConstraints.WEST;
		preferredJobComboBoxGridBagConstraint.insets = new Insets(0, 0, 5, 0);
		preferredJobComboBoxGridBagConstraint.gridx = 1; // 1 (original value) 4 (new default)
		preferredJobComboBoxGridBagConstraint.gridy = 6; // 6 (original value) 5 (new default)
		surveyPanel.add(preferredJobComboBox, preferredJobComboBoxGridBagConstraint);

		// Married
		JLabel maritalStatusLabel = new JLabel("Married: ");
		maritalStatusLabel.setFont(newSurveyFont);
		GridBagConstraints maritalStatusLabelGridBagConstraint = new GridBagConstraints();
		maritalStatusLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		maritalStatusLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		maritalStatusLabelGridBagConstraint.gridx = 0;
		maritalStatusLabelGridBagConstraint.gridy = 7;
		surveyPanel.add(maritalStatusLabel, maritalStatusLabelGridBagConstraint);
		// ------
		marriedButtonGroup.add(yesMarriedRadioButton);
		GridBagConstraints yesMarriedRadioButtonGridBagConstraint = new GridBagConstraints();
		yesMarriedRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		yesMarriedRadioButtonGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		yesMarriedRadioButtonGridBagConstraint.gridx = 1;
		yesMarriedRadioButtonGridBagConstraint.gridy = 7;
		yesMarriedRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(yesMarriedRadioButton, yesMarriedRadioButtonGridBagConstraint);
		// ------
		marriedButtonGroup.add(noMarriedRadioButton);
		GridBagConstraints noMarriedRadioButtonGridBagConstraint = new GridBagConstraints();
		noMarriedRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		noMarriedRadioButtonGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		noMarriedRadioButtonGridBagConstraint.gridx = 2;
		noMarriedRadioButtonGridBagConstraint.gridy = 7;
		noMarriedRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(noMarriedRadioButton, noMarriedRadioButtonGridBagConstraint);

		// Children
		JLabel childrenLabel = new JLabel("Children: ");
		childrenLabel.setFont(newSurveyFont);
		GridBagConstraints childrenLabelGridBagConstraint = new GridBagConstraints();
		childrenLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		childrenLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		childrenLabelGridBagConstraint.gridx = 0;
		childrenLabelGridBagConstraint.gridy = 8;
		surveyPanel.add(childrenLabel, childrenLabelGridBagConstraint);
		// ------
		childrenButtonGroup.add(yesChildrenRadioButton);
		GridBagConstraints yesChildrenRadioButtonGridBagConstraint = new GridBagConstraints();
		yesChildrenRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		yesChildrenRadioButtonGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		yesChildrenRadioButtonGridBagConstraint.gridx = 1;
		yesChildrenRadioButtonGridBagConstraint.gridy = 8;
		yesChildrenRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(yesChildrenRadioButton, yesChildrenRadioButtonGridBagConstraint);
		// ------
		childrenButtonGroup.add(noChildrenRadioButton);
		GridBagConstraints noChildrenRadioButtonGridBagConstraint = new GridBagConstraints();
		noChildrenRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		noChildrenRadioButtonGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		noChildrenRadioButtonGridBagConstraint.gridx = 2;
		noChildrenRadioButtonGridBagConstraint.gridy = 8;
		noChildrenRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(noChildrenRadioButton, noChildrenRadioButtonGridBagConstraint);

		// How Many Children
		JLabel childrenCountLabel = new JLabel("How many: ");
		childrenCountLabel.setFont(newSurveyFont);
		GridBagConstraints childrenCountLabelGridBagConstraint = new GridBagConstraints();
		childrenCountLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		childrenCountLabelGridBagConstraint.insets = new Insets(0, 0, 5, 5);
		childrenCountLabelGridBagConstraint.gridx = 3;
		childrenCountLabelGridBagConstraint.gridy = 8;
		surveyPanel.add(childrenCountLabel, childrenCountLabelGridBagConstraint);

		GridBagConstraints childrenCountComboBoxGridBagConstraint = new GridBagConstraints();
		childrenCountComboBoxGridBagConstraint.gridwidth = 2;
		childrenCountComboBoxGridBagConstraint.anchor = GridBagConstraints.WEST;
		childrenCountComboBoxGridBagConstraint.insets = new Insets(0, 0, 5, 0);
		childrenCountComboBoxGridBagConstraint.gridx = 4;
		childrenCountComboBoxGridBagConstraint.gridy = 8;
		surveyPanel.add(childrenCountComboBox, childrenCountComboBoxGridBagConstraint);

		// Credit Cards
		JLabel creditCardsLabel = new JLabel("Credit Cards: ");
		creditCardsLabel.setFont(newSurveyFont);
		GridBagConstraints creditCardsLabelGridBagConstraint = new GridBagConstraints();
		creditCardsLabelGridBagConstraint.anchor = GridBagConstraints.WEST;
		creditCardsLabelGridBagConstraint.insets = new Insets(0, 0, 0, 5);
		creditCardsLabelGridBagConstraint.gridx = 0;
		creditCardsLabelGridBagConstraint.gridy = 9;
		surveyPanel.add(creditCardsLabel, creditCardsLabelGridBagConstraint);

		creditCardsButtonGroup.add(yesCreditCardsRadioButton);
		GridBagConstraints yesCreditCardsRadioButtonGridBagConstraint = new GridBagConstraints();
		yesCreditCardsRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		yesCreditCardsRadioButtonGridBagConstraint.insets = new Insets(0, 0, 0, 5);
		yesCreditCardsRadioButtonGridBagConstraint.gridx = 1;
		yesCreditCardsRadioButtonGridBagConstraint.gridy = 9;
		yesCreditCardsRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(yesCreditCardsRadioButton, yesCreditCardsRadioButtonGridBagConstraint);

		creditCardsButtonGroup.add(noCreditCardsRadioButton);
		GridBagConstraints noCreditCardsRadioButtonGridBagConstraint = new GridBagConstraints();
		noCreditCardsRadioButtonGridBagConstraint.anchor = GridBagConstraints.WEST;
		noCreditCardsRadioButtonGridBagConstraint.insets = new Insets(0, 0, 0, 5);
		noCreditCardsRadioButtonGridBagConstraint.gridx = 2;
		noCreditCardsRadioButtonGridBagConstraint.gridy = 9;
		noCreditCardsRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(noCreditCardsRadioButton, noCreditCardsRadioButtonGridBagConstraint);

		// Credit Card Uses
		JLabel creditCardUsesLabel = new JLabel("Used for: ");
		creditCardUsesLabel.setFont(newSurveyFont);
		GridBagConstraints creditCardUsesLabelGridBagLayout = new GridBagConstraints();
		creditCardUsesLabelGridBagLayout.anchor = GridBagConstraints.WEST;
		creditCardUsesLabelGridBagLayout.insets = new Insets(0, 0, 0, 5);
		creditCardUsesLabelGridBagLayout.gridx = 3;
		creditCardUsesLabelGridBagLayout.gridy = 9;
		surveyPanel.add(creditCardUsesLabel, creditCardUsesLabelGridBagLayout);
		// ------
		GridBagConstraints creditCardUsesComboBoxGridBagConstraint = new GridBagConstraints();
		creditCardUsesComboBoxGridBagConstraint.gridwidth = 2;
		creditCardUsesComboBoxGridBagConstraint.anchor = GridBagConstraints.WEST;
		creditCardUsesComboBoxGridBagConstraint.gridx = 4;
		creditCardUsesComboBoxGridBagConstraint.gridy = 9;
		surveyPanel.add(creditCardUsesComboBox, creditCardUsesComboBoxGridBagConstraint);

		// Events
		noCreditCardsRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				creditCardUsesComboBox.setEnabled(false);
			}
		});
		yesCreditCardsRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				creditCardUsesComboBox.setEnabled(true);
			}
		});
		noChildrenRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				childrenCountComboBox.setEnabled(false);
			}
		});
		yesChildrenRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				childrenCountComboBox.setEnabled(true);
			}
		});
		preferredJobCategoryComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				// Get Job Names from Controller
				List<String> lstJobNames = Controller.getControllerInstance()
						.getJobsByCategoryList(// Cast Current Selected Category as String
								(String) preferredJobCategoryComboBox.getSelectedItem());
				// Create Model of Job Names
				DefaultComboBoxModel<?> model = new DefaultComboBoxModel<>(
						lstJobNames.toArray());

				// Populate Jobs ComboBox
				preferredJobComboBox.setModel((ComboBoxModel<?>) model);

			}
		});

		return surveyPanel;
	} // -- end drawSurvey() method

	public void defaultValues() {

		periodComboBox.setSelectedIndex(0);
		teacherComboBox.setSelectedItem("none");
		maleGenderRadioButton.setSelected(false);
		femaleGenderRadioButton.setSelected(false);
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		GPAComboBox.setSelectedIndex(0);
		educationComboBox.setSelectedIndex(0);
		preferredJobCategoryComboBox.setSelectedIndex(0);
		preferredJobComboBox.setSelectedIndex(0);
		yesMarriedRadioButton.setSelected(false);
		noMarriedRadioButton.setSelected(false);
		yesChildrenRadioButton.setSelected(false);
		noChildrenRadioButton.setSelected(false);
		childrenCountComboBox.setSelectedIndex(0);
		childrenCountComboBox.setEnabled(false);
		yesCreditCardsRadioButton.setSelected(false);
		noCreditCardsRadioButton.setSelected(false);
		creditCardUsesComboBox.setSelectedIndex(0);
		creditCardUsesComboBox.setEnabled(false);

	} // end defaultValues() method

	/**
	 * Adds a new survey to the Group.
	 * 
	 * @return 0: Failure<br>
	 *         1: Success
	 */
	public Survey addSurvey() {

		Survey survey = new Survey();

		survey.setGroupID(Controller.getControllerInstance().getGroup().getID());
		survey.setTeacher(teacherComboBox.getSelectedItem().toString());
		survey.setFName(firstNameTextField.getText());
		survey.setLName(lastNameTextField.getText());
		survey.setCPeriod(Integer.parseInt(periodComboBox.getSelectedItem()
				.toString()));
		survey.setGPA(GPAComboBox.getSelectedIndex());
		survey.setEducation(educationComboBox.getSelectedIndex());

		int intJobID = Controller
				.getControllerInstance()
				.searchJobsList("name",
						preferredJobComboBox.getSelectedItem().toString())
				.get(0).getID();

		survey.setPreferredJob(intJobID);

		// default values - can be changed in statements after this 4-line block
		survey.setGender(1);
		survey.setMaritalStatus(0);
		survey.setChildren(0);
		survey.setCreditCards(0);

		boolean isGenderMale = maleGenderRadioButton.isSelected();
		boolean isMarried = yesMarriedRadioButton.isSelected();
		boolean haveChildren = yesChildrenRadioButton.isSelected();
		boolean useCreditCards = yesCreditCardsRadioButton.isSelected();

		if (isGenderMale)
			survey.setGender(0);
		if (isMarried)
			survey.setMaritalStatus(1);
		if (haveChildren)
			survey.setChildren(Integer.parseInt(childrenCountComboBox
					.getSelectedItem().toString()));
		if (useCreditCards) {
			survey.setCreditCards(1);
			survey.setCreditCardUses(creditCardUsesComboBox.getSelectedIndex());
		}
		
		// the rest of the default values
		survey.setAssignedJob(intJobID); // same as preferred until "further notice"

		return survey;
	} // -- end addSurvey() method

	public boolean validateSurvey() {

		// Validate GPA
		if (GPAComboBox.getSelectedIndex() == 0) {

			return false;
		}

		return true;
	} // -- end validateSurvey() method
} // end NewSurveyPanel class
