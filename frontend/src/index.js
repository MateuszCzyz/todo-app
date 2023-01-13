import React from "react";
import ReactDOM from "react-dom/client";
import Login from "./views/Login";
import Register from "./views/Register";
import App from "./views/App";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/login" element={<Login />} />
        <Route path="/registration" element={<Register />} />
      </Routes>
    </Router>
  </React.StrictMode>
);
