import { BrowserRouter, Routes, Route } from "react-router-dom";

import LogInScreen from './screens/LogInScreen.js';
import RegistrationScreen from "./screens/RegistrationScreen.js";
import ResetScreen from "./screens/ResetScreen.js";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<LogInScreen/>} />
                <Route path="/registration" element={<RegistrationScreen/>} />
                <Route path="/reset" element={<ResetScreen/>} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;