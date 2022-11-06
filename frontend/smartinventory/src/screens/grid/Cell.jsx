import "./styles/Cell.css";

const BOX_LEVEL_CLASSES = {
  EMPTY: "box-empty",
  LOW: "box-low",
  MEDIUM: "box-medium",
  HIGH: "box-high",
};

function Cell({ row, col, isBlock, isActive, handleCellClick }) {

  return (
    <div
      className={`cell ${isActive ? "active" : ""} ${isBlock ? "block" : ""}`}
      onClick={() => handleCellClick(row, col)}
    ></div>
  );
}

export default Cell;
