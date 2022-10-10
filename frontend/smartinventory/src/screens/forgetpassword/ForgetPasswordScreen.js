import {
    MDBBtn,
    MDBCol,
    MDBContainer,
    MDBInput,
    MDBRow,
    MDBTypography,
    MDBModal,
    MDBModalDialog,
    MDBModalContent,
    MDBModalHeader,
    MDBModalTitle,
    MDBModalBody,
    MDBModalFooter,
    MDBSpinner,
} from "mdb-react-ui-kit";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "../style/ForgetPasswordScreen.css";
import { forgetPassword } from "../../services/authService";

//import {getAuth, sendPasswordResetEmail} from 'firebase/auth'

function ForgetPasswordScreen() {
    // setEmail: the email to be sent to
    const [data, setEmail] = useState({ email: "" });
    const [failedModal, setFailedModal] = useState(false);
    const [successModal, setSuccessModal] = useState(false);
    const [loadingButton, setLoadingButton] = useState(false);
    const [disabledButton, setdisabledButton] = useState(false);

    const toggleFailedModal = () => setFailedModal(!failedModal);
    const toggleSuccessModal = () => setSuccessModal(!successModal);

    const onChange = (e) => {
        setEmail({ ...data.email, [e.target.name]: e.target.value });
    };

    const sendEmail = async (e) => {
        e.preventDefault();
        setLoadingButton(true);
        setdisabledButton(true);
        console.log(data.email);
        try {
            const res = await forgetPassword(data.email);
            console.log("Respond", res);
            if (res) {
                // console.log("success clause");
                toggleSuccessModal();
                setLoadingButton(false);
                setdisabledButton(false);
                // navigate("/resendEmailScreen");
            } else {
                //handle send email failed, either no registered email found
                // console.log("fail clause");

                toggleFailedModal();
                setLoadingButton(false);
                setdisabledButton(false);
            }
        } catch (error) {
            console.log("fail clause");
            toggleFailedModal();
            setLoadingButton(false);
            setdisabledButton(false);

            console.log(error);
        }
    };

    const navigate = useNavigate();

    const handleLogIn = () => {
        navigate("/");
    };

    return (
        <>
            <MDBContainer className="mb-8"></MDBContainer>
            <MDBContainer className="pt-6 gradient-form">
                <MDBRow>
                    <MDBCol col="6" className="mb-5">
                        <div className="d-flex flex-column mt-5 mb-">
                            <div className="text-center">
                                <img
                                    src="resetpassword.png"
                                    style={{ width: "55px" }}
                                    alt="logo"
                                />
                            </div>
                        </div>

                        <MDBTypography tag="strong" className="pb-3">
                            <div class="text-center">
                                <h2 class="fw-bold">Forgot Password?</h2>
                                <p class="fw-normal">
                                    No worries, we'll send you reset
                                    instructions.
                                </p>
                            </div>

                            <div className="d-flex flex-row align-items-center justify-content-center pb-4 mb-4 email-field">
                                <MDBInput
                                    wrapperClass="w-25 mb-4b"
                                    label="Email"
                                    id="form1"
                                    type="text"
                                    value={data.email}
                                    name="email"
                                    required
                                    onChange={onChange}
                                    //TO ASK: error when using this
                                    // {email.check_textInputChange ? (
                                    //     <MDBIcon fas icon="check" />
                                    // ) : null}
                                />
                            </div>

                            <div className="d-grid gap-2 col-5 mx-auto mb-4 mt-3 send-button">
                                <MDBBtn
                                    className="text-center"
                                    id="send-email-btn"
                                    onClick={
                                        (e) => sendEmail(e)
                                        // handleResendEmail();
                                    }
                                    disabled={disabledButton}
                                >
                                    <div
                                        className={
                                            "d-flex justify-content-center"
                                        }
                                    >
                                        <div
                                            className={
                                                loadingButton ? "visible" : "invisible"
                                            }
                                        >
                                            <MDBSpinner
                                                size="sm"
                                                role="status"
                                                tag="span"
                                                className={""}
                                            />
                                        </div>
                                        <div class="send-email-text">
                                            Send email
                                        </div>
                                    </div>
                                </MDBBtn>
                            </div>

                            <div className="d-flex flex-row align-items-center justify-content-center pb-4 mb-4 login-button">
                                {/* to ask: arrow never appear :( */}
                                <i class="fas fa-arrow-left"></i>

                                <MDBBtn
                                    outline
                                    className="text-dark text-muted"
                                    color="light"
                                    onClick={() => handleLogIn()}
                                >
                                    Back to log in
                                </MDBBtn>
                            </div>
                        </MDBTypography>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>

            <MDBModal show={failedModal} setShow={setFailedModal} tabIndex="-1">
                <MDBModalDialog>
                    <MDBModalContent>
                        <MDBModalHeader>
                            <MDBModalTitle>Reset Password Failed</MDBModalTitle>
                            <MDBBtn
                                className="btn-close"
                                color="none"
                                onClick={toggleFailedModal}
                            ></MDBBtn>
                        </MDBModalHeader>
                        <MDBModalBody>
                            Email not found. Please check your email again.
                        </MDBModalBody>

                        <MDBModalFooter>
                            <MDBBtn
                                color="secondary"
                                onClick={toggleFailedModal}
                            >
                                Close
                            </MDBBtn>
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>

            <MDBModal
                show={successModal}
                setShow={setSuccessModal}
                tabIndex="-1"
            >
                <MDBModalDialog>
                    <MDBModalContent>
                        <MDBModalHeader>
                            <MDBModalTitle>
                                Email to reset password sent.
                            </MDBModalTitle>
                            <MDBBtn
                                className="btn-close"
                                color="none"
                                onClick={toggleSuccessModal}
                            ></MDBBtn>
                        </MDBModalHeader>
                        <MDBModalBody>
                            Please check your email for our email. Email link
                            expires in 15 minutes.
                        </MDBModalBody>

                        <MDBModalFooter>
                            <MDBBtn
                                color="secondary"
                                onClick={toggleSuccessModal}
                            >
                                Close
                            </MDBBtn>
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
        </>
    );
}

export default ForgetPasswordScreen;
