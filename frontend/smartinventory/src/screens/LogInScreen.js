import {
  MDBBtn,
  MDBCol,
  MDBContainer,
  MDBInput,
  MDBRow,
  MDBTypography,
  MDBValidation,
  MDBValidationItem,
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import React, { useState } from "react";
import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { useNavigate } from "react-router-dom";

import { login } from "../services/authService";
import "./LogInScreen.css";

function LoginScreen() {
  const [data, setData] = useState({ username: "", password: "" });
  const [error, setError] = useState("");

  const onChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };
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
    navigate("/forgetPassword");
  };

  const handleRegistration = () => {
    navigate("/registration");
  };

  return (
    <MDBContainer className="my-5 gradient-form">
      <MDBRow>
        {/* Description Side */}
        <MDBCol col="6" className="">
          <div className="d-flex flex-column justify-content-center gradient-custom-2 h-100 mb-4">
            <div className="text-white px-3 py-4 p-md-5 mx-md-4">
              <Carousel
                className="carousel"
                infiniteLoop={true}
                autoPlay={true}
                showStatus={false}
                showArrows={false}
                showThumbs={false}
                showIndicators={false}
                interval={5000}
              >
                <div>
                  {/* <img
                                        src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                                        style={{ width: "100px" }}
                                        alt="logo"
                                    /> */}
                  <p class="small mb-0">
                    1. Lorem ipsum dolor sit amet, consectetur adipisicing elit,
                    sed do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                  </p>
                </div>
                <div>
                  <p class="small mb-0">
                    2. Lorem ipsum dolor sit amet, consectetur adipisicing elit,
                    sed do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                  </p>
                </div>
                <div>
                  <p class="small mb-0">
                    3. Lorem ipsum dolor sit amet, consectetur adipisicing elit,
                    sed do eiusmod tempor incididunt ut labore et dolore magna
                    aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                    ullamco laboris nisi ut aliquip ex ea commodo consequat.
                  </p>
                </div>
              </Carousel>
            </div>
          </div>
        </MDBCol>

        {/* Login Side */}
        <MDBCol col="6" className="">
          <div className="d-flex flex-column ms-5">
            <div className="text-center">
              <img
                src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                style={{ width: "185px" }}
                alt="logo"
              />
              <h4 className="mt-1 mb-5 pb-1">Smart Inventory</h4>
            </div>

            <MDBTypography tag="strong" className="mb-3">
              Log into your account
            </MDBTypography>
            <MDBValidation>
              {/*Username field*/}
              <MDBValidationItem
                tooltip
                feedback="Please enter username"
                invalid
              >
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

              {/*Password field*/}
              <MDBValidationItem
                tooltip
                feedback="Please enter a password"
                invalid
              >
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

              <div className="text-center pt-1 mb-5 pb-1">
                <MDBBtn
                  className="mb-4 w-100 gradient-custom-2"
                  onClick={handleLogin}
                >
                  Sign in
                </MDBBtn>
                {/* <a
                                className="text-muted"
                                href="javascript:handleForgetPassword()"
                            >
                                Forgot password?
                            </a> */}
                <MDBBtn
                  outline
                  className="text-dark text-muted"
                  color="light"
                  onClick={() => handleForgetPassword()}
                >
                  Forget Password?
                </MDBBtn>
              </div>
            </MDBValidation>

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
          </div>
        </MDBCol>
      </MDBRow>
    </MDBContainer>
  );
}

export default LoginScreen;
