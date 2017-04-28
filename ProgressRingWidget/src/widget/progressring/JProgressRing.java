package widget.progressring;



import widget.progressring.interfaces.IJProgressRing;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class JProgressRing   {

	private double width=200;
	private double height=100;
	private boolean showLoadingLabel;
	private Group group;

//@Override
//	public Group getGroup() {
//		return this.group;
//	}

	public JProgressRing(Group group) {
		this.group=group;
		
	}
	

	private void init() {
		double width=getLoaderWidth();
		double length=getLoaderHeight();


		boolean showLoadingLabel = getShowLoadingLabel();

		double thickness=Math.sqrt(Math.pow(length,2)+Math.pow(width,2));

		Rectangle rect=new Rectangle(width, length);
		rect.setOpacity(0);
		group.getChildren().add(rect);


		Circle circle1=getNewCircle(width/2,5*thickness/100, 5*thickness/100, Color.BLUE);
		group.getChildren().add(circle1);

		Circle circle2=getNewCircle(width/2,5*thickness/100, 5*thickness/100, Color.RED);
		group.getChildren().add(circle2);

		Circle circle3=getNewCircle(width/2,5*thickness/100, 5*thickness/100, Color.YELLOW);
		group.getChildren().add(circle3);

		Path path = createEllipsePath((width-5*thickness/100)/2, (length)/2, (width-10*thickness/100)/2, (length-10*thickness/100)/2, 0);

		if(showLoadingLabel){ 
			Label loadingLabel=new Label("Loading...");
			group.getChildren().add(loadingLabel);
			loadingLabel.setPrefWidth(50.01);
			loadingLabel.setPrefHeight(10*length/100);
			loadingLabel.setTranslateX((width/2)-(width/100)-loadingLabel.getPrefWidth()/2);
			loadingLabel.setTranslateY((length)/2-5*length/100);
			loadingLabel.setScaleX(width/100);
			loadingLabel.setScaleY(length/100);

		}
		group.getChildren().add(path);

		PathTransition pathTransition1 = getNewPathTransition(Duration.seconds(1.5),path, circle1, false, Timeline.INDEFINITE, Duration.millis(0), Interpolator.SPLINE(0.7,0.5, 0.3, 1));
		pathTransition1.play();
		PathTransition	pathTransition2=getNewPathTransition(Duration.seconds(1.5),path, circle2, false, Timeline.INDEFINITE, Duration.millis(250), Interpolator.SPLINE(0.7,0.5, 0.3, 1));
		pathTransition2.play();
		PathTransition	pathTransition3=getNewPathTransition(Duration.seconds(1.5),path, circle3, false, Timeline.INDEFINITE, Duration.millis(500), Interpolator.SPLINE(0.7,0.5, 0.3, 1));
		pathTransition3.play();

	}

	private Circle getNewCircle(double posX, double posY, double radius, Color color) {
		Circle circle = new Circle(posX,posY, radius);
		circle.setFill(color);
		return circle;	
	}


	private PathTransition getNewPathTransition(Duration duration, Path path,
			Node node, boolean autoReverse, int CycleCount, Duration Delay,
			Interpolator interpolator) {

		PathTransition pathTransition=new PathTransition();
		pathTransition.setDuration(duration);
		pathTransition.setPath(path);
		pathTransition.setNode(node);
		pathTransition.setAutoReverse(autoReverse);
		pathTransition.setCycleCount(CycleCount);
		pathTransition.setDelay(Delay);
		pathTransition.setInterpolator(interpolator);

		return pathTransition;
	}


	private Path createEllipsePath(double centerX, double centerY, double radiusX, double radiusY, double rotate) {
		ArcTo arcTo = new ArcTo();
		arcTo.setX(2*centerX - radiusX ); // to simulate a full 360 degree celcius circle.
		arcTo.setY(centerY - radiusY);
		arcTo.setSweepFlag(true);
		arcTo.setLargeArcFlag(true);
		arcTo.setRadiusX(radiusX);
		arcTo.setRadiusY(radiusY);
		arcTo.setXAxisRotation(rotate);

		Path path=new Path();
		path.getElements().setAll(new MoveTo(2*centerX - radiusX+1, centerY - radiusY),  arcTo,new ClosePath());
		path.setOpacity(0);
		return path;
	}



	public void start(){
		group.getChildren().clear();
		init();
		group.setVisible(true);
	}

	public void stop(){
		group.setVisible(false);
	}

	public void dispose(){
		group.getChildren().clear();
	}


	public double getLoaderWidth() {
		return width;
	}

	public void setLoaderWidth(double width) {
		if(width<0){
			this.width=0;
		}
		else{
			this.width = width;
		}
	}
	public double getLoaderHeight() {
		return height;
	}
	public void setLoaderHeight(double height) {
		if(height<0){
			this.height =0;
		}
		else{
			this.height = height;
		}
	}
	
	public boolean getShowLoadingLabel() {
		return showLoadingLabel;
	}
	public void showLoadingLabel(boolean showLoadingLabel) {
		this.showLoadingLabel = showLoadingLabel;
	}
	


}