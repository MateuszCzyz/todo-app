const LOGIN_ENDPOINT = "http://localhost:8080/api/auth/login";
const REGISTER_ENDPOINT = "http://localhost:8080/api/auth/register";
const VERIFY_TOKEN_ENDPOINT =
  "http://localhost:8080/api/auth/verify-auth-token";
const TOKEN_STORAGE_KEY = "auth-token";
const TOKEN_PREFIX = "Bearer ";

class AuthService {
  static async signIn(email, password) {
    const requestBody = {
      email: email,
      password: password,
    };

    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    };

    const response = await fetch(LOGIN_ENDPOINT, requestOptions);
    const responseBody = await response.json();
    if (response.ok) {
      const accessToken = responseBody["accessToken"];
      localStorage.setItem(TOKEN_STORAGE_KEY, accessToken);
    } else {
      throw new Error(responseBody["message"]);
    }
  }

  static async signUp(email, password) {
    const requestBody = {
      email: email,
      password: password,
    };

    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    };

    const response = await fetch(REGISTER_ENDPOINT, requestOptions);
    if (!response.ok) {
      const responseBody = await response.json();
      throw new Error(responseBody["message"]);
    }
  }

  static signOut() {
    localStorage.removeItem(TOKEN_STORAGE_KEY);
  }

  static async verifyAuthToken() {
    const token = localStorage.getItem(TOKEN_STORAGE_KEY);
    if (token == null || token.length == 0) {
      return false;
    }

    const requestBody = {
      token: token,
    };

    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    };

    const response = await fetch(VERIFY_TOKEN_ENDPOINT, requestOptions);
    return response.ok;
  }

  static getAuthToken() {
    const authToken = localStorage.getItem(TOKEN_STORAGE_KEY);
    return TOKEN_PREFIX + authToken;
  }
}

export default AuthService;
