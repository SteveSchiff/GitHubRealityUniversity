package lib.print;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JFrame;

public class PrintMultiplePages implements Printable {
	
	BufferedImage[] bufferedImages = null;

	public PrintMultiplePages(JFrame[] pageFrames) {
		// TODO Auto-generated constructor stub
		Book book = new Book();
		
		for (int i = 0; i < pageFrames.length; i++) {
			book.append((Printable) bufferedImages[i], null);
		}
		
		this.print();
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
		// TODO Auto-generated method stub
		return 0;
	} // end three parameter print() method

	public static void printComponent(JFrame[] pageFrames) {		
		// the next four statements are for debugging purposes only
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	    StackTraceElement tre = stacktrace[1];//coz 0th will be getStackTrace so 1st
	    String methodName = tre.getClassName() + "." + tre.getMethodName();
	    System.out.println("---Entering " + methodName);
		// end of debugging statement set - 4 lines in all
	    
		(new PrintMultiplePages(pageFrames)).print();
	}

} // end PrintMultiplePages class
