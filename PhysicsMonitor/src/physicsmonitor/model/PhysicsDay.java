package physicsmonitor.model;

import java.io.Serializable;
import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

public class PhysicsDay implements Serializable, PhysicsTime {


  /**
   *
   */
  private double timeSpent = 0;
  private String stimeSpent = "";
  private Calendar calendar;

  /**
   *
   */
  public PhysicsDay(final Calendar dateTime, final String timeSpent) {
    this.calendar = dateTime;
  }


  /**
   * @return the dateTime
   */
  public String getDateTime() {
    Calendar date = this.calendar;
    String sDate = date.get(Calendar.DATE) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR);
    return sDate;

  }

  /**
   * @param dateTime the dateTime to set
   */
  public void setDateTime(final SimpleObjectProperty dateTime) {
    this.calendar = (Calendar) dateTime.get();
  }


  /**
   * @return the day
   */
  public String getDay() {
    int dayNum = this.calendar.get(Calendar.DAY_OF_WEEK);
    switch (dayNum) {
      case 1:
        return "SUN";
      case 2:
        return "MON";
      case 3:
        return "TUE";
      case 4:
        return "WED";
      case 5:
        return "THR";
      case 6:
        return "FRI";
      case 7:
        return "SAT";

    }
    return "NONE";
  }

  /**
   * @param pday the day to set
   */
  public void setSTimeSpent(final String timeSpent) {

    this.stimeSpent = timeSpent;
    int indexOfColon = timeSpent.indexOf(':');
    String hrs = "";
    String mins = "";
    if (indexOfColon != -1) {
      hrs = timeSpent.substring(0, indexOfColon);
      mins = timeSpent.substring(indexOfColon + 1);
    }
    else {
      hrs = timeSpent;
    }
    try {
      setTimeSpent(Integer.parseInt(hrs));
      if (indexOfColon != -1) {
        setTimeSpent(Integer.parseInt(hrs) + ((double) Integer.parseInt(mins) / 60));
      }
    }
    catch (NumberFormatException nfex) {
      //
    }
  }


  /**
   * @return the stimeSpent
   */
  public String getStimeSpent() {

    return this.stimeSpent;
  }


  /**
   * @return the timeSpent
   */
  public double getTimeSpent() {
    return this.timeSpent;
  }


  /**
   * @param timeSpent the timeSpent to set
   */
  public void setTimeSpent(final double timeSpent) {
    this.timeSpent = timeSpent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Calendar getCalendar() {
    return this.calendar;
  }

  @Override
  public void setCalendar(final Calendar calendar) {
    this.calendar = calendar;
  }

  /**
   * {@inheritDoc}
   */
}
