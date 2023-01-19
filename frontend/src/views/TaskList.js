import { Component } from "react";
import TaskComponent from "../components/TaskComponent";
import DateUtils from "../utils/dates/DateUtils";

class TaskList extends Component {
  render() {
    return (
      <div id="task-list">
        {this.props.tasks.map((task) => {
          if (
            DateUtils.deadlineMatchesSelectedPeriod(
              task.deadline,
              this.props.selectedPeriod,
              this.props.offset
            )
          ) {
            return <TaskComponent key={task.id} task={task} />;
          }
        })}
      </div>
    );
  }
}

export default TaskList;
