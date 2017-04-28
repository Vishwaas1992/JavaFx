package widget.progressring.wrapper;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import widget.progressring.JProgressRing;
import widget.progressring.interfaces.IJProgressRing;

public class SWTWrapper {

	public static void wrap(Composite parent, Control control){
		final FXCanvas fxCanvas = new FXCanvas(parent, SWT.NONE) {
			public Point computeSize(int wHint, int hHint, boolean changed) {
				getScene().getWindow().sizeToScene();
				int width = (int) getScene().getWidth();
				int height = (int) getScene().getHeight();
				return new Point(width, height);
			}
		};
//		Group group = new Group();
		if(control instanceof IJProgressRing){
			IJProgressRing iJProgRing=(IJProgressRing)control;
			Group group = iJProgRing.getGroup();
			group.getChildren().add(control);
			 Scene scene = new Scene(group, Color.rgb(
					 parent.getBackground().getRed(),
					 parent.getBackground().getGreen(),
					 parent.getBackground().getBlue()));
			fxCanvas.setScene(scene);
		}
//		Parent root = control.getScene().getRoot();
	
	}
}
