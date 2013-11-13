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
		GridBagLayout gbl_pnlSurvey = new GridBagLayout();
		surveyPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		surveyPanel.setBorder(new TitledBorder(null, "New Survey",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbl_pnlSurvey.columnWidths = new int[] { 78, 31, 49, 94, 53, 0, 0 };
		gbl_pnlSurvey.rowHeights = new int[] { 23, 0, 0, 0, 30, 0, 0, 0, 0, 0,
				0 };
		gbl_pnlSurvey.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gbl_pnlSurvey.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		surveyPanel.setLayout(gbl_pnlSurvey);

		// Class Period
		JLabel lblCPeriod = new JLabel("Class Period: ", SwingConstants.RIGHT);
		lblCPeriod.setFont(newSurveyFont);
		GridBagConstraints gbc_lblCPeriod = new GridBagConstraints();
		gbc_lblCPeriod.anchor = GridBagConstraints.WEST;
		gbc_lblCPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_lblCPeriod.gridx = 0;
		gbc_lblCPeriod.gridy = 0;
		surveyPanel.add(lblCPeriod, gbc_lblCPeriod);
		// ------
		periodComboBox.setName("Class Period"); // JComboBox
		GridBagConstraints gbc_cboCPeriod = new GridBagConstraints();
		gbc_cboCPeriod.anchor = GridBagConstraints.WEST;
		gbc_cboCPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_cboCPeriod.gridx = 1;
		gbc_cboCPeriod.gridy = 0;
		surveyPanel.add(periodComboBox, gbc_cboCPeriod);

		// Teacher
		JLabel teacherLabel = new JLabel("Teacher: ", SwingConstants.RIGHT);
		teacherLabel.setFont(newSurveyFont);
		GridBagConstraints gbc_lblTeacher = new GridBagConstraints();
		gbc_lblTeacher.anchor = GridBagConstraints.WEST;
		gbc_lblTeacher.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeacher.gridx = 3;
		gbc_lblTeacher.gridy = 0;
		surveyPanel.add(teacherLabel, gbc_lblTeacher);
		// ------
		teacherComboBox.setName("Teacher"); // JComboBox
		teacherComboBox.setEditable(true); // JComboBox
		GridBagConstraints gbc_cboTeacher = new GridBagConstraints();
		gbc_cboTeacher.gridwidth = 2;
		gbc_cboTeacher.anchor = GridBagConstraints.WEST;
		gbc_cboTeacher.insets = new Insets(0, 0, 5, 0);
		gbc_cboTeacher.gridx = 4;
		gbc_cboTeacher.gridy = 0;
		surveyPanel.add(teacherComboBox, gbc_cboTeacher);

		// First Name
		JLabel firstNameLabel = new JLabel("First Name: ", SwingConstants.RIGHT);
		firstNameLabel.setFont(newSurveyFont);
		GridBagConstraints gbc_lblFName = new GridBagConstraints();
		gbc_lblFName.anchor = GridBagConstraints.WEST;
		gbc_lblFName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFName.gridx = 0;
		gbc_lblFName.gridy = 1;
		surveyPanel.add(firstNameLabel, gbc_lblFName);
		// ------
		firstNameTextField.setName("FirstName");
		GridBagConstraints gbc_txtFName = new GridBagConstraints();
		gbc_txtFName.gridwidth = 2;
		gbc_txtFName.anchor = GridBagConstraints.WEST;
		gbc_txtFName.insets = new Insets(0, 0, 5, 5);
		gbc_txtFName.gridx = 1;
		gbc_txtFName.gridy = 1;
		surveyPanel.add(firstNameTextField, gbc_txtFName);

		// Last Name
		JLabel lblLName = new JLabel("Last Name: ", SwingConstants.RIGHT);
		lblLName.setFont(newSurveyFont);
		GridBagConstraints gbc_lblLName = new GridBagConstraints();
		gbc_lblLName.anchor = GridBagConstraints.WEST;
		gbc_lblLName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLName.gridx = 3;
		gbc_lblLName.gridy = 1;
		surveyPanel.add(lblLName, gbc_lblLName);
		lastNameTextField.setName("Last Name");
		GridBagConstraints gbc_txtLName = new GridBagConstraints();
		gbc_txtLName.gridwidth = 2;
		gbc_txtLName.anchor = GridBagConstraints.WEST;
		gbc_txtLName.insets = new Insets(0, 0, 5, 0);
		gbc_txtLName.gridx = 4;
		gbc_txtLName.gridy = 1;
		surveyPanel.add(lastNameTextField, gbc_txtLName);

		// Gender
		JLabel lblGender = new JLabel("Gender: ", SwingConstants.RIGHT);
		lblGender.setFont(newSurveyFont);
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.WEST;
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 2;
		surveyPanel.add(lblGender, gbc_lblGender);

		genderButtonGroup.add(maleGenderRadioButton);
		GridBagConstraints gbc_btnGenderMale = new GridBagConstraints();
		gbc_btnGenderMale.anchor = GridBagConstraints.WEST;
		gbc_btnGenderMale.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenderMale.gridx = 1;
		gbc_btnGenderMale.gridy = 2;
		surveyPanel.add(maleGenderRadioButton, gbc_btnGenderMale);

		genderButtonGroup.add(femaleGenderRadioButton);
		GridBagConstraints gbc_btnGenderFemale = new GridBagConstraints();
		gbc_btnGenderFemale.anchor = GridBagConstraints.WEST;
		gbc_btnGenderFemale.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenderFemale.gridx = 2;
		gbc_btnGenderFemale.gridy = 2;
		surveyPanel.add(femaleGenderRadioButton, gbc_btnGenderFemale);

		// GPA
		JLabel lblGPA = new JLabel("Current GPA: ", SwingConstants.RIGHT);
		lblGPA.setFont(newSurveyFont);
		GridBagConstraints gbc_lblGPA = new GridBagConstraints();
		gbc_lblGPA.anchor = GridBagConstraints.WEST;
		gbc_lblGPA.insets = new Insets(0, 0, 5, 5);
		gbc_lblGPA.gridx = 3;
		gbc_lblGPA.gridy = 2;
		surveyPanel.add(lblGPA, gbc_lblGPA);
		GridBagConstraints gbc_cboGPA = new GridBagConstraints();
		gbc_cboGPA.gridwidth = 2;
		gbc_cboGPA.anchor = GridBagConstraints.WEST;
		gbc_cboGPA.insets = new Insets(0, 0, 5, 0);
		gbc_cboGPA.gridx = 4;
		gbc_cboGPA.gridy = 2;
		surveyPanel.add(GPAComboBox, gbc_cboGPA);

		// Education
		JLabel lblEducation = new JLabel("Planned Education:  ",
				SwingConstants.RIGHT);
		lblEducation.setFont(newSurveyFont);
		GridBagConstraints gbc_lblEducation = new GridBagConstraints();
		gbc_lblEducation.anchor = GridBagConstraints.WEST;
		gbc_lblEducation.insets = new Insets(0, 0, 5, 5);
		gbc_lblEducation.gridx = 0;
		gbc_lblEducation.gridy = 3;
		surveyPanel.add(lblEducation, gbc_lblEducation);
		GridBagConstraints gbc_cboEducation = new GridBagConstraints();
		gbc_cboEducation.gridwidth = 3;
		gbc_cboEducation.anchor = GridBagConstraints.WEST;
		gbc_cboEducation.insets = new Insets(0, 0, 5, 5);
		gbc_cboEducation.gridx = 1;
		gbc_cboEducation.gridy = 3;
		surveyPanel.add(educationComboBox, gbc_cboEducation);

		// Occupation
		JLabel lblJob = new JLabel("Preferred Occupation: ");
		lblJob.setFont(newSurveyFont);

		GridBagConstraints gbc_lblJob = new GridBagConstraints();
		gbc_lblJob.anchor = GridBagConstraints.WEST;
		gbc_lblJob.insets = new Insets(0, 0, 5, 5);
		gbc_lblJob.gridx = 0;
		gbc_lblJob.gridy = 5;
		surveyPanel.add(lblJob, gbc_lblJob);

		preferredJobCategoryComboBox.setSize(150, 20);
		GridBagConstraints gbc_cboJobCategory = new GridBagConstraints();
		gbc_cboJobCategory.gridwidth = 5;
		gbc_cboJobCategory.anchor = GridBagConstraints.WEST;
		gbc_cboJobCategory.insets = new Insets(0, 0, 5, 0);
		gbc_cboJobCategory.gridx = 1;
		gbc_cboJobCategory.gridy = 5;
		surveyPanel.add(preferredJobCategoryComboBox, gbc_cboJobCategory);

		preferredJobComboBox.setSize(150, 20);
		GridBagConstraints gbc_cboJob = new GridBagConstraints();
		gbc_cboJob.gridwidth = 5;
		gbc_cboJob.anchor = GridBagConstraints.WEST;
		gbc_cboJob.insets = new Insets(0, 0, 5, 0);
		gbc_cboJob.gridx = 1;
		gbc_cboJob.gridy = 6;
		surveyPanel.add(preferredJobComboBox, gbc_cboJob);

		// Married
		JLabel lblMarried = new JLabel("Married: ");
		lblMarried.setFont(newSurveyFont);
		GridBagConstraints gbc_lblMarried = new GridBagConstraints();
		gbc_lblMarried.anchor = GridBagConstraints.WEST;
		gbc_lblMarried.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarried.gridx = 0;
		gbc_lblMarried.gridy = 7;
		surveyPanel.add(lblMarried, gbc_lblMarried);

		marriedButtonGroup.add(yesMarriedRadioButton);
		GridBagConstraints gbc_btnMarriedYes = new GridBagConstraints();
		gbc_btnMarriedYes.anchor = GridBagConstraints.WEST;
		gbc_btnMarriedYes.insets = new Insets(0, 0, 5, 5);
		gbc_btnMarriedYes.gridx = 1;
		gbc_btnMarriedYes.gridy = 7;
		yesMarriedRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(yesMarriedRadioButton, gbc_btnMarriedYes);

		marriedButtonGroup.add(noMarriedRadioButton);
		GridBagConstraints gbc_btnMarriedNo = new GridBagConstraints();
		gbc_btnMarriedNo.anchor = GridBagConstraints.WEST;
		gbc_btnMarriedNo.insets = new Insets(0, 0, 5, 5);
		gbc_btnMarriedNo.gridx = 2;
		gbc_btnMarriedNo.gridy = 7;
		noMarriedRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(noMarriedRadioButton, gbc_btnMarriedNo);

		// Children
		JLabel lblChildren = new JLabel("Children: ");
		lblChildren.setFont(newSurveyFont);
		GridBagConstraints gbc_lblChildren = new GridBagConstraints();
		gbc_lblChildren.anchor = GridBagConstraints.WEST;
		gbc_lblChildren.insets = new Insets(0, 0, 5, 5);
		gbc_lblChildren.gridx = 0;
		gbc_lblChildren.gridy = 8;
		surveyPanel.add(lblChildren, gbc_lblChildren);
		childrenButtonGroup.add(yesChildrenRadioButton);
		GridBagConstraints gbc_btnChildrenYes = new GridBagConstraints();
		gbc_btnChildrenYes.anchor = GridBagConstraints.WEST;
		gbc_btnChildrenYes.insets = new Insets(0, 0, 5, 5);
		gbc_btnChildrenYes.gridx = 1;
		gbc_btnChildrenYes.gridy = 8;
		yesChildrenRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(yesChildrenRadioButton, gbc_btnChildrenYes);

		childrenButtonGroup.add(noChildrenRadioButton);
		GridBagConstraints gbc_btnChildrenNo = new GridBagConstraints();
		gbc_btnChildrenNo.anchor = GridBagConstraints.WEST;
		gbc_btnChildrenNo.insets = new Insets(0, 0, 5, 5);
		gbc_btnChildrenNo.gridx = 2;
		gbc_btnChildrenNo.gridy = 8;
		noChildrenRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(noChildrenRadioButton, gbc_btnChildrenNo);

		// How Many Children
		JLabel lblChildrenCount = new JLabel("How many: ");
		lblChildrenCount.setFont(newSurveyFont);
		GridBagConstraints gbc_lblChildrenCount = new GridBagConstraints();
		gbc_lblChildrenCount.anchor = GridBagConstraints.WEST;
		gbc_lblChildrenCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblChildrenCount.gridx = 3;
		gbc_lblChildrenCount.gridy = 8;
		surveyPanel.add(lblChildrenCount, gbc_lblChildrenCount);

		GridBagConstraints gbc_cboChildrenCount = new GridBagConstraints();
		gbc_cboChildrenCount.gridwidth = 2;
		gbc_cboChildrenCount.anchor = GridBagConstraints.WEST;
		gbc_cboChildrenCount.insets = new Insets(0, 0, 5, 0);
		gbc_cboChildrenCount.gridx = 4;
		gbc_cboChildrenCount.gridy = 8;
		surveyPanel.add(childrenCountComboBox, gbc_cboChildrenCount);

		// Credit Cards
		JLabel lblCCards = new JLabel("Credit Cards: ");
		lblCCards.setFont(newSurveyFont);
		GridBagConstraints gbc_lblCCards = new GridBagConstraints();
		gbc_lblCCards.anchor = GridBagConstraints.WEST;
		gbc_lblCCards.insets = new Insets(0, 0, 0, 5);
		gbc_lblCCards.gridx = 0;
		gbc_lblCCards.gridy = 9;
		surveyPanel.add(lblCCards, gbc_lblCCards);

		creditCardsButtonGroup.add(yesCreditCardsRadioButton);
		GridBagConstraints gbc_btnCCardsYes = new GridBagConstraints();
		gbc_btnCCardsYes.anchor = GridBagConstraints.WEST;
		gbc_btnCCardsYes.insets = new Insets(0, 0, 0, 5);
		gbc_btnCCardsYes.gridx = 1;
		gbc_btnCCardsYes.gridy = 9;
		yesCreditCardsRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(yesCreditCardsRadioButton, gbc_btnCCardsYes);

		creditCardsButtonGroup.add(noCreditCardsRadioButton);
		GridBagConstraints gbc_btnCCardsNo = new GridBagConstraints();
		gbc_btnCCardsNo.anchor = GridBagConstraints.WEST;
		gbc_btnCCardsNo.insets = new Insets(0, 0, 0, 5);
		gbc_btnCCardsNo.gridx = 2;
		gbc_btnCCardsNo.gridy = 9;
		noCreditCardsRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		surveyPanel.add(noCreditCardsRadioButton, gbc_btnCCardsNo);

		// Credit Card Uses
		JLabel lblCCardUse = new JLabel("Used for: ");
		lblCCardUse.setFont(newSurveyFont);
		GridBagConstraints gbc_lblCCardUse = new GridBagConstraints();
		gbc_lblCCardUse.anchor = GridBagConstraints.WEST;
		gbc_lblCCardUse.insets = new Insets(0, 0, 0, 5);
		gbc_lblCCardUse.gridx = 3;
		gbc_lblCCardUse.gridy = 9;
		surveyPanel.add(lblCCardUse, gbc_lblCCardUse);
		GridBagConstraints gbc_cboCCardUses = new GridBagConstraints();
		gbc_cboCCardUses.gridwidth = 2;
		gbc_cboCCardUses.anchor = GridBagConstraints.WEST;
		gbc_cboCCardUses.gridx = 4;
		gbc_cboCCardUses.gridy = 9;
		surveyPanel.add(creditCardUsesComboBox, gbc_cboCCardUses);

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
						.getJobsByCategoryList(
								// Cast Current Selected Category as String
								(String) preferredJobCategoryComboBox
										.getSelectedItem());
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
