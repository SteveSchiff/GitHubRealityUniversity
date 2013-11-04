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

public class PrintMultiplePages implements Printable {
	
	BufferedImage[] bufferedImages;

	// constructor
	public PrintMultiplePages(JFrame[] pageFrames) {
		// TODO Auto-generated constructor stub		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
	    System.out.println("PRINTMULTIPLEPAGES CONSTRUCTOR");
		// end of debugging statement set - 4 lines in all
		
	    
	    bufferedImages = new BufferedImage[pageFrames.length];
	    
		for (int i = 0; i < pageFrames.length; i++) {
			System.out.println("pageFrames[" + i + "].toString() is " + pageFrames[i].toString());
			bufferedImages[i] = getImage(pageFrames[i]);
			System.out.println("bufferedImages[" + i +"].toString is" + bufferedImages[i]); 
		}
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
		
	} // end constructor

	public void print() {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
	    System.out.println("No parameters.");
		// end of debugging statement set - 4 lines in all
	    
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		
			if (printJob.printDialog()) {
				try {
					printJob.print();
				} catch (PrinterException pe) {
					System.out.println("Error printing: " + pe);
				}
			} // end inner if
		
		
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
	    System.out.println("No parameters.");
		// end of debugging statement set
		
	} // end print() method

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
	    System.out.println("Three parameter method.");
		// end of debugging statement set - 4 lines in all
		// TODO Auto-generated method stub
	    
		if (pageIndex < bufferedImages.length) {
			

			Graphics2D g2d = (Graphics2D) graphics;
			g2d.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
//			disableDoubleBuffering(componentToBePrinted);
//			componentToBePrinted.paint(g2d);//			enableDoubleBuffering(componentToBePrinted);
			
//			g2d.drawImage(bufferedImages[pageIndex], 0, 0, null);
			
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName + " and PAGE_EXISTS.");
			// end of debugging statement set
		    
			return PAGE_EXISTS;
		} else {
			// the next statement is for debugging purposes only
		    System.out.println("\n---Leaving " + methodName + " and NO_SUCH_PAGE.");
		    System.out.println("Three parameter method.");
			// end of debugging statement set
			return NO_SUCH_PAGE;
		}
	} // end three parameter print() method

	public static void printComponent(JFrame[] pageFrames) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		(new PrintMultiplePages(pageFrames)).print();
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	} // end printComponent() method
	
	public static BufferedImage getImage(Component c) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    BufferedImage bufferedImage = null;
	    try {
	        bufferedImage = new BufferedImage(c.getWidth(),c.getHeight(), BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d =bufferedImage.createGraphics();
	        c.print(g2d);
	        g2d.dispose();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	    
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
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
		
		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	}

	/** Re-enables double buffering globally. */

	public static void enableDoubleBuffering(Component c) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);

		// the next statement is for debugging purposes only
	    System.out.println("\n---Leaving " + methodName);
		// end of debugging statement set
	}

} // end PrintMultiplePages class
