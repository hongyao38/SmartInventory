import React, { useState } from "react";
import "../style/SidePanel.css";

function SidePanel({ setIsViewingBox }) {

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
    const closeNav = () => {
        setIsViewingBox(false);
        document.getElementById('mySidenav').style.width = "0px" ;
    }

    return (
        <>
            <div id="mySidenav" class="sidenav">
                {/* <button class="closebtn" onClick={() => closeNav()}><img src="close_button.png" style={{ width: "35px" }} alt="Close Button" /></button> */}
                
                <div class="form_container">
                    <div class="capacity-field">
                        <label for="inputName" class="form-label">Box Capacity</label>
                        <input type="name" class="form-control" id="inputName" onChange={inputName} ></input>
                    </div>

                    <div class="name-field">
                        <label for="inputExpiryDate" class="form-label">Item Name</label>
                        <input type="name" class="form-control" id="inputExpiryDate" onChange={inputExpiryDate} ></input>
                    </div>

                    <div class="quantity-field">
                        <label for="inputQuantity" class="form-label">Item Quantity</label>
                        <input type="name" class="form-control" id="inputQuantity" onChange={inputQuantity} ></input>
                    </div>
                    
                    <button class="savebtn" onClick={saveData} type="button">Save</button>
                    <button class="cancelbtn" onClick={saveData} type="button">Cancel</button>
                </div>
            </div>

        </>
    )

}

export default SidePanel;