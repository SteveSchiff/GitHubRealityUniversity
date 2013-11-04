package gui.dialogs;

import gui.GuiInterface;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ctrl.Controller;

public class About extends JDialog implements GuiInterface {

	public About() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("BEGIN ABOUT CONSTRUCTOR");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		JPanel pnlContent = new JPanel();
		JPanel pnlHeader = new JPanel();

		JLabel groupLogo = new JLabel(new ImageIcon(MISC_ABOUT_LOGO));
		groupLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlHeader.add(groupLogo);
		GridBagLayout gbl_pnlContent = new GridBagLayout();
		gbl_pnlContent.columnWidths = new int[] { 217, 1, 0 };
		gbl_pnlContent.rowHeights = new int[] { 1, 0 };
		gbl_pnlContent.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pnlContent.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlContent.setLayout(gbl_pnlContent);

		getContentPane().add(pnlHeader, BorderLayout.NORTH);
		getContentPane().add(pnlContent, BorderLayout.CENTER);

		setLocationRelativeTo(Controller.getControllerInstance().getFrame());
		pack();
		setVisible(true);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // -- end About constructor
} // end About class
