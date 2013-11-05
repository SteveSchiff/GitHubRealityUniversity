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

		System.out.println("BEGIN ABOUT CONSTRUCTOR");

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

	} // -- end About constructor
} // end About class
