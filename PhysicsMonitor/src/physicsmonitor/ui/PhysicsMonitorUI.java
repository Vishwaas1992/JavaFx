package physicsmonitor.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import physicsmonitor.customcontrol.CustomControl;
import physicsmonitor.model.PhysicsAllTime;
import physicsmonitor.model.PhysicsDay;
import physicsmonitor.model.PhysicsMonth;
import physicsmonitor.model.PhysicsWeek;
import physicsmonitor.model.PhysicsYear;
import physicsmonitor.utilities.PhysicsLoaderSaver;

public class PhysicsMonitorUI extends CustomControl {

  @FXML
  TableView dateTable;

  @FXML
  GridPane gridPane;

  @FXML
  Label totalTxt;

  @FXML
  RadioButton weekRBtn;

  @FXML
  RadioButton monthRBtn;

  @FXML
  RadioButton yearRBtn;

  @FXML
  RadioButton allTimeRBtn;

  @FXML
  private LineChart lineChart;

  @FXML
  private NumberAxis xAxis;

  @FXML
  private NumberAxis yAxis;

  @FXML
  private DatePicker phyDatePicker;

  @FXML
  private Button fwdBtn;

  @FXML
  private Button backBtn;

  private PhysicsYear physicsYear;

  private PhysicsMonth physicsMonth;

  private PhysicsWeek physicsWeek;

  private PhysicsAllTime physicsAllTime;


  // private final PhysicsAllTime physicsAllTime;
  private int monthNum;

  @FXML
  private Button goBtn;

  private Calendar calendar;

  /**
   *
   */


