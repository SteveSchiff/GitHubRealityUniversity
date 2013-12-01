package print;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JFrame;
import javax.swing.RepaintManager;

public class PrintMultiplePages implements Printable {

	JFrame[] pageFrames = null;
	BufferedImage[] bufferedImages;

	// constructor
	public PrintMultiplePages(JFrame[] jFrames) {

		bufferedImages = new BufferedImage[jFrames.length];

		pageFrames = jFrames;
	} // end constructor

	public void print() {

		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);

		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (PrinterException pe) {
				System.out.println("Error printing: " + pe);
			}
		} // end inner if
	} // end print() method

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		if (pageIndex < bufferedImages.length) {

			bufferedImages[pageIndex] = this.getImage(pageFrames[pageIndex]);
			
			// Made the third parameter to be an offset to get rid of the
			// ink depleting black band at the top of the image frame.
			//TODO Maybe fix this little "black band" problem in the future.
			graphics.drawImage(bufferedImages[pageIndex], 10, -23, null);

			return PAGE_EXISTS;
		} else {
			return NO_SUCH_PAGE;
		}
	} // end three parameter print() method

	public static void printComponent(JFrame[] pageFrames) {

		(new PrintMultiplePages(pageFrames)).print();

	} // end printComponent() method

	public BufferedImage getImage(Component c) {

		BufferedImage bufferedImage = null;
		try {
			// JFrame frame = new JFrame();
			// frame.setBackground(Color.WHITE);
			// frame.setUndecorated(true);
			// frame.getContentPane().add(c);
			// frame.pack();
			c.setBackground(Color.WHITE);
			// bufferedImage = new BufferedImage(c.getWidth(),c.getHeight() -
			// 10, BufferedImage.TYPE_BYTE_GRAY); // TYPE_INT_RGB
			bufferedImage = new BufferedImage(570, 690,
					BufferedImage.TYPE_BYTE_GRAY); // 580, 700
			Graphics graphics = bufferedImage.getGraphics();
			c.paint(graphics);
			graphics.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return bufferedImage;
	} // end getImage() method

	/**
	 * The speed and quality of printing suffers dramatically if any of the
	 * containers have double buffering turned on. So this turns it off
	 * globally.
	 * 
	 * @see enableDoubleBuffering
	 */
	public static void disableDoubleBuffering(Component c) {

		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);

	}

	/** Re-enables double buffering globally. */

	public static void enableDoubleBuffering(Component c) {

		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);

	}

} // end PrintMultiplePages class
