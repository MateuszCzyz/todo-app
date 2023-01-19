import moment from "moment";
import Periods from "./Periods";

class DateUtils {
  static getCurrentWeekDay(offset = 0) {
    return moment().add(offset, "days").format("dddd");
  }

  static getCurrentDate(offset = 0) {
    return moment().add(offset, "days").format("LL");
  }

  static getCurrentWeek(offset = 0) {
    const weekIndex = moment().add(offset, "weeks").week();
    return "Week " + weekIndex;
  }

  static getWeekRange(offset = 0) {
    const startOfWeek = moment().add(offset, "weeks").startOf("week");
    const endOfWeek = moment().add(offset, "weeks").endOf("week");
    return startOfWeek.format("LL") + " - " + endOfWeek.format("LL");
  }

  static getCurrentMonth(offset = 0) {
    return moment().add(offset, "months").format("MMMM");
  }

  static getMonthRange(offset = 0) {
    const date = moment().add(offset, "months");
    const startOfMonth = date.startOf("month").format("LL");
    const endOfMonth = date.endOf("month").format("LL");
    return startOfMonth + " - " + endOfMonth;
  }

  static getCurrentYear(offset = 0) {
    return moment().add(offset, "years").year();
  }

  static getYearRange(offset = 0) {
    const startOfYear = moment().add(offset, "years").startOf("year");
    const endOfYear = moment().add(offset, "years").endOf("year");
    return startOfYear.format("DD MMMM") + " - " + endOfYear.format("DD MMMM");
  }

  // check whether a task's deadline is valid when it comes to the selected period by the user
  // users can display tasks by current day, week, month, etc...
  static deadlineMatchesSelectedPeriod(
    taskDeadline,
    selectedPeriod,
    offset = 0
  ) {
    const taskDeadlineDate = moment(taskDeadline);
    const currentDate = moment();
    if (selectedPeriod === Periods.DAY) {
      return currentDate.add(offset, "days").isSame(taskDeadlineDate, "day");
    } else if (selectedPeriod === Periods.WEEK) {
      return currentDate.add(offset, "weeks").isSame(taskDeadlineDate, "week");
    } else if (selectedPeriod === Periods.MONTH) {
      return currentDate
        .add(offset, "months")
        .isSame(taskDeadlineDate, "month");
    } else {
      return currentDate.add(offset, "years").isSame(taskDeadlineDate, "year");
    }
  }
}

export default DateUtils;
