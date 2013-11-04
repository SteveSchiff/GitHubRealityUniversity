package gui;

import gui.dialogs.ManageJobs;
import gui.dialogs.NewGroup;
import gui.dialogs.OpenGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;

import ctrl.Controller;

public class ToolBar extends JToolBar implements GuiInterface {

	
	private static ToolBar toolBarInstance = null;
//	JButton saveButton = new JButton();
	
	//Controller controller = Controller.getInstance();

	public ToolBar() {
		// the next five statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("BEGIN TOOLBAR CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 5 lines in all

		/*********************************
		 * Declarations
		 *********************************/
		// Buttons
	    JButton newGroupButton = new JButton();
		JButton openGroupButton = new JButton();
//		JButton btnPrintSurveys = new JButton();
//		JButton btnUndo = new JButton();
		JButton cutButton = new JButton(new DefaultEditorKit.CutAction());
		JButton copyButton = new JButton(new DefaultEditorKit.CopyAction());
		JButton pasteButton = new JButton(new DefaultEditorKit.PasteAction());
		JButton editJobsButton = new JButton();

		/*******************************
		 * Configurations
		 *******************************/
		// File
		newGroupButton.setToolTipText("New Group");
		openGroupButton.setToolTipText("Open Group");
//		btnPrintSurveys.setToolTipText("Print Current Survey");
//		saveButton.setToolTipText("Save Current Survey");

		// Edit
//		btnUndo.setText("");
		cutButton.setText("");
		copyButton.setText("");
		pasteButton.setText("");
//		btnUndo.setToolTipText("Undelete Last Survey");
		cutButton.setToolTipText("Cut Text");
		copyButton.setToolTipText("Copy Text");
		pasteButton.setToolTipText("Paste Text");
		editJobsButton.setToolTipText("Edit Jobs");

		// Set Icons to Buttons
		newGroupButton.setIcon(new ImageIcon(SM_NEW_GROUP));
		openGroupButton.setIcon(new ImageIcon(SM_OPEN_GROUP));
//		saveButton.setIcon(new ImageIcon(SM_SAVE));
//		btnPrintSurveys.setIcon(new ImageIcon(SM_PRINT));

//		btnUndo.setIcon(new ImageIcon(SM_UNDO));
		cutButton.setIcon(new ImageIcon(SM_CUT));
		copyButton.setIcon(new ImageIcon(SM_COPY));
		pasteButton.setIcon(new ImageIcon(SM_PASTE));
		editJobsButton.setIcon(new ImageIcon(SM_SETTINGS));

//		btnUndo.setEnabled(false);
//		saveButton.setEnabled(false);

		/*******************************
		 * Assembly
		 *******************************/
		add(newGroupButton);
		add(openGroupButton);
//		add(saveButton);
//		add(btnPrintSurveys);
		addSeparator();
//		add(btnUndo);
		add(cutButton);
		add(copyButton);
		add(pasteButton);
		addSeparator();
		add(editJobsButton);

		/*********************************
		 * Events
		 *********************************/
		newGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				new NewGroup();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			}
		});
		openGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				new OpenGroup();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			}
		});

//		saveButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {		
//				// the next four statements are for debugging purposes only
//				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
//			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
//			    String methodName = tre.getClassName() + "." + tre.getMethodName();
//			    System.out.println("---Entering " + methodName);
//				// end of debugging statement set - 4 lines in all
//			    
//			    // the saveGroup() method is a method that calls the
//			    // problem processingLibrary package of classes
//			    //XXX Needs to be DIFFERENT ROUTINE ASAP!
//				Controller.getControllerInstance().saveGroup();
//				
//				// the next statement is for debugging purposes only
//			    System.out.println("\n---Leaving " + methodName);
//				// end of debugging statement set
//			}
//		});

//		btnUndo.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// Undo Action
//			}
//		});
		
		editJobsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// the next four statements are for debugging purposes only
				StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
			    String methodName = tre.getClassName() + "." + tre.getMethodName();
			    System.out.println("---Entering " + methodName);
				// end of debugging statement set - 4 lines in all
			    
				ManageJobs.getManageJobsInstance();
				
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName);
				// end of debugging statement set
			}
		});

//		btnPrintSurveys.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int response = JOptionPane.showConfirmDialog(null, new String(
//						"Hey Mitch... You gotta make this work!!! =D"),
//						"Printing stuff is old-school...",
//						JOptionPane.OK_CANCEL_OPTION, 0, new ImageIcon(
//								LG_EXCEPTION));
//
//				if (response == JOptionPane.OK_OPTION) {
//					JOptionPane.showConfirmDialog(null, new String(
//							"no... seriously... it's gotta work"), "lawlz",
//							JOptionPane.OK_OPTION, 0, new ImageIcon(
//									LG_EXCEPTION));
//				}
//			}
//		});
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end constructor
	
	public static ToolBar getToolBarInstance() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
	    if(toolBarInstance != null) {
			// debugging statement
			System.out.println("SAME OLD SAME OLD TOOLBAR INSTANCE");
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
	    	
			return toolBarInstance;
	    }
	    else {
			// debugging statement set
		    System.out.println("");
			System.out.println("******NEW TOOLBAR INSTANCE******");
			// end of debugging statement set
			toolBarInstance = new ToolBar();
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName);
			// end of debugging statement set
			return toolBarInstance;	    	
	    }
	}

	public void setSaveEnabled(boolean value) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
//		saveButton.setEnabled(value);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	}
}
