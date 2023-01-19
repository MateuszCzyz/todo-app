import { Component } from "react";
import { Link, Navigate } from "react-router-dom";
import AuthService from "../services/authentication/AuthService";
import AuthValidator from "../utils/validators/AuthValidator";

class Register extends Component {
  state = {
    redirect: false,
    form: {
      hasError: false,
      errorMessage: null,
    },
    email: {
      hasError: false,
      errorMessage: null,
    },
    password: {
      hasError: false,
      errorMessage: null,
    },
    confirmPassword: {
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
      email: {
        hasError: false,
        errorMessage: null,
      },
      password: {
        hasError: false,
        errorMessage: null,
      },
      confirmPassword: {
        hasError: false,
        errorMessage: null,
      },
    });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { email, password, confirmPassword } = event.target;

    this.resetFormState();

    const emailValidationMessage = AuthValidator.validateEmail(email.value);
    const passwordValidationMessage = AuthValidator.validatePassword(
      password.value
    );
    const confirmedPasswordValidationMessage =
      AuthValidator.validateConfirmedPassword(
        password.value,
        confirmPassword.value
      );

    this.setState({
      email: {
        hasError: emailValidationMessage != null,
        errorMessage: emailValidationMessage,
      },
      password: {
        hasError: passwordValidationMessage != null,
        errorMessage: passwordValidationMessage,
      },
      confirmPassword: {
        hasError: confirmedPasswordValidationMessage != null,
        errorMessage: confirmedPasswordValidationMessage,
      },
    });

    if (
      emailValidationMessage == null &&
      passwordValidationMessage == null &&
      confirmedPasswordValidationMessage == null
    ) {
      try {
        await AuthService.signUp(email.value, password.value);
        this.setState({ redirect: true });
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

  componentDidMount() {
    AuthService.signOut();
  }

  render() {
    const { redirect } = this.state;
    if (redirect) {
      return <Navigate to="/login" />;
    }

    return (
      <div id="auth-form">
        <div id="auth-form-navbar">
          <span>Registration panel</span>
        </div>
        <div id="divider"></div>
        <form
          onSubmit={async (event) => {
            await this.handleSubmit(event);
          }}
          autoComplete="off"
        >
          <div className="input-section">
            <span>E-mail:</span>
            <input type="text" name="email" className="auth-input" />
            {this.state.email.hasError && (
              <p className="error-message">{this.state.email.errorMessage}</p>
            )}
          </div>
          <div className="input-section">
            <span>Password:</span>
            <input type="password" name="password" className="auth-input" />
            {this.state.password.hasError && (
              <p className="error-message">
                {this.state.password.errorMessage}
              </p>
            )}
          </div>
          <div className="input-section">
            <span>Confirm password:</span>
            <input
              type="password"
              name="confirmPassword"
              className="auth-input"
            />
            {this.state.confirmPassword.hasError && (
              <p className="error-message">
                {this.state.confirmPassword.errorMessage}
              </p>
            )}
          </div>
          <div className="input-section">
            {this.state.form.hasError && (
              <p className="error-message form-error-message">
                {this.state.form.errorMessage}
              </p>
            )}
            <input type="submit" value="Create an account" id="auth-button" />
            <Link to={"/login"} replace id="create-new-account">
              Back to the login page
            </Link>
          </div>
        </form>
      </div>
    );
  }
}

export default Register;
