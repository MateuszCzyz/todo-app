import { Component } from "react";

class Period extends Component {
  render() {
    return (
      <div
        className={
          this.props.selected
            ? "period-button selected-period-button"
            : "period-button"
        }
        onClick={this.props.onClick}
      >
        <div class="hidden-flex-div"></div>
        <p>
          {this.props.label[0] + this.props.label.substring(1).toLowerCase()}
        </p>
        <div class="bottom-period-border"></div>
      </div>
    );
  }
}

export default Period;
