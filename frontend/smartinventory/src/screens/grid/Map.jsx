import { useState } from "react";
import { getBox } from "../../services/InventoryService.js";
import SidePanel from "../sidePanel/SidePanel.js";
import Cell from "./Cell.jsx";
import "./styles/Map.css";

function Map({
  blocks,
  boxes,
  activeCell,
  setActiveCell,
  rows = 100,
  cols = 100,
}) {

  // Map States
  const [isViewingBox, setIsViewingBox] = useState(false);
  const [stockUpdateNeeded, setStockUpdateNeeded] = useState(false);
  const [stockLevel, setStockLevel] = useState("");

  // Setting of active cell
  const handleCellClick = (row, col) => {

    // Set active cell
    setIsViewingBox(false);
    setActiveCell({ row, col });

    // Bring up the side-panel if clicked on box
    if (checkIsBox(row, col)) {
      setIsViewingBox(true);
    }

  };

  const getStockLevel = async (row, col) => {

    // No need to send request to backend if nothing changed / not box
    if (!stockUpdateNeeded) return;
    if (!checkIsBox(row, col)) return;

    // Get container from backend
    let container = null;
    try {
      container = await getBox(row, col);
    } catch (e) {
      // console.log("No container found");
      return;
    }

    // calculate quantity / capacity
    let stockPercentage = container.quantity / container.capacity;
    if (row === 99 && col === 99) {
      setStockUpdateNeeded(false);
    }

    // Return a string stating the stock level
    if (stockPercentage === 0) setStockLevel("");
    else if (stockPercentage > 0.5) setStockLevel("high");
    else if (stockPercentage > 0.2) setStockLevel("med");
    else setStockLevel("low");
  }

  // Iterates through the list of blocks and determine if cell is a block
  const checkIsBlock = (row, col) => {
    for (let i = 0; i < blocks.length; i++) {
      if (row === blocks[i][0] && col === blocks[i][1]) {
        return true;
      }
    }
    return false;
  };

  // Iterates through the list of boxes and determine if cell is a box
  const checkIsBox = (row, col) => {
    for (let i = 0; i < boxes.length; i++) {
      if (row === boxes[i][0] && col === boxes[i][1]) {
        return true;
      }
    }
    return false;
  };

  return (
    <div className="map">

      {/* 2D Array of all Cells */}
      {Array(rows)
        .fill(null)
        .map((_, row) =>
          Array(cols)
            .fill(null)
            .map((_, col) => (
              <Cell
                row={row}
                col={col}
                isBlock={checkIsBlock(row, col)}
                isActive={row === activeCell?.row && col === activeCell?.col}
                isBox={checkIsBox(row, col)}
                stockLevel={stockLevel}
                handleCellClick={handleCellClick}
                key={`${row}-${col}`}
              />
            ))
        )}

      {/* Side Panel */}
      {isViewingBox ? 
        <SidePanel 
          class="box-side-panel" 
          activeCell={activeCell} 
          setIsViewingBox={setIsViewingBox}
          setStockUpdateNeeded={setStockUpdateNeeded}
        /> : ""}
    </div>
  );
}

export default Map;
