import {
  MDBBtn,
  MDBCol,
  MDBContainer,
  MDBInput,
  MDBRow,
  MDBTypography,
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ForgetPasswordScreen.css";
//import {getAuth, sendPasswordResetEmail} from 'firebase/auth'

function ForgetPasswordScreen() {
  // setEmail: the email to be sent to
  const [data, setEmail] = useState({ email: "" });

  const onChange = (e) => {
    setEmail({ ...data.email, [e.target.name]: e.target.value });
  };

  //TODO: connect to backend
  // const sendEmail = async(e) => {
  //     e.preventDefault();
  //     try{
  //         const auth = getAuth();
  //         await sendPasswordResetEmail(auth,email)
  //         toast.success('Email was sent!')
  //     } catch (error) {
  //         toast.error('Could not send reset email. Check your email address and try again')
  //     }
  // }

  const navigate = useNavigate();

  const handleLogIn = () => {
    navigate("/");
  };

  const handleResendEmail = () => {
    navigate("/resendEmailScreen");
  };

  return (
    <MDBContainer className="my-6 gradient-form">
      <MDBRow>
        <MDBCol col="6" className="mb-5">
          <div className="d-flex flex-column mt-4 mb-3">
            <div className="text-center">
              <img
                //TODO: replace this link w forget password link

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
                No worries, we'll send you reset instructions.
              </p>
            </div>

            <div className="d-flex flex-row align-items-center justify-content-center pb-4 mb-4">
              <MDBInput
                wrapperClass="w-50 mb-4b"
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

            <div className="d-grid gap-2 col-5 mx-auto mb-4 mt-3">
              <MDBBtn
                className="text-center"
                onClick={() => {
                  setEmail();
                  handleResendEmail();
                }}
              >
                Send password reset email
              </MDBBtn>
            </div>

            <div className="d-flex flex-row align-items-center justify-content-center pb-4 mb-4">
              {/* to ask: arrow never appear :( */}
              <i class="fas fa-arrow-left"></i>
              <MDBBtn
                outline
                className="mx-2"
                class="text-dark text-muted"
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
  );
}

export default ForgetPasswordScreen;
