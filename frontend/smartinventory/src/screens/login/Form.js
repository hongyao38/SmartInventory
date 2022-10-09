import {
  MDBBtn,
  MDBInput,
  MDBTypography,
  MDBValidation,
  MDBValidationItem,
} from "mdb-react-ui-kit";

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../services/authService";

function Form() {
  // User data
  const [data, setData] = useState({ username: "", password: "" });
  // Error message variable
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    try {
      const isLoggedIn = await login({
        username: data.username,
        password: data.password,
      });
      if (isLoggedIn) {
        navigate("/dashboard");
      }
    } catch (e) {
      setError("Invalid Username or Password. Please try Again!");
    }
  };

  const handleForgetPassword = () => {
    navigate("/forget-password");
  };

  const handleRegistration = () => {
    navigate("/registration");
  };

  const onChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  // Button for Creating account
  function CreateAccountButton() {
    return (
      <div className="d-flex flex-row align-items-center justify-content-center pb-4 mb-4">
        <p className="mb-0">Don't have an account?</p>
        <MDBBtn
          outline
          className="mx-2"
          color="danger"
          onClick={() => handleRegistration()}
        >
          Create here
        </MDBBtn>
      </div>
    );
  }

  // Buttons and onClick Handling
  function SignInAndForgetPassword() {
    return (
      <div className="text-center pt-1 mb-5 pb-1">
        <MDBBtn className="mb-4 w-100 gradient-custom-2" onClick={handleLogin}>
          Sign in
        </MDBBtn>

        <MDBBtn
          outline
          className="text-dark text-muted"
          color="light"
          onClick={() => handleForgetPassword()}
        >
          Forget Password?
        </MDBBtn>
      </div>
    );
  }

  return (
    <div className="d-flex flex-column ms-5">
      
      {/* Company Logo */}
      <div className="text-center">
        <img
          src="logo.png"
          style={{ width: "350px" }}
          alt="logo"
        />
      </div>

      {/* Form Title */}
      <MDBTypography tag="strong" className="mb-3 mt-5">
        Log into your account
      </MDBTypography>

      {/* Username and Password Input Fields */}
      <MDBValidation>
        {/* Username field */}
        <MDBValidationItem tooltip feedback="Please enter username" invalid>
          <MDBInput
            wrapperClass="mb-5"
            label="Username"
            id="form1"
            type="text"
            value={data.username}
            name="username"
            required
            onChange={onChange}
          />
        </MDBValidationItem>

        {/* Password field */}
        <MDBValidationItem tooltip feedback="Please enter a password" invalid>
          <MDBInput
            wrapperClass="mb-5"
            label="Password"
            id="form2"
            type="password"
            value={data.password}
            name="password"
            required
            onChange={onChange}
          />
        </MDBValidationItem>

        {/* Display error message */}
        {error !== "" ? <div className="error">{error}</div> : ""}

        <SignInAndForgetPassword />
      </MDBValidation>

      {/* Create account button */}
      <CreateAccountButton />
    </div>
  );
}

export default Form;
