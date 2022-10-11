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
    MDBValidationItem,
    MDBSpinner,
} from "mdb-react-ui-kit";
import React, { useEffect, useState } from "react";
import { register, usernameExists } from "../../services/authService";
import { useNavigate } from "react-router-dom";

// import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "../style/RegistrationScreen.css";


function RegistrationScreen() {
    const [data, setData] = useState({
        email: "",
        username: "",
        password: "",
        confirmpassword: "",
    });
    const [username, setDebouncedUsername] = useState("");

    // Errors
    const [regError, setRegError] = useState("");
    const [emailError, setEmailError] = useState("");
    const [usernameError, setUsernameError] = useState("");
    const [usernamePass, setUsernamePass] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [confirmError, setConfirmError] = useState("");

    // Email Validation Regex (RFC 5322)
    const emailRegEx = "([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])";

    // Spinner & Button variables
    const [basicModal, setBasicModal] = useState(false);
    const [loadingButton, setLoadingButton] = useState(false);
    const [disabledButton, setdisabledButton] = useState(false);

    // Setting errors for conditional rendering
    useEffect(() => {
        // Email field error setting
        setEmailError("");
        if (data.email && !data.email.match(emailRegEx))
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
        if (!data.username) return;

        // Check if it is of desired length
        if (data.username.length < 6) {
            setUsernameError("Must be at least 6 characters");
            return;
        }

        // Send request to backend once
        // debounced username is the same as data.username
        if (username !== data.username) return;
        usernameExists(username).then((exists) => {
            if (exists) {
                setUsernameError("Username already taken");
            } else setUsernamePass("Username available");
        });
    }, [data.username, username]);

    // Debouncing of username
    useEffect(() => {
        const timeout = setTimeout(() => {
            setDebouncedUsername(data.username);
        }, 500);

        return () => clearTimeout(timeout);
    }, [data.username]);

    const toggleShow = () => setBasicModal(!basicModal);

    const onChange = (e) => {
        setData({ ...data, [e.target.name]: e.target.value });
    };

    const navigate = useNavigate();

    const handleBackToLogin = ()=>{
      navigate("/");
    }

    const handleRegister = async (e) => {
        // Show spinner on click
        setLoadingButton(true);
        setdisabledButton(true);
        e.preventDefault();

        try {
            // Ensure non-empty inputs
            if (!data.email || !data.username || !data.password) {
                setRegError("Please enter your credentials");
                return;
            }

            // Check valid email
            if (!data.email.match(emailRegEx)) {
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

            // Check passwords matching
            if (data.password !== data.confirmpassword) {
                setRegError("Passwords entered are not matching");
                return;
            }

            // Create a DTO for request body
            if (
                await register({
                    email: data.email,
                    username: data.username,
                    password: data.password,
                    }
                )) {
                setRegError("");
                toggleShow();
            }

        } catch (err) {
            let errMessage = err.response.data.message;

            setRegError(errMessage);
            if (errMessage === "Failed to send email")
              setRegError("Unable to send email. Check the entered email address")

        } finally {
            // Disable spinner when finish this function
            setLoadingButton(false);
            setdisabledButton(false);
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
                            Please check your email for our confirmation email.
                            Confirmation link expires in 15 minutes.
                        </MDBModalBody>

                        <MDBModalFooter>
                            <button class="success-back-button" onClick={()=>handleBackToLogin()}>
                                Back to login
                            </button>
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
            <MDBContainer className="my-5 gradient-form">
                <MDBRow>
                    <MDBCol col="6" className="mb-5">
                        <div className="d-flex flex-column justify-content-center gradient-custom-3 h-100 mb-4">
                            <div className="text-grey px-3 py-4 p-md-5 mx-md-4">
                                <h4 class="mb-1 ml-2 carousel-text">
                                    <strong>We are more than just a company</strong>
                                </h4>
                                <p class="small mb-12 carousel-text">
                                    We aim to revolutionize inventory management!
                                </p>
                            </div>
                        </div>
                    </MDBCol>
                    <MDBCol col="6" className="mb-5">
                        <div className="d-flex flex-column ms-5">
                            <MDBValidation>
                                <div className="text-center">
                                <img src="logo.png" style={{ width: "350px" }} alt="logo" />
                                    <h4 className="mt-5 mb-3 pb-1">
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
                                        {emailError ? (
                                            <div className="email-error">
                                                {emailError}
                                            </div>
                                        ) : (
                                            ""
                                        )}
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
                                        {/* {username ? <div>debounced: {username}</div> : ""} */}
                                        {usernameError ? (
                                            <div className="username-error">
                                                {usernameError}
                                            </div>
                                        ) : (
                                            ""
                                        )}
                                        {usernamePass ? (
                                            <div className="username-pass">
                                                {usernamePass}
                                            </div>
                                        ) : (
                                            ""
                                        )}
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
                                {passwordError ? (
                                    <div className="password-error">
                                        {passwordError}
                                    </div>
                                ) : (
                                    ""
                                )}

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
                                    <button
                                        class="signup-button"
                                        onClick={handleRegister}
                                        disabled={disabledButton}
                                    >
                                        <div className={"d-flex justify-content-center"}>
                                            <div className={ loadingButton ? "" : "invisible" }>
                                                <MDBSpinner
                                                    size="sm"
                                                    role="status"
                                                    tag="span"
                                                    className={"me-2 ml-2"}
                                                />
                                            </div>
                                            <div class="sign-up-text">Sign Up</div>
                                        </div>
                                    </button>
                                    {regError ? <div className="reg-error">{regError}</div> : ""}
                                </div>
                            </MDBValidation>
                            <MDBBtn
                                outline
                                className="text-dark text-muted mt-1"
                                class="back-to-login-button"
                                color="light"
                                onClick={() => handleBackToLogin()}
                            >
                               Back to Login
                            </MDBBtn>
                        </div>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </>
    );
}

export default RegistrationScreen;
