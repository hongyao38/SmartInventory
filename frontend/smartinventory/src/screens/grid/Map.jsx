import { useState } from "react";
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

  const getStockLevel = (row, col) => {

    // Get container from backend

    // calculate quantity / capacity

    // Return a string stating the stock level

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
                isBox={checkIsBox(row, col)}
                isActive={row === activeCell?.row && col === activeCell?.col}
                handleCellClick={handleCellClick}
                key={`${row}-${col}`}
              />
            ))
        )}

      {/* Side Panel */}
      {isViewingBox ? <SidePanel class="box-side-panel" activeCell={activeCell} setIsViewingBox={setIsViewingBox}/> : ""}
    </div>
  );
}

export default Map;
