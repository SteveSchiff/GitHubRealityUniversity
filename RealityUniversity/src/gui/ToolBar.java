package gui;

import gui.dialogs.ManageJobs;
import gui.dialogs.NewGroup;
import gui.dialogs.OpenGroup;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar implements GuiInterface {

	private static ToolBar toolBarInstance = null;

	public ToolBar() {

		/*********************************
		 * Declarations
		 *********************************/
		// Buttons
	    JButton newGroupButton = new JButton("New Group");
		JButton openGroupButton = new JButton("Open Group");
		JButton editJobsButton = new JButton("Edit Jobs");

		/*******************************
		 * Configurations
		 *******************************/
		// File
		newGroupButton.setToolTipText("New Group");
		openGroupButton.setToolTipText("Open Group");
		editJobsButton.setToolTipText("Edit Jobs");

		// Set Icons to Buttons
		newGroupButton.setIcon(new ImageIcon(SM_NEW_GROUP));
		openGroupButton.setIcon(new ImageIcon(SM_OPEN_GROUP));
		editJobsButton.setIcon(new ImageIcon(SM_SETTINGS));
		
		// Set buttons' background colors
		newGroupButton.setOpaque(true);
		newGroupButton.setBackground(Color.CYAN);
		openGroupButton.setOpaque(true);
		openGroupButton.setBackground(Color.GREEN);
		editJobsButton.setOpaque(true);
		editJobsButton.setBackground(Color.YELLOW);
		
		// Set toolbar background
		this.setOpaque(true);
		this.setBackground(Color.RED);

		/*******************************
		 * Assembly
		 *******************************/
		add(newGroupButton);
		addSeparator();
		add(openGroupButton);
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
	} // end getToolBarInstance() method
} // end ToolBar class
