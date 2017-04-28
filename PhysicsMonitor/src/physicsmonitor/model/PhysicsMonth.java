package physicsmonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PhysicsMonth implements PhysicsTime, Serializable {

  private List<PhysicsDay> physicsMonth;
  private boolean isEmpty = true;
  private Calendar calendar;
  int monthNum = 0;
  private double timeSpent = 0;

  /**
   * @return the monthNum
   */
  public int getMonthNum() {
    return this.monthNum;
  }

  public String getDateTime() {
    // Calendar date = this.calendar;
    // String sDate = date.get(Calendar.DATE) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR);
    return this.calendar.get(Calendar.MONTH) + 1 + "";
  }

  /**
   * @param month
   */
  public String getDay() {
    int month = this.calendar.get(Calendar.MONTH);
    switch (month) {
      case 0:
        return "JAN";
      case 1:
        return "FEB";
      case 2:
        return "MAR";
      case 3:
        return "APR";
      case 4:
        return "MAY";
      case 5:
        return "JUN";
      case 6:
        return "JUL";
      case 7:
        return "AUG";
      case 8:
        return "SEP";
      case 9:
        return "OCT";
      case 10:
        return "NOV";
      case 11:
        return "DEC";
      default:
        return "UNKNOWN";
    }
  }

  /**
   * @param monthNum the monthNum to set
   */
  public void setMonthNum(final int monthNum) {
    this.monthNum = monthNum;
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
   *
   */
  public PhysicsMonth() {
    this.physicsMonth = new ArrayList<PhysicsDay>();
  }

  /**
   * @param newCal
   * @param i
   */
  public PhysicsMonth(final Calendar newCal, final int timeSpent) {
    this.calendar = newCal;
    this.timeSpent = timeSpent;

  }

  /**
   * @param physicsDay
   */
  public void addPhysicsDay(final PhysicsDay physicsDay) {
    getPhysicsMonth().add(physicsDay);
  }

  public void addPhysicsDay(final int pos, final PhysicsDay physicsDay) {
    getPhysicsMonth().add(pos, physicsDay);
  }

  /**
   * @return the isEmpty
   */
  public boolean isEmpty() {
    return this.isEmpty;
  }

  public String getStimeSpent() {
    return getHrs(this.timeSpent) + ": " + getMins(this.timeSpent);
  }

  /**
   * @param isEmpty the isEmpty to set
   */
  /**
   * {@inheritDoc}
   */
  @Override
  public Calendar getCalendar() {
    // TODO Auto-generated method stub
    return this.calendar;
  }

  @Override
  public void setCalendar(final Calendar calendar) {
    this.calendar = calendar;
  }


  /**
   * @return the physicsMonth
   */
  public List<PhysicsDay> getPhysicsMonth() {
    return this.physicsMonth;
  }

  public void setEmpty(final boolean empty) {
    this.isEmpty = empty;
  }

  private int getHrs(final double timeSpent) {
    int hrs = (int) Math.floor(timeSpent);
    return hrs;
  }

  private int getMins(final double timeSpent) {
    int mins = (int) ((timeSpent - Math.floor(timeSpent)) * 60);
    return mins;
  }

}
