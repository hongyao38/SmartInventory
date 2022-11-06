import NavBar from "../navbar/NavBar.js";
import Map from "./Map.jsx";
import Toolbar from "./Toolbar.jsx";
import "./Grid.css";
import { useState } from "react";

// To lookup:
// - State mgmt library

function Grid() {

  const [activeCell, setActiveCell] = useState(null);

  return (
    <div>
      <NavBar className="NavBar" />

      <div className="Grid">
        <Map 
          activeCell={activeCell}
          setActiveCell={setActiveCell}
        />
        <Toolbar 
          activeCell={activeCell}
        />
      </div>

    </div>
  );
}

export default Grid;
