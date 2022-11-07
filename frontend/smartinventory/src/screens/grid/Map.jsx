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
  // Setting of active cell
  const handleCellClick = (row, col) => {

    // Set active cell
    setActiveCell({ row, col });
    
  };

  // Iterates through the list of blocks and determine if cell is a block
  const checkIsBlock = (row, col) => {
    for (let i = 0; i < blocks.length; i++) {
      if (row === blocks[i][0] && col === blocks[i][1]) {
        return true;
      }
    }
    return false;
  };

  // Iterates through the list of boxes and determine if cell is a container
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
    </div>
  );
}

export default Map;
