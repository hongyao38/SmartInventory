import axios from "axios";
import axiosInstance from "../utils/AxiosInstance";

const SignIn = async (user) => {
    const res = await axiosInstance({
        method: "post",
        url: "/api/v1/login",
        data: user,
        headers: { "content-Type": "multipart/form-data" },
    });

    let isLoggedIn = false;

    if (res.status === 200 && res.data.access_token) {
        sessionStorage.setItem("token", res.data.access_token);
        sessionStorage.setItem("name", user.username);
        isLoggedIn = true;
    }
    return isLoggedIn;
};

const SignUp = async (user) => {
   
    const res = await axiosInstance({
        method : 'post',
        url: '/api/v1/registration',
        data: user,
    })
    console.log(res.data);
    return res.status === 201;
};

const ConfirmEmail= async (token) => {
    const res = await axiosInstance({
        method: 'get',
        url: 'api/v1/registration/confirm' + token
    })
    console.log(res.status);
    return res.status === 200;
}

export { SignIn, SignUp, ConfirmEmail};
