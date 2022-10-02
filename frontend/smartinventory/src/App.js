import { BrowserRouter, Routes, Route } from "react-router-dom";

import LogInScreen from './screens/LogInScreen.js';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<LogInScreen/>} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;