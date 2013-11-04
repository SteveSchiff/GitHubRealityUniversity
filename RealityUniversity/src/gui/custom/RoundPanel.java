/************************************************************************************
 * Name: RoundPanel.java
 * 
 * Description: 
 * 
 ************************************************************************************/

package gui.custom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The Class RoundPanel extends the JPanel component and customizes it to have
 * round edges and a beveled look for a smoother looking panel.
 */
public class RoundPanel extends JPanel {

	/**
	 * Instantiates a new round panel.
	 */
	public RoundPanel() {
		super();
		setOpaque(false);
		setBorder(new EmptyBorder(11, 11, 11, 11));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		int strokeSize = 1;
		int shadowGap = 5;
		int shadowOffset = 4;
		int shadowAlpha = 150;
		boolean shady = true; // Drop Shadow
		boolean highQuality = true;
		Color shadowColor = new Color(128, 128, 128);
		Dimension arcs = new Dimension(20, 20);

		Color shadowColorA = new Color(shadowColor.getRed(),
				shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
		Graphics2D graphics = (Graphics2D) g;

		// Sets anti-aliasing if HQ.
		if (highQuality) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Draws shadow borders
		if (shady) {
			graphics.setColor(shadowColorA);
			graphics.fillRoundRect(shadowOffset, // X Position
					shadowOffset, // Y Position
					width - strokeSize - shadowOffset, // Width
					height - strokeSize - shadowOffset, // Height
					arcs.width, arcs.height); // Arc Dimension
		} else {
			shadowGap = 1;
		}

		// Draws the rounded opaque panel with borders.
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap,
				arcs.width, arcs.height);
		graphics.setColor(getForeground());
		graphics.setStroke(new BasicStroke(strokeSize));
		graphics.drawRoundRect(0, 0, width - shadowGap, height - shadowGap,
				arcs.width, arcs.height);

		// Sets strokes to default, is better.
		graphics.setStroke(new BasicStroke());

	}
}
