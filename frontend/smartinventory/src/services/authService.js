import axiosInstance from "../utils/AxiosInstance";

const login = async (user) => {
    const res = await axiosInstance({
        method: "post",
        url: "/login",
        data: user,
    });

    let isLoggedIn = false;
    if (res.status === 200 && res.data.jwt) {
        sessionStorage.setItem("jwt", res.data.jwt);
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

const getUsers = async () => {
    const res = await axiosInstance({
        method: "get",
        url: "/users",
    });
    return res.data;
};

export { login, register, confirmEmail, getUsers };

