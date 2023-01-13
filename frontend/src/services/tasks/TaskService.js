import AuthService from "../authentication/AuthService";
import Task from "../../models/Task";

class TaskService {
  static async getAllTasks() {
    const authToken = AuthService.getAuthToken();
    const requestOptions = {
      method: "GET",
      headers: {
        Authorization: authToken,
      },
    };
    const response = await fetch(
      "http://localhost:8080/api/tasks",
      requestOptions
    );
    if (response.ok) {
      const requestBody = await response.json();
      return requestBody.map(
        (task) =>
          new Task(
            task.id,
            task.title,
            task.description,
            task.importance,
            task.deadline,
            task.done
          )
      );
    }
  }

  static async saveTask(title, description, deadline, importance) {
    const authToken = AuthService.getAuthToken();
    const requestBody = {
      title: title,
      description: description,
      deadline: deadline,
      importance: importance,
    };

    const requestOptions = {
      method: "POST",
      headers: {
        Authorization: authToken,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    };

    const response = await fetch(
      "http://localhost:8080/api/tasks",
      requestOptions
    );

    if (!response.ok) {
      throw new Error("Could not save a new task");
    }

    const responseBody = await response.json();
    return new Task(
      responseBody.id,
      responseBody.title,
      responseBody.description,
      responseBody.importance,
      responseBody.deadline,
      responseBody.done
    );
  }

  static async deleteTask(id) {
    const authToken = AuthService.getAuthToken();
    const requestOptions = {
      method: "DELETE",
      headers: {
        Authorization: authToken,
      },
    };

    const response = await fetch(
      `http://localhost:8080/api/tasks/${id}/delete`,
      requestOptions
    );
  }

  static async toggleTaskStatus(id) {
    const authToken = AuthService.getAuthToken();
    const requestOptions = {
      method: "PATCH",
      headers: {
        Authorization: authToken,
      },
    };

    const response = await fetch(
      `http://localhost:8080/api/tasks/${id}/toggle-status`,
      requestOptions
    );
  }
}

export default TaskService;
