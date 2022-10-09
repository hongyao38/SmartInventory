import {
    MDBBtn,
    MDBCol,
    MDBContainer,
    MDBInput,
    MDBModal,
    MDBModalContent,
    MDBModalDialog,
    MDBRow,
} from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "../style/ResetScreen.css";
import { resetPassword } from "../../services/authService";

function ResetScreen() {
    const location = useLocation();

    const [data, setData] = useState({
        newPassword: "",
        confirmPassword: "",
    });

    const onChange = (e) => {
        setData({ ...data, [e.target.name]: e.target.value });
        // console.log(data);
    };

    const [basicModal, setBasicModal] = useState(false);

    const toggleShow = () => setBasicModal(!basicModal);

    const navigate = useNavigate();
    const handleBackToLogin = () => {
        navigate("/");
    };

    const handleReset = async (e) => {
        e.preventDefault();
        console.log(data.newPassword);
        if (!data.newPassword.localeCompare(data.confirmPassword) === 0) {
            //password not the same
        } else {
            const passwords = {
                newPassword: data.newPassword,
                confirmNewPassword: data.confirmPassword,
            };
            const token = location.search;
            console.log(token);
            try {
                const res = await resetPassword(passwords, token);
                if (res) {
                    //successful
                    toggleShow();
                }
            } catch (err) {
                alert(err);
            }
        }
    };

    return (
        <>
            <MDBContainer className="mb-8"></MDBContainer>
            <MDBContainer className="my-5 gradient-form">
                <MDBCol className="mt-4">
                    <MDBRow rows="6" className="mb-5">
                        <div className="d-flex flex-column  mt-4">
                            <div className="text-center">
                                <img
                                    src="gold-key.png"
                                    style={{ width: "55px" }}
                                    alt="logo"
                                />
                                <h4 className="mt-2 mb-2 pb-2">
                                    <strong>Set new password</strong>
                                </h4>
                                <h6>
                                    {" "}
                                    Your new password must be different to
                                    previously used passwords.
                                </h6>
                            </div>
                            <br></br>
                            <div className="mb-5">
                                <MDBCol className="d-grid gap-2 col-4 mx-auto">
                                    <MDBInput
                                        id="pass1"
                                        label="Password"
                                        type="password"
                                        value={data.username}
                                        name="newPassword"
                                        required
                                        onChange={onChange}
                                    />
                                </MDBCol>
                                <div
                                    id="pass1"
                                    className="form-text d-grid gap-2 col-4 mx-auto"
                                >
                                    must be at least 8 characters long.
                                </div>
                                <br></br>
                                <MDBCol className="d-grid gap-2 col-4 mx-auto">
                                    <MDBInput
                                        label="Confirm Password"
                                        id="pass2"
                                        type="password"
                                        value={data.confirmPassword}
                                        name="confirmPassword"
                                        required
                                        onChange={onChange}
                                    />
                                </MDBCol>
                            </div>
                            <MDBCol className="d-flex justify-content-center ">
                                <MDBBtn onClick={(e) => handleReset(e)}>
                                    Reset Password
                                </MDBBtn>
                            </MDBCol>
                        </div>
                    </MDBRow>
                </MDBCol>
                <MDBCol col="6"></MDBCol>
            </MDBContainer>
            <MDBModal show={basicModal} setShow={setBasicModal} tabIndex="-1">
                <MDBModalDialog centered>
                    <MDBModalContent>
                        <div className="mt-4 text-center">
                            <img
                                src="checked 2.png"
                                style={{ width: "55px" }}
                                alt="logo"
                            />
                            <h3 className="mt-2 mb-2 pb-2">
                                <strong>Password reset</strong>
                            </h3>
                            <h6>
                                {" "}
                                Your password has been successfully reset.
                                <br></br> click below to sign-in.
                            </h6>
                        </div>
                        <div className="d-grid gap-2 col-5 mx-auto mb-4 mt-3">
                            <MDBBtn onClick={() => handleBackToLogin()}>
                                continue
                            </MDBBtn>
                        </div>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
        </>
    );
}

export default ResetScreen;
