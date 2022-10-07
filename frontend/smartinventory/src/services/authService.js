import axios from "axios";
import axiosInstance from "../utils/AxiosInstance";

const SignIn = async (user) => {
    console.log("USER", user);
    const res = await axiosInstance({
        method: "post",
        url: "/login",
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
        url: "/registration",
        data: user,
    });
    return res.status === 201;
};

const ConfirmEmail = async (token) => {
    const res = await axiosInstance({
        method: "get",
        url: "/registration/confirm" + token,
    });
    return res.status === 200;
};

const testGet = async () => {
    console.log("before calling");

    const res = await axiosInstance({
        method: "get",
        url: "/users",
    });
    console.log("After calling", res);

    return res.data;
};

export { SignIn, SignUp, ConfirmEmail, testGet };
