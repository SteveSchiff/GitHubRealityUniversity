package gui.sidebars;

import gui.GuiInterface;
import gui.custom.RoundPanel;
import gui.dialogs.EditSurvey;
import gui.dialogs.ViewSurvey;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import lib.print.PrintMultiplePages;
import obj.Group;
import obj.Survey;
import ctrl.Controller;

/**
 * The Class GroupSurveys extends from a JPanel to create a display of the
 * current group's surveys.  This panel is shown on the lower right of the
 * overall frame that we see when filling out a new survey.
 */
public class GroupSurveysLowerRightSidePanel extends RoundPanel implements GuiInterface {

	/******* Field ************/
	private static GroupSurveysLowerRightSidePanel lowerRightSidePanelInstance = null;
	private Group group = Controller.getControllerInstance().getGroup();	
	final List<Survey> listOfSurveys = null;
	

	/**
	 * Constructor
	 */
	public GroupSurveysLowerRightSidePanel() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("GROUPSURVEYSLOWERRIGHTSIDEPANEL CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all

		setBackground(FOOTPANEL_BACKGROUND_INDIANRED); // Indian Red by Steve
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(new Dimension(SIDEBAR_WIDTH, SIDEBAR_HEIGHT - 125));
		setPreferredSize(new Dimension(SIDEBAR_WIDTH, SIDEBAR_HEIGHT - 200));

		// if statement takes up the rest of the constructor
        // if the group exists, otherwise, there's no point in
		// executing the rest of the constructor
		if (Controller.getControllerInstance().getGroup().getID() > 0) { // outer if statement - ends on line 482

			JScrollPane scrollPane = new JScrollPane();
			JPanel surveysPanel = new JPanel();

			// the JPanel object pnlSurveys is contained by the
			// scrollPane object.
			scrollPane.setViewportView(surveysPanel); // insert surveysPanel into scrollPane to allow scrolling for long survey lists
			scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			scrollPane.setBackground(PANEL_BACKGROUNDLIGHTGREEN);

			// GridBagLayout declaration
			GridBagLayout gbl_surveysPanel = new GridBagLayout();
			gbl_surveysPanel.rowWeights = new double[] { 0.0 };
			surveysPanel.setLayout(gbl_surveysPanel); // insert gbl_pnlSurveys into surveysPanel
			surveysPanel.setBackground(FOOTPANEL_BACKGROUND_INDIANRED); // Indian Red by Steve

			// GridBagConstraint declaration
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.anchor = GridBagConstraints.NORTHWEST;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;

//			List<Survey> listOfNewSurveys = Controller.getControllerInstance()
//					.getNewSurveysList();
//			List<Survey> listOfDeletedSurveys = Controller.getControllerInstance()
//					.getDeletedSurveysList();
//			Group group = Controller.getControllerInstance().getGroup();
			Controller.getControllerInstance().setSQLselectWhereSurveysList(group);
			final List<Survey> listOfSurveys = Controller.getControllerInstance().getSurveysList();
			
			// debugging statements
			System.out.println("\n**********************************************************************");
//			System.out.println("listOfNewSurveys has " + listOfNewSurveys.size() + " surveys.");
//			System.out.println("listOfDeletedSurveys has " + listOfDeletedSurveys.size() + " surveys.");
			System.out.println("listOfSurveys has " + listOfSurveys.size() + " surveys.");
			System.out.println("Inside the GroupSurveysLowerRightSidePanel constructor");
			System.out.println("**********************************************************************\n");


			
			if (listOfSurveys.size() > 0) { // inner if statement - ends on line 448

				for (int row = 0; row < listOfSurveys.size(); row++) { // ends on line 359

					final Survey survey = listOfSurveys.get(row);
//					Survey survey = listOfSurveys.get(i);

					String firstName = survey.getFName();
					String lastName = survey.getLName();

					firstName = formatString(firstName, 10);
					lastName = formatString(lastName, 10);

					// Place them under New Surveys and Deleted Surveys
//					int row = i;
//					if (listOfNewSurveys.size() > 0) {
//						row += listOfNewSurveys.size();
//					}
//					if (listOfDeletedSurveys.size() > 0) {
//						row += listOfDeletedSurveys.size();
//					}

					String tooltip = "<html><table cellpadding='0' cellspacing='0' border='0'>"
							+ "<tr><td>Name: </td><td>"
							+ firstName
							+ " "
							+ lastName
							+ "</td></tr><tr><td>Class: </td><td>"
							+ survey.getCPeriod()
							+ "</td></tr><tr><td>Teacher: </td><td>"
							+ survey.getTeacher()
							+ "</td></tr>"
							+ "<tr><td>Preferred Job: </td><td>"
							+ Controller
									.getControllerInstance()
									.searchJobsList(
											"id",
											Integer.toString(survey
													.getPreferredJob())).get(0)
									.getName() + "</td></tr></table></html>";

					// GridBagContraint declaration - name label
					JLabel lblName = new JLabel((row + 1) + ". " + lastName + ", " + firstName, JLabel.LEFT);
					lblName.setToolTipText(tooltip);
					GridBagConstraints gbc_lblName = new GridBagConstraints();
					gbc_lblName.anchor = GridBagConstraints.NORTHWEST;
					gbc_lblName.insets = new Insets(0, 0, 5, 5);
					gbc_lblName.gridx = 0;
					gbc_lblName.gridy = row;
					gbc_lblName.weightx = 1.0;
					surveysPanel.add(lblName, gbc_lblName);

					// GridBagContraint declaration
					JLabel lblView = new JLabel(new ImageIcon(SM_VIEW),
							SwingConstants.RIGHT);
					GridBagConstraints gbc_lblView = new GridBagConstraints();
					gbc_lblView.anchor = GridBagConstraints.NORTHEAST;
					gbc_lblView.insets = new Insets(0, 0, 5, 5);
					gbc_lblView.gridx = 1;
					gbc_lblView.gridy = row;
					surveysPanel.add(lblView, gbc_lblView);

					// GridBagContraint declaration
					JLabel lblEdit = new JLabel(new ImageIcon(SM_EDIT),
							SwingConstants.RIGHT);
					GridBagConstraints gbc_lblEdit = new GridBagConstraints();
					gbc_lblEdit.anchor = GridBagConstraints.NORTHEAST;
					gbc_lblEdit.insets = new Insets(0, 0, 5, 5);
					gbc_lblEdit.gridx = 2;
					gbc_lblEdit.gridy = row;
					surveysPanel.add(lblEdit, gbc_lblEdit);

					// GridBagContraint declaration
					JLabel lblPrint = new JLabel(new ImageIcon(SM_PRINT),
							SwingConstants.RIGHT);
					GridBagConstraints gbc_lblPrint = new GridBagConstraints();
					gbc_lblPrint.insets = new Insets(0, 0, 5, 5);
					gbc_lblPrint.anchor = GridBagConstraints.NORTHEAST;
					gbc_lblPrint.gridx = 3;
					gbc_lblPrint.gridy = row;
					surveysPanel.add(lblPrint, gbc_lblPrint);

					// GridBagContraint declaration
					JLabel lblDelete = new JLabel(new ImageIcon(SM_DELETE),
							SwingConstants.RIGHT);
					GridBagConstraints gbc_lblDelete = new GridBagConstraints();
					gbc_lblDelete.insets = new Insets(0, 0, 5, 0);
					gbc_lblDelete.anchor = GridBagConstraints.NORTHEAST;
					gbc_lblDelete.gridx = 4;
					gbc_lblDelete.gridy = row;
					surveysPanel.add(lblDelete, gbc_lblDelete);

					// View option - mouseListener Six (6) Third if
					lblView.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {
							// the next four statements are for debugging purposes only
							StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
						    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
						    String methodName = tre.getClassName() + "." + tre.getMethodName();
						    System.out.println("*************************************");
						    System.out.println("*      -- View Icon Clicked--       *");
						    System.out.println("*                                   *");
						    System.out.println("*************************************");
						    System.out.println("---Entering " + methodName);
							// end of debugging statement set - 4 lines in all
						    
//							Controller.getControllerInstance().openViewSurvey(survey);
						    
						    ViewSurvey vSurvey = new ViewSurvey(survey);
							vSurvey.setVisible(true);    
							
							// the next statement is for debugging purposes only
						    System.out.println("\n---Leaving " + methodName);
							// end of debugging statement set
						} // end mouseClicked() method
						// Even though the next four methods are not
						// used, they have to be inherited.
						public void mouseEntered(MouseEvent arg0) {
						}

						public void mouseExited(MouseEvent arg0) {
						}

						public void mousePressed(MouseEvent arg0) {
						}

						public void mouseReleased(MouseEvent arg0) {
						}
					});

					// Edit option - mouseListener Seven (7) Third if
					lblEdit.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {		
							// the next four statements are for debugging purposes only
							StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
						    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
						    String methodName = tre.getClassName() + "." + tre.getMethodName();
						    System.out.println("*************************************");
						    System.out.println("*      -- Edit Icon Clicked--       *");
						    System.out.println("*                                   *");
						    System.out.println("*************************************");
						    System.out.println("---Entering " + methodName);
							// end of debugging statement set - 4 lines in all
						    
//							Controller.getControllerInstance().openEditSurvey(survey);
							
							new EditSurvey(survey);
							
							// the next statement is for debugging purposes only
						    System.out.println("\n---Leaving " + methodName);
							// end of debugging statement set
						} // end of mouseClicked() method
						// Even though the next four methods are not
						// used, they have to be inherited.
						public void mouseEntered(MouseEvent arg0) {
						}

						public void mouseExited(MouseEvent arg0) {
						}

						public void mousePressed(MouseEvent arg0) {
						}

						public void mouseReleased(MouseEvent arg0) {
						}
					});

