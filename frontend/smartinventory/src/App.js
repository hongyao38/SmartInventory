import { BrowserRouter, Routes, Route } from "react-router-dom";

import LogInScreen from './screens/LogInScreen.js';
import RegistrationScreen from "./screens/RegistrationScreen.js";
import ForgetPasswordScreen from "./screens/ForgetPasswordScreen.js";
//import ResendEmailScreen from "./screens/ResendEmailScreen";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<LogInScreen/>} />
                <Route path="/registration" element={<RegistrationScreen/>} />
                <Route path="/ForgetPassword" element={<ForgetPasswordScreen/>} />
                {/* <Route path="/ForgetPassword" element={<ResendEmailScreen/>} /> */}
            </Routes>
        </BrowserRouter>
    );
}

export default App;