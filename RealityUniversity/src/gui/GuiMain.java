package gui;

import gui.sidebars.GroupInfoUpperRightSidePanel;
import gui.sidebars.GroupSurveysLowerRightSidePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

//import ctrl.Controller;

public class GuiMain implements GuiInterface {

	/* Class fields */
	private static GuiMain guiMainInstance = null;
	private static JFrame bigDaddyFrame = new JFrame();
	private JPanel mainWindowPanel = new JPanel();

	/* Main class constructor */
	public GuiMain() {
		// the next few statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
	    System.out.println("BEGIN GUI_MAIN CONSTRUCTOR");
	    System.out.println("-- Sets up MenuBar and ToolBar --");
	    System.out.println("And invokes the drawWindow() method");
		// end of debugging statement set
	    
	    
		bigDaddyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bigDaddyFrame.setBackground(FRAME_BACKGROUND);
		bigDaddyFrame.setTitle(FRAME_TITLE);
		bigDaddyFrame.getContentPane().setLayout(null);
		bigDaddyFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		bigDaddyFrame.setResizable(true); // changed it from false (by Steve)
		bigDaddyFrame.setLocationRelativeTo(null);
		bigDaddyFrame.setJMenuBar(MenuBar.getMenuBarInstance()); // Instantiate MenuBar instance
		bigDaddyFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		//XXX Make this getInstance for ToolBar! DONE!
		bigDaddyFrame.getContentPane().add(ToolBar.getToolBarInstance(), BorderLayout.NORTH); // Instantiate ToolBar instance
		bigDaddyFrame.getContentPane().add(mainWindowPanel);
		bigDaddyFrame.setIconImage(MED_CIS_LOGO);
		// check main window's visibility
		if (!bigDaddyFrame.isVisible()) {
			bigDaddyFrame.setVisible(true);
		} // end if
	    
		drawWindow();  // essential step in this whole process
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		
	} // end constructor

	public static GuiMain getGUIMainInstance() {
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
	    System.out.println("-- Does nothing but return a GUIMain instance --");
		// end of debugging statement set - 4 lines in all
	    

		// If we already have an instance of this GuiMain object, return it.
		// Otherwise, create a new one..
		if (guiMainInstance != null) {
			System.out.println("SAME OLD SAME OLD GUIMAIN INSTANCE");
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return guiMainInstance;
		}
		else {
			System.out.println("");
			System.out.println("******NEW GUIMAIN INSTANCE******");
			guiMainInstance = new GuiMain();
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return guiMainInstance;
		}
	} // -- end getGUIMainInstance() method

	public JFrame getBigDaddyFrame() { // called only by Controller.getFrame() method
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	    
		return bigDaddyFrame;
	} // -- end getBigDaddyFrame() method

	public void drawWindow() { // major, major method!
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		mainWindowPanel.removeAll();
		mainWindowPanel.setBackground(FRAME_BACKGROUND);
		mainWindowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainWindowPanel.setLayout(new BorderLayout(0, 0));

		try {
			JPanel sidebarPanel = new JPanel();
			
			// The two subpanels that go into sidebarPanel
			JPanel groupInformationPanel = new GroupInfoUpperRightSidePanel(); // Instantiate instance
			 // This panel shows the list of surveys
			JPanel groupSurveysListPanel = new GroupSurveysLowerRightSidePanel(); // Instantiate instance
			groupSurveysListPanel.setLayout(new GridLayout(1, 0, 5, 5));

			sidebarPanel.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 700));
			sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
			sidebarPanel.add(groupInformationPanel);
			sidebarPanel.add(groupSurveysListPanel);

			sidebarPanel.setBackground(FRAME_BACKGROUND);
			mainWindowPanel.add(new NewSurveyPanel());
			mainWindowPanel.add(sidebarPanel, BorderLayout.EAST);

			bigDaddyFrame.getContentPane().add(mainWindowPanel);
			mainWindowPanel.validate();
			mainWindowPanel.repaint();

		} catch (NullPointerException npe)
		// If we get an NullPointerException, then display the welcome screen.
		{
			mainWindowPanel.add(WelcomeWindowFrame.getWelcomeWindowFrameInstance(), BorderLayout.CENTER);
			
			// debugging statement
			System.out.println("The exception was " + npe.getStackTrace());

			bigDaddyFrame.getContentPane().add(mainWindowPanel);
			mainWindowPanel.validate();
			mainWindowPanel.repaint();
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName + " from catch block");
			// end of debugging statement set
		}
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end drawWindow method

	public static void main(String[] args) throws Exception {
		try {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (UnsupportedLookAndFeelException e) {
			}
		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = e.getClassName() + "." + e.getMethodName();
	    System.out.println("---Entering " + methodName);
	    System.out.println("Does nothing but call the getGUIMainInstance() method.");
		// end of debugging statement set - 4 lines in all
	    
		// Initial Database Setup
		// Controller.getInstance(); // comment out after commit 10/01/2013

		GuiMain.getGUIMainInstance();
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end main method
} // end GuiMain class
