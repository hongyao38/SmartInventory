import { MDBBtn } from "mdb-react-ui-kit";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import React from "react";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader

import { getUsers } from "../services/authService";
import "./LogInScreen.css";

function ConfirmRegistration() {
  const getMethod = async () => {
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
          getMethod();
        }}
      >
        GET
      </MDBBtn>
    </>
  );
}

export default ConfirmRegistration;
