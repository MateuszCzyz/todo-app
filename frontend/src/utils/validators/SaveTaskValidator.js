class SaveTaskValidator {
  static validateTitle(title) {
    const titleValue = title.trim();
    if (!titleValue) {
      return "Title must not be empty";
    }
    return null;
  }

  static validateDescription(description) {
    const descriptionValue = description.trim();
    if (!descriptionValue) {
      return "Description must not be empty";
    }
    return null;
  }

  static validateDeadline(deadline) {
    if (!deadline) {
      return "Deadline must not be empty";
    }

    const currentDate = new Date();
    const givenDeadline = new Date(deadline);

    if (givenDeadline < currentDate) {
      return "Deadline must bet set as present of future date";
    }
    return null;
  }
}

export default SaveTaskValidator;
