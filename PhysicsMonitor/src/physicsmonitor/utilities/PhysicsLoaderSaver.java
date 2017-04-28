package physicsmonitor.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import physicsmonitor.model.PhysicsAllTime;
import physicsmonitor.model.PhysicsDay;
import physicsmonitor.model.PhysicsMonth;
import physicsmonitor.model.PhysicsTime;
import physicsmonitor.model.PhysicsYear;

public class PhysicsLoaderSaver {

  private static String filePath = System.getProperty("user.dir") + "\\PhysicsAllTime";


  public static PhysicsTime loadPhysicsTime(final Calendar calendar, final int timeParam) {
    ObjectInputStream in = null;
    PhysicsTime physicsTime = null;
    FileInputStream fileIn = null;
    Object readObject = null;
    // System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
    try {

      switch (timeParam) {
        case 0: {
          fileIn = new FileInputStream(filePath + ".ser");
          in = new ObjectInputStream(fileIn);
          readObject = in.readObject();
          physicsTime = (PhysicsAllTime) readObject;
        }
          break;
        case 1: {
          fileIn = new FileInputStream(filePath + "\\" + calendar.get(Calendar.YEAR) + ".ser");
          in = new ObjectInputStream(fileIn);
          readObject = in.readObject();
          physicsTime = (PhysicsYear) readObject;
        }
          break;
        case 2: {
          fileIn =
              new FileInputStream(filePath + "\\" + calendar.get(Calendar.YEAR) + "\\" + calendar.get(Calendar.MONTH) +
                  ".ser");
          in = new ObjectInputStream(fileIn);
          readObject = in.readObject();
          physicsTime = (PhysicsMonth) readObject;
        }
          break;
        case 5: {
          fileIn =
              new FileInputStream(filePath + "\\" + calendar.get(Calendar.YEAR) + "\\" + calendar.get(Calendar.MONTH) +
                  "\\" + calendar.get(Calendar.DAY_OF_MONTH) + ".ser");
          in = new ObjectInputStream(fileIn);
          readObject = in.readObject();
          physicsTime = (PhysicsDay) readObject;
        }
        break;
      }

      if ((in != null) && (fileIn != null)) {
        in.close();
        fileIn.close();
      }
    }
    catch (FileNotFoundException fne) {
      // fne.printStackTrace();
      return null;
    }
    catch (IOException i) {
      i.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException c) {
      c.printStackTrace();
      return null;
    }
    finally {
      try {
        if (in != null) {
          in.close();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }


    }
    return physicsTime;
  }

  public static void savePhysicsTime(final PhysicsTime physicsTime) {

    FileOutputStream fileOut = null;
    ObjectOutputStream out = null;
    File file = null;
    file = new File(filePath);
    file.mkdir();
    int year = physicsTime.getCalendar().get(Calendar.YEAR);
    int month = physicsTime.getCalendar().get(Calendar.MONTH);
    int day = physicsTime.getCalendar().get(Calendar.DAY_OF_MONTH);

    try {
      if (physicsTime instanceof PhysicsDay) {
        PhysicsDay physicsDay = (PhysicsDay) physicsTime;
        file = new File(filePath);
        file.mkdir();
        file = new File(filePath + "\\" + year);
        file.mkdir();
        file = new File(filePath + "\\" + year + "\\" + month);
        file.mkdir();
        fileOut = new FileOutputStream(file.getPath() + "\\" + day + ".ser");
        out = new ObjectOutputStream(fileOut);
        out.writeObject(physicsDay);
      }
      else if (physicsTime instanceof PhysicsMonth) {
        PhysicsMonth physicsMonth = (PhysicsMonth) physicsTime;
        file = new File(filePath);
        file.mkdir();
        file = new File(filePath + "\\" + year);
        file.mkdir();
        fileOut = new FileOutputStream(file.getPath() + "\\" + month + ".ser");
        out = new ObjectOutputStream(fileOut);
        out.writeObject(physicsMonth);
      }
      else if (physicsTime instanceof PhysicsYear) {
        PhysicsYear physicsYear = (PhysicsYear) physicsTime;
        file = new File(filePath);
        file.mkdir();
        fileOut = new FileOutputStream(file.getPath() + "\\" + year + ".ser");
        out = new ObjectOutputStream(fileOut);
        out.writeObject(physicsYear);
      }
      if ((out != null) && (fileOut != null)) {
        out.close();
        fileOut.close();
      }
    }
    catch (IOException i) {
      i.printStackTrace();
    }
    finally {
      try {
        if (fileOut != null) {
          fileOut.close();
        }
        if (out != null) {
          out.close();
        }

      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * @param calendar
   */
  public static void savePrevMonth(final Calendar calendar) {
    PhysicsMonth physicsPrevMonth = new PhysicsMonth();

    Calendar calendarPrevMonth = new GregorianCalendar();
    if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
      if ((calendar.get(Calendar.MONTH) - 1) == 12) {
        calendarPrevMonth.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
      }
      calendarPrevMonth.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);

      int days = calendarPrevMonth.getMaximum(Calendar.DAY_OF_MONTH);
      for (int i = 1; i < days; i++) {
        calendarPrevMonth.set(Calendar.DAY_OF_MONTH, i);
        PhysicsDay physicsDay = (PhysicsDay) loadPhysicsTime(calendarPrevMonth, Calendar.DAY_OF_MONTH);
        physicsPrevMonth.addPhysicsDay(physicsDay);
      }
      savePhysicsTime(physicsPrevMonth);
    }
  }

  public static void savePrevYear(final Calendar calendar) {
    if (calendar.get(Calendar.DAY_OF_YEAR) == 1) {
      PhysicsYear physicsPrevYear = new PhysicsYear();
      Calendar calendarPrevYear = new GregorianCalendar();
      if (calendar.get(Calendar.DAY_OF_YEAR) == 1) {
        calendarPrevYear.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        for (int i = 1; i < 12; i++) {
          PhysicsMonth physicsMonth = (PhysicsMonth) loadPhysicsTime(calendarPrevYear, Calendar.MONTH);
          physicsPrevYear.addPhysicsMonth(physicsMonth);
        }
      }
    }
  }

}
