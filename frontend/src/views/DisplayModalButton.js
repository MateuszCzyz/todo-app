import { Component } from "react";

class DisplayModalButton extends Component {
  render() {
    return (
      <button id="add-task-button" onClick={this.props.onClick}>
        + add task
      </button>
    );
  }
}

export default DisplayModalButton;
