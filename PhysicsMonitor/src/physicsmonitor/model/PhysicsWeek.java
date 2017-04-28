package physicsmonitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PhysicsWeek implements Serializable, PhysicsTime {

  private final List<PhysicsDay> physicsWeek;
  int weekNum = 0;
  private double timeSpent;
  private boolean isEmpty = true;
  private Calendar calendar;


  /**
   * @return the physicsWeek
   */
  public PhysicsWeek() {
    this.physicsWeek = new ArrayList<PhysicsDay>();
  }

  public List<PhysicsDay> getPhysicsWeek() {

    return this.physicsWeek;
  }


  public PhysicsDay getPhysicsDay(final int pday) {
    return this.physicsWeek.get(pday);
  }

  public void addPhysicsDay(final PhysicsDay physicsDay) {
    this.physicsWeek.add(physicsDay);
  }

  public void addPhysicsDay(final int pos, final PhysicsDay physicsDay) {
    this.physicsWeek.add(pos, physicsDay);
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
    return this.calendar;
  }

  @Override
  public void setCalendar(final Calendar calendar) {
    this.calendar = calendar;
  }


}
