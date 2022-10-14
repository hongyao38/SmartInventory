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
                <div class="email-confirmed-title">
                    <h2>
                        Email has been confirmed
                    </h2>
                </div>

                <button class="confirm-button"
                    // outline
                    // className="text-dark text-muted"
                    // color="light"
                    onClick={() => handleBackToLogin()}
                >
                    Back To Login
                </button>
            </div>
        </div>
    );
}

export default ConfirmRegistration;
