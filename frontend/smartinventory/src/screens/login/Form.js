import {
    MDBCol,
    MDBBtn,
    MDBInput,
    MDBTypography,
    MDBValidation,
    MDBValidationItem,
    MDBSpinner,
} from "mdb-react-ui-kit";

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../services/authService";

import "../style/LogInScreen.css";

function Form() {
    // User data
    const [data, setData] = useState({ username: "", password: "" });
    // Error message variable
    const [error, setError] = useState("");

    const [loadingButton, setLoadingButton] = useState(false);
    const [disabledButton, setdisabledButton] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        setLoadingButton(true);
        setdisabledButton(true);
        // this.forceUpdate();
        try {
            const isLoggedIn = await login({
                username: data.username,
                password: data.password,
            });
            if (isLoggedIn) {
                navigate("/dashboard");
                setLoadingButton(false);
                setdisabledButton(false);
            }
        } catch (e) {
            setError("Invalid Username or Password. Please try Again!");

            setLoadingButton(false);
            setdisabledButton(false);
            // this.forceUpd ate();
            console.log("error trigger:", loadingButton);
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
                <button class="create-button"
                    // outline
                    // className="mx-2"
                    // color="success"
                    // border-radius="50%"
                    onClick={() => handleRegistration()}
                >
                    Create here
                </button>
            </div>
        );
    }

    // Buttons and onClick Handling
    function SignInAndForgetPassword() {
        return (
            <div className="text-center pt-1 mb-5 pb-1">
                {/* <div className={loadingButton ? "invisible" : "invisible"}> */}
                <button
                    className="mb-4 w-100 gradient-custom-2"
                    class="sign-in-button"
                    onClick={handleLogin}
                    disabled={disabledButton}
                >
                    <div className={"d-flex justify-content-center"}>
                        <div className={loadingButton ? "" : "invisible"}>
                            <MDBSpinner
                                size="sm"
                                role="status"
                                tag="span"
                                className={"me-2 ml-2"}
                            />
                        </div>
                        <div class="sign-in-text">Sign in</div>
                    </div>
                </button>
                {/* </div> */}

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
                <img src="logo.png" style={{ width: "350px" }} alt="logo" />
            </div>

            {/* Form Title */}
            <MDBTypography tag="strong" className="mb-3 mt-5">
                Log into your account
            </MDBTypography>

            {/* Username and Password Input Fields */}
            <MDBValidation>
                {/* Username field */}
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

                {/* Password field */}
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
                {error ? (
                    <MDBCol className="d-flex justify-content-center login-error">
                        {error}
                    </MDBCol>
                ) : 
                    ""
                }

                <SignInAndForgetPassword />
            </MDBValidation>

            {/* Create account button */}
            <CreateAccountButton />
        </div>
    );
}

export default Form;
