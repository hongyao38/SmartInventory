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
    const containerCoords = await getAllBoxes();

    // Get the coordinates of all containers
    const n = containerCoords.length;
    let allContainers = Array(n);

    for (let i = 0; i < n; i++) {
      allContainers[i] = [containerCoords[i].i, containerCoords[i].j];
    }

    // Update application parameters
    setBoxes(allContainers);
    setIsRetrievingBoxes(false);
    return allContainers;
  };

  return (
    <div>
      <NavBar className="NavBar" />

      <div className="Grid">
        <Map
          blocks={isRetrievingBlocks ? loadBlocks() : blocks}
          boxes={isRetrievingBoxes ? loadBoxes() : boxes}
          activeCell={activeCell}
          setActiveCell={setActiveCell}
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
