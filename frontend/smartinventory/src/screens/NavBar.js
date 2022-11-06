import {
    MDBBtn,
    MDBCol,
    MDBContainer,
    MDBInput,
    MDBRow,
    MDBTypography,
    MDBModal,
    MDBModalDialog,
    MDBModalContent,
    MDBModalHeader,
    MDBModalTitle,
    MDBModalBody,
    MDBModalFooter,
    MDBSpinner,
} from "mdb-react-ui-kit";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./NavBar.css";

function NavBar() {

    const [name, setName] = useState({ name: "" });
    const [expiryDate, setExpiryDate] = useState({ expiryDate: "" });
    const [quantity, setQuantity] = useState({ quantity: "" });
    const [modal, setModal] = useState(false);

    const [tempName, saveName] = useState({ name: "" });
    const [tempExpiryDate, saveExpiryDate] = useState({ tempExpiryDate: "" });
    const [tempQuantity, saveQuantity] = useState({ tempQuantity: "" });

    const inputName = (e) => {
        saveName({ tempName });
    };

    const inputExpiryDate = (e) => {
        saveExpiryDate({ tempExpiryDate });
    };

    const inputQuantity = (e) => {
        saveQuantity({ tempQuantity });
    };

    const saveData = (e) => {
        setName({ tempName });
        setExpiryDate({ tempExpiryDate });
        setQuantity({ tempQuantity });
        closeNav({});
        <h1>saved!</h1>
    };

    const openNav = () => { document.getElementById('mySidenav').style.width = "250px" }
    const closeNav = () => { document.getElementById('mySidenav').style.width = "0px" }

    return (
        <>


            <div id="mySidenav" class="sidenav">
                <a class="closebtn" onClick={() => closeNav()}><img src="close_button.png" style={{ width: "35px" }} /></a>
                
                <div class="mb-3">
                    <label for="inputName" class="form-label">Item name</label>
                    <input type="name" class="form-control" id="inputName" onChange={inputName} ></input>
                </div>

                <div class="mb-3">
                    <label for="inputExpiryDate" class="form-label">Item name</label>
                    <input type="name" class="form-control" id="inputExpiryDate" onChange={inputExpiryDate} ></input>
                </div>

                <div class="mb-3">
                    <label for="inputQuantity" class="form-label">Item name</label>
                    <input type="name" class="form-control" id="inputQuantity" onChange={inputQuantity} ></input>
                </div>
                <a>
                    <button onClick={saveData} type="button" class="btn btn-primary">Save</button>
                    
                </a>
            </div>



            <button onClick={() => openNav()}>test</button>
        </>
    )

}

export default NavBar;