import { BrowserRouter, Routes, Route } from "react-router-dom";

import LoginScreen from "./screens/login/LogInScreen.js";
import RegistrationScreen from "./screens/registration/RegistrationScreen.js";
import ResetScreen from "./screens/forgetpassword/ResetScreen.js";
import RegSuccessScreen from "./screens/registration/RegSuccessScreen.js";
import ForgetPasswordScreen from "./screens/forgetpassword/ForgetPasswordScreen.js";
import LoginSuccessScreen from "./screens/login/LoginSuccessScreen.js";

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
      </Routes>
    </BrowserRouter>
  );
}

export default App;
