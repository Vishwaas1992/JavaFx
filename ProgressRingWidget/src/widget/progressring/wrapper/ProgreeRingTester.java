package widget.progressring.wrapper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ProgreeRingTester {

	public static void main(String[] args) {
		Display display=new Display();
		Shell shell=new Shell(display);
		GridLayout gridlayout=new GridLayout();
		gridlayout.numColumns=10;
		ProgressRing progRing=new ProgressRing(shell, SWT.NULL);
		shell.open();
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
	}
	
}
