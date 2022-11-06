import "./Toolbar.css";
import { newBlock } from "../../services/InventoryService";
import { activeCell } from "./Map";

function Toolbar({ activeCell }) {

  const handleClickNewBlock = async (e) => {
    try {
      await newBlock({
        i: activeCell?.row,
        j: activeCell?.col
      })
    } catch (e) {
      alert("Could not send request to add block")
    }
  }

  return (
    <div className="container">
      <div className="toolbar">
        <button 
          className="newBlockBtn"
          onClick={() => handleClickNewBlock()}
        >
          New Block
        </button>

        <button className="newContainerBtn">New Container</button>
        <button className="deleteBlockBtn">Delete Block</button>
      </div>
    </div>
  );
}

export default Toolbar;