  @SuppressWarnings("unchecked")
  public PhysicsMonitorUI() {
    RadioButton[] physicsTimes = { this.weekRBtn, this.monthRBtn, this.yearRBtn, this.allTimeRBtn };
    this.physicsWeek = new PhysicsWeek();
    this.physicsMonth = new PhysicsMonth();
    this.physicsYear = new PhysicsYear();
    this.calendar = Calendar.getInstance();
    // this.calendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH) + 1);
    // this.calendar.set(Calendar.DAY_OF_MONTH, 1);
    setDatePickerFromCalendar();
    // calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
    this.physicsWeek.setCalendar(this.calendar);
    this.physicsMonth.setCalendar(this.calendar);

    // PhysicsLoaderSaver.savePrevMonth(calendar);
    // PhysicsLoaderSaver.savePrevYear(calendar);


    if (this.calendar.get(Calendar.DAY_OF_MONTH) == 1) {
      if (this.calendar.get(Calendar.MONTH) == 0) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.calendar.get(Calendar.YEAR) - 1);
        calendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH) - 1);
        saveYear(calendar);
      }
      else {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH) - 1);
        saveMonth(calendar);
      }
    }


    // this.phyDatePicker.setValue(LocalDate.of(this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH) + 1,
    // this.calendar.get(Calendar.DAY_OF_MONTH)));


    TableColumn column1 = new TableColumn("Date");
    column1.setCellValueFactory(new PropertyValueFactory<PhysicsDay, Date>("dateTime"));
    TableColumn column2 = new TableColumn("Day");
    column2.setCellValueFactory(new PropertyValueFactory<PhysicsDay, String>("day"));
    TableColumn column3 = new TableColumn("Time spent");
    column3.setCellValueFactory(new PropertyValueFactory<PhysicsDay, String>("stimeSpent"));

    column3.setCellFactory(TextFieldTableCell.forTableColumn());
    column3.setOnEditCommit(new EventHandler<CellEditEvent<PhysicsDay, String>>() {

      @Override
      public void handle(final CellEditEvent<PhysicsDay, String> t) {
        String timeSpent = t.getNewValue();
        int row = t.getTablePosition().getRow();
        PhysicsDay physicsDay = PhysicsMonitorUI.this.physicsWeek.getPhysicsDay(row);
        physicsDay.setSTimeSpent(timeSpent.trim());
        setTodayText(physicsDay, row);
        dayText(row);
        setChartParam(7, "Days");
        Calendar calendar = getCalendarFromDatePiciker();
        setWeekTableData(calendar);
      }
    });

    ObservableList<PhysicsDay> physicsWeekObsList =
        FXCollections.observableArrayList(this.physicsWeek.getPhysicsWeek());
    this.dateTable.setItems(physicsWeekObsList);

    this.dateTable.getColumns().addAll(column1, column2, column3);
    this.dateTable.setEditable(true);


    this.weekRBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent event) {
        column1.setText("Date");
        column2.setText("Day");
        Calendar calendar = getCalendarFromDatePiciker();
        setWeekTableData(calendar);
        hendleRBtnSelected(physicsTimes, 0);
        PhysicsMonitorUI.this.dateTable.setEditable(true);
      }
    });

    this.monthRBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent event) {
        column1.setText("Date");
        column2.setText("Day");
        Calendar calendar = getCalendarFromDatePiciker();
        boolean isPrevMonth = isCalPrevMonth(calendar);
        setMonthTableData(calendar, isPrevMonth);
        hendleRBtnSelected(physicsTimes, 1);
        PhysicsMonitorUI.this.dateTable.setEditable(false);
      }
    });

    this.yearRBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent event) {
        column1.setText("Month No");
        column2.setText("Month");
        Calendar calendar = getCalendarFromDatePiciker();
        boolean isPrevMonth = isCalPrevYear(calendar);
        setYearTableData(calendar, isPrevMonth);
        hendleRBtnSelected(physicsTimes, 2);
        PhysicsMonitorUI.this.dateTable.setEditable(false);
      }
    });

    this.allTimeRBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent event) {
        column1.setText("Year No");
        column2.setText("Year");
        Calendar calendar = getCalendarFromDatePiciker();
        setAllTimeTableData();
        PhysicsMonitorUI.this.dateTable.setEditable(false);
        hendleRBtnSelected(physicsTimes, 3);
      }
    });

    this.goBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent event) {
        Calendar calendar = getCalendarFromDatePiciker();
        PhysicsMonitorUI.this.calendar = calendar;

        // calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        if (PhysicsMonitorUI.this.weekRBtn.isSelected()) {
          PhysicsMonitorUI.this.physicsWeek.setCalendar(calendar);
          setWeekTableData(calendar);
        }
        else if (PhysicsMonitorUI.this.monthRBtn.isSelected()) {
          boolean isPrevMonth = isCalPrevMonth(calendar);
          if (PhysicsMonitorUI.this.physicsMonth != null) {
            PhysicsMonitorUI.this.physicsMonth.setCalendar(calendar);
          }
          setMonthTableData(calendar, isPrevMonth);
        }
        else if (PhysicsMonitorUI.this.yearRBtn.isSelected()) {
          boolean isPrevYear = isCalPrevMonth(calendar);
          if (PhysicsMonitorUI.this.physicsYear != null) {
            PhysicsMonitorUI.this.physicsYear.setCalendar(calendar);
          }
          setYearTableData(calendar, isPrevYear);
        }
      }
    });

    this.fwdBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent event) {
        PhysicsMonitorUI.this.calendar.set(Calendar.WEEK_OF_MONTH,
            PhysicsMonitorUI.this.calendar.get(Calendar.WEEK_OF_MONTH) + 1);
        setDatePickerFromCalendar();
        setWeekTableData(PhysicsMonitorUI.this.calendar);
      }
    });

    this.backBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent event) {
        PhysicsMonitorUI.this.calendar.set(Calendar.WEEK_OF_MONTH,
            PhysicsMonitorUI.this.calendar.get(Calendar.WEEK_OF_MONTH) - 1);
        setDatePickerFromCalendar();
        setWeekTableData(PhysicsMonitorUI.this.calendar);
      }
    });


    setWeekTableData(this.calendar);
  }

  /**
   * @param calendar2
   */
  private boolean isCalPrevYear(final Calendar newCalendar) {
    Calendar calendar = Calendar.getInstance();
    if (newCalendar.get(Calendar.YEAR) < calendar.get(Calendar.YEAR)) {
      return true;
    }
    return false;
  }

  /**
   * @param calendar2
   */
  // private boolean isCalPrevMonth(final Calendar newCalendar) {
  // Calendar calendar = Calendar.getInstance();
  // if (newCalendar.get(Calendar.YEAR) < calendar.get(Calendar.YEAR)) {
  // return true;
  // }
  // if (newCalendar.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)) {
  // return true;
  // }
  // return false;
  //
  // }

  private boolean isCalPrevMonth(final Calendar newCalendar) {
    Calendar calendar = Calendar.getInstance();
    if (newCalendar.get(Calendar.YEAR) < calendar.get(Calendar.YEAR)) {
      return true;
    }
    if (newCalendar.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)) {
      return true;
    }
    return false;
  }

  /**
   * @param calendar2
   */
  protected void setDatePickerFromCalendar() {
    this.phyDatePicker.setValue(LocalDate.of(this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH) + 1,
        this.calendar.get(Calendar.DAY_OF_MONTH)));

  }

  /**
   * @param calendar
   */


  /**
   * @param i
   */
  protected void hendleRBtnSelected(final RadioButton[] radioBtn, final int i) {

    radioBtn[i].setSelected(true);
    for (int j = 0; (j < 4); j++) {
      if (j != i) {
        radioBtn[j].setSelected(false);
      }
    }
  }

  /**
   *
   */
  private void setChartParam(final double xU, final String timeGroup) {
    this.lineChart.getData().clear();
    this.xAxis.setLowerBound(0);
    this.xAxis.setUpperBound(xU);
    this.xAxis.setTickUnit(1);
    this.xAxis.setLabel(timeGroup);
    // this.lineChart.getXAxis().setAutoRanging(false);

    this.yAxis.setLowerBound(0);
    this.yAxis.setUpperBound(24);
    this.yAxis.setTickUnit(1);
    this.yAxis.setLabel("Hours");
    // this.lineChart.getYAxis().setAutoRanging(false);
    // lineChart.getXAxis().
  }

  private Data<Number, Number> addChartData(final Calendar calendar, final int i, final double timeSpent) {
    Data<Number, Number> data = new XYChart.Data<Number, Number>(i, timeSpent);
    Tooltip toolTip = new Tooltip();
    Circle circle = new Circle(5);
    toolTip.setText(calendar.getTime() + " : " + getHrs(timeSpent) + " hrs " + getMins(timeSpent) + " mins");
    Tooltip.install(circle, toolTip);
    data.setNode(circle);
    return data;
  }

  /**
   * @param i
   * @return
   */
  private String getDay(final int dayNum) {
    switch (dayNum) {
      case 0:
        return "SUN";
      case 1:
        return "MON";
      case 2:
        return "TUE";
      case 3:
        return "WED";
      case 4:
        return "THU";
      case 5:
        return "FRI";
      case 6:
        return "SAT";
      default:
        return "UNKNOWN";
    }
  }

  private Calendar getCalendarFromDatePiciker() {
    int year = PhysicsMonitorUI.this.phyDatePicker.getValue().getYear();
    LocalDate value = PhysicsMonitorUI.this.phyDatePicker.getValue();
    int month = PhysicsMonitorUI.this.phyDatePicker.getValue().getMonth().getValue();
    int dayOfMonth = PhysicsMonitorUI.this.phyDatePicker.getValue().getDayOfMonth();
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

    return calendar;
  }

  // LineChart chart = new LineChart(xAxis, yAxis, lineChartData);

  /**
   * @param i
   */
  private void setWeekTableData(final Calendar calendar) {
    PhysicsWeek physicsWeek = getPhysicsWeek(calendar);

    this.physicsWeek.setEmpty(true);
    this.physicsWeek.getPhysicsWeek().clear();
    if (!physicsWeek.isEmpty()) {
      this.physicsWeek = physicsWeek;
    }
    else {
      for (int i = 1; i < 8; i++) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        newCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        newCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        newCalendar.set(Calendar.DAY_OF_WEEK, i);
        PhysicsDay physicsDay = new PhysicsDay(newCalendar, "0");
        // PhysicsDay physicsDay = this.physicsWeek.getPhysicsWeek().get(i);
        this.physicsWeek.addPhysicsDay(physicsDay);
        physicsDay.setCalendar(newCalendar);
        physicsDay.setSTimeSpent("0");
        this.physicsWeek.setEmpty(false);

      }
    }
    PhysicsMonitorUI.this.dateTable.setItems(FXCollections.observableArrayList(PhysicsMonitorUI.this.physicsWeek
        .getPhysicsWeek()));
  }

  public PhysicsWeek getPhysicsWeek(final Calendar calendar) {
    double timeSpent = 0;
    Calendar upCal = Calendar.getInstance();
    upCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
    upCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
    upCal.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
    Calendar downCal = Calendar.getInstance();
    downCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
    downCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
    downCal.set(Calendar.DATE, calendar.get(Calendar.DATE));
    setChartParam(7, "Days");
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    this.lineChart.getData().clear();
    PhysicsWeek physicsWeek = new PhysicsWeek();
    int i = 0, j = 0;
    while (upCal.get(Calendar.DAY_OF_WEEK) < (6 + Calendar.SUNDAY)) {
      Calendar newCal = Calendar.getInstance();

      newCal.set(Calendar.DATE, upCal.get(Calendar.DATE));

      upCal.set(Calendar.DATE, upCal.get(Calendar.DATE) + 1);
      newCal.set(Calendar.YEAR, upCal.get(Calendar.YEAR));
      newCal.set(Calendar.MONTH, upCal.get(Calendar.MONTH));
      newCal.set(Calendar.DATE, upCal.get(Calendar.DATE));
      PhysicsDay physicsDay = (PhysicsDay) PhysicsLoaderSaver.loadPhysicsTime(newCal, Calendar.DAY_OF_MONTH);
      if (physicsDay != null) {
        double timeSpentDay = physicsDay.getTimeSpent();
        timeSpent = timeSpent + timeSpentDay;
        Data<Number, Number> data = addChartData(newCal, newCal.get(Calendar.DAY_OF_WEEK) - 1, timeSpentDay);
        series.getData().add(data);
        physicsWeek.addPhysicsDay(physicsDay);
        physicsWeek.setEmpty(false);
      }
      else {
        PhysicsDay physicsDay2 = new PhysicsDay(newCal, "0");
        physicsWeek.addPhysicsDay(physicsDay2);
        Data<Number, Number> data = addChartData(newCal, i, 0);
        series.getData().add(data);
        physicsWeek.setEmpty(false);
      }
      i++;
    }
    while (downCal.get(Calendar.DAY_OF_WEEK) > (Calendar.SUNDAY)) {
      Calendar newCal = Calendar.getInstance();

      newCal.set(Calendar.DATE, downCal.get(Calendar.DATE));

      downCal.set(Calendar.DATE, downCal.get(Calendar.DATE) - 1);
      newCal.set(Calendar.YEAR, downCal.get(Calendar.YEAR));
      newCal.set(Calendar.MONTH, downCal.get(Calendar.MONTH));
      newCal.set(Calendar.DATE, downCal.get(Calendar.DATE));
      PhysicsDay physicsDay = (PhysicsDay) PhysicsLoaderSaver.loadPhysicsTime(newCal, Calendar.DAY_OF_MONTH);
      if (physicsDay != null) {
        double timeSpentDay = physicsDay.getTimeSpent();
        timeSpent = timeSpent + timeSpentDay;
        Data<Number, Number> data = addChartData(newCal, newCal.get(Calendar.DAY_OF_WEEK) - 1, timeSpentDay);
        series.getData().add(data);
        physicsWeek.addPhysicsDay(0, physicsDay);
        physicsWeek.setEmpty(false);
      }
      else {
        PhysicsDay physicsDay2 = new PhysicsDay(newCal, "0");
        physicsWeek.addPhysicsDay(0, physicsDay2);
        Data<Number, Number> data = addChartData(newCal, j, 0);
        series.getData().add(data);
        physicsWeek.setEmpty(false);
      }
      j++;

    }
    physicsWeek.setTimeSpent(timeSpent);
    this.totalTxt.setText(getHrs(timeSpent) + " hrs " + getMins(timeSpent) + " mins");
    this.lineChart.getData().add(series);
    return physicsWeek;
  }

  private void setMonthTableData(final Calendar calendar, final boolean prev) {
    if (prev) {
      PhysicsMonth physicsMonth = (PhysicsMonth) PhysicsLoaderSaver.loadPhysicsTime(calendar, Calendar.MONTH);
      this.physicsMonth = physicsMonth;
    }
    else {
      PhysicsMonth physicsMonth = getPhysicsMonth(calendar);
      this.physicsMonth = physicsMonth;
      PhysicsLoaderSaver.savePhysicsTime(physicsMonth);
    }
    if (this.physicsMonth != null) {
      PhysicsMonitorUI.this.dateTable.setItems(FXCollections.observableArrayList(PhysicsMonitorUI.this.physicsMonth
          .getPhysicsMonth()));
    }
    else {
      PhysicsMonitorUI.this.dateTable.setItems(null);
    }
  }

  public PhysicsMonth getPhysicsMonth(final Calendar calendar) {
    setChartParam(calendar.getMaximum(Calendar.DAY_OF_MONTH), "Days");
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    this.lineChart.getData().clear();
    double timeSpent = 0;
    Calendar upCal = Calendar.getInstance();
    upCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
    upCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
    upCal.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
    Calendar downCal = Calendar.getInstance();
    downCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
    downCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
    downCal.set(Calendar.DATE, calendar.get(Calendar.DATE));

    PhysicsMonth physicsMonth = new PhysicsMonth();
    int i = 0, j = 0;
    int index = upCal.get(Calendar.DAY_OF_MONTH);
    while (upCal.get(Calendar.DAY_OF_MONTH) < (calendar.getMaximum(Calendar.DAY_OF_MONTH))) {
      if (index == upCal.get(Calendar.DAY_OF_MONTH)) {
        Calendar newCal = Calendar.getInstance();
        newCal.set(Calendar.DATE, upCal.get(Calendar.DATE));

        upCal.set(Calendar.DATE, upCal.get(Calendar.DATE) + 1);
        newCal.set(Calendar.YEAR, upCal.get(Calendar.YEAR));
        newCal.set(Calendar.MONTH, upCal.get(Calendar.MONTH));
        newCal.set(Calendar.DATE, upCal.get(Calendar.DATE));
        PhysicsDay physicsDay = (PhysicsDay) PhysicsLoaderSaver.loadPhysicsTime(newCal, Calendar.DAY_OF_MONTH);
        if (physicsDay != null) {
          double timeSpentDay = physicsDay.getTimeSpent();
          timeSpent = timeSpent + timeSpentDay;
          Data<Number, Number> data = addChartData(newCal, newCal.get(Calendar.DAY_OF_MONTH) - 1, timeSpentDay);
          series.getData().add(data);
          physicsMonth.addPhysicsDay(physicsDay);
          physicsMonth.setEmpty(false);
        }
        else {
          PhysicsDay physicsDay2 = new PhysicsDay(newCal, "0");
          physicsMonth.addPhysicsDay(physicsDay2);
          Data<Number, Number> data = addChartData(newCal, i, 0);
          series.getData().add(data);
          physicsMonth.setEmpty(false);
        }
        i++;
        index++;
      }
      else {
        break;
      }
    }
    while (downCal.get(Calendar.DAY_OF_MONTH) > 1) {
      Calendar newCal = Calendar.getInstance();
      newCal.set(Calendar.DATE, downCal.get(Calendar.DATE));
      downCal.set(Calendar.DATE, downCal.get(Calendar.DATE) - 1);
      newCal.set(Calendar.YEAR, downCal.get(Calendar.YEAR));
      newCal.set(Calendar.MONTH, downCal.get(Calendar.MONTH));
      newCal.set(Calendar.DATE, downCal.get(Calendar.DATE));
      PhysicsDay physicsDay = (PhysicsDay) PhysicsLoaderSaver.loadPhysicsTime(newCal, Calendar.DAY_OF_MONTH);
      if (physicsDay != null) {
        double timeSpentDay = physicsDay.getTimeSpent();
        timeSpent = timeSpent + timeSpentDay;
        Data<Number, Number> data = addChartData(newCal, newCal.get(Calendar.DAY_OF_MONTH) - 1, timeSpentDay);
        series.getData().add(data);
        physicsMonth.addPhysicsDay(0, physicsDay);
        physicsMonth.setEmpty(false);
      }
      else {
        PhysicsDay physicsDay2 = new PhysicsDay(newCal, "0");
        physicsMonth.addPhysicsDay(0, physicsDay2);
        Data<Number, Number> data = addChartData(newCal, j, 0);
        series.getData().add(data);
        physicsMonth.setEmpty(false);
      }
      j++;
    }
    physicsMonth.setTimeSpent(timeSpent);
    physicsMonth.setCalendar(calendar);
    this.totalTxt.setText(getHrs(timeSpent) + " hrs " + getMins(timeSpent) + " mins");
    this.lineChart.getData().add(series);
    return physicsMonth;
  }

  private void saveMonth(final Calendar calendar) {
    PhysicsMonth physicsMonth = getPhysicsMonth(calendar);
    PhysicsLoaderSaver.savePhysicsTime(physicsMonth);
  }

  private void saveYear(final Calendar calendar) {
    PhysicsYear physicsYear = getPhysicsYear(calendar);
    PhysicsLoaderSaver.savePhysicsTime(physicsYear);
  }

  protected void setYearTableData(final Calendar calendar, final boolean isPrev) {
    if (isPrev) {
      PhysicsYear physicsYear = (PhysicsYear) PhysicsLoaderSaver.loadPhysicsTime(calendar, Calendar.MONTH);
      this.physicsYear = physicsYear;
    }
    else {
      PhysicsYear physicsYear = getPhysicsYear(calendar);
      this.physicsYear = physicsYear;
      PhysicsLoaderSaver.savePhysicsTime(physicsYear);
    }
    if (this.physicsYear != null) {
      PhysicsMonitorUI.this.dateTable.setItems(FXCollections.observableArrayList(PhysicsMonitorUI.this.physicsYear
          .getPhysicsYear()));
    }

  }

  private PhysicsYear getPhysicsYear(final Calendar calendar) {
    setChartParam(12, "Months");
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    double timeSpent = 0;
    PhysicsYear physicsYear = new PhysicsYear();

    this.lineChart.getData().clear();
    for (int i = 0; i < 12; i++) {
      Calendar newCal = Calendar.getInstance();
      newCal.set(Calendar.YEAR, this.calendar.get(Calendar.YEAR));
      newCal.set(Calendar.MONTH, i);
      PhysicsMonth physicsMonth = (PhysicsMonth) PhysicsLoaderSaver.loadPhysicsTime(newCal, Calendar.MONTH);
      if (physicsMonth != null) {
        physicsYear.addPhysicsMonth(physicsMonth);
        double timeSpentMonth = physicsMonth.getTimeSpent();
        timeSpent = timeSpent + timeSpentMonth;
        Data<Number, Number> data = addChartData(newCal, i, timeSpentMonth);
        series.getData().add(data);
      }
      else {
        physicsYear.addPhysicsMonth(new PhysicsMonth(newCal, 0));
        Data<Number, Number> data = addChartData(newCal, i, 0);
        series.getData().add(data);
      }
    }
    this.totalTxt.setText(getHrs(timeSpent) + " hrs " + getMins(timeSpent) + " mins");
    this.lineChart.getData().add(series);
    physicsYear.setTimeSpent(timeSpent);
    physicsYear.setCalendar(calendar);
    return physicsYear;
  }


  private void setAllTimeTableData() {
    double timeSpent = 0;
    setChartParam(100, "Years");
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    this.lineChart.getData().clear();
    List<PhysicsYear> physicsYears = new ArrayList<PhysicsYear>();
    int i = 0;
    while (true) {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2016 + i);
      PhysicsYear physicsYear = (PhysicsYear) PhysicsLoaderSaver.loadPhysicsTime(calendar, Calendar.YEAR);
      if (physicsYear == null) {
        break;
      }
      physicsYears.add(physicsYear);
      timeSpent = timeSpent + physicsYear.getTimeSpent();
      Data<Number, Number> data = addChartData(calendar, i, timeSpent);
      series.getData().add(data);
      i++;
    }
    this.totalTxt.setText(getHrs(timeSpent) + " hrs " + getMins(timeSpent) + " mins");
    this.lineChart.getData().add(series);
    PhysicsMonitorUI.this.dateTable.setItems(FXCollections.observableArrayList(physicsYears));
  }

  /**
   * @param dateTable2
   */
  protected void dayText(final int day) {
    double timeSpentWeek = 0;
    for (PhysicsDay physicsDay : this.physicsWeek.getPhysicsWeek()) {
      timeSpentWeek = timeSpentWeek + physicsDay.getTimeSpent();
    }
    this.physicsWeek.setTimeSpent(timeSpentWeek);
    int mins = getMins(timeSpentWeek);
    int hrs = getHrs(timeSpentWeek);
    // this.thisWeekTxt.setText(hrs + " : " + mins);
    PhysicsLoaderSaver.savePhysicsTime(this.physicsWeek.getPhysicsDay(day));
  }


  /**
   * @param timeSpent
   */
  protected void setTodayText(final PhysicsDay physicsDay, final int row) {
    Calendar dateTime = Calendar.getInstance();
    double timeSpentDay = 0;

    if (dateTime.get(Calendar.DAY_OF_WEEK) == (row + 1)) {
      timeSpentDay = physicsDay.getTimeSpent();
      int mins = getMins(timeSpentDay);
      int hrs = getHrs(timeSpentDay);
      this.totalTxt.setText(hrs + " : " + mins);
    }
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
