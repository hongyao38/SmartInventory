import { BrowserRouter, Routes, Route } from "react-router-dom";

import LoginScreen from "./screens/LogInScreen.js";
import RegistrationScreen from "./screens/RegistrationScreen.js";
import ResetScreen from "./screens/ResetScreen.js";
import RegSuccessPage from "./screens/RegSuccessPage.js";
import ForgetPasswordScreen from "./screens/ForgetPasswordScreen.js";
import ResendEmailScreen from "./screens/ResendEmailScreen.js";
import LoginSuccessScreen from "./screens/LoginSuccessScreen.js";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginScreen />} />
        <Route path="/registration" element={<RegistrationScreen />} />
        <Route path="/registration/confirm" element={<RegSuccessPage />} />

        <Route path="/reset" element={<ResetScreen />} />
        <Route path="/forgetPassword" element={<ForgetPasswordScreen />} />
        <Route path="/resendEmailScreen" element={<ResendEmailScreen />} />
        <Route path="/dashboard" element={<LoginSuccessScreen />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
