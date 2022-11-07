import NavBar from "../navbar/NavBar.js";
import Map from "./Map.jsx";
import Toolbar from "./Toolbar.jsx";
import "./styles/Grid.css";
import { useState } from "react";
import {
  getAllBlocks,
  getAllBoxes,
} from "../../services/InventoryService.js";

// To lookup:
// - State mgmt library

function Grid() {
  // Application parameters
  const [isRetrievingBlocks, setIsRetrievingBlocks] = useState(true);
  const [isRetrievingBoxes, setIsRetrievingBoxes] = useState(true);
  const [activeCell, setActiveCell] = useState(null);
  const [blocks, setBlocks] = useState([]);
  const [boxes, setBoxes] = useState([]);
  const [boxesQuantities, setBoxesQuantities] = useState([]);
  const [lowItems, setLowItems] = useState([]);

  /**
   * Sends a request to backend API to retrieve all users persisted blocks
   * @returns int[][] blockCoordinates
   */
  const loadBlocks = async () => {
    const blockCoords = await getAllBlocks();

    // Get the coordinates of all blocks
    const n = blockCoords.length;
    let allBlocks = Array(n);

    for (let i = 0; i < n; i++) {
      allBlocks[i] = [blockCoords[i].i, blockCoords[i].j];
    }

    // Update application parameters
    setBlocks(allBlocks);
    setIsRetrievingBlocks(false);
    return allBlocks;
  };

  const loadBoxes = async () => {
    const allContainers = await getAllBoxes();

    const n = allContainers.length;

    // Get the coordinates of all containers
    let containerCoords = Array(n);
    let quantitiesArr = Array(n);
    for (let i = 0; i < n; i++) {

      let ctn = allContainers[i];
      containerCoords[i] = [ctn.i, ctn.j];

      let percentage = ctn.quantity / ctn.capacity;
      if (ctn.quantity === 0) percentage = 0;
      quantitiesArr[i] = [ctn.i, ctn.j, percentage, ctn.food, ctn.quantity, ctn.capacity];
    }

    // Update application parameters
    setBoxes(containerCoords);
    setBoxesQuantities(quantitiesArr);
    setIsRetrievingBoxes(false);
    return containerCoords;
  };

  const getAlerts = () => {
    for (let i = 0; i < boxesQuantities.length; i++) {
      let qty = boxesQuantities[i];
      if (qty[2] < 0.1) {
        lowItems.push(qty[3]);
        setLowItems(lowItems);
      }
    }
  }

  return (
    <div>
      <NavBar className="NavBar" />

      <div className="Grid">
        <Map
          blocks={isRetrievingBlocks ? loadBlocks() : blocks}
          boxes={isRetrievingBoxes ? loadBoxes() : boxes}
          boxesQuantities={boxesQuantities}
          activeCell={activeCell}
          setActiveCell={setActiveCell}
          setIsRetrievingBoxes={setIsRetrievingBoxes}
        />
        <Toolbar
          activeCell={activeCell}
          setActiveCell={setActiveCell}
          blocks={blocks}
          setBlocks={setBlocks}
          boxes={boxes}
          setBoxes={setBoxes}
        />
      </div>
    </div>
  );
}

export default Grid;
