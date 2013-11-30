package gui.sidebars;

import gui.GuiInterface;
import gui.custom.RoundPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
//import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

import obj.Group;
import ctrl.Controller;

/**
 * The Class GroupInfo extends from a JPanel to create a display of the current
 * group's information.
 */
public class GroupInfoUpperRightSidePanel extends RoundPanel implements
		GuiInterface {

	/**
	 * Constructor - it's the whole class pretty much.
	 */
	public GroupInfoUpperRightSidePanel() {

		/*********************************
		 * Declarations
		 *********************************/
		// This is the panel that contains the actual information that we
		// actually see
		JPanel groupInfoPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane();

		Group group;
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
				"M/d/yyyy - hh:mm:ss a");

		if (Controller.getControllerInstance().getGroup() == null) {
			group = new Group();
		} else {
			group = Controller.getControllerInstance().getGroup();
		}

		/*********************************
		 * Configurations
		 *********************************/
		setBackground(TOPGROUPPANEL_BACKGROUND_YELLOW); // Not sure if this
														// matters
		setBorder(new EmptyBorder(0, 0, 0, 0));

		scrollPane.setBorder(new EmptyBorder(5, 7, 5, 10));
		scrollPane.setPreferredSize(new Dimension(200, 200)); // experimental
		scrollPane.setViewportView(groupInfoPanel);
		groupInfoPanel.setBackground(TOPGROUPPANEL_BACKGROUND_YELLOW); // yellow
																		// - set
																		// by
																		// Steve
																		// for
																		// identification

		/*********************************
		 * Assembly
		 *********************************/

		// GridBagLayout declaration
		GridBagLayout gbl_pnlSummary = new GridBagLayout();
		gbl_pnlSummary.columnWidths = new int[] { 32, 34, 0 };
		gbl_pnlSummary.rowHeights = new int[] { 14, 0, 0, 0, 0 };
		gbl_pnlSummary.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pnlSummary.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		groupInfoPanel.setLayout(gbl_pnlSummary);

		// first GridBadConstraints declaration
		JLabel lblName = new JLabel("Name: ");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.BOTH;
		gbc_lblName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		groupInfoPanel.add(lblName, gbc_lblName);

		// second GridBagConstraints declaration
		JLabel strName = new JLabel(group.getName());
		GridBagConstraints gbc_strName = new GridBagConstraints();
		gbc_strName.fill = GridBagConstraints.BOTH;
		gbc_strName.anchor = GridBagConstraints.NORTHWEST;
		gbc_strName.insets = new Insets(0, 0, 5, 0);
		gbc_strName.gridx = 1;
		gbc_strName.gridy = 0;
		groupInfoPanel.add(strName, gbc_strName);

		// third GridBagConstraints declaration
		JLabel lblNoOfSurveys = new JLabel("Surveys: ");
		GridBagConstraints gbc_lblNoOfSurveys = new GridBagConstraints();
		gbc_lblNoOfSurveys.fill = GridBagConstraints.BOTH;
		gbc_lblNoOfSurveys.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNoOfSurveys.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoOfSurveys.gridx = 0;
		gbc_lblNoOfSurveys.gridy = 1;
		groupInfoPanel.add(lblNoOfSurveys, gbc_lblNoOfSurveys);

		// fourth GridBagConstraints declaration
		int groupSize = Controller.getControllerInstance()
				.getSurveysList("GroupID", Integer.toString(group.getID()))
				.size();
		JLabel strNoOfSurveys = new JLabel("" + groupSize);
		GridBagConstraints gbc_strNoOfSurveys = new GridBagConstraints();
		gbc_lblNoOfSurveys.fill = GridBagConstraints.BOTH;
		gbc_strNoOfSurveys.anchor = GridBagConstraints.NORTHWEST;
		gbc_strNoOfSurveys.insets = new Insets(0, 0, 5, 0);
		gbc_strNoOfSurveys.gridx = 1;
		gbc_strNoOfSurveys.gridy = 1;
		groupInfoPanel.add(strNoOfSurveys, gbc_strNoOfSurveys);

		// fifth GridBagConstraints declaration
		JLabel lblCreated = new JLabel("Created: ");
		GridBagConstraints gbc_lblCreated = new GridBagConstraints();
		gbc_lblCreated.fill = GridBagConstraints.BOTH;
		gbc_lblCreated.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCreated.insets = new Insets(0, 0, 5, 5);
		gbc_lblCreated.gridx = 0;
		gbc_lblCreated.gridy = 2;
		groupInfoPanel.add(lblCreated, gbc_lblCreated);

		// sixth GridBagConstraints declaration
		JLabel strCreated = new JLabel(""
				+ sdfDateFormat.format(group.getCreated()));
		GridBagConstraints gbc_strCreated = new GridBagConstraints();
		gbc_lblCreated.fill = GridBagConstraints.BOTH;
		gbc_strCreated.anchor = GridBagConstraints.NORTHWEST;
		gbc_strCreated.insets = new Insets(0, 0, 5, 0);
		gbc_strCreated.gridx = 1;
		gbc_strCreated.gridy = 2;
		groupInfoPanel.add(strCreated, gbc_strCreated);

		// seventh GridBagConstraints declaration
		JLabel lblModified = new JLabel("Last Modified: ");
		GridBagConstraints gbc_lblModified = new GridBagConstraints();
		gbc_lblModified.fill = GridBagConstraints.BOTH;
		gbc_lblModified.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblModified.insets = new Insets(0, 0, 5, 5);
		gbc_lblModified.gridx = 0;
		gbc_lblModified.gridy = 3;
		groupInfoPanel.add(lblModified, gbc_lblModified);

		// eighth GridBagConstraints declaration
		JLabel strModified = new JLabel("Never");
		GridBagConstraints gbc_strModified = new GridBagConstraints();
		gbc_strModified.fill = GridBagConstraints.BOTH;
		gbc_strModified.insets = new Insets(0, 0, 5, 0);
		gbc_strModified.anchor = GridBagConstraints.NORTHWEST;
		gbc_strModified.gridx = 1;
		gbc_strModified.gridy = 3;
		groupInfoPanel.add(strModified, gbc_strModified);

		// ninth GridBagConstraints declaration
		final JButton processSurveysButton = new JButton("Process Surveys");
		processSurveysButton.setFont(FNT_BIG_AND_BOLD);
		// ImageIcon imgIcon = new ImageIcon("../img/MD_PROCESS");
		processSurveysButton.setIcon(new ImageIcon(SM_PROCESS));
		processSurveysButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblProcess = new GridBagConstraints();
		gbc_lblProcess.fill = GridBagConstraints.BOTH;
		gbc_lblProcess.gridwidth = 2;
		gbc_lblProcess.anchor = GridBagConstraints.NORTH;
		gbc_lblProcess.insets = new Insets(0, 0, 0, 5);
		gbc_lblProcess.gridx = 0;
		gbc_lblProcess.gridy = 4;
		groupInfoPanel.add(processSurveysButton, gbc_lblProcess);

		processSurveysButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {

				System.out
						.println("************************************************************************************************");
				System.out.println("*   Process Surveys Button Clicked  *");
				System.out
						.println("************************************************************************************************");
				System.out
						.println("************************************************************************************************");

				// TODO Figure out why this part uses up so much CPU power!
				/**************************************
				 * This is the place where the problems start and make the
				 * program use almost 30% of my Ivy bridge processor! Wow!
				 **************************************/
				Controller.getControllerInstance().processGroup();

			} // -- end mouseClicked() method

			public void mouseEntered(MouseEvent arg0) {

				processSurveysButton.setForeground(HOVER_COLOR);

			} // -- end mouseEntered() method

			public void mouseExited(MouseEvent arg0) {

				processSurveysButton.setForeground(new Color(0, 0, 0));

			} // -- end mouseExited() method

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});

		if (group.getModified() != null)
			strModified.setText("" + sdfDateFormat.format(group.getModified()));
		setLayout(new GridLayout(0, 1, 0, 0));

		add(scrollPane);

	} // end constructor
} // end GroupInfoUpperRightSidePanel class