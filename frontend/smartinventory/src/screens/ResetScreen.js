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
import "./ResetScreen.css";
// import { SignIn } from "../services/authService";

function ResetScreen() {
    const [data, setData] = useState({
        newPassword: "",
        confirmPassword: "",
    });

    const onChange = (e) => {
        setData({ ...data, [e.target.name]: e.target.value });
        console.log(data);
    };

    const handleSignIn = async (e) => {};

    return (
        <MDBContainer className="my-5 gradient-form">
            <MDBCol className="mt-4">
                <MDBRow rows="6" className="mb-5">
                    <div className="d-flex flex-column ms-5 mt-4">
                        <div className="text-center">
                            <img
                                src="gold-key.png"
                                style={{ width: "55px" }}
                                alt="logo"
                            />
                            <h4 className="mt-2 mb-2 pb-2">
                                Set new password
                            </h4>
                            <h6> Your new password must be different to 
                                previously used passwords.
                            </h6>
                        </div>
                        <br></br>
                        <div className= "mb-5">
                        <MDBCol className="d-flex justify-content-center">
                                <MDBInput
                                    id="pass1"
                                    label="password"
                                    type="password"
                                    value={data.username}
                                    name="newPassword"
                                    required
                                    onChange={onChange}
                                />
                        </MDBCol>
                            <div id='pass1' className='form-text d-flex justify-content-center'>
                                    must be at least 8 characters long.
                             </div>
                        
                        <br></br>
                        <MDBCol className="d-flex justify-content-center ">
                                <MDBInput
                                    label="confirm password"
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
                        <div className="text-center">
                            <MDBBtn
                                className="mb-4 gradient-custom-2"
                                onClick={handleSignIn}
                            >
                                Reset Password
                            </MDBBtn>
                            
                        </div>
                        </MDBCol>
                    </div>
                </MDBRow>
            </MDBCol>
            <MDBCol col="6"></MDBCol>
        </MDBContainer>
//         <form>
//   {/* <!-- Email input --> */}
//   <div class="form-outline mb-4">
//     <input type="email" id="form1Example1" class="form-control" />
//     <label class="form-label" for="form1Example1">Email address</label>
//   </div>

//   {/* <!-- Password input --> */}
//   <div class="form-outline mb-4">
//     <input type="password" id="form1Example2" class="form-control" />
//     <label class="form-label" for="form1Example2">Password</label>
//   </div>

//   {/* <!-- 2 column grid layout for inline styling --> */}
//   <div class="row mb-4">
//     <div class="col d-flex justify-content-center">
//       {/* <!-- Checkbox --> */}
//       <div class="form-check">
//         <input class="form-check-input" type="checkbox" value="" id="form1Example3" checked />
//         <label class="form-check-label" for="form1Example3"> Remember me </label>
//       </div>
//     </div>

//     <div class="col">
//       {/* <!-- Simple link --> */}
//       <a href="#!">Forgot password?</a>
//     </div>
//   </div>

//   {/* <!-- Submit button --> */}
//   <button type="submit" class="btn btn-primary btn-block">Sign in</button>

//   <div class="form-outline">
//     <input type="password" id="typePassword" class="form-control" />
//     <label class="form-label" for="typePassword">Password input</label>
//     </div>  
// </form>
    );
    
}

export default ResetScreen;
