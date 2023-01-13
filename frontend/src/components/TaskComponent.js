import { Component } from "react";
import TaskService from "../services/tasks/TaskService";

class TaskComponent extends Component {
  constructor(props) {
    super(props);
  }

  state = {
    display: true,
    taskIsDone: this.props.task.done,
  };

  render() {
    if (!this.state.display) {
      return null;
    }
    return (
      <div class="task">
        <div class="task-left-side">
          <div
            className={
              this.state.taskIsDone ? "checkbox checkbox-selected" : "checkbox"
            }
            onClick={() => {
              TaskService.toggleTaskStatus(this.props.task.id);
              this.setState({ taskIsDone: !this.state.taskIsDone });
            }}
          >
            <img src="/assets/images/checkbox-selected.png" />
          </div>
          <div class="task-content">
            <p
              className={
                this.state.taskIsDone ? "task-title stricken" : "task-title"
              }
            >
              {this.props.task.title}
            </p>
            <p
              class={
                this.state.taskIsDone
                  ? "task-description stricken"
                  : "task-description"
              }
            >
              {this.props.task.description}
            </p>
          </div>
        </div>
        <img
          src="/assets/images/trash.png"
          alt="trash"
          class="remove-task-icon"
          onClick={() => {
            TaskService.deleteTask(this.props.task.id);
            this.setState({ display: false });
          }}
        />
      </div>
    );
  }
}

export default TaskComponent;
