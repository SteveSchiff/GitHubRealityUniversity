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

public class ToolBar extends JToolBar implements GuiInterface {

	private static ToolBar toolBarInstance = null;

	// JButton saveButton = new JButton();

	// Controller controller = Controller.getInstance();

	public ToolBar() {

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

//		btnUndo.setIcon(new ImageIcon(SM_UNDO));
		cutButton.setIcon(new ImageIcon(SM_CUT));
		copyButton.setIcon(new ImageIcon(SM_COPY));
		pasteButton.setIcon(new ImageIcon(SM_PASTE));
		editJobsButton.setIcon(new ImageIcon(SM_SETTINGS));

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
				new NewGroup();				
			}
		});
		openGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				new OpenGroup();
			}
		});
		editJobsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				ManageJobs.getManageJobsInstance();
			}
		});
	} // end constructor

	public static ToolBar getToolBarInstance() {

		if (toolBarInstance != null) {
			return toolBarInstance;
		} else {
			toolBarInstance = new ToolBar();
			return toolBarInstance;
		}
	}

	public void setSaveEnabled(boolean value) {

		// saveButton.setEnabled(value);

	}
}
