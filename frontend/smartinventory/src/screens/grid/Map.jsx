import { useState } from "react";
import Cell from "./Cell.jsx";
import "./styles/Map.css";

function Map({ blocks, activeCell, setActiveCell, rows = 100, cols = 100 }) {
  const handleCellClick = (row, col) => {
    console.log({ row, col });
    setActiveCell({ row, col });

    // TODO: Can deselect if clicked again
  };

  const checkIsBlock = (row, col) => {
    for (let i = 0; i < blocks.length; i++) {
      if (row === blocks[i][0] && col === blocks[i][1]) {
        return true;
      }
    }
    return false;
  }

  return (
    <div className="map">
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
              handleCellClick={handleCellClick}
              key={`${row}-${col}`}
            />
          ))
      )}
    </div>
  );
}

export default Map;
