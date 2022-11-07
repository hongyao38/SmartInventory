import { BrowserRouter, Route, Routes } from "react-router-dom";

import ForgetPasswordScreen from "./screens/forgetpassword/ForgetPasswordScreen.js";
import ResetScreen from "./screens/forgetpassword/ResetScreen.js";
import Grid from "./screens/grid/Grid.jsx";
import LoginScreen from "./screens/login/LogInScreen.js";
import LoginSuccessScreen from "./screens/login/LoginSuccessScreen.js";
import RegistrationScreen from "./screens/registration/RegistrationScreen.js";
import RegSuccessScreen from "./screens/registration/RegSuccessScreen.js";
import SidePanel from "./screens/sidePanel/SidePanel.js";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginScreen />} />
        <Route path="/registration" element={<RegistrationScreen />} />
        <Route path="/registration/confirm" element={<RegSuccessScreen />} />

        <Route path="/forget-password/reset" element={<ResetScreen />} />
        <Route path="/forget-password" element={<ForgetPasswordScreen />} />
        <Route path="/dashboard" element={<LoginSuccessScreen />} />

        <Route path="/grid" element={<Grid/>} />
        <Route path="/additem" element={<SidePanel />}  />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
