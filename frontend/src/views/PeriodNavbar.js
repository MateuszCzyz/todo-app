import { Component } from "react";
import Periods from "../utils/dates/Periods";
import Period from "./Period";

class PeriodNavbar extends Component {
  state = {
    selectedPeriod: Periods.DAY,
  };

  changeSelectedPeriod(selectedPeriod) {
    this.setState({
      selectedPeriod: selectedPeriod,
    });
    this.props.changeSelectedPeriod(selectedPeriod);
    this.props.resetOffset();
  }

  render() {
    return (
      <div id="period-navbar">
        <div id="smaller-period-box">
          {Object.entries(Periods).map(([key, index]) => {
            return (
              <Period
                label={Periods[key]}
                selected={this.state.selectedPeriod === Periods[key]}
                onClick={() => {
                  this.changeSelectedPeriod(Periods[key]);
                }}
              />
            );
          })}
        </div>
      </div>
    );
  }
}

export default PeriodNavbar;
