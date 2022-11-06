import "./Cell.css";

const BOX_LEVEL_CLASSES = {
  EMPTY: "box-empty",
  LOW: "box-low",
  MEDIUM: "box-medium",
  HIGH: "box-high",
};

function Cell({ row, col, isActive, handleCellClick }) {

  // bool isContainer = false;
  // HashMap<String, Integer> items

  return (
    <div
      className={`cell ${isActive ? "active" : ""}`}
      onClick={() => handleCellClick(row, col)}
    ></div>
  );
}

export default Cell;
