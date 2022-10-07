import React, { useState } from "react";
import {
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBInput,
    MDBTypography,
    MDBValidation,
    MDBValidationItem,
    MDBCarousel,
    MDBCarouselItem,
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import { useNavigate } from "react-router-dom";

function ResendEmailScreen() {

    const navigate = useNavigate();

    const handleLogIn = () => {
        navigate("/LogInScreen");
    }

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
                            <h2 class="fw-bold">Check your email!</h2>
                            <p class="fw-normal">We have sent a password reset link to your email! </p>
                        </div>

                        <div className="d-flex flex-row align-items-center justify-content-center pb-4 mb-4">
                            {/* to ask: arrow never appear :( */}
                            <i class="fas fa-arrow-left"></i>
                            <MDBBtn
                                outline className="mx-2" 
                                className="text-dark text-muted"
                                color="light"
                                onClick={() => handleLogIn()}>

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