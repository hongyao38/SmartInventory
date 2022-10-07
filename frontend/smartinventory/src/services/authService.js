import axiosInstance from "../utils/AxiosInstance";

const login = async (user) => {
    const res = await axiosInstance({
        method: "post",
        url: "/login",
        data: user,
    });

    let isLoggedIn = false;
    if (res.status === 200 && res.data.jwt) {
        sessionStorage.setItem("token", res.data.jwt);
        sessionStorage.setItem("name", user.username);
        isLoggedIn = true;
    }
    return isLoggedIn;
};

const register = async (user) => {
    const res = await axiosInstance({
        method: "post",
        url: "/registration",
        data: user,
    });
    return res.status === 201;
};

const confirmEmail = async (token) => {
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

export { login, register, confirmEmail, testGet };

