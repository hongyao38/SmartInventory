import Map from "./Map.jsx";
import Toolbar from "./Toolbar.jsx";
import "./Grid.css";

// To lookup:
// - State mgmt library

function Grid() {
  return (
    <div className="Grid">
      <Map />
      <Toolbar />
    </div>
  );
}

export default Grid;
