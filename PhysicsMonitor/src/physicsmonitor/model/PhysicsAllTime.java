package physicsmonitor.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PhysicsAllTime implements PhysicsTime {

  private final List<PhysicsYear> physicsYears;
  private Calendar calendar;


  /**
   *
   */
  public PhysicsAllTime() {
    this.physicsYears = new ArrayList<PhysicsYear>();
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
}
