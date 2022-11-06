import { useState } from "react";
import Cell from "./Cell.jsx";
import "./Map.css";

function Map({ rows = 100, cols = 100 }) {
  const [activeCell, setActiveCell] = useState(null);

  const handleCellClick = (row, col) => {
    console.log({ row, col });
    setActiveCell({ row, col });

    // TODO: Can deselect if clicked again
  };

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
