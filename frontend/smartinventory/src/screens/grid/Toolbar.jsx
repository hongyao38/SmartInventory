import "./styles/Toolbar.css";
import { newBlock } from "../../services/InventoryService";

function Toolbar({ activeCell, blocks, setBlocks }) {

  const handleClickNewBlock = async (e) => {
    try {
      await newBlock({
        i: activeCell?.row,
        j: activeCell?.col
      })
    } catch (e) {
      alert("Could not send request to add block")
    }
    blocks.push([activeCell?.row, activeCell?.col])
    setBlocks(blocks)
    console.log("Blocks: ", blocks)
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
        <button className="newContainerBtn">New Container</button>

        {/* Delete Block Button */}
        <button className="deleteBlockBtn">Delete Block</button>
      </div>
    </div>
  );
}

export default Toolbar;
