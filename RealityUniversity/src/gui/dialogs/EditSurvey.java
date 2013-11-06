package gui.dialogs;

import gui.GuiInterface;
import gui.custom.RoundPanel;
import gui.custom.StatusTip;

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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import obj.Survey;

import org.netbeans.validation.api.builtin.stringvalidation.StringValidators;
import org.netbeans.validation.api.ui.swing.SwingValidationGroup;
import org.netbeans.validation.api.ui.swing.ValidationPanel;

import ctrl.Controller;

public class EditSurvey extends JDialog implements GuiInterface {

	private Survey survey;

	private ValidationPanel surveyValidationPanel = new ValidationPanel();

	RoundPanel mainPanel = new RoundPanel();
	JPanel contentPanel = new JPanel();
	JPanel footerPanel = new JPanel();
	JButton updateSurveyButton = new JButton();
	JButton resetButton = new JButton("Reset");
	private Font labelFont = new Font("sansserif", Font.PLAIN, 12);

	// Text Fields
	private JTextField firstNameTextField = new JTextField(11);
	private JTextField lastNameTextField = new JTextField(11);

	// Combo Boxes
	private List<String> jobCategoriesList = Controller.getControllerInstance()
			.getJobCategoriesList();
	private List<String> jobsList = Controller.getControllerInstance()
			.getJobsByCategoryList(jobCategoriesList.get(0));

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
			new DefaultComboBoxModel(jobsList.toArray()));

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

	// Text fields
	private JTextField annualSalaryTextField;
	private JTextField annualTaxesTextField;
	private JTextField monthlySalaryTextField;
	private JTextField monthlyTaxesTextField;
	private JTextField childSupportTextField;
	private JTextField spousalIncomeTextField;
	private JTextField netIncomeTextField;
	private JTextField checkbookEntryTextField;

	/**
	 * Constructor
	 * 
	 * @param survey
	 */
	public EditSurvey(Survey survey) {

		Controller.getControllerInstance().getGroup();
		this.survey = survey;

		/*********************************
		 * Configurations
		 *********************************/
		setSize(EDIT_SURVEY_WIDTH, EDIT_SURVEY_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setBackground(FRAME_BACKGROUND);

		try {
			Controller.getControllerInstance().getGroup();

			SwingValidationGroup svgValSurvey = surveyValidationPanel
					.getValidationGroup();

			footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			footerPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
			mainPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
			mainPanel.setLayout(new BorderLayout(0, 0));
			contentPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

			surveyValidationPanel.setBorder(null);
			surveyValidationPanel.setInnerComponent(drawSurvey());
			surveyValidationPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

			String pattern = "^[a-zA-Z0-9������]*$";

			svgValSurvey.add(firstNameTextField, StringValidators.regexp(
					pattern, "First Name: Symbols may not be used.", false),
					StringValidators.REQUIRE_NON_EMPTY_STRING,
					StringValidators.NO_WHITESPACE,
					StringValidators.MAY_NOT_START_WITH_DIGIT, StringValidators
							.maxLength(15), StringValidators.minLength(3));
			svgValSurvey.add(lastNameTextField, StringValidators.regexp(
					pattern, "Last Name: Symbols may not be used.", false),
					StringValidators.REQUIRE_NON_EMPTY_STRING,
					StringValidators.NO_WHITESPACE,
					StringValidators.MAY_NOT_START_WITH_DIGIT, StringValidators
							.maxLength(15), StringValidators.minLength(3));
			svgValSurvey.add(teacherComboBox, StringValidators.regexp(pattern,
					"Teacher: Symbols may not be used.", false),
					StringValidators.REQUIRE_NON_EMPTY_STRING,
					StringValidators.NO_WHITESPACE,
					StringValidators.MAY_NOT_START_WITH_DIGIT, StringValidators
							.maxLength(15), StringValidators.minLength(3));
			contentPanel
					.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

			updateSurveyButton.setToolTipText("Update Survey");
			updateSurveyButton.setText("Update Survey");

			// TODO - DONE! make this work as intended
			updateSurveyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (surveyValidationPanel.isFatalProblem()) {
						new StatusTip("Error: Please check form values.",
								LG_EXCEPTION);
					} else {
						// Custom Validator
						if (validateSurvey()) {

							Controller.getControllerInstance().updateSQLSurvey(
									updateSurvey());
							Controller.getControllerInstance().refreshScreen();
						} else {
							new StatusTip("Error: Enter GPA", LG_EXCEPTION);
						}
					}

				} // -- end actionPerformed method
			});
			footerPanel.add(resetButton);
			footerPanel.add(updateSurveyButton);
			contentPanel.add(drawHeader());
			contentPanel.add(surveyValidationPanel);
			// pnlContent.add(vpnlJobInfo);
			mainPanel.add(contentPanel, BorderLayout.CENTER);
			contentPanel.add(footerPanel, BorderLayout.SOUTH);
			add(mainPanel);
			setInitialSurveyValues();
		} catch (NullPointerException npe) {

		}

	} // -- end constructor

	public JPanel drawHeader() {

		JPanel pnlHeader = new JPanel();
		JLabel lblLogo = new JLabel("Logo");
		JLabel lblEditSurvey = new JLabel("Edit Survey", JLabel.RIGHT);

		pnlHeader.setLayout(new GridLayout(0, 2));
		pnlHeader.setBorder(new EmptyBorder(15, 5, 15, 5));
		pnlHeader.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

		lblLogo = new JLabel(new ImageIcon(MISC_01), JLabel.LEFT);
		lblEditSurvey.setFont(FNT_SURVEY_TEXT);
		lblEditSurvey.setForeground(CLR_SURVEY_TEXT);
		lblEditSurvey.setBorder(new EmptyBorder(5, 5, 5, 25));

		pnlHeader.add(lblLogo);
		pnlHeader.add(lblEditSurvey);

		return pnlHeader;
	} // end drawHeader() method

	public JPanel drawSurvey() {

		JPanel surveyPanel = new JPanel();

		// Main Panel
		GridBagLayout gbl_pnlSurvey = new GridBagLayout();
		surveyPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		surveyPanel.setBorder(new TitledBorder(null, "Edit Survey",
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
		lblCPeriod.setFont(labelFont);
		GridBagConstraints gbc_lblCPeriod = new GridBagConstraints();
		gbc_lblCPeriod.anchor = GridBagConstraints.WEST;
		gbc_lblCPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_lblCPeriod.gridx = 0;
		gbc_lblCPeriod.gridy = 0;
		surveyPanel.add(lblCPeriod, gbc_lblCPeriod);

		periodComboBox.setName("Class Period");
		GridBagConstraints gbc_cboCPeriod = new GridBagConstraints();
		gbc_cboCPeriod.anchor = GridBagConstraints.WEST;
		gbc_cboCPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_cboCPeriod.gridx = 1;
		gbc_cboCPeriod.gridy = 0;
		surveyPanel.add(periodComboBox, gbc_cboCPeriod);

		// Gender
		JLabel lblGender = new JLabel("Gender: ", SwingConstants.RIGHT);
		lblGender.setFont(labelFont);
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.anchor = GridBagConstraints.WEST;
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 3;
		gbc_lblGender.gridy = 0;
		surveyPanel.add(lblGender, gbc_lblGender);

		genderButtonGroup.add(maleGenderRadioButton);
		GridBagConstraints gbc_btnGenderMale = new GridBagConstraints();
		gbc_btnGenderMale.anchor = GridBagConstraints.WEST;
		gbc_btnGenderMale.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenderMale.gridx = 4;
		gbc_btnGenderMale.gridy = 0;
		surveyPanel.add(maleGenderRadioButton, gbc_btnGenderMale);

		genderButtonGroup.add(femaleGenderRadioButton);
		GridBagConstraints gbc_btnGenderFemale = new GridBagConstraints();
		gbc_btnGenderFemale.anchor = GridBagConstraints.WEST;
		gbc_btnGenderFemale.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenderFemale.gridx = 5;
		gbc_btnGenderFemale.gridy = 0;
		surveyPanel.add(femaleGenderRadioButton, gbc_btnGenderFemale);

		// First Name
		JLabel lblFName = new JLabel("First Name: ", SwingConstants.RIGHT);
		lblFName.setFont(labelFont);
		GridBagConstraints gbc_lblFName = new GridBagConstraints();
		gbc_lblFName.anchor = GridBagConstraints.WEST;
		gbc_lblFName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFName.gridx = 0;
		gbc_lblFName.gridy = 1;
		surveyPanel.add(lblFName, gbc_lblFName);
		firstNameTextField.setName("First Name");
		GridBagConstraints gbc_txtFName = new GridBagConstraints();
		gbc_txtFName.gridwidth = 2;
		gbc_txtFName.anchor = GridBagConstraints.WEST;
		gbc_txtFName.insets = new Insets(0, 0, 5, 5);
		gbc_txtFName.gridx = 1;
		gbc_txtFName.gridy = 1;
		surveyPanel.add(firstNameTextField, gbc_txtFName);

		// Last Name
		JLabel lblLName = new JLabel("Last Name: ", SwingConstants.RIGHT);
		lblLName.setFont(labelFont);
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

		// Teacher
		JLabel lblTeacher = new JLabel("Teacher: ", SwingConstants.RIGHT);
		lblTeacher.setFont(labelFont);
		GridBagConstraints gbc_lblTeacher = new GridBagConstraints();
		gbc_lblTeacher.anchor = GridBagConstraints.WEST;
		gbc_lblTeacher.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeacher.gridx = 0;
		gbc_lblTeacher.gridy = 2;
		surveyPanel.add(lblTeacher, gbc_lblTeacher);
		teacherComboBox.setName("Teacher");
		teacherComboBox.setEditable(true);
		GridBagConstraints gbc_cboTeacher = new GridBagConstraints();
		gbc_cboTeacher.gridwidth = 2;
		gbc_cboTeacher.anchor = GridBagConstraints.WEST;
		gbc_cboTeacher.insets = new Insets(0, 0, 5, 5);
		gbc_cboTeacher.gridx = 1;
		gbc_cboTeacher.gridy = 2;
		surveyPanel.add(teacherComboBox, gbc_cboTeacher);

		// GPA
		JLabel lblGPA = new JLabel("Current GPA: ", SwingConstants.RIGHT);
		lblGPA.setFont(labelFont);
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
		lblEducation.setFont(labelFont);
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
		lblJob.setFont(labelFont);

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
		lblMarried.setFont(labelFont);
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
		lblChildren.setFont(labelFont);
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
		lblChildrenCount.setFont(labelFont);
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
		lblCCards.setFont(labelFont);
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
		lblCCardUse.setFont(labelFont);
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
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						lstJobNames.toArray());

				// Populate Jobs ComboBox
				preferredJobComboBox.setModel(model);
			}
		});

		return surveyPanel;
	} // -- end drawSurvey() method

	public JPanel drawJobInfo() {

		JPanel pnlJobInfo = new JPanel();
		pnlJobInfo.setBorder(new TitledBorder(null, "Job Information",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlJobInfo.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

		GridBagLayout gbl_pnlJobInfo = new GridBagLayout();
		gbl_pnlJobInfo.columnWidths = new int[] { 24, 72, 84, 91, 77, 71, 0 };
		gbl_pnlJobInfo.rowHeights = new int[] { 20, 0, 0, 0, 0, 0, 0 };
		gbl_pnlJobInfo.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		gbl_pnlJobInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		pnlJobInfo.setLayout(gbl_pnlJobInfo);

		JLabel lblOccupation = new JLabel("Job: ");

		GridBagConstraints gbc_lblOccupation = new GridBagConstraints();
		gbc_lblOccupation.anchor = GridBagConstraints.EAST;
		gbc_lblOccupation.insets = new Insets(0, 0, 5, 5);
		gbc_lblOccupation.gridx = 0;
		gbc_lblOccupation.gridy = 0;
		pnlJobInfo.add(lblOccupation, gbc_lblOccupation);

		JComboBox cboJobCategory = new JComboBox((ComboBoxModel) null);
		GridBagConstraints gbc_cboJobCategory = new GridBagConstraints();
		gbc_cboJobCategory.gridwidth = 5;
		gbc_cboJobCategory.insets = new Insets(0, 0, 5, 5);
		gbc_cboJobCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboJobCategory.gridx = 1;
		gbc_cboJobCategory.gridy = 0;
		pnlJobInfo.add(cboJobCategory, gbc_cboJobCategory);

		JComboBox cboJob = new JComboBox((ComboBoxModel) null);
		GridBagConstraints gbc_cboJob = new GridBagConstraints();
		gbc_cboJob.gridwidth = 5;
		gbc_cboJob.insets = new Insets(0, 0, 5, 5);
		gbc_cboJob.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboJob.gridx = 1;
		gbc_cboJob.gridy = 1;
		pnlJobInfo.add(cboJob, gbc_cboJob);
		JLabel lblAnnualSalary = new JLabel("Annual Salary: ");
		GridBagConstraints gbc_lblAnnualSalary = new GridBagConstraints();
		gbc_lblAnnualSalary.anchor = GridBagConstraints.EAST;
		gbc_lblAnnualSalary.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnnualSalary.gridx = 0;
		gbc_lblAnnualSalary.gridy = 2;
		pnlJobInfo.add(lblAnnualSalary, gbc_lblAnnualSalary);

		annualSalaryTextField = new JTextField();
		annualSalaryTextField.setEnabled(false);
		GridBagConstraints gbc_txtAnnualSalary = new GridBagConstraints();
		gbc_txtAnnualSalary.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnnualSalary.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnnualSalary.gridx = 1;
		gbc_txtAnnualSalary.gridy = 2;
		pnlJobInfo.add(annualSalaryTextField, gbc_txtAnnualSalary);
		JLabel lblMonthlySalary = new JLabel("Monthly Salary: ");
		GridBagConstraints gbc_lblMonthlySalary = new GridBagConstraints();
		gbc_lblMonthlySalary.anchor = GridBagConstraints.EAST;
		gbc_lblMonthlySalary.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonthlySalary.gridx = 2;
		gbc_lblMonthlySalary.gridy = 2;
		pnlJobInfo.add(lblMonthlySalary, gbc_lblMonthlySalary);

		monthlySalaryTextField = new JTextField();
		monthlySalaryTextField.setEnabled(false);
		GridBagConstraints gbc_txtMonthlySalary = new GridBagConstraints();
		gbc_txtMonthlySalary.insets = new Insets(0, 0, 5, 5);
		gbc_txtMonthlySalary.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMonthlySalary.gridx = 3;
		gbc_txtMonthlySalary.gridy = 2;
		pnlJobInfo.add(monthlySalaryTextField, gbc_txtMonthlySalary);
		JLabel lblChildSupport = new JLabel("Child Support: ");
		GridBagConstraints gbc_lblChildSupport = new GridBagConstraints();
		gbc_lblChildSupport.anchor = GridBagConstraints.EAST;
		gbc_lblChildSupport.insets = new Insets(0, 0, 5, 5);
		gbc_lblChildSupport.gridx = 4;
		gbc_lblChildSupport.gridy = 2;
		pnlJobInfo.add(lblChildSupport, gbc_lblChildSupport);

		childSupportTextField = new JTextField();
		childSupportTextField.setEnabled(false);
		GridBagConstraints gbc_txtChildSupport = new GridBagConstraints();
		gbc_txtChildSupport.insets = new Insets(0, 0, 5, 0);
		gbc_txtChildSupport.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtChildSupport.gridx = 5;
		gbc_txtChildSupport.gridy = 2;
		pnlJobInfo.add(childSupportTextField, gbc_txtChildSupport);
		JLabel lblAnnualTaxes = new JLabel("Annual Taxes: ");
		GridBagConstraints gbc_lblAnnualTaxes = new GridBagConstraints();
		gbc_lblAnnualTaxes.anchor = GridBagConstraints.EAST;
		gbc_lblAnnualTaxes.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnnualTaxes.gridx = 0;
		gbc_lblAnnualTaxes.gridy = 3;
		pnlJobInfo.add(lblAnnualTaxes, gbc_lblAnnualTaxes);

		annualTaxesTextField = new JTextField();
		annualTaxesTextField.setEnabled(false);
		GridBagConstraints gbc_txtAnnualTaxes = new GridBagConstraints();
		gbc_txtAnnualTaxes.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnnualTaxes.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnnualTaxes.gridx = 1;
		gbc_txtAnnualTaxes.gridy = 3;
		pnlJobInfo.add(annualTaxesTextField, gbc_txtAnnualTaxes);
		JLabel lblMonthlyTaxes = new JLabel("Monthly Taxes: ");
		GridBagConstraints gbc_lblMonthlyTaxes = new GridBagConstraints();
		gbc_lblMonthlyTaxes.anchor = GridBagConstraints.EAST;
		gbc_lblMonthlyTaxes.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonthlyTaxes.gridx = 2;
		gbc_lblMonthlyTaxes.gridy = 3;
		pnlJobInfo.add(lblMonthlyTaxes, gbc_lblMonthlyTaxes);

		monthlyTaxesTextField = new JTextField();
		monthlyTaxesTextField.setEnabled(false);
		GridBagConstraints gbc_txtMonthlyTaxes = new GridBagConstraints();
		gbc_txtMonthlyTaxes.insets = new Insets(0, 0, 5, 5);
		gbc_txtMonthlyTaxes.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMonthlyTaxes.gridx = 3;
		gbc_txtMonthlyTaxes.gridy = 3;
		pnlJobInfo.add(monthlyTaxesTextField, gbc_txtMonthlyTaxes);
		JLabel lblSpousalIncome = new JLabel("Spousal Income: ");
		GridBagConstraints gbc_lblSpousalIncome = new GridBagConstraints();
		gbc_lblSpousalIncome.anchor = GridBagConstraints.EAST;
		gbc_lblSpousalIncome.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpousalIncome.gridx = 4;
		gbc_lblSpousalIncome.gridy = 3;
		pnlJobInfo.add(lblSpousalIncome, gbc_lblSpousalIncome);

		spousalIncomeTextField = new JTextField();
		spousalIncomeTextField.setEnabled(false);
		GridBagConstraints gbc_txtSpousalIncome = new GridBagConstraints();
		gbc_txtSpousalIncome.insets = new Insets(0, 0, 5, 0);
		gbc_txtSpousalIncome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSpousalIncome.gridx = 5;
		gbc_txtSpousalIncome.gridy = 3;
		pnlJobInfo.add(spousalIncomeTextField, gbc_txtSpousalIncome);
		JLabel lblNetIncome = new JLabel("Net Income: ");
		GridBagConstraints gbc_lblNetIncome = new GridBagConstraints();
		gbc_lblNetIncome.anchor = GridBagConstraints.EAST;
		gbc_lblNetIncome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNetIncome.gridx = 4;
		gbc_lblNetIncome.gridy = 4;
		pnlJobInfo.add(lblNetIncome, gbc_lblNetIncome);

		netIncomeTextField = new JTextField();
		netIncomeTextField.setEnabled(false);
		GridBagConstraints gbc_txtNetIncome = new GridBagConstraints();
		gbc_txtNetIncome.insets = new Insets(0, 0, 5, 0);
		gbc_txtNetIncome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNetIncome.gridx = 5;
		gbc_txtNetIncome.gridy = 4;
		pnlJobInfo.add(netIncomeTextField, gbc_txtNetIncome);
		JLabel lblCheckbookEntry = new JLabel("Checkbook Entry: ");
		GridBagConstraints gbc_lblCheckbookEntry = new GridBagConstraints();
		gbc_lblCheckbookEntry.anchor = GridBagConstraints.EAST;
		gbc_lblCheckbookEntry.insets = new Insets(0, 0, 0, 5);
		gbc_lblCheckbookEntry.gridx = 4;
		gbc_lblCheckbookEntry.gridy = 5;
		pnlJobInfo.add(lblCheckbookEntry, gbc_lblCheckbookEntry);

		checkbookEntryTextField = new JTextField();
		checkbookEntryTextField.setEnabled(false);
		GridBagConstraints gbc_txtCheckbookEntry = new GridBagConstraints();
		gbc_txtCheckbookEntry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCheckbookEntry.gridx = 5;
		gbc_txtCheckbookEntry.gridy = 5;
		pnlJobInfo.add(checkbookEntryTextField, gbc_txtCheckbookEntry);

		return pnlJobInfo;
	} // end drawJobinfo() method

	/**
	 * Set the form fields to the survey
	 */
	public void setInitialSurveyValues() {

		firstNameTextField.setText(survey.getFName());
		lastNameTextField.setText(survey.getLName());
		periodComboBox.setSelectedIndex(survey.getCPeriod() - 1);
		educationComboBox.setSelectedIndex(survey.getEducation());
		GPAComboBox.setSelectedIndex(survey.getGPA());
		teacherComboBox.setSelectedItem(survey.getTeacher());
		preferredJobCategoryComboBox.setSelectedItem(Controller
				.getControllerInstance()
				.searchJobsList("id",
						Integer.toString(survey.getPreferredJob())).get(0)
				.getCategory());
		preferredJobComboBox.setSelectedItem(Controller
				.getControllerInstance()
				.searchJobsList("id",
						Integer.toString(survey.getPreferredJob())).get(0)
				.getName());
		// Gender
		if (survey.getGender() == 0) {
			maleGenderRadioButton.setSelected(true);
		} else {
			femaleGenderRadioButton.setSelected(true);
		}
		// Married
		if (survey.getMarried() == 1) {
			yesMarriedRadioButton.setSelected(true);
		} else {
			noMarriedRadioButton.setSelected(true);
		}
		// Children
		if (survey.getChildren() > 0) {
			yesChildrenRadioButton.setSelected(true);
			childrenCountComboBox.setSelectedItem(survey.getChildren());
		} else {
			noChildrenRadioButton.setSelected(true);
			childrenCountComboBox.setEnabled(false);
		}
		// Credit Cards
		if (survey.getCreditCards() == 1) {
			yesCreditCardsRadioButton.setSelected(true);
			creditCardUsesComboBox.setSelectedIndex(survey.getCreditCardUses());
		} else {
			noCreditCardsRadioButton.setSelected(true);
			creditCardUsesComboBox.setEnabled(false);
		}

		System.out.println("firstNameTextField's value is "
				+ firstNameTextField.getText());

	} // end setSurveyForm() method

	/**
	 * Gets the information currently entered in a survey
	 * 
	 * @return a Survey object
	 * @throws NullPointerException
	 */
	public Survey updateSurvey() throws NullPointerException {

		Survey updatedSurvey = new Survey();

		updatedSurvey.setID(survey.getID());
		updatedSurvey.setGroupID(Controller.getControllerInstance().getGroup()
				.getID());

		updatedSurvey.setTeacher(teacherComboBox.getSelectedItem().toString());
		updatedSurvey.setFName(firstNameTextField.getText());
		updatedSurvey.setLName(lastNameTextField.getText());
		updatedSurvey.setCPeriod(Integer.parseInt(periodComboBox
				.getSelectedItem().toString()));

		updatedSurvey.setGPA(GPAComboBox.getSelectedIndex());

		updatedSurvey.setEducation(educationComboBox.getSelectedIndex());
		// Go to the controller
		// Get the job (it will be returned as a list so you have to .get(0)
		// Get the ID
		// updatedSurvey.setPreferredJob(Controller.getControllerInstance()
		// .searchJobsList("name", "'" +
		// preferredJobComboBox.getSelectedItem().toString() + "'")
		// .get(0).getID());

		int intJobID = Controller
				.getControllerInstance()
				.searchJobsList("name",
						preferredJobComboBox.getSelectedItem().toString())
				.get(0).getID();

		updatedSurvey.setPreferredJob(intJobID);

		updatedSurvey.setGender(1);

		updatedSurvey.setMarried(0);
		updatedSurvey.setChildren(0);
		updatedSurvey.setCreditCards(0);

		boolean genderMale = maleGenderRadioButton.isSelected();
		boolean married = yesMarriedRadioButton.isSelected();
		boolean children = yesChildrenRadioButton.isSelected();
		boolean ccards = yesCreditCardsRadioButton.isSelected();

		if (genderMale)
			updatedSurvey.setGender(0);
		if (married) {
			updatedSurvey.setMarried(1);
		} else {
			updatedSurvey.setSpouse(0);
		}
		if (children)
			updatedSurvey.setChildren(Integer.parseInt(childrenCountComboBox
					.getSelectedItem().toString()));
		if (ccards) {
			updatedSurvey.setCreditCards(1);
			updatedSurvey.setCreditCardUses(creditCardUsesComboBox
					.getSelectedIndex());
		}

		return updatedSurvey;
	} // -- end getSurveyForm() method

	public boolean validateSurvey() {

		// Validate GPA
		if (GPAComboBox.getSelectedIndex() == 0) {

			return false;
		}

		System.out.println("firstNameTextField's value is "
				+ firstNameTextField.getText());

		return true;
	} // -- end validateSurvey() method
} // end EditSurvey class