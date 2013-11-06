package gui.custom;

import gui.GuiInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ctrl.Controller;

/**
 * The Class StatusTip will create a JDialog and add what you want it to say.
 */
public class StatusTip extends JDialog implements GuiInterface {

	protected String message;
	protected Timer timer;
	protected int st_width;
	protected int st_height;

	/**
	 * Instantiates a new fade box.
	 * 
	 * @param message
	 *            (string) : the message you want to display <br>
	 */
	public StatusTip(final String message, final Image icon) {

		this.message = message;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBackground(STATUSTIP_BACKGROUND);
		setUndecorated(true);
		setAlwaysOnTop(true);

		timer = new Timer(8, null);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				RoundPanel pnlMain = new RoundPanel();
				JPanel pnlMessage = new JPanel();
				JPanel pnlClickToClose = new JPanel();

				JLabel lblIcon = new JLabel(new ImageIcon(icon),
						SwingConstants.LEFT);
				JLabel lblMessage = new JLabel(message);
				JLabel lblClickToClose = new JLabel("Click to Close");

				pnlMain.setBackground(STATUSTIP_BACKGROUND);
				pnlClickToClose.setBackground(STATUSTIP_BACKGROUND);
				pnlClickToClose.add(lblClickToClose);

				lblMessage.setForeground(Color.white);
				lblClickToClose.setForeground(Color.white);
				lblClickToClose.setFont(new Font("Tahoma", Font.ITALIC, 10));
				lblClickToClose.setHorizontalAlignment(SwingConstants.RIGHT);

				GridBagLayout gbl_container = new GridBagLayout();
				gbl_container.columnWeights = new double[] { 1.0, 0.0 };
				gbl_container.rowWeights = new double[] { 1.0, 0.0 };
				pnlMain.setLayout(gbl_container);

				pnlMessage.add(lblIcon);
				pnlMessage.add(lblMessage);
				pnlMessage.setBackground(STATUSTIP_BACKGROUND);
				GridBagConstraints gbc_pnlMessage = new GridBagConstraints();
				gbc_pnlMessage.gridwidth = 2;
				gbc_pnlMessage.insets = new Insets(0, 0, 5, 5);
				gbc_pnlMessage.gridx = 0;
				gbc_pnlMessage.gridy = 0;
				pnlMain.add(pnlMessage, gbc_pnlMessage);

				GridBagConstraints gbc_pnlClickToClose = new GridBagConstraints();
				gbc_pnlClickToClose.anchor = GridBagConstraints.SOUTHEAST;
				gbc_pnlClickToClose.gridwidth = 2;
				gbc_pnlClickToClose.gridx = 0;
				gbc_pnlClickToClose.gridy = 1;
				pnlMain.add(pnlClickToClose, gbc_pnlClickToClose);

				setContentPane(pnlMain);

				// Start at 1
				setOpacity(1);

				// Increment the opacity down
				timer.start();
				timer.setRepeats(true);

				// Resize the DialogBox
				pack();

				// Reset Location
				int width = Controller.getControllerInstance().getFrame()
						.getX()
						+ FRAME_WIDTH - pnlMain.getWidth() - 10;
				int height = Controller.getControllerInstance().getFrame()
						.getY()
						+ FRAME_HEIGHT - pnlMain.getHeight() - 10;

				setLocation(width, height);

				// Set Visibility
				setVisible(true);

				// Every time the timer ticks, decrease the opacity
				timer.addActionListener(new ActionListener() {
					private float opacity = 1;

					@Override
					public void actionPerformed(ActionEvent e) {

						opacity -= 0.0015f;
						setOpacity(Math.max(opacity, 0));
						if (opacity <= 0) {
							timer.stop();
							dispose();
						}
					}
				});
			}
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				timer.stop();
				dispose();
			}

		});
	}

}