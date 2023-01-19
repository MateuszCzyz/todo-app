import { Component } from "react";
import { Link, Navigate } from "react-router-dom";
import AuthService from "../services/authentication/AuthService";
import AuthValidator from "../utils/validators/AuthValidator";

class Login extends Component {
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
    });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { email, password } = event.target;

    this.resetFormState();

    const emailValidationMessage = AuthValidator.validateEmail(email.value);
    const passwordValidationMessage = AuthValidator.validatePassword(
      password.value
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
    });

    if (emailValidationMessage == null && passwordValidationMessage == null) {
      try {
        await AuthService.signIn(email.value, password.value);
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
      return <Navigate to="/" />;
    }
    return (
      <div id="auth-form">
        <div id="auth-form-navbar">
          <span>Login panel</span>
        </div>
        <div id="divider"></div>
        <form
          autoComplete="off"
          onSubmit={async (event) => {
            await this.handleSubmit(event);
          }}
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
            {this.state.form.hasError && (
              <p className="error-message form-error-message">
                {this.state.form.errorMessage}
              </p>
            )}
            <input type="submit" value="Sign in" id="auth-button" />
            <Link to={"/registration"} replace id="create-new-account">
              Create a new account
            </Link>
          </div>
        </form>
      </div>
    );
  }
}

export default Login;
