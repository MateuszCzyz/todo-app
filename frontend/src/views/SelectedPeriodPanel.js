import { Component } from "react";
import DateUtils from "../utils/dates/DateUtils";
import Periods from "../utils/dates/Periods";

class SelectedPeriodPanel extends Component {
  render() {
    return (
      <div id="selected-period-panel">
        <div id="selected-period-menu">
          <img
            src="/assets/images/arrow.png"
            alt="left arrow"
            onClick={this.props.decreasePeriodOffset}
          />
          <h2>
            {this.props.selectedPeriod === Periods.DAY
              ? DateUtils.getCurrentWeekDay(this.props.periodOffset)
              : this.props.selectedPeriod === Periods.WEEK
              ? DateUtils.getCurrentWeek(this.props.periodOffset)
              : this.props.selectedPeriod === Periods.MONTH
              ? DateUtils.getCurrentMonth(this.props.periodOffset)
              : DateUtils.getCurrentYear(this.props.periodOffset)}
          </h2>
          <img
            src="/assets/images/arrow.png"
            alt="right arrow"
            onClick={this.props.increasePeriodOffset}
          />
        </div>
        <p>
          {this.props.selectedPeriod === Periods.DAY
            ? DateUtils.getCurrentDate(this.props.periodOffset)
            : this.props.selectedPeriod === Periods.WEEK
            ? DateUtils.getWeekRange(this.props.periodOffset)
            : this.props.selectedPeriod === Periods.MONTH
            ? DateUtils.getMonthRange(this.props.periodOffset)
            : DateUtils.getYearRange(this.props.periodOffset)}
        </p>
      </div>
    );
  }
}

export default SelectedPeriodPanel;
