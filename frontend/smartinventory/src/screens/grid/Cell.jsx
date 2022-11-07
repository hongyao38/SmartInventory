import "./styles/Cell.css";

const BOX_LEVEL_CLASSES = {
  EMPTY: "box-empty",
  LOW: "box-low",
  MEDIUM: "box-medium",
  HIGH: "box-high",
};

function Cell({ row, col, isBlock, isActive, isBox, stockLevel, handleCellClick }) {

  if (row === 1 && col === 4) console.log("STOCK LEVEL: ", stockLevel);

  return (
    <div
      className={
        `cell 
        ${isActive ? "active" : ""} 
        ${isBlock ? "block" : ""} 
        ${isBox ? "box" : ""} 
        ${stockLevel}`
      }
      onClick={() => handleCellClick(row, col)}
    ></div>
  );
}

export default Cell;
