package gui;

import gui.custom.RoundPanel;
import gui.dialogs.NewGroup;
import gui.dialogs.OpenGroup;
import gui.dialogs.ManageJobs;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ctrl.Controller;

/**
 * The Class Welcome displays a welcome panel and allows for quick navigation.
 */
public class WelcomeWindowFrame extends RoundPanel implements GuiInterface {

	private static WelcomeWindowFrame welcomeWindowFrameInstance = null;
	/**
	 * Instantiates a new welcome screen.
	 */
	public WelcomeWindowFrame() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("BEGIN WELCOMEWINDOWFRAME CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all

		/*********************************
		 * Declarations
		 *********************************/
		Container bigDaddyContainer = new Container();

		bigDaddyContainer.setPreferredSize(new Dimension(WELCOME_WIDTH, WELCOME_HEIGHT));
		bigDaddyContainer.setLayout(new BoxLayout(bigDaddyContainer, BoxLayout.PAGE_AXIS));

		JPanel contentPanel = new JPanel();
		RoundPanel headerPanel = new RoundPanel();
		RoundPanel buttonsPanel = new RoundPanel();

		Container headerContainer = new Container();
		Container buttonsContainer = new Container();

		GridLayout headerGridLayout = new GridLayout(0, 2);
		GridLayout buttonGridLayout = new GridLayout(2, 2);

		JLabel headerLogoLabel = new JLabel("Logo");
		JLabel headerTextLabel = new JLabel("Text");

		JButton newGroupButton = new JButton("New Group");
		JButton openGroupButton = new JButton("Open Group");
		JButton editJobsButton = new JButton("Edit Jobs");
		JButton helpButton = new JButton("Help Contents");

		/*********************************
		 * Configurations
		 *********************************/
		setBackground(PANEL_BACKGROUNDLIGHTGREEN); // sets the background color of the overall frame/window
		contentPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		contentPanel.setLayout(new GridLayout(2, 0));
		headerPanel.setBackground(PANEL_BACKGROUNDLIGHTGREEN);
		buttonsPanel.setBackground(Color.lightGray);

		headerContainer.setLayout(headerGridLayout);
		buttonsContainer.setLayout(buttonGridLayout);

		headerLogoLabel = new JLabel(new ImageIcon(MISC_WELCOME_01), JLabel.LEFT);
		headerTextLabel = new JLabel(new ImageIcon(MISC_WELCOME_02), JLabel.RIGHT);
		
		newGroupButton.setIcon(new ImageIcon(LG_NEW_GROUP));
		openGroupButton.setIcon(new ImageIcon(LG_OPEN_GROUP));
		editJobsButton.setIcon(new ImageIcon(LG_SETTINGS));
		helpButton.setIcon(new ImageIcon(LG_HELP));

		/*********************************
		 * Assembly
		 *********************************/
		headerContainer.add(headerLogoLabel);
		headerContainer.add(headerTextLabel);

		buttonsContainer.add(newGroupButton);
		buttonsContainer.add(openGroupButton);
		buttonsContainer.add(editJobsButton);
		buttonsContainer.add(helpButton);

		headerPanel.add(headerContainer);
		buttonsPanel.add(buttonsContainer);

		//contentPanel.add(headerContainer); // original
		contentPanel.add(headerPanel);  // added as an experiment
		contentPanel.add(buttonsPanel);

		bigDaddyContainer.add(contentPanel);

		add(bigDaddyContainer);
		
		// this -> bidDaddyContainer -> contentPanel -> headerPanel,buttonsPanel -> headerContainer,buttonsContainer

		/*********************************
		 * Events
		 *********************************/
		newGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next five statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("*************************************");
			    System.out.println("*    -- New Group Button Clicked--  *");
			    System.out.println("*************************************");
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 5 lines in all
			    
				new NewGroup();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			}
		});

		openGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next five statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("*************************************");
			    System.out.println("*   -- Open Group Button Clicked--  *");
			    System.out.println("*************************************");
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 5 lines in all
			    
				new OpenGroup();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			}
		});

		editJobsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next five statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("*************************************");
			    System.out.println("*   -- Edit Jobs Button Clicked--   *");
			    System.out.println("*************************************");
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 5 lines in all
			    
				new ManageJobs();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			}
		});
		
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next five statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("*************************************");
			    System.out.println("*     -- Help Button Clicked--      *");
			    System.out.println("*************************************");
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 5 lines in all
			    
				Controller.getControllerInstance().openHelp();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			}
		});
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end constructor
	
	public static WelcomeWindowFrame getWelcomeWindowFrameInstance() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
	    if(welcomeWindowFrameInstance != null){
			// debugging statement
			System.out.println("SAME OLD SAME OLD WELCOME WINDOW FRAME INSTANCE");
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
	    	return welcomeWindowFrameInstance;
	    }
	    else {
			// debugging statement set
		    System.out.println("");
			System.out.println("******NEW WELCOME WINDOW FRAME INSTANCE******");
			// end of debugging statement set
			welcomeWindowFrameInstance = new WelcomeWindowFrame();
			Controller.getControllerInstance().checkExistenceOfSQLTables();
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return welcomeWindowFrameInstance;	    	
	    }
		
	} // end getWelcomeWindowFrameInstance method
} // end WelcomeWindowFrame class
