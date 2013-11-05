package lib.print;

import java.awt.event.*;

/**
 * A listener that you attach to the top-level Frame or JFrame of your
 * application, so quitting the frame exits the application. 1998-99 Marty Hall,
 * http://www.apl.jhu.edu/~hall/java/
 */

public class CloseOverallProgramListener extends WindowAdapter {
	public void windowClosing(WindowEvent event) {

		System.out.println(methodName);

		System.exit(0);
	}
}
