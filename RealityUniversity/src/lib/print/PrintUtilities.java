package lib.print;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		componentToBePrinted = localComponentToBePrinted;
	}

	public void print() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("No parameters.");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (PrinterException pe) {
				System.out.println("Error printing: " + pe);
			}
		} // end if
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		
	} // end print() method

	// this has to be put in because it's required by the Printable interface
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("Three parameter method.");
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		if (pageIndex > 0) {
				// the next statement is for debugging purposes only
			    System.out.println("\n---Leaving " + methodName + " and NO_SUCH_PAGE.");
				// end of debugging statement set
				return (NO_SUCH_PAGE);
		} else {
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			disableDoubleBuffering(componentToBePrinted);
			componentToBePrinted.paint(g2d);
			enableDoubleBuffering(componentToBePrinted);
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName + " and PAGE_EXISTS.");
			// end of debugging statement set
			return (PAGE_EXISTS);
		}
	} // end print() method of 3 parameters

	public static void printComponent(Component c) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		(new PrintUtilities(c)).print();
	}

	/**
	 * The speed and quality of printing suffers dramatically if any of the
	 * containers have double buffering turned on. So this turns it off
	 * globally.
	 * 
	 * @see enableDoubleBuffering
	 */
	public static void disableDoubleBuffering(Component c) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	/** Re-enables double buffering globally. */

	public static void enableDoubleBuffering(Component c) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}
}
