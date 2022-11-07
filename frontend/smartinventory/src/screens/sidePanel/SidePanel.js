import React, { useState } from "react";
import { addFoodToBox, updateBoxCapacity } from "../../services/InventoryService";
import "../style/SidePanel.css";

function SidePanel({ activeCell, setIsViewingBox, setIsRetrievingBoxes }) {

    // Add Food Attributes
    const [foodName, setFoodName] = useState("");
    const [capacity, setCapacity] = useState(0);
    const [quantity, setQuantity] = useState(0);


    const inputCapacity = (e) => {
        setCapacity( parseFloat(e.target.value, 10) );
    };

    const inputName = (e) => {
        setFoodName( e.target.value );
    };

    const inputQuantity = (e) => {
        setQuantity( parseFloat(e.target.value, 10) );
    };

    const sendAddFoodRequest = async (e) => {
        // Update Box capacity
        try {
            await updateBoxCapacity({
                capacity: capacity,
                i: activeCell?.row,
                j: activeCell?.col
            })
        } catch (e) {
            alert("Cannot update container capacity");
        }

        // Add Food to Box
        try {
            await addFoodToBox(activeCell?.row, activeCell?.col, {
                name: foodName,
                quantity: quantity,
            })
        } catch (e) {
            alert("Cannot add food to container");
        }
        setIsRetrievingBoxes(true);
    }

    const openSidePanel = () => { document.getElementById('mySidenav').style.width = "250px" }

    const closeNav = () => {
        setIsViewingBox(false);
    }

    return (
        <>
            <div id="mySidenav" class="sidenav">
                {/* <button class="closebtn" onClick={() => closeNav()}><img src="close_button.png" style={{ width: "35px" }} alt="Close Button" /></button> */}
                
                <div class="form_container">
                    <div class="capacity-field">
                        <label class="form-label">Box Capacity</label>
                        <input type="text" name="capacity" class="form-control" id="inputName" onChange={inputCapacity} ></input>
                    </div>

                    <div class="name-field">
                        <label class="form-label">Item Name</label>
                        <input type="text" name="foodName" class="form-control" id="inputExpiryDate" onChange={inputName} ></input>
                    </div>

                    <div class="quantity-field">
                        <label class="form-label">Item Quantity</label>
                        <input type="text" name="quantity" class="form-control" id="inputQuantity" onChange={inputQuantity} ></input>
                    </div>
                    
                    <button class="savebtn" type="button" onClick={sendAddFoodRequest}>Save</button>
                    <button class="cancelbtn" onClick={closeNav} type="button">Cancel</button>
                </div>
            </div>

        </>
    )

}

export default SidePanel;