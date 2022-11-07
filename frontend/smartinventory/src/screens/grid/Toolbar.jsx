import "./styles/Toolbar.css";
import { newBlock, newBox } from "../../services/InventoryService";

function Toolbar({ activeCell, setActiveCell, blocks, setBlocks, boxes, setBoxes }) {

  // After selecting a cell, clicking New Block will create a new block
  const handleClickNewBlock = async (e) => {
    try {
      await newBlock({
        i: activeCell?.row,
        j: activeCell?.col
      });
    } catch (e) {
      alert("Could not send request to add block");
    }
    setActiveCell({ row: null, col: null });
    blocks.push([activeCell?.row, activeCell?.col]);
    setBlocks(blocks);
  }

  // TODO: Implement initialising of container capacity
  const handleClickNewContainer = async (e) => {
    try {
      await newBox({
        capacity: 0,
        i: activeCell?.row,
        j: activeCell?.col
      });
    } catch (e) {
      alert("Could not send request to add container");
    }
    setActiveCell({ row: null, col: null });
    boxes.push([activeCell?.row, activeCell?.col]);
    setBoxes(boxes);
  }

  return (
    <div className="container">
      <div className="toolbar">

        {/* New Block Button */}
        <button 
          className="newBlockBtn"
          onClick={() => handleClickNewBlock()}
        >
          New Block
        </button>

        {/* New Container Button */}
        <button 
          className="newContainerBtn"
          onClick={() => handleClickNewContainer()}
        >
          New Container
        </button>

        {/* Delete Block Button */}
        <button className="deleteBlockBtn">Delete Block</button>
      </div>
    </div>
  );
}

export default Toolbar;
