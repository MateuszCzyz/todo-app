import { Component } from "react";
import TaskService from "../services/tasks/TaskService";
import SaveTaskValidator from "../utils/validators/SaveTaskValidator";

class ModalPanel extends Component {
  state = {
    form: {
      hasError: false,
      errorMessage: null,
    },
    title: {
      hasError: false,
      errorMessage: null,
    },
    description: {
      hasError: false,
      errorMessage: null,
    },
    deadline: {
      hasError: false,
      errorMessage: null,
    },
  };

  resetFormState() {
    this.setState({
      form: {
        hasError: false,
        errorMessage: null,
      },
      title: {
        hasError: false,
        errorMessage: null,
      },
      description: {
        hasError: false,
        errorMessage: null,
      },
      deadline: {
        hasError: false,
        errorMessage: null,
      },
    });
  }

  async submitForm(event) {
    event.preventDefault();
    this.resetFormState();
    const { title, description, deadline } = event.target;

    const titleValidationMessage = SaveTaskValidator.validateTitle(title.value);
    const descriptionValidationMessage = SaveTaskValidator.validateDescription(
      description.value
    );

    const deadlineValidationMessage = SaveTaskValidator.validateDeadline(
      deadline.value
    );

    this.setState({
      title: {
        hasError: titleValidationMessage != null,
        errorMessage: titleValidationMessage,
      },
      description: {
        hasError: descriptionValidationMessage != null,
        errorMessage: descriptionValidationMessage,
      },
      deadline: {
        hasError: deadlineValidationMessage != null,
        errorMessage: deadlineValidationMessage,
      },
    });

    if (
      titleValidationMessage == null &&
      descriptionValidationMessage == null &&
      deadlineValidationMessage == null
    ) {
      try {
        const savedTask = await TaskService.saveTask(
          title.value,
          description.value,
          deadline.value,
          1
        );

        this.props.closeModal();
        this.props.addTaskToList(savedTask);
      } catch (error) {
        this.setState({
          form: {
            hasError: true,
            errorMessage: error.message,
          },
        });
      }
    }
  }

  render() {
    return (
      <>
        <div id="shadow"></div>
        <div id="add-task-menu">
          <div id="add-task-menu-navbar">
            <span></span>
            <span>Add new task</span>
            <img
              src="/assets/images/exit.png"
              alt="exit icon"
              onClick={this.props.closeModal}
            />
          </div>
          <form onSubmit={(event) => this.submitForm(event)}>
            <span className="add-task-input-label">Title</span>
            <input type="text" name="title" className="add-task-input" />
            {this.state.title.hasError ? (
              <p className="error-message">{this.state.title.errorMessage}</p>
            ) : null}
            <span className="add-task-input-label">Description</span>
            <textarea
              type="text"
              name="description"
              className="add-task-input textarea-input"
            />
            {this.state.description.hasError && (
              <p className="error-message">
                {this.state.description.errorMessage}
              </p>
            )}
            <span className="add-task-input-label">Date</span>
            <input
              type="date"
              name="deadline"
              className="add-task-input"
              data-date-format="YYYY-MM-DD"
            />
            {this.state.deadline.hasError && (
              <p className="error-message">
                {this.state.deadline.errorMessage}
              </p>
            )}
            {this.state.form.hasError && (
              <p className="error-message form-error-message">
                {this.state.form.errorMessage}
              </p>
            )}
            <input type="submit" value="Save" id="save-task-button" />
          </form>
        </div>
      </>
    );
  }
}

export default ModalPanel;
