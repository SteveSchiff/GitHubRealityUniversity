package lib.print;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

/**
 * A simple utility class that lets you very simply print an arbitrary
 * component. Just pass the component to the PrintUtilities.printComponent. The
 * component you want to print doesn't need a print method and doesn't have to
 * implement any interface or do anything special at all.
 * <P>
 * If you are going to be printing many times, it is marginally more efficient
 * to first do the following:
 * 
 * <PRE>
 * PrintUtilities printHelper = new PrintUtilities(theComponent);
 * </PRE>
 * 
 * then later do printHelper.print(). But this is a very tiny difference, so in
 * most cases just do the simpler
 * PrintUtilities.printComponent(componentToBePrinted).
 * 
 * 7/99 Marty Hall, http://www.apl.jhu.edu/~hall/java/ May be freely used or
 * adapted.
 */

public class PrintUtilities implements Printable {

	private Component componentToBePrinted = null;

	public PrintUtilities(Component localComponentToBePrinted) {

		System.out.println("PRINTUTILITIES CONSTRUCTOR");
		System.out.println(methodName);

		componentToBePrinted = localComponentToBePrinted;

	}

	public static void printComponent(Component c) {

		(new PrintUtilities(c)).print();

	}

	public void print() {

		System.out.println("No parameters.");

		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);

		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (PrinterException pe) {
				System.out.println("Error printing: " + pe);
			}
		} // end inner if

		System.out.println("No parameters.");

	} // end print() method

	// this has to be put in because it's required by the Printable interface
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) {

		System.out.println("Three parameter method.");

		if (pageIndex > 0) {

			System.out.println("\n---Leaving " + methodName
					+ " and NO_SUCH_PAGE.");
			System.out.println("Three parameter method.");

			return (NO_SUCH_PAGE);
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			disableDoubleBuffering(componentToBePrinted);
			componentToBePrinted.paint(g2d);
			enableDoubleBuffering(componentToBePrinted);

			System.out.println("\n---Leaving " + methodName
					+ " and PAGE_EXISTS.");

			return (PAGE_EXISTS);
		}
	} // end print() method of 3 parameters

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
} // end class PrintUtilities
