package widget.progressring;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Tester  extends Application {

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Group group=new Group();
		
		JProgressRing control = new JProgressRing(group);
		control.start();
		
		Scene myScene = new Scene(group, 800, 600);
		stage.setScene(myScene);
		stage.show();
	}
}
