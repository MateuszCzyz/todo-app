class FormFieldValidator {
  static validateEmail(email) {
    const emailValue = email.trim();
    if (!emailValue) {
      return "E-mail must not be empty";
    } else if (!emailValue.match(/\S+@\S+\.\S+/)) {
      return "E-mail's format is not valid";
    }
    return null;
  }

  static validatePassword(password) {
    const passwordValue = password.trim();
    if (!passwordValue) {
      return "Password must not be empty";
    }
    return null;
  }

  static validateConfirmedPassword(originPassword, confirmedPassword) {
    const originPasswordValue = originPassword.trim();
    const confirmedPasswordValue = confirmedPassword.trim();
    if (!confirmedPasswordValue) {
      return "Confirmed password must not be empty";
    } else if (originPasswordValue != confirmedPasswordValue) {
      return "Value does not match with the first password";
    }
    return null;
  }
}

export default FormFieldValidator;