					// ==============================================
					//XXX The second paper print routine
					// Paper Print option - mouseListener (8) Third if
					lblPrint.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {		
							// the next four statements are for debugging purposes only
							StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
						    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
						    String methodName = tre.getClassName() + "." + tre.getMethodName();
						    System.out.println("*************************************");
						    System.out.println("*      -- Print Icon Clicked--      *");
						    System.out.println("*                                   *");
						    System.out.println("*************************************");
						    System.out.println("---Entering " + methodName);
							// end of debugging statement set - 4 lines in all
						    
//							ViewSurvey viewSurvey = new ViewSurvey(survey);
//							viewSurvey.paperPrintSurvey(survey);
						    
						    // using the two statements below because they are the same as the
						    // routines used in the other print option
						    ViewSurvey viewSurvey = new ViewSurvey(survey);
							viewSurvey.paperPrintOneSurvey(survey);
							viewSurvey.dispose();
							
							// the next statement is for debugging purposes only
						    System.out.println("\n---Leaving " + methodName);
							// end of debugging statement set
						} // end of mouseClicked() method
						// Even though the next four methods are not
						// used, they have to be inherited.
						public void mouseEntered(MouseEvent arg0) {
						}

						public void mouseExited(MouseEvent arg0) {
						}

						public void mousePressed(MouseEvent arg0) {
						}

