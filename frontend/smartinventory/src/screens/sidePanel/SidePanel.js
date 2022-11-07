import React, { useState } from "react";
import { addFoodToBox, getBox, updateBoxCapacity } from "../../services/InventoryService";
import "../style/SidePanel.css";

function SidePanel({ activeCell, boxesQuantities, setIsViewingBox, setIsRetrievingBoxes }) {

    // Add Food Attributes
    const [foodName, setFoodName] = useState("");
    const [capacity, setCapacity] = useState(0);
    const [quantity, setQuantity] = useState(0);

    // Box Information Attributes
    const [boxInfo, setBoxInfo] = useState({item: "", capacity: 0, quantity: 0})


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

    const getBoxInfo = (row, col) => {
        
    }

    const getItem = (row, col) => {
        return boxInfo.item;
    }

    const openSidePanel = () => { document.getElementById('mySidenav').style.width = "250px" }

    const closeNav = () => {
        setIsViewingBox(false);
    }

    return (
        <>
            <div id="mySidenav" class="sidenav">
                {/* <button class="closebtn" onClick={() => closeNav()}><img src="close_button.png" style={{ width: "35px" }} alt="Close Button" /></button> */}
                <div class="box-info-container">
                    <div class="box-info-title"><strong>Box Information</strong></div>
                    <div class="box-info-itm">Item: {getItem(activeCell?.row, activeCell?.col)}</div>
                    <div class="box-info-qty">Quantity: {boxInfo.quantity}</div>
                    <div class="box-info-cap">Capacity: {boxInfo.capacity}</div>
                </div>

                <div class="form-container">
                    <div class="form-title"><strong>Add New Item</strong></div>
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
                    <button class="cancelbtn" onClick={closeNav} type="button">Close</button>
                </div>
            </div>

        </>
    )

}

export default SidePanel;