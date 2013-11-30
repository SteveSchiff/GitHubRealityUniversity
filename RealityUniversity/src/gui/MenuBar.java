package gui;

import gui.dialogs.NewGroup;
import gui.dialogs.OpenGroup;
import gui.dialogs.ManageJobs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit;

import ctrl.Controller;

public class MenuBar extends JMenuBar implements GuiInterface {

	/*********************************
	 * Class Fields
	 *********************************/

	private static MenuBar menuBarInstance = null;

	// Menus and their MenuItems
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newGroupMenuItem = new JMenuItem("New Group");
	private JMenuItem openGroupMenuItem = new JMenuItem("Open Group");
	private JMenuItem deleteGroupMenuItem = new JMenuItem("Delete Group");
	private JMenuItem exitMenuItem = new JMenuItem("Exit");
	// private JMenuItem mnuitmSelectDB = new JMenuItem("Select Database");

	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem cutMenuItem = new JMenuItem(
			new DefaultEditorKit.CutAction());
	private JMenuItem copyMenuItem = new JMenuItem(
			new DefaultEditorKit.CopyAction());
	private JMenuItem pasteMenuItem = new JMenuItem(
			new DefaultEditorKit.PasteAction());
	private JMenuItem editJobsMenuItem = new JMenuItem("Settings");

	private JMenu viewMenu = new JMenu("View");

	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem helpMenuItem = new JMenuItem("Help");

	// private JMenuItem mnuitmAbout = new JMenuItem("About");

	/**
	 * Constructor
	 */
	public MenuBar() {

		/*******************************
		 * Configurations
		 *******************************/
		// Mnemonics
		fileMenu.setMnemonic('F');
		newGroupMenuItem.setMnemonic(KeyEvent.VK_N);
		openGroupMenuItem.setMnemonic(KeyEvent.VK_O);
		exitMenuItem.setMnemonic('x');

		editMenu.setMnemonic('E');
		cutMenuItem.setMnemonic(KeyEvent.VK_X);
		copyMenuItem.setMnemonic(KeyEvent.VK_C);
		pasteMenuItem.setMnemonic(KeyEvent.VK_V);

		viewMenu.setMnemonic('V');
		helpMenu.setMnemonic('H');

		// Text
		cutMenuItem.setText("Cut");
		copyMenuItem.setText("Copy");
		pasteMenuItem.setText("Paste");

		// Icons
		newGroupMenuItem.setIcon(new ImageIcon(SM_NEW_GROUP));
		openGroupMenuItem.setIcon(new ImageIcon(SM_OPEN_GROUP));
		exitMenuItem.setIcon(new ImageIcon(SM_EXIT));
		cutMenuItem.setIcon(new ImageIcon(SM_CUT));
		copyMenuItem.setIcon(new ImageIcon(SM_COPY));
		pasteMenuItem.setIcon(new ImageIcon(SM_PASTE));
		editJobsMenuItem.setIcon(new ImageIcon(SM_SETTINGS));
		helpMenuItem.setIcon(new ImageIcon(SM_HELP));

		// Enabled / Disabled
		// mnuitmSelectDB.setEnabled(false);
		try {
			//Controller.getInstance().getGroup().equals(null);
			Controller.getControllerInstance().getGroup();
			deleteGroupMenuItem.setEnabled(true);
		} catch (NullPointerException npe) {
			deleteGroupMenuItem.setEnabled(false);
		}
		
//		mnuitmAbout.setEnabled(false);

		/*******************************
		 * Assembly
		 *******************************/
		// File
		// mnuFile.add(mnuitmSelectDB);
		// mnuFile.addSeparator();
		fileMenu.add(newGroupMenuItem);
		fileMenu.add(openGroupMenuItem);
		fileMenu.add(deleteGroupMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		// Edit
		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.addSeparator();
		editMenu.add(editJobsMenuItem);

		// Help
		helpMenu.add(helpMenuItem);
		helpMenu.addSeparator();
//		mnuHelp.add(mnuitmAbout);

		// Menu
		add(fileMenu);
		add(editMenu);
		add(helpMenu);

		/*********************************
		 * Events
		 *********************************/
		// File
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				
				
			    
			    
			    
				
			    
//				Controller.getControllerInstance().closeApplication();
			    
			    // Close-out routine
			    int response = JOptionPane
						.showConfirmDialog(
								null,
								new String(
										"Are you sure you want to quit?\nAny work that is not saved will be lost!"),
								"Exit Application", JOptionPane.YES_NO_OPTION, 0,
								new ImageIcon(LG_EXIT));

				if (response == JOptionPane.YES_OPTION)	    
					
					
				    
					
					System.exit(0);
			}
		});
		newGroupMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				
			    
			    
			    
				
				new NewGroup();
				
				
			    
				
			}
		});
		openGroupMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				
				
			    
			    
			    
				
				new OpenGroup();
				
				
			    
				
			}
		});
		deleteGroupMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				
				
			    
			    
			    
				
			    
			    int result = JOptionPane.showConfirmDialog(null,
			    				new String("Are you sure you want to delete group "
			    					+ Controller.getControllerInstance().getGroup().getName() + "?"
			    						+ "\nAll surveys associated with this group will be lost, too!"),
			    						"Confirm Group Deletion", JOptionPane.YES_NO_OPTION);
			    
			    if (result == JOptionPane.YES_OPTION) {
					Controller.getControllerInstance().deleteSQLGroup();
				}
				
				
			    
				
			}
		});
		editJobsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				
				
			    
			    
			    
				
				ManageJobs.getManageJobsInstance();
				
				
			    
				
			}
		});
//		mnuitmAbout.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				new About();
//			}
//		});
		helpMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				
				
			    
			    
			    
				
				Controller.getControllerInstance().openHelp();
				
				
			    
				
			}
		});
		
		
	    
		
	} // end constructor

	/**
	 * Gets the single instance of MenuBar.
	 * 
	 * @return single instance of MenuBar
	 */
	public static MenuBar getMenuBarInstance() {

		// If we have an instance of this class, simply return it.
		// Otherwise, return a new one.
		if (menuBarInstance != null) {

			return menuBarInstance;
		} else {

			menuBarInstance = new MenuBar();

			return menuBarInstance;
		}
	} // end of getMenuBarInstance method

	public void setDeleteGroup(boolean enabled) {

		deleteGroupMenuItem.setEnabled(enabled);

	} // -- end setDeletGroup() method
} // end MenuBar class