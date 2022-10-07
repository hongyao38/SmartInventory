import axios from "axios";
import axiosInstance from "../utils/AxiosInstance";

const SignIn = async (user) => {
    console.log("USER", user);
    const res = await axiosInstance({
        method: "post",
        url: "/api/v1/login",
        data: user,
    });
    console.log("JWT:", res.data.jwt);

    let isLoggedIn = false;
    if (res.status === 200 && res.data.jwt) {
        sessionStorage.setItem("token", res.data.jwt);
        sessionStorage.setItem("name", user.username);
        isLoggedIn = true;
    }
    return isLoggedIn;
};

const SignUp = async (user) => {
    const res = await axiosInstance({
        method: "post",
        url: "/api/v1/registration",
        data: user,
    });
    console.log(res.data);
    return res.status === 201;
};

const ConfirmEmail = async (token) => {
    const res = await axiosInstance({
        method: "get",
        url: "api/v1/registration/confirm" + token,
    });
    console.log(res.status);
    return res.status === 200;
};

const testGet = async () => {
    console.log("before calling");

    const res = await axiosInstance({
        method: "get",
        url: "api/v1/users",
    });
    console.log("After calling", res);

    return res.data;
};

export { SignIn, SignUp, ConfirmEmail, testGet };
