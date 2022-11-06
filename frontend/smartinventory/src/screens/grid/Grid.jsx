import NavBar from "../navbar/NavBar.js";
import Map from "./Map.jsx";
import Toolbar from "./Toolbar.jsx";
import "./Grid.css";

// To lookup:
// - State mgmt library

function Grid() {
  return (
    <div>
      <NavBar className="NavBar" />

      <div className="Grid">
        <Map />
        <Toolbar />
      </div>
      
    </div>
  );
}

export default Grid;
