package physicsmonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PhysicsYear implements Serializable, PhysicsTime {

  private List<PhysicsMonth> physicsYear;
  private boolean isEmpty = true;
  private Calendar calendar;
  private double timeSpent = 0;


  public PhysicsYear() {
    this.physicsYear = new ArrayList<PhysicsMonth>();
  }

  public PhysicsYear(final Calendar newCal, final int timeSpent) {
    this.calendar = newCal;
    this.timeSpent = timeSpent;

  }

  /**
   * @param physicsMonth
   */
  public void addPhysicsMonth(final PhysicsMonth physicsMonth) {
    this.physicsYear.add(physicsMonth);
  }


  /**
   * @return the isEmpty
   */
  public boolean isEmpty() {
    return this.isEmpty;
  }


  /**
   * @param isEmpty the isEmpty to set
   */
  public void setEmpty(final boolean isEmpty) {
    this.isEmpty = isEmpty;
  }

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
   * @return the physicsYear
   */
  public List<PhysicsMonth> getPhysicsYear() {
    return this.physicsYear;
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


}
