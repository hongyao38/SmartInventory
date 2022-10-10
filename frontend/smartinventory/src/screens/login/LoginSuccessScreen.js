import React from "react";

import { MDBBtn } from "mdb-react-ui-kit";
import { getUsers } from "../../services/authService";

import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import "../style/LogInScreen.css";

function LoginSuccess() {
  const getUsersMethod = async () => {
    try {
      const res = await getUsers();
      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <h1>login success</h1>
      <MDBBtn
        onClick={() => {
          getUsersMethod();
        }}
      >
        GET
      </MDBBtn>
    </>
  );
}

export default LoginSuccess;
