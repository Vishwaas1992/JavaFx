package physicsmonitor.ui;

import java.util.Calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonitorTester extends Application {

  public static void main(final String[] args) {
    launch();
    // Calendar cal = Calendar.getInstance();
    // testCalendar(cal);
  }

  /**
   *
   */
  private static void testCalendar(final Calendar cal) {

    Calendar upCal = cal;
    Calendar downCal = cal;
    while (upCal.get(Calendar.DAY_OF_WEEK) < (6 + Calendar.SUNDAY)) {
      Calendar newCal = Calendar.getInstance();
      newCal.set(Calendar.YEAR, upCal.get(Calendar.YEAR));
      newCal.set(Calendar.MONTH, upCal.get(Calendar.MONTH));
      newCal.set(Calendar.DATE, upCal.get(Calendar.DATE));

      upCal.set(Calendar.DATE, upCal.get(Calendar.DATE) + 1);
      newCal.set(Calendar.DATE, upCal.get(Calendar.DATE));

      System.out.println(newCal.getTime());
    }
    while (downCal.get(Calendar.DAY_OF_WEEK) > (Calendar.SUNDAY)) {
      Calendar newCal = Calendar.getInstance();
      newCal.set(Calendar.YEAR, downCal.get(Calendar.YEAR));
      newCal.set(Calendar.MONTH, downCal.get(Calendar.MONTH));
      newCal.set(Calendar.DATE, downCal.get(Calendar.DATE));

      downCal.set(Calendar.DATE, downCal.get(Calendar.DATE) - 1);
      newCal.set(Calendar.DAY_OF_WEEK, downCal.get(Calendar.DAY_OF_WEEK));
      System.out.println(newCal.get(Calendar.DATE));
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(final Stage primaryStage) throws Exception {
    primaryStage.setScene(new Scene(new PhysicsMonitorUI(), 800, 400));
    primaryStage.show();

  }

}
