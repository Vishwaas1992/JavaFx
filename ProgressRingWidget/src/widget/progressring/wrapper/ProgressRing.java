package widget.progressring.wrapper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import widget.progressring.JProgressRing;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import com.sun.javafx.geom.Shape;

public class ProgressRing extends Canvas {

	

	public ProgressRing(Composite parent, int style) {
		super(parent, style);
		final RowLayout layout = new RowLayout();
		parent.setLayout(layout);


		final FXCanvas fxCanvas = new FXCanvas(parent, SWT.NONE) {
			public Point computeSize(int wHint, int hHint, boolean changed) {
				getScene().getWindow().sizeToScene();
				int width = (int) getScene().getWidth();
				int height = (int) getScene().getHeight();
				return new Point(width, height);
			}
		};
		Group group=new Group();
		JProgressRing loader=new JProgressRing(group);
		
		loader.setLoaderHeight(200);
		loader.setLoaderWidth(200);
		loader.showLoadingLabel(true);
		
		
		
//		group.getChildren().add(loader);
		
		 Scene scene = new Scene(group, Color.rgb(
				 parent.getBackground().getRed(),
				 parent.getBackground().getGreen(),
				 parent.getBackground().getBlue()));
		/* Attach an external stylesheet */
		//     scene.getStylesheets().add("twobuttons/Buttons.css");
		fxCanvas.setScene(scene);
		
		loader.start();
		parent.layout();

		parent.addDisposeListener(new DisposeListener() {
			 public void widgetDisposed(DisposeEvent e) {
				 loader.dispose();
			 }
			 });
			
	}
	@Override
public Point computeSize(int paramInt1, int paramInt2) {
	double width=	OS.GetSystemMetrics(10);
	double height=	OS.GetSystemMetrics(10);
	
	return new Point((int)width, (int)height);
};
	

	
public void releaseWidget(){
	
}
}
		


