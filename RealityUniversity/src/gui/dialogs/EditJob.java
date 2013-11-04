package gui.dialogs;

import gui.GuiInterface;
import gui.custom.RoundPanel;
import gui.custom.StatusTip;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import obj.Job;

import org.netbeans.validation.api.ui.swing.ValidationPanel;

import ctrl.Controller;

public class EditJob extends JDialog implements GuiInterface {

	private ValidationPanel vpnlSurvey = new ValidationPanel();

	private Job job;
	private String action;

	private JTextField txtName;
	private JTextField txtAnnGrossSal;
	private JTextField txtMonGrossSal;
	private JTextField txtMarAnnualTax;
	private JTextField txtMarMonthlyTax;
	private JTextField txtMarAfterTax;
	private JTextField txtSinAnnualTax;
	private JTextField txtSinMonthlyTax;
	private JTextField txtSinAfterTax;
	private JTextField txtLoan;

	private List<String> lstTypes = Controller.getControllerInstance().getJobTypesList();
	private List<String> lstIndustries = Controller.getControllerInstance()
			.getJobIndustriesList();
	private List<String> lstCategories = Controller.getControllerInstance()
			.getJobCategoriesList();

	private JComboBox<String> cboType = new JComboBox<>(
			new DefaultComboBoxModel(lstTypes.toArray()));
	private JComboBox<String> cboIndustry = new JComboBox<>(
			new DefaultComboBoxModel(lstIndustries.toArray()));
	private JComboBox<String> cboCategory = new JComboBox<>(
			new DefaultComboBoxModel(lstCategories.toArray()));
	private JComboBox<String> cboGPA = new JComboBox<>(ARR_GPA);

	public EditJob(final Job job) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("BEGIN EDITJOB CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all

		try {
			job.getID();
			this.action = "update";
			this.job = job;
		} catch (NullPointerException npe) {
			this.action = "new";
			this.job = new Job();
		}

		/*********************************
		 * Configurations
		 *********************************/
		setSize(EDIT_JOB_WIDTH, EDIT_JOB_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setBackground(FRAME_BACKGROUND);
		setModal(true);
		setAlwaysOnTop(true);

		RoundPanel pnlMain = new RoundPanel();
		JPanel pnlContent = new JPanel();
		JPanel pnlFooter = new JPanel();
		JButton btnOK = new JButton("OK");
		JButton btnReset = new JButton("Reset");

		pnlFooter.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnlFooter.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		pnlMain.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		pnlMain.setLayout(new BorderLayout(0, 0));
		pnlContent.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

		vpnlSurvey.setBorder(null);
		vpnlSurvey.setInnerComponent(drawJob());
		vpnlSurvey.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

		pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));

