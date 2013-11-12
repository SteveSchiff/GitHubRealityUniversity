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

		bigDaddyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bigDaddyFrame.setBackground(FRAME_BACKGROUND);
		bigDaddyFrame.setTitle(FRAME_TITLE);
		bigDaddyFrame.getContentPane().setLayout(null);
		bigDaddyFrame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		bigDaddyFrame.setResizable(true); // changed it from false (by Steve)
		bigDaddyFrame.setLocationRelativeTo(null);
		bigDaddyFrame.setJMenuBar(MenuBar.getMenuBarInstance()); // Instantiate
																	// MenuBar
																	// instance
		bigDaddyFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		// XXX Make this getInstance for ToolBar! DONE!
		bigDaddyFrame.getContentPane().add(ToolBar.getToolBarInstance(),
				BorderLayout.NORTH); // Instantiate ToolBar instance
		bigDaddyFrame.getContentPane().add(mainWindowPanel);
		bigDaddyFrame.setIconImage(MED_CIS_LOGO);
		// check main window's visibility
		if (!bigDaddyFrame.isVisible()) {
			bigDaddyFrame.setVisible(true);
		} // end if

		drawWindow(); // essential step in this whole process

	} // end constructor

	public static GuiMain getGUIMainInstance() {

		// If we already have an instance of this GuiMain object, return it.
		// Otherwise, create a new one..
		if (guiMainInstance != null) {

			return guiMainInstance;
		} else {
			guiMainInstance = new GuiMain();

			return guiMainInstance;
		}
	} // -- end getGUIMainInstance() method

	public JFrame getBigDaddyFrame() { // called only by Controller.getFrame()
										// method

		return bigDaddyFrame;
	} // -- end getBigDaddyFrame() method

	public void drawWindow() { // major, major method!

		mainWindowPanel.removeAll();
		mainWindowPanel.setBackground(FRAME_BACKGROUND);
		mainWindowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainWindowPanel.setLayout(new BorderLayout(0, 0));
		// mainWindowPanel.setLayout(new GridLayout(1, 2, 2, 2)); //
		// Experimental

		try {
			JPanel sidebarPanel = new JPanel();

			// The two subpanels that go into sidebarPanel
			JPanel groupInformationPanel = new GroupInfoUpperRightSidePanel(); // Instantiate
																				// instance

			// This panel shows the list of surveys
			JPanel groupSurveysListPanel = new GroupSurveysLowerRightSidePanel(); // Instantiate
																					// instance
			groupSurveysListPanel.setLayout(new GridLayout(1, 0, 1, 1));

			sidebarPanel.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 600));
			sidebarPanel
					.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
			sidebarPanel.add(groupInformationPanel);
			sidebarPanel.add(groupSurveysListPanel);
			sidebarPanel.setBackground(FRAME_BACKGROUND);

			// allows left-side resizing
			// mainWindowPanel.add(new NewSurveyPanel());
			// mainWindowPanel.add(sidebarPanel, BorderLayout.EAST);

			// allows right-side resizing
			mainWindowPanel.add(new NewSurveyPanel());
			mainWindowPanel.add(sidebarPanel, BorderLayout.EAST);
			// mainWindowPanel.add(sidebarPanel);
			// mainWindowPanel.validate();
			// mainWindowPanel.repaint();

			bigDaddyFrame.getContentPane().add(mainWindowPanel);
			bigDaddyFrame.pack();
			mainWindowPanel.validate();
			mainWindowPanel.repaint();

		} catch (NullPointerException npe)
		// If we get an NullPointerException, then display the welcome screen.
		{
			mainWindowPanel.add(
					WelcomeWindowFrame.getWelcomeWindowFrameInstance(),
					BorderLayout.CENTER);

			bigDaddyFrame.getContentPane().add(mainWindowPanel);
			mainWindowPanel.validate();
			mainWindowPanel.repaint();
		}

	} // end drawWindow method

	public static void main(String[] args) throws Exception {
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}

		GuiMain.getGUIMainInstance();

	} // end main method
} // end GuiMain class
