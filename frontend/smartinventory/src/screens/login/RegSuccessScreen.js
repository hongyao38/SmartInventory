
import {
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBTypography,
    MDBBtn,
    MDBIcon
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import React from "react";
import { useLocation } from "react-router-dom";
import { confirmEmail } from "../../services/authService"
import "../style/LogInScreen.css";

function ConfirmRegistration() {
    const location = useLocation();

    // useEffect(()=>{
    try {
        const res = confirmEmail(location.search);
        console.log(res);
        if (res) {
            //successfully registered, call pop up to tell user to check email
            console.log("success");
        }
    } catch (err) {
        alert(err);
    }
    // },[]);

    return (
        <MDBContainer className="my-6 gradient-form">
            <MDBRow>
                <MDBCol className="mb-5">
                    <div className="d-flex flex-column mt-4 mb-3">
                        <div className="text-center">
                            <img
                                src="email2.png"
                                style={{ width: "55px" }}
                                alt="icon"
                            />
                            
                        </div>
                    </div>
                    <MDBIcon fas icon="check" />
                    <MDBTypography tag="strong" className="pb-3">
                        <div class="text-center">
                            <h2 class="fw-bold">Email has been confirmed</h2>
                            
                        </div>

                        <div className="d-flex flex-row align-items-center justify-content-center pb-4 mb-4">
                            <i class="fas fa-arrow-left"></i>
                            <MDBBtn
                                // outline
                                className="mx-2"
                                class=""
                                // color="light"
                                // onClick={}
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

export default ConfirmRegistration;
