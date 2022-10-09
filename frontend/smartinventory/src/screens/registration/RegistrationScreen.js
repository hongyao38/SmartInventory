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
    MDBTypography,
    MDBValidation,
    MDBValidationItem,
} from "mdb-react-ui-kit";
import React, { useState } from "react";
import { register } from "../../services/authService";

import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "../style/RegistrationScreen.css";

function RegistrationScreen() {
    const [data, setData] = useState({
        email: "",
        username: "",
        password: "",
        confirmpassword: "",
    });

    const [basicModal, setBasicModal] = useState(false);
    const toggleShow = () => setBasicModal(!basicModal);

    const onChange = (e) => {
        setData({ ...data, [e.target.name]: e.target.value });
    };

    const handleRegister = async (e) => {
        // console.log(data);
        e.preventDefault();
        if (data.password !== data.confirmpassword) {
            alert("Sign Up Failed");
        } else {
            const info = {
                email: data.email,
                username: data.username,
                password: data.password,
            };
            console.log(info);

            try {
                const res = await register(info);
                if (res) {
                    //successfully registered, call pop up to tell user to check email
                    toggleShow();
                }
            } catch (err) {
                alert(err);
            }
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
                            <MDBBtn color="secondary" onClick={toggleShow}>
                                Close
                            </MDBBtn>
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
            <MDBContainer className="mt-5 gradient-form">
                <MDBRow>
                    <MDBCol col="6" className="mb-5">
                        <div className="d-flex flex-column justify-content-center gradient-custom-2 h-100 mb-4">
                            <div className="text-white px-3 py-4 p-md-5 mx-md-4">
                                <h4 class="mb-4">
                                    We are more than just a company
                                </h4>
                                <p class="small mb-0">
                                    Lorem ipsum dolor sit amet, consectetur
                                    adipisicing elit, sed do eiusmod tempor
                                    incididunt ut labore et dolore magna aliqua.
                                    Ut enim ad minim veniam, quis nostrud
                                    exercitation ullamco laboris nisi ut aliquip
                                    ex ea commodo consequat.
                                </p>
                            </div>
                        </div>
                    </MDBCol>
                    <MDBCol col="6" className="mb-5">
                        <div className="d-flex flex-column ms-5">
                            <MDBValidation>
                                <div className="text-center">
                                    <img
                                        src="logo.png"
                                        style={{ width: "350px" }}
                                        alt="logo"
                                    />

                                    
                                </div>

                                <MDBTypography
                                    tag="strong"
                                    className="pb-5 mb-5 mt-5"
                                >
                                    Sign Up Here
                                </MDBTypography>
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

                                <div className="text-center pt-1 mb-5 pb-1">
                                    <MDBBtn
                                        className="mb-4 w-100 gradient-custom-2"
                                        onClick={handleRegister}
                                    >
                                        Sign Up
                                    </MDBBtn>
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
