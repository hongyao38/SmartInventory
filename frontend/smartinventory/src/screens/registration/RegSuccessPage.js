import React from "react";
import { useLocation } from "react-router-dom";
import { confirmEmail } from "../../services/authService";

import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
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
    <>
      <h1>reg success</h1>
      <h2>aefsa</h2>
    </>
  );
}

export default ConfirmRegistration;
