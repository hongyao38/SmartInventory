import { MDBCol, MDBContainer, MDBRow } from "mdb-react-ui-kit";

import DescriptionPanel from "./DescriptionPanel";
import Form from "./Form";

import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import "../style/LogInScreen.css";

function LoginScreen() {
  return (
    <MDBContainer className="my-5 gradient-form">
      <MDBRow>
        {/* Description Side */}
        <MDBCol col="6" className="">
          <DescriptionPanel />
        </MDBCol>

        {/* Login Side */}
        <MDBCol col="6" className="">
          <Form />
        </MDBCol>
      </MDBRow>
    </MDBContainer>
  );
}

export default LoginScreen;
