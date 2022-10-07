import {
    MDBBtn, MDBCol, MDBContainer,
    MDBRow, MDBTypography
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import React from "react";
import { useNavigate } from "react-router-dom";

function ResendEmailScreen() {
  const navigate = useNavigate();

  const handleLogIn = () => {
    navigate("/");
  };
  return (
    <MDBContainer className="my-6 gradient-form">
      <MDBRow>
        <MDBCol>
          <MDBTypography tag="strong" className="pb-3">
            <div className="d-flex flex-column">
              <div className="text-center mt-5 mb-3">
                <img src="email.png" style={{ width: "55px" }} alt="logo" />
              </div>
            </div>

            <div class="text-center">
              <h2 class="fw-bold">Check your email</h2>
              <p class="fw-normal">
                We have sent a reset password link to your email
              </p>
            </div>
            <div className="text-center">
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

export default ResendEmailScreen;
