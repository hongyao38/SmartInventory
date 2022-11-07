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
// import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "../style/ForgetPasswordScreen.css";
import { forgetPassword } from "../../services/AuthService";

//import {getAuth, sendPasswordResetEmail} from 'firebase/auth'

function ForgetPasswordScreen() {
  // setEmail: the email to be sent to
  const [data, setEmail] = useState({ email: "" });
  const [error, setError] = useState("");
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
    if (!data.email) {
      setError("Please enter your email address");
      return;
    }

    e.preventDefault();
    setError("");
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
    <div class="forget-password-page-container">
      <div class="forget-password-form">
        <div class="form-title">
          <h2>
            <strong>Forgot Password?</strong>
          </h2>
          <p>No worries, we'll send you reset instructions.</p>
        </div>

        <div class="email-input-field">
          <MDBInput
            label="Email"
            id="form1"
            type="text"
            value={data.email}
            name="email"
            required
            onChange={onChange}
          />
        </div>
        {error ? <div class="email-fail-error">{error}</div> : ""}

        <div class="send-button">
          <button
            class="send-email-button"
            // className="text-center"
            id="send-email-btn"
            onClick={
              (e) => sendEmail(e)
              // handleResendEmail();
            }
            disabled={disabledButton}
          >
            <div class="send-email-label">
              <div className={loadingButton ? " visible" : " invisible"}>
                <MDBSpinner size="sm" role="status" tag="span" className={""} />
              </div>
              <div class="send-email-text">Send email</div>
            </div>
          </button>
        </div>

        <div class="back-to-home-button">
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
      </div>

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
              <button
                class="email-fail-close-button"
                color="secondary"
                onClick={toggleFailedModal}
              >
                Close
              </button>
            </MDBModalFooter>
          </MDBModalContent>
        </MDBModalDialog>
      </MDBModal>

      <MDBModal show={successModal} setShow={setSuccessModal} tabIndex="-1">
        <MDBModalDialog>
          <MDBModalContent>
            <MDBModalHeader>
              <MDBModalTitle>Email to reset password sent.</MDBModalTitle>
              <MDBBtn
                className="btn-close"
                color="none"
                onClick={toggleSuccessModal}
              ></MDBBtn>
            </MDBModalHeader>
            <MDBModalBody>
              Please check your email for our email. Email link expires in 15
              minutes.
            </MDBModalBody>

            <MDBModalFooter>
              <button
                class="back-popup-button"
                color="secondary"
                onClick={() => handleLogIn()}
              >
                Back to login
              </button>
            </MDBModalFooter>
          </MDBModalContent>
        </MDBModalDialog>
      </MDBModal>
    </div>
  );
}

export default ForgetPasswordScreen;
