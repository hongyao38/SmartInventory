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
import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "../style/ResetScreen.css";
import { resetPassword } from "../../services/authService";

function ResetScreen() {
    const location = useLocation();

    const [data, setData] = useState({
        newPassword: "",
        confirmPassword: "",
    });
    const [error, setError] = useState("");
    const [field1Error, setField1Error] = useState("");
    const [field2Error, setField2Error] = useState("");

    // Setting errors for conditional rendering
    useEffect(() => {

        // New password field error rendering
        setField1Error("");
        if (data.newPassword && data.newPassword.length < 8)
            setField1Error("Password must be at least 8 characters long");

        // Confirm password field error rendering
        setField2Error("");
        if (data.confirmPassword && data.confirmPassword !== data.newPassword)
            setField2Error("Passwords need to match");
            
    }, [data.newPassword, data.confirmPassword]);

    const OnChange = (e) => {
        setData({ ...data, [e.target.name]: e.target.value });
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

        // Password entered is not of required length
        if (data.newPassword.length < 8) {
            setError("Password must be at least 8 characters long.");
            return;
        }
        
        // Get new passwords from input
        const passwords = {
            newPassword: data.newPassword,
            confirmNewPassword: data.confirmPassword,
        };

        // Retrieve token from URL
        const token = location.search;

        try {
            const res = await resetPassword(passwords, token);
            if (res) toggleShow();

        } catch (err) {
            // Setting error message for conditional rendering
            let errMessage = err.response.data.message;

            if (errMessage === "Passwords entered are not matching!") {
                setError("Passwords entered are not matching! Please try again.");
            }
            else if (errMessage === "Token not found, already expired!") {
                setError("Link expired, please request new link.");
            }
            else if (errMessage === "Token not found!") {
                setError("Invalid reset password link")
            }
        }
        
    };

    return (
        <>
            <MDBContainer className="my-5 gradient-form">
                <MDBCol className="mt-4">
                    <MDBRow rows="6" className="mb-5">
                        <div className="d-flex flex-column  mt-4">
                            <div className="text-center">
                                <img
                                    src="/gold-key.png"
                                    style={{ width: "55px" }}
                                    alt="logo"
                                />
                                <h4 className="mt-2 mb-2 pb-2">
                                    <strong>Set new password</strong>
                                </h4>
                                <h6>
                                    {" "}
                                    New password must be at least 8 characters long.
                                </h6>
                            </div>
                            <br></br>
                            <div className="mb-5">
                                <MDBCol className="d-grid gap-2 col-4 mx-auto">
                                    <MDBInput
                                        id="pass1"
                                        label="New Password"
                                        type="password"
                                        value={data.username}
                                        name="newPassword"
                                        required
                                        onChange={OnChange}
                                    />
                                </MDBCol>
                                { field1Error ? <div
                                    className="form-text d-grid gap-2 col-4 mx-auto field-error"
                                >
                                    {field1Error}
                                </div>: ""}
                                <br></br>
                                <MDBCol className="d-grid gap-2 col-4 mx-auto">
                                    <MDBInput
                                        label="Confirm Password"
                                        id="pass2"
                                        type="password"
                                        value={data.confirmPassword}
                                        name="confirmPassword"
                                        required
                                        onChange={OnChange}
                                    />
                                </MDBCol>
                                { field2Error ? <div
                                    className="form-text d-grid gap-2 col-4 mx-auto field-error"
                                >
                                    {field2Error}
                                </div>: ""}
                            </div>

                            {/* Display error message */}
                            {error ? <MDBCol className="d-flex justify-content-center error">{error}</MDBCol> : ""}

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
                                src="/checked.png"
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