		btnOK.setToolTipText("Confirm Changes");

		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				if (vpnlSurvey.isFatalProblem()) {
					new StatusTip("Error: Please check form values.",
							LG_EXCEPTION);
				} else {
					Job nJob = getJobForm();
					if (action.equals("update")) {
						Controller.getControllerInstance().updateJob(job, nJob);
					}
					else {
						Controller.getControllerInstance().addJob(nJob);
					}
					ManageJobs.getManageJobsInstance().setVisible(true);
					ManageJobs.getManageJobsInstance().btnApply.setEnabled(true);
					dispose();
				}
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			} // -- end actionPerformed method
		});

		pnlFooter.add(btnReset);
		pnlFooter.add(btnOK);
		pnlContent.add(drawHeader());
		pnlContent.add(vpnlSurvey);
		pnlMain.add(pnlContent, BorderLayout.CENTER);
		pnlContent.add(pnlFooter, BorderLayout.SOUTH);
		getContentPane().add(pnlMain);
		setJobForm();

		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				dispose();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			} // -- end windowClosed() method event

			public void windowClosing(WindowEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				dispose();
				ManageJobs.getManageJobsInstance().setVisible(true);
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			} // end windowClosing() method event
		});
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end constructor

	public JPanel drawHeader() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		JPanel pnlHeader = new JPanel();
		JLabel lblLogo = new JLabel("Logo");

		JLabel lblTitle = new JLabel(this.action.substring(0, 1).toUpperCase()
				+ this.action.substring(1) + " Job", JLabel.RIGHT);

		pnlHeader.setLayout(new GridLayout(0, 2));
		pnlHeader.setBorder(new EmptyBorder(15, 5, 15, 5));
		pnlHeader.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

		lblLogo = new JLabel(new ImageIcon(MISC_01), JLabel.LEFT);
		lblTitle.setFont(FNT_JOB_TEXT);
		lblTitle.setForeground(CLR_JOB_TEXT);
		lblTitle.setBorder(new EmptyBorder(5, 5, 5, 25));

		pnlHeader.add(lblLogo);
		pnlHeader.add(lblTitle);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set

		return pnlHeader;
	} // -- end drawHeader() method

	public JPanel drawJob() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		JPanel pnlJob = new JPanel();

		// Main Panel
		pnlJob.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		pnlJob.setBorder(new TitledBorder(null, "Edit Job",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_pnlJob = new GridBagLayout();
		gbl_pnlJob.columnWidths = new int[] { 91, 157, 43, 79, 130, 0 };
		gbl_pnlJob.rowHeights = new int[] { 0, 0, 70, 0, 70, 0, 0, 0, 70, 0, 0,
				0 };
		gbl_pnlJob.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pnlJob.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		pnlJob.setLayout(gbl_pnlJob);

		JLabel lblJobName = new JLabel("Job Name:");
		GridBagConstraints gbc_lblJobName = new GridBagConstraints();
		gbc_lblJobName.insets = new Insets(0, 0, 5, 5);
		gbc_lblJobName.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblJobName.gridx = 0;
		gbc_lblJobName.gridy = 0;
		pnlJob.add(lblJobName, gbc_lblJobName);

		txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.anchor = GridBagConstraints.NORTH;
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 0;
		pnlJob.add(txtName, gbc_txtName);
		txtName.setColumns(20);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setToolTipText("Category the Job Is In");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 3;
		gbc_lblCategory.gridy = 0;
		pnlJob.add(lblCategory, gbc_lblCategory);
		cboCategory.setToolTipText("Category the Job Is In");

		cboCategory.setEditable(true);
		GridBagConstraints gbc_cboCategory = new GridBagConstraints();
		gbc_cboCategory.insets = new Insets(0, 0, 5, 0);
		gbc_cboCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboCategory.gridx = 4;
		gbc_cboCategory.gridy = 0;
		pnlJob.add(cboCategory, gbc_cboCategory);

		JLabel lblType = new JLabel("Type:");
		lblType.setToolTipText("(Used for processing) Type of Job");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 1;
		pnlJob.add(lblType, gbc_lblType);
		cboType.setToolTipText("(Used for processing) Type of Job");
		GridBagConstraints gbc_cboType = new GridBagConstraints();
		gbc_cboType.insets = new Insets(0, 0, 5, 5);
		gbc_cboType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboType.gridx = 1;
		gbc_cboType.gridy = 1;
		pnlJob.add(cboType, gbc_cboType);

		JLabel lblIndustry = new JLabel("Industry:");
		lblIndustry
				.setToolTipText("(Used for processing) Industry the Job Is In");
		GridBagConstraints gbc_lblIndustry = new GridBagConstraints();
		gbc_lblIndustry.anchor = GridBagConstraints.EAST;
		gbc_lblIndustry.insets = new Insets(0, 0, 5, 5);
		gbc_lblIndustry.gridx = 3;
		gbc_lblIndustry.gridy = 1;
		pnlJob.add(lblIndustry, gbc_lblIndustry);
		cboIndustry
				.setToolTipText("(Used for processing) Industry the Job Is In");

		cboIndustry.setEditable(true);
		GridBagConstraints gbc_cboIndustry = new GridBagConstraints();
		gbc_cboIndustry.insets = new Insets(0, 0, 5, 0);
		gbc_cboIndustry.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboIndustry.gridx = 4;
		gbc_cboIndustry.gridy = 1;
		pnlJob.add(cboIndustry, gbc_cboIndustry);

		JLabel lblAnnGrossSal = new JLabel("Annual Salary:");
		GridBagConstraints gbc_lblAnnGrossSal = new GridBagConstraints();
		gbc_lblAnnGrossSal.anchor = GridBagConstraints.EAST;
		gbc_lblAnnGrossSal.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnnGrossSal.gridx = 0;
		gbc_lblAnnGrossSal.gridy = 3;
		pnlJob.add(lblAnnGrossSal, gbc_lblAnnGrossSal);

		txtAnnGrossSal = new JTextField();
		GridBagConstraints gbc_txtAnnGrossSal = new GridBagConstraints();
		gbc_txtAnnGrossSal.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnnGrossSal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnnGrossSal.gridx = 1;
		gbc_txtAnnGrossSal.gridy = 3;
		pnlJob.add(txtAnnGrossSal, gbc_txtAnnGrossSal);
		txtAnnGrossSal.setColumns(10);

		JLabel lblMonGrossSal = new JLabel("Monthly Salary:");
		GridBagConstraints gbc_lblMonGrossSal = new GridBagConstraints();
		gbc_lblMonGrossSal.anchor = GridBagConstraints.EAST;
		gbc_lblMonGrossSal.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonGrossSal.gridx = 3;
		gbc_lblMonGrossSal.gridy = 3;
		pnlJob.add(lblMonGrossSal, gbc_lblMonGrossSal);

		txtMonGrossSal = new JTextField();
		txtMonGrossSal.setColumns(10);
		GridBagConstraints gbc_txtMonGrossSal = new GridBagConstraints();
		gbc_txtMonGrossSal.insets = new Insets(0, 0, 5, 0);
		gbc_txtMonGrossSal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMonGrossSal.gridx = 4;
		gbc_txtMonGrossSal.gridy = 3;
		pnlJob.add(txtMonGrossSal, gbc_txtMonGrossSal);

		JLabel lblMarAnnualTax = new JLabel("Married Annual Tax:");
		lblMarAnnualTax.setToolTipText("Annual Tax if Married");
		GridBagConstraints gbc_lblMarAnnualTax = new GridBagConstraints();
		gbc_lblMarAnnualTax.anchor = GridBagConstraints.EAST;
		gbc_lblMarAnnualTax.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarAnnualTax.gridx = 0;
		gbc_lblMarAnnualTax.gridy = 5;
		pnlJob.add(lblMarAnnualTax, gbc_lblMarAnnualTax);

		txtMarAnnualTax = new JTextField();
		txtMarAnnualTax.setToolTipText("Annual Tax if Married");
		txtMarAnnualTax.setColumns(10);
		GridBagConstraints gbc_txtMarAnnualTax = new GridBagConstraints();
		gbc_txtMarAnnualTax.insets = new Insets(0, 0, 5, 5);
		gbc_txtMarAnnualTax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMarAnnualTax.gridx = 1;
		gbc_txtMarAnnualTax.gridy = 5;
		pnlJob.add(txtMarAnnualTax, gbc_txtMarAnnualTax);

		JLabel lblSinAnnualTax = new JLabel("Single Annual Tax:");
		lblSinAnnualTax.setToolTipText("Annual Tax if Single");
		GridBagConstraints gbc_lblSinAnnualTax = new GridBagConstraints();
		gbc_lblSinAnnualTax.anchor = GridBagConstraints.EAST;
		gbc_lblSinAnnualTax.insets = new Insets(0, 0, 5, 5);
		gbc_lblSinAnnualTax.gridx = 3;
		gbc_lblSinAnnualTax.gridy = 5;
		pnlJob.add(lblSinAnnualTax, gbc_lblSinAnnualTax);

		txtSinAnnualTax = new JTextField();
		txtSinAnnualTax.setToolTipText("Annual Tax if Single");
		txtSinAnnualTax.setColumns(10);
		GridBagConstraints gbc_txtSinAnnualTax = new GridBagConstraints();
		gbc_txtSinAnnualTax.insets = new Insets(0, 0, 5, 0);
		gbc_txtSinAnnualTax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSinAnnualTax.gridx = 4;
		gbc_txtSinAnnualTax.gridy = 5;
		pnlJob.add(txtSinAnnualTax, gbc_txtSinAnnualTax);

		JLabel lblMarMonthlyTax = new JLabel("Married Monthly Tax:");
		lblMarMonthlyTax.setToolTipText("Monthly Tax if Married");
		GridBagConstraints gbc_lblMarMonthlyTax = new GridBagConstraints();
		gbc_lblMarMonthlyTax.anchor = GridBagConstraints.EAST;
		gbc_lblMarMonthlyTax.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarMonthlyTax.gridx = 0;
		gbc_lblMarMonthlyTax.gridy = 6;
		pnlJob.add(lblMarMonthlyTax, gbc_lblMarMonthlyTax);

		txtMarMonthlyTax = new JTextField();
		txtMarMonthlyTax.setToolTipText("Monthly Tax if Married");
		txtMarMonthlyTax.setColumns(10);
		GridBagConstraints gbc_txtMarMonthlyTax = new GridBagConstraints();
		gbc_txtMarMonthlyTax.insets = new Insets(0, 0, 5, 5);
		gbc_txtMarMonthlyTax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMarMonthlyTax.gridx = 1;
		gbc_txtMarMonthlyTax.gridy = 6;
		pnlJob.add(txtMarMonthlyTax, gbc_txtMarMonthlyTax);

		JLabel lblSinMonthlyTax = new JLabel("Single Monthly Tax:");
		lblSinMonthlyTax.setToolTipText("Monthly Tax if Single");
		GridBagConstraints gbc_lblSinMonthlyTax = new GridBagConstraints();
		gbc_lblSinMonthlyTax.anchor = GridBagConstraints.EAST;
		gbc_lblSinMonthlyTax.insets = new Insets(0, 0, 5, 5);
		gbc_lblSinMonthlyTax.gridx = 3;
		gbc_lblSinMonthlyTax.gridy = 6;
		pnlJob.add(lblSinMonthlyTax, gbc_lblSinMonthlyTax);

		txtSinMonthlyTax = new JTextField();
		txtSinMonthlyTax.setToolTipText("Monthly Tax if Single");
		txtSinMonthlyTax.setColumns(10);
		GridBagConstraints gbc_txtSinMonthlyTax = new GridBagConstraints();
		gbc_txtSinMonthlyTax.insets = new Insets(0, 0, 5, 0);
		gbc_txtSinMonthlyTax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSinMonthlyTax.gridx = 4;
		gbc_txtSinMonthlyTax.gridy = 6;
		pnlJob.add(txtSinMonthlyTax, gbc_txtSinMonthlyTax);

		JLabel lblMarAfterTax = new JLabel("Married After Tax:");
		lblMarAfterTax.setToolTipText("Salary After Taxes if Married");
		GridBagConstraints gbc_lblMarAfterTax = new GridBagConstraints();
		gbc_lblMarAfterTax.anchor = GridBagConstraints.EAST;
		gbc_lblMarAfterTax.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarAfterTax.gridx = 0;
		gbc_lblMarAfterTax.gridy = 7;
		pnlJob.add(lblMarAfterTax, gbc_lblMarAfterTax);

		txtMarAfterTax = new JTextField();
		txtMarAfterTax.setToolTipText("Salary After Taxes if Married");
		txtMarAfterTax.setColumns(10);
		GridBagConstraints gbc_txtMarAfterTax = new GridBagConstraints();
		gbc_txtMarAfterTax.insets = new Insets(0, 0, 5, 5);
		gbc_txtMarAfterTax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMarAfterTax.gridx = 1;
		gbc_txtMarAfterTax.gridy = 7;
		pnlJob.add(txtMarAfterTax, gbc_txtMarAfterTax);

		JLabel lblSinAfterTax = new JLabel("Single After Tax:");
		lblSinAfterTax.setToolTipText("Salary After Taxes if Single");
		GridBagConstraints gbc_lblSinAfterTax = new GridBagConstraints();
		gbc_lblSinAfterTax.anchor = GridBagConstraints.EAST;
		gbc_lblSinAfterTax.insets = new Insets(0, 0, 5, 5);
		gbc_lblSinAfterTax.gridx = 3;
		gbc_lblSinAfterTax.gridy = 7;
		pnlJob.add(lblSinAfterTax, gbc_lblSinAfterTax);

		txtSinAfterTax = new JTextField();
		txtSinAfterTax.setToolTipText("Salary After Taxes if Single");
		txtSinAfterTax.setColumns(10);
		GridBagConstraints gbc_txtSinAfterTax = new GridBagConstraints();
		gbc_txtSinAfterTax.insets = new Insets(0, 0, 5, 0);
		gbc_txtSinAfterTax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSinAfterTax.gridx = 4;
		gbc_txtSinAfterTax.gridy = 7;
		pnlJob.add(txtSinAfterTax, gbc_txtSinAfterTax);

		JLabel lblGPA = new JLabel("GPA:");
		lblGPA.setToolTipText("Minimum GPA for Job");
		GridBagConstraints gbc_lblGPA = new GridBagConstraints();
		gbc_lblGPA.anchor = GridBagConstraints.EAST;
		gbc_lblGPA.insets = new Insets(0, 0, 5, 5);
		gbc_lblGPA.gridx = 0;
		gbc_lblGPA.gridy = 9;
		pnlJob.add(lblGPA, gbc_lblGPA);

		cboGPA.setToolTipText("Minimum GPA for Job");
		GridBagConstraints gbc_cboGPA = new GridBagConstraints();
		gbc_cboGPA.insets = new Insets(0, 0, 5, 5);
		gbc_cboGPA.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboGPA.gridx = 1;
		gbc_cboGPA.gridy = 9;
		pnlJob.add(cboGPA, gbc_cboGPA);

		JLabel lblLoan = new JLabel("Loans:");
		lblLoan.setToolTipText("College Loans Required for Job");
		GridBagConstraints gbc_lblLoan = new GridBagConstraints();
		gbc_lblLoan.anchor = GridBagConstraints.EAST;
		gbc_lblLoan.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoan.gridx = 3;
		gbc_lblLoan.gridy = 9;
		pnlJob.add(lblLoan, gbc_lblLoan);

		txtLoan = new JTextField();
		txtLoan.setToolTipText("College Loans Required for Job");
		GridBagConstraints gbc_txtLoans = new GridBagConstraints();
		gbc_txtLoans.insets = new Insets(0, 0, 5, 0);
		gbc_txtLoans.anchor = GridBagConstraints.NORTH;
		gbc_txtLoans.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLoans.gridx = 4;
		gbc_txtLoans.gridy = 9;
		pnlJob.add(txtLoan, gbc_txtLoans);
		txtLoan.setColumns(10);

		txtAnnGrossSal.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			};

			public void focusLost(FocusEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				if (!e.isTemporary()) {
					Double annualSalary = Double.parseDouble(txtAnnGrossSal
							.getText());
					Double monthlySalary = annualSalary / 12;
					Double singleAfterTaxes;
					Double marriedAfterTaxes;

					Double stateTaxMarried = 0.0;
					Double fedTaxMarried = 0.0;
					Double stateTaxSingle = 0.0;
					Double fedTaxSingle = 0.0;

					// Calculate State (GA) Tax for Single
					if (monthlySalary > 0 && monthlySalary < 750)
						stateTaxSingle = monthlySalary * .01;
					if (monthlySalary > 751 && monthlySalary < 2250)
						stateTaxSingle = monthlySalary * .02;
					if (monthlySalary > 2251 && monthlySalary < 3750)
						stateTaxSingle = monthlySalary * .03;
					if (monthlySalary > 3751 && monthlySalary < 5250)
						stateTaxSingle = monthlySalary * .04;
					if (monthlySalary > 5251 && monthlySalary < 7000)
						stateTaxSingle = monthlySalary * .05;
					if (monthlySalary > 7001)
						stateTaxSingle = monthlySalary * .06;

					// Calculate State (GA) Tax for Married
					if (monthlySalary > 0 && monthlySalary < 500)
						stateTaxMarried = monthlySalary * .01;
					if (monthlySalary > 501 && monthlySalary < 1500)
						stateTaxMarried = monthlySalary * .02 + 5.00;
					if (monthlySalary > 1501 && monthlySalary < 2500)
						stateTaxMarried = monthlySalary * .03 + 25.00;
					if (monthlySalary > 2501 && monthlySalary < 3500)
						stateTaxMarried = monthlySalary * .04 + 55.00;
					if (monthlySalary > 3501 && monthlySalary < 5000)
						stateTaxMarried = monthlySalary * .05 + 95.00;
					if (monthlySalary > 5001)
						stateTaxMarried = monthlySalary * .06 + 170.00;

					// Calculate Federal Tax for Single
					if (monthlySalary > 92 && monthlySalary < 464)
						fedTaxSingle = monthlySalary * .10;
					if (monthlySalary > 465 && monthlySalary < 1602)
						fedTaxSingle = monthlySalary * .15;
					if (monthlySalary > 1603 && monthlySalary < 3752)
						fedTaxSingle = monthlySalary * .25;
					if (monthlySalary > 3753 && monthlySalary < 7727)
						fedTaxSingle = monthlySalary * .28;
					if (monthlySalary > 7728 && monthlySalary < 16690)
						fedTaxSingle = monthlySalary * .33;
					if (monthlySalary > 16691 && monthlySalary < 16758)
						fedTaxSingle = monthlySalary * .35;
					if (monthlySalary > 16759)
						fedTaxSingle = monthlySalary * .40;

					// Calculate Federal Tax for Married
					if (monthlySalary > 346 && monthlySalary < 1090)
						fedTaxMarried = monthlySalary * .10;
					if (monthlySalary > 1091 && monthlySalary < 3367)
						fedTaxMarried = monthlySalary * .15;
					if (monthlySalary > 3368 && monthlySalary < 6446)
						fedTaxMarried = monthlySalary * .25;
					if (monthlySalary > 6447 && monthlySalary < 9640)
						fedTaxMarried = monthlySalary * .28;
					if (monthlySalary > 9641 && monthlySalary < 16944)
						fedTaxMarried = monthlySalary * .33;
					if (monthlySalary > 16945 && monthlySalary < 19096)
						fedTaxMarried = monthlySalary * .35;
					if (monthlySalary > 19097)
						fedTaxMarried = monthlySalary * .40;

					marriedAfterTaxes = monthlySalary
							- (stateTaxMarried + fedTaxMarried);
					singleAfterTaxes = monthlySalary
							- (stateTaxSingle + fedTaxSingle);

					txtMonGrossSal.setText(String.valueOf(monthlySalary));
					txtMarMonthlyTax.setText(String.valueOf(stateTaxMarried
							+ fedTaxMarried));
					txtMarAnnualTax.setText(String
							.valueOf((stateTaxMarried + fedTaxMarried) * 12));
					txtMarAfterTax.setText(String.valueOf(marriedAfterTaxes));
					
					txtSinMonthlyTax.setText(String.valueOf(stateTaxSingle
							+ fedTaxSingle));
					txtSinAnnualTax.setText(String
							.valueOf((stateTaxSingle + fedTaxSingle) * 12));
					txtSinAfterTax.setText(String.valueOf(singleAfterTaxes));
				}
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			} // -- end focusLost() method
		});

		txtMarAnnualTax.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("--Entering " + methodName);
				// end of debugging statement set - 4 lines in all
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			};

			public void focusLost(FocusEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println(methodName);
				// end of debugging statement set - 4 lines in all
			    
				if (!e.isTemporary()) {
					Double annualTax = Double.parseDouble(txtMarAnnualTax
							.getText());
					Double monthlyTax = annualTax / 12;

					txtMarMonthlyTax.setText(String.valueOf(monthlyTax));
				}
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			} // -- end focusLost() method
		});

		txtSinAnnualTax.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			};

			public void focusLost(FocusEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				if (!e.isTemporary()) {
					Double annualTax = Double.parseDouble(txtSinAnnualTax
							.getText());
					Double monthlyTax = annualTax / 12;

					txtSinMonthlyTax.setText(String.valueOf(monthlyTax));
				}
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			} // -- end focusLost() method
		});
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set

		return pnlJob;
	} // -- end drawJob() method

	/**
	 * Set the form fields to the job
	 */
	public void setJobForm() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		try {
			txtName.setText(job.getName());
			cboType.setSelectedItem(job.getType());
			cboIndustry.setSelectedItem(job.getIndustry());
			cboCategory.setSelectedItem(job.getCategory());
			cboGPA.setSelectedIndex(job.getGPA());
			txtAnnGrossSal.setText(String.valueOf(job.getAnnGrossSal()));
			txtMonGrossSal.setText(String.valueOf(job.getMonGrossSal()));
			txtMarAnnualTax.setText(String.valueOf(job.getMarAnnualTax()));
			txtMarMonthlyTax.setText(String.valueOf(job.getMarMonthlyTax()));
			txtMarAfterTax.setText(String.valueOf(job.getMarAfterTax()));
			txtSinAnnualTax.setText(String.valueOf(job.getSinAnnualTax()));
			txtSinMonthlyTax.setText(String.valueOf(job.getSinMonthlyTax()));
			txtSinAfterTax.setText(String.valueOf(job.getSinAfterTax()));
			txtLoan.setText(String.valueOf(job.getLoan()));
		} catch (NullPointerException npe) {
			// If we are creating a new job
			txtName.setText("");
			cboType.setSelectedItem("Select Type");
			cboIndustry.setSelectedItem("Select Industry");
			cboCategory.setSelectedItem("Select Category");
			cboGPA.setSelectedIndex(0);
		}
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set

	} // -- end jobForm() method

	/**
	 * Gets the information currently entered in a job
	 * 
	 * @return a Job object
	 * @throws NullPointerException
	 */
	public Job getJobForm() throws NullPointerException {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all

		Job newJob = new Job();

		newJob.setID(job.getID());
		newJob.setName(txtName.getText());

		newJob.setType(cboType.getSelectedItem().toString());
		newJob.setIndustry(cboIndustry.getSelectedItem().toString());
		newJob.setCategory(cboCategory.getSelectedItem().toString());
		newJob.setGPA(cboGPA.getSelectedIndex());

		newJob.setAnnGrossSal(Double.parseDouble(txtAnnGrossSal.getText()));
		newJob.setMonGrossSal(Double.parseDouble(txtMonGrossSal.getText()));
		newJob.setMarAnnualTax(Double.parseDouble(txtMarAnnualTax.getText()));
		newJob.setMarMonthlyTax(Double.parseDouble(txtMarMonthlyTax.getText()));
		newJob.setMarAfterTax(Double.parseDouble(txtMarAfterTax.getText()));
		newJob.setSinAnnualTax(Double.parseDouble(txtSinAnnualTax.getText()));
		newJob.setSinMonthlyTax(Double.parseDouble(txtSinMonthlyTax.getText()));
		newJob.setSinAfterTax(Double.parseDouble(txtSinAfterTax.getText()));
		newJob.setLoan(Double.parseDouble(txtLoan.getText()));
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set

		return newJob;
	} // -- end getJobForm() method
}