package lib.print;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JFrame;
import javax.swing.RepaintManager;

public class PrintAllUtilities implements Printable {
	
	private Component componentToBePrinted = null;
//	private JFrame[] frames = null;
//	private BufferedImage[] frameImages = null;
	
	
	public PrintAllUtilities(Component localComponentToBePrinted) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		componentToBePrinted = localComponentToBePrinted;
	} // end PrintAllUtilities() method

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
		if (printJob.printDialog())
			try {
				printJob.print();
			} catch (PrinterException pe) {
				System.out.println("Error printing: " + pe);
			}
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		
	} // end print() method

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {		
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
			Graphics2D g2d = (Graphics2D) graphics;
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

		
	} // end 3-parameter print() method

	public static void printComponent(Component c) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println(methodName);
		// end of debugging statement set - 4 lines in all
	    
		(new PrintAllUtilities(c)).print();
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
	    
//		RepaintManager currentManager = RepaintManager.setPaintManager(frameImages2);
//		currentManager.setDoubleBufferingEnabled(false);
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

} // end PrintAllUtilities class