						public void mouseReleased(MouseEvent arg0) {
						}
					});
					
					// =============================================
					// Delete option
					lblDelete.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {	
							// the next four statements are for debugging purposes only
							StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
						    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
						    String methodName = tre.getClassName() + "." + tre.getMethodName();
						    System.out.println("*************************************");
						    System.out.println("*      -- Delete Icon Clicked--     *");
						    System.out.println("*                                   *");
						    System.out.println("*************************************");
						    System.out.println("---Entering " + methodName);
							// end of debugging statement set - 4 lines in all

							int response = JOptionPane
									.showConfirmDialog(
											null,
											"Are you sure you want to delete this survey?\n"
													+ "This action cannot be undone yet!",
											"Delete Survey",
											JOptionPane.YES_NO_OPTION);

							if (response == JOptionPane.YES_OPTION) {
//								Controller.getControllerInstance().removeSurveyFromListOfNewSurveys(survey);
								Controller.getControllerInstance().deleteSQLSurvey(survey);
								//MARK trying out refresh() method
								Controller.getControllerInstance().refreshScreen();
							}
							
							// the next statement is for debugging purposes only
						    System.out.println("\n---Leaving " + methodName);
							// end of debugging statement set
						} // end mouseClicked() method
						// Even though the next four methods are not
						// used, they have to be inherited.
						public void mouseEntered(MouseEvent arg0) {
						}

						public void mouseExited(MouseEvent arg0) {
						}

						public void mousePressed(MouseEvent arg0) {
						}

						public void mouseReleased(MouseEvent arg0) {
						}
					});
				} //MARK line 704 end for loop inside inner if statement
				// The above for loop assigns four icon buttons to each individual survey
				//========================================================================

				
				// Push surveys to top of panel (outside for loop, but still inside inner if statement)
				//XXX put some text into the lblFiller to see what it does
				JLabel fillerLabel = new JLabel("");
