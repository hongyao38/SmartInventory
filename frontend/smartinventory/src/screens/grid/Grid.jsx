import NavBar from "../navbar/NavBar.js";
import Map from "./Map.jsx";
import Toolbar from "./Toolbar.jsx";
import "./styles/Grid.css";
import { useState } from "react";
import { getAllBlocks } from "../../services/InventoryService.js";

// To lookup:
// - State mgmt library

function Grid() {

  const [loggingIn, setLoggingIn] = useState(true);

  const loadBlocks = async () => {
    const blockCoords = await getAllBlocks();

    // Get the coordinates of all blocks
    const n = blockCoords.length;
    let allBlocks = Array(n)

    for (let i = 0; i < n; i++) {
        allBlocks[i] = [blockCoords[i].i, blockCoords[i].j];
    }

    setBlocks(allBlocks);
    setLoggingIn(false);
    return allBlocks;
  }

  const [activeCell, setActiveCell] = useState(null);
  const [blocks, setBlocks] = useState([])

  return (
    <div>
      <NavBar className="NavBar" />

      <div className="Grid">
        <Map 
          blocks={loggingIn ? loadBlocks() : blocks}
          activeCell={activeCell}
          setActiveCell={setActiveCell}
        />
        <Toolbar 
          activeCell={activeCell}
          blocks={blocks}
          setBlocks={setBlocks}
        />
      </div>

    </div>
  );
}

export default Grid;
