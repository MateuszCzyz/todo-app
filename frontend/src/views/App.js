import { Component } from "react";
import AuthService from "../services/authentication/AuthService.js";
import TaskService from "../services/tasks/TaskService.js";
import ModalPanel from "../components/ModalPanel";
import { Navigate } from "react-router-dom";
import TaskComponent from "../components/TaskComponent.js";

class App extends Component {
  state = {
    loading: true,
    authenticated: false,
    showModalPanel: false,
    tasks: [],
  };

  async componentDidMount() {
    const tokenIsValid = await AuthService.verifyAuthToken();
    if (!tokenIsValid) {
      this.setState({ loading: false });
    } else {
      const tasks = await TaskService.getAllTasks();
      this.setState({ loading: false, authenticated: true, tasks: tasks });
    }
  }

  displayModal() {
    this.setState({
      showModalPanel: true,
    });
  }

  closeModal() {
    this.setState({
      showModalPanel: false,
    });
  }

  addTaskToList(task) {
    this.setState({ tasks: [...this.state.tasks, task] });
  }

  render() {
    if (!this.state.authenticated && !this.state.loading) {
      return <Navigate to="/login" />;
    }

    if (this.state.loading) {
      return <p>Loading...</p>;
    }
    return (
      <>
        {this.state.showModalPanel && (
          <ModalPanel
            closeModal={() => this.closeModal()}
            addTaskToList={(task) => this.addTaskToList(task)}
          />
        )}
        <div id="app-root">
          <div id="container">
            <div id="period-navbar">
              <div id="smaller-period-box">
                <div class="period-button selected-period-button">
                  <div class="hidden-flex-div"></div>
                  <p>Day</p>
                  <div class="bottom-period-border"></div>
                </div>
                <div class="period-button">
                  <div class="hidden-flex-div"></div>
                  <p>Week</p>
                  <div class="bottom-period-border"></div>
                </div>
                <div class="period-button">
                  <div class="hidden-flex-div"></div>
                  <p>Month</p>
                  <div class="bottom-period-border"></div>
                </div>
                <div class="period-button">
                  <div class="hidden-flex-div"></div>
                  <p>Year</p>
                  <div class="bottom-period-border"></div>
                </div>
              </div>
            </div>
            <div id="selected-day-box">
              <div id="selected-day-menu">
                <img src="/assets/images/arrow.png" alt="left arrow" />
                <h2>Thursday</h2>
                <img src="/assets/images/arrow.png" alt="right arrow" />
              </div>
              <p>March 23, 2022</p>
            </div>
            <button id="add-task-button" onClick={() => this.displayModal()}>
              + add task
            </button>
            <div id="task-list">
              {this.state.tasks.map((task) => (
                <TaskComponent key={task.id} task={task} />
              ))}
            </div>
          </div>
        </div>
      </>
    );
  }
}

export default App;
