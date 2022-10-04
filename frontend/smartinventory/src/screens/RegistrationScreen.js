import React, { useState } from "react";
import {
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBInput,
    MDBTypography,
    MDBIcon,
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "./RegistrationScreen.css";
// import { SignIn } from "../services/authService";

function RegistrationScreen() {
    const [data, setData] = useState({
        username: "",
        password: "",
    });

    const onChange = (e) => {
        setData({ ...data, [e.target.name]: e.target.value });
        console.log(data);
    };

    const handleSignIn = async (e) => {};

    return (
        <MDBContainer className="my-5 gradient-form">
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
                                incididunt ut labore et dolore magna aliqua. Ut
                                enim ad minim veniam, quis nostrud exercitation
                                ullamco laboris nisi ut aliquip ex ea commodo
                                consequat.
                            </p>
                        </div>
                    </div>
                </MDBCol>
                <MDBCol col="6" className="mb-5">
                    <div className="d-flex flex-column ms-5">
                        <div className="text-center">
                            <img
                                src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                                style={{ width: "185px" }}
                                alt="logo"
                            />
                            <h4 className="mt-1 mb-5 pb-1">
                                We are The Lotus Team
                            </h4>
                        </div>

                        <MDBTypography tag="strong" className="pb-3">
                            Sign Up Here
                        </MDBTypography>
                        <MDBRow>
                            <MDBCol >
                                <MDBInput
                                    wrapperClass="mb-4"
                                    label="Email address"
                                    id="form1"
                                    type="email"
                                    value={data.username}
                                    name="username"
                                    required
                                    onChange={onChange}
                                />
                            </MDBCol>
                            <MDBCol md='5'>
                                <MDBInput
                                    wrapperClass="mb-4"
                                    label="Username"
                                    id="form1"
                                    type="email"
                                    value={data.username}
                                    name="username"
                                    required
                                    onChange={onChange}
                                />
                            </MDBCol>
                        </MDBRow>
                       
                                <MDBInput
                                    wrapperClass="mb-4"
                                    label="Password"
                                    id="form2"
                                    type="password"
                                    value={data.password}
                                    name="password"
                                    required
                                    onChange={onChange}
                                />
                            
                                <MDBInput
                                    wrapperClass="mb-4"
                                    label="Confirm Password"
                                    id="form2"
                                    type="password"
                                    value={data.password}
                                    name="password"
                                    required
                                    onChange={onChange}
                                />
                           

                        <div className="text-center pt-1 mb-5 pb-1">
                            <MDBBtn
                                className="mb-4 w-100 gradient-custom-2"
                                onClick={handleSignIn}
                            >
                                Sign Up
                            </MDBBtn>
                            
                        </div>

                        
                    </div>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    );
}

export default RegistrationScreen;
