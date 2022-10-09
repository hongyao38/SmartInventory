import {
  MDBBtn,
  MDBCol,
  MDBContainer,
  MDBInput,
  MDBModal,
  MDBModalBody,
  MDBModalContent,
  MDBModalDialog,
  MDBModalFooter,
  MDBModalHeader,
  MDBModalTitle,
  MDBRow,
  MDBValidation,
  MDBValidationItem
} from "mdb-react-ui-kit";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { register, usernameExists } from "../../services/authService";

import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "../style/RegistrationScreen.css";

function RegistrationScreen() {
  const [data, setData] = useState({
    email: "",
    username: "",
    password: "",
    confirmpassword: "",
  });

  const [regError, setRegError] = useState("");
  const [emailError, setEmailError] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [usernamePass, setUsernamePass] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [confirmError, setConfirmError] = useState("");

  // Setting errors for conditional rendering
  useEffect(() => {

    // Email field error setting
    setEmailError("");
    if (data.email && !data.email.includes("@"))
      setEmailError("Please enter valid email address");

    // Password field error setting
    setPasswordError("");
    if (data.password && data.password.length < 8)
      setPasswordError("Password must be at least 8 characters");

    // Confirm password field error setting
    setConfirmError("");
    if (data.confirmpassword && data.confirmpassword !== data.password)
      setConfirmError("Passwords need to match");

  }, [data.email, data.password, data.confirmpassword]);


  // Split this into a separate useEffect 
  // to not repeatedly send requests
  useEffect(() => {
    // Username field error setting
    setUsernameError("");
    setUsernamePass("");
    if (data.username) {

      // Check if it is of desired length
      if (data.username.length < 6) {
        setUsernameError("must be at least 6 characters");
        return;
      }

      // Check if username exists
      usernameExists(data.username).then((exists) => {
        if (exists) {
          setUsernamePass("");
          setUsernameError("Username already taken");
        } else
          setUsernamePass("Username available");
      });
    }
  }, [data.username]);

  const [basicModal, setBasicModal] = useState(false);
  const toggleShow = () => setBasicModal(!basicModal);

  const onChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  const navigate = useNavigate();
  const handleRegister = async (e) => {
    e.preventDefault();

    // Ensure non-empty inputs
    if (!data.email || !data.username || !data.password) {
      setRegError("Please enter your credentials");
      return;
    }

    // Check valid email
    if (!data.email.includes("@")) {
      setRegError("Please enter a valid email address");
      return;
    }

    // Check valid username
    if (data.username.length < 6) {
      setRegError("Please enter a valid username");
      return;
    }

    // Check valid password
    if (data.password.length < 8) {
      setRegError("Password must be at least 8 characters");
      return;
    }

    // Create a DTO for request body
    const info = {
      email: data.email,
      username: data.username,
      password: data.password,
    };

    try {
      const res = await register(info);
      if (res) {
        setRegError("");
        toggleShow();
      }
    } catch (err) {
      setRegError(err.response.data.message);
    }
  };

  return (
    <>
      <MDBModal show={basicModal} setShow={setBasicModal} tabIndex="-1">
        <MDBModalDialog>
          <MDBModalContent>
            <MDBModalHeader>
              <MDBModalTitle>Registration Success!</MDBModalTitle>
              <MDBBtn
                className="btn-close"
                color="none"
                onClick={toggleShow}
              ></MDBBtn>
            </MDBModalHeader>
            <MDBModalBody>
              Please check your email for our confirmation email. Confirmation
              link expires in 15 minutes.
            </MDBModalBody>

            <MDBModalFooter>
              <MDBBtn color="secondary" onClick={toggleShow}>
                Close
              </MDBBtn>
            </MDBModalFooter>
          </MDBModalContent>
        </MDBModalDialog>
      </MDBModal>
      <MDBContainer className="my-5 gradient-form">
        <MDBRow>
          <MDBCol col="6" className="mb-5">
            <div className="d-flex flex-column justify-content-center gradient-custom-2 h-100 mb-4">
              <div className="text-white px-3 py-4 p-md-5 mx-md-4">
                <h4 class="mb-4">We are more than just a company</h4>
                <p class="small mb-0">
                  Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
                  do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                  Ut enim ad minim veniam, quis nostrud exercitation ullamco
                  laboris nisi ut aliquip ex ea commodo consequat.
                </p>
              </div>
            </div>
          </MDBCol>
          <MDBCol col="6" className="mb-5">
            <div className="d-flex flex-column ms-5">
              <MDBValidation>
                <div className="text-center">
                  <img
                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                    style={{ width: "185px" }}
                    alt="logo"
                  />
                  <h4 className="mt-1 mb-5 pb-1">
                    <strong>Create an Account</strong>
                  </h4>
                </div>

                <MDBRow>
                  <MDBCol>
                    <MDBValidationItem>
                      <MDBInput
                        wrapperClass="mb-5"
                        label="Email address"
                        id="form1"
                        type="text"
                        value={data.email}
                        name="email"
                        required
                        onChange={onChange}
                      />
                    </MDBValidationItem>
                    {emailError ? <div className="email-error">{emailError}</div> : ""}

                  </MDBCol>
                  <MDBCol md="5">
                    <MDBValidationItem>
                      <MDBInput
                        wrapperClass="mb-5"
                        label="Username"
                        id="form2"
                        type="text"
                        value={data.username}
                        name="username"
                        required
                        onChange={onChange}
                      />
                    </MDBValidationItem>
                    {usernameError ? <div className="username-error">{usernameError}</div> : ""}
                    {usernamePass ? <div className="username-pass">{usernamePass}</div> : ""}

                  </MDBCol>
                </MDBRow>
                <MDBValidationItem>
                  <MDBInput
                    wrapperClass="mb-5"
                    label="Password"
                    id="form3"
                    type="password"
                    value={data.password}
                    name="password"
                    required
                    onChange={onChange}
                  />
                </MDBValidationItem>
                {passwordError ? <div className="password-error">{passwordError}</div> : ""}

                <MDBValidationItem>
                  <MDBInput
                    wrapperClass="mb-5"
                    label="Confirm Password"
                    id="form4"
                    type="password"
                    value={data.confirmpassword}
                    name="confirmpassword"
                    required
                    onChange={onChange}
                  />
                </MDBValidationItem>
                {confirmError ? <div className="confirm-error">{confirmError}</div> : ""}

                <div className="text-center pt-1 mb-5 pb-1">
                  <MDBBtn
                    className="mb-4 w-100 gradient-custom-2"
                    onClick={handleRegister}
                  >
                    Sign Up
                  </MDBBtn>
                  {regError ? <div className="reg-error">{regError}</div> : ""}
                </div>
              </MDBValidation>
            </div>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </>
  );
}

export default RegistrationScreen;