//				fillerLabel.setOpaque(true);
//				fillerLabel.setBackground(SURVEY_SHADOW);
				GridBagConstraints gbc_lblFiller = new GridBagConstraints();
				gbc_lblFiller.insets = new Insets(0, 0, 5, 0);
				gbc_lblFiller.fill = GridBagConstraints.BOTH;
				gbc_lblFiller.anchor = GridBagConstraints.SOUTH;
				gbc_lblFiller.weighty = 1.0;
				gbc_lblFiller.gridwidth = 5;
				gbc_lblFiller.gridx = 0;
				gbc_lblFiller.gridy = listOfSurveys.size();
				surveysPanel.add(fillerLabel, gbc_lblFiller);
				
				//MARK this is where some code for "Print All" button needs to be
				JButton printButton = new JButton("Print All Surveys");
				printButton.setFont(FNT_BIG_AND_BOLD);
				printButton.setEnabled(true);
				GridBagConstraints gbc_printButton = new GridBagConstraints();
				gbc_printButton.fill = GridBagConstraints.HORIZONTAL;
				gbc_printButton.anchor = GridBagConstraints.SOUTH;
				gbc_printButton.weighty = 0.0;
				gbc_printButton.gridwidth = 2;
				gbc_printButton.gridx = 0;
				gbc_printButton.gridy = listOfSurveys.size() + 1;
				surveysPanel.add(printButton, gbc_printButton);
				
				//MARK listener for "Print All" button
				printButton.addMouseListener(new MouseListener() { // inner class
					public void mouseClicked(MouseEvent arg0) {
						// the next four statements are for debugging purposes only
						StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
					    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
					    String methodName = tre.getClassName() + "." + tre.getMethodName();
					    System.out.println("*************************************");
					    System.out.println("*   -- Print All Button Clicked--   *");
					    System.out.println("*                                   *");
					    System.out.println("*************************************");
					    System.out.println("---Entering " + methodName);
						// end of debugging statement set - 4 lines in all
					    
					    
					    List<Survey> listOfSurveys = Controller.getControllerInstance().getSurveysList();
					    System.out.println("listSurveys.size() is " + listOfSurveys.size());
					    
						if (listOfSurveys.size() > 0) {
							
							JFrame[] imagePages = new JFrame[listOfSurveys.size()];
							for (int i = 0; i < listOfSurveys.size(); i++) {//								
								
								ViewSurvey viewSurvey = new ViewSurvey(listOfSurveys.get(i));
								imagePages[i] = viewSurvey.paperFrameSetUp();
//								viewSurvey.dispose();
								
								System.out.println(listOfSurveys.get(i));
							} // end for loop
							
							PrintMultiplePages.printComponent(imagePages);
							
						} // end if
//						
						// the next statement is for debugging purposes only
					    System.out.println("\n---Leaving " + methodName);
						// end of debugging statement set
					} // end of mouseClicked() method
					// Even though the next four methods are not
					// used, they have to be inherited.
					public void mouseEntered(MouseEvent arg0) {
					}

					public void mouseExited(MouseEvent arg0) {
					}

					public void mousePressed(MouseEvent arg0) {
					}

					public void mouseReleased(MouseEvent arg0) {
					}
				});
		
		
				// --- End code for "Print All" button
				
				add(scrollPane);

			} // end inner if statement
			// ***********************************************************************************
			//MARK else if empty group that contains no surveys as yet
			else {
				JLabel fillerLabel = new JLabel("");
//				fillerLabel.setOpaque(true);
//				fillerLabel.setBackground(SURVEY_SHADOW);
				GridBagConstraints gbc_lblFiller = new GridBagConstraints();
				gbc_lblFiller.insets = new Insets(0, 0, 5, 0);
				gbc_lblFiller.fill = GridBagConstraints.BOTH;
				gbc_lblFiller.anchor = GridBagConstraints.SOUTH;
				gbc_lblFiller.weighty = 1.0;
				gbc_lblFiller.gridwidth = 5;
				gbc_lblFiller.gridx = 0;
				gbc_lblFiller.gridy = listOfSurveys.size();
				surveysPanel.add(fillerLabel, gbc_lblFiller);
				
				//MARK this is where some code for "Print All" button needs to be
				JButton printButton = new JButton("Print All Surveys");
				printButton.setFont(FNT_BIG_AND_BOLD);
				printButton.setEnabled(false);
				GridBagConstraints gbc_printButton = new GridBagConstraints();
				gbc_printButton.fill = GridBagConstraints.HORIZONTAL;
				gbc_printButton.anchor = GridBagConstraints.SOUTH;
				gbc_printButton.weighty = 0.0;
				gbc_printButton.gridwidth = 2;
				gbc_printButton.gridx = 0;
				gbc_printButton.gridy = listOfSurveys.size() + 1;
				surveysPanel.add(printButton, gbc_printButton);
				
				// --- End code for "Print All" button
				
				add(scrollPane);
			} // end else
			
		} // end of the BIG constructor-wide if statement
		// *************************************************************************************
		// *************************************************************************************
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end constructor
	// ********** end constructor *****************
	
	
	
	/************************************
	 * List of methods - 3 of them
	 ***********************************/
	public static GroupSurveysLowerRightSidePanel getLowerRightSidePanelInstance() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all

		// If we do not yet have an instance of this controller
		// create a new one. Otherwise, return the controller.
		if (lowerRightSidePanelInstance == null) {
			lowerRightSidePanelInstance = new GroupSurveysLowerRightSidePanel();
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return lowerRightSidePanelInstance;
		}
		else {
			Group group = Controller.getControllerInstance().getGroup();
			Controller.getControllerInstance().setSQLselectWhereSurveysList(group);
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return lowerRightSidePanelInstance;
		}
	}
	/*********************/
	
	public static String formatString(final String text, int length) {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		// The letters [iIl1] are slim enough to only count as half a character.
		length += Math.ceil(text.replaceAll("[^iIl]", "").length() / 2.0d);

		if (text.length() > length) {
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return text.substring(0, length - 3) + "...";
		}
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set

		return text;
	}
	/*********************/

	public void refresh() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		new GroupSurveysLowerRightSidePanel();
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	}
}