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
		
		
	    
	    
	    System.out.println("BEGIN WELCOMEWINDOWFRAME CONSTRUCTOR");
	    
		

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
				
			    
			    
			    System.out.println("*************************************");
			    System.out.println("*    -- New Group Button Clicked--  *");
			    System.out.println("*************************************");
			    
				 - 5 lines in all
			    
				new NewGroup();
				
				
			    
				
			}
		});

		openGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next five statements are for debugging purposes only
				
			    
			    
			    System.out.println("*************************************");
			    System.out.println("*   -- Open Group Button Clicked--  *");
			    System.out.println("*************************************");
			    
				 - 5 lines in all
			    
				new OpenGroup();
				
				
			    
				
			}
		});

		editJobsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next five statements are for debugging purposes only
				
			    
			    
			    System.out.println("*************************************");
			    System.out.println("*   -- Edit Jobs Button Clicked--   *");
			    System.out.println("*************************************");
			    
				 - 5 lines in all
			    
				new ManageJobs();
				
				
			    
				
			}
		});
		
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next five statements are for debugging purposes only
				
			    
			    
			    System.out.println("*************************************");
			    System.out.println("*     -- Help Button Clicked--      *");
			    System.out.println("*************************************");
			    
				 - 5 lines in all
			    
				Controller.getControllerInstance().openHelp();
				
				
			    
				
			}
		});
		
		
	    
		
	} // end constructor

	public static WelcomeWindowFrame getWelcomeWindowFrameInstance() {

		if (welcomeWindowFrameInstance != null) {
			// debugging statement
			System.out
					.println("SAME OLD SAME OLD WELCOME WINDOW FRAME INSTANCE");

			return welcomeWindowFrameInstance;
		} else {
			// debugging statement set
			System.out.println("");
			System.out.println("******NEW WELCOME WINDOW FRAME INSTANCE******");

			welcomeWindowFrameInstance = new WelcomeWindowFrame();
			Controller.getControllerInstance().checkExistenceOfSQLTables();

			return welcomeWindowFrameInstance;
		}

	} // end getWelcomeWindowFrameInstance method
} // end WelcomeWindowFrame class
