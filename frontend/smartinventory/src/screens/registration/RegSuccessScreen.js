// import "mdb-react-ui-kit/dist/css/mdb.min.css";
import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { confirmEmail } from "../../services/authService";
import "../style/ConfirmEmailScreen.css";

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
    const navigate = useNavigate();

    const handleBackToLogin = () => {
        navigate("/");
    };
    return (
        <div class="background">
            <div class="email-confirmed-container">
                <h2 class="email-confirmed-title">
                    Email has been confirmed
                </h2>

                <button class="confirm-button"
                    onClick={() => handleBackToLogin()}
                >
                    Back To Login
                </button>
            </div>
        </div>
    );
}

export default ConfirmRegistration;
