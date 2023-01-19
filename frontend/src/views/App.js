import { Component } from "react";
import AuthService from "../services/authentication/AuthService.js";
import TaskService from "../services/tasks/TaskService.js";
import ModalPanel from "../components/ModalPanel";
import { Navigate } from "react-router-dom";
import PeriodNavbar from "./PeriodNavbar.js";
import SelectedPeriodPanel from "./SelectedPeriodPanel.js";
import TaskList from "./TaskList.js";
import DisplayModalButton from "./DisplayModalButton.js";
import Periods from "../utils/dates/Periods";

class App extends Component {
  state = {
    loading: true,
    authenticated: false,
    showModalPanel: false,
    selectedPeriod: Periods.DAY,
    periodOffset: 0,
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

  // methods to manage a modal menu

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

  changeSelectedPeriod(period) {
    this.setState({ selectedPeriod: period });
  }

  // methods to manage an offset state

  increasePeriodOffset() {
    this.setState({ periodOffset: ++this.state.periodOffset });
  }

  decreasePeriodOffset() {
    this.setState({ periodOffset: --this.state.periodOffset });
  }

  resetPeriodOffset() {
    this.setState({ periodOffset: 0 });
  }

  // build component

  render() {
    if (!this.state.authenticated && !this.state.loading) {
      return <Navigate to="/login" />;
    }

    this.state.loading && <p>Loading...</p>;

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
            <PeriodNavbar
              changeSelectedPeriod={(period) =>
                this.changeSelectedPeriod(period)
              }
              resetOffset={() => this.resetPeriodOffset()}
            />
            <SelectedPeriodPanel
              selectedPeriod={this.state.selectedPeriod}
              periodOffset={this.state.periodOffset}
              increasePeriodOffset={() => this.increasePeriodOffset()}
              decreasePeriodOffset={() => this.decreasePeriodOffset()}
            />
            <DisplayModalButton onClick={() => this.displayModal()} />
            <TaskList
              tasks={this.state.tasks}
              selectedPeriod={this.state.selectedPeriod}
              offset={this.state.periodOffset}
            />
          </div>
        </div>
      </>
    );
  }
}

export default App;
