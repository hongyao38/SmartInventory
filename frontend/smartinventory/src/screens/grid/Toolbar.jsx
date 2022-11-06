import "./Toolbar.css";

function Toolbar() {
  return (
    <div className="container">
      <div className="toolbar">
        <button className="newBlockBtn">New Block</button>
        <button className="newContainerBtn">New Container</button>
        <button className="deleteBlockBtn">Delete Block</button>
      </div>
    </div>
  );
}

export default Toolbar;
