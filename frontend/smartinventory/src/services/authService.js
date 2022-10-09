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

const usernameExists = async (usernameToCheck) => {
    const res = await axiosInstance({
        method: "post",
        url: "/users/check-username",
        data: {username: usernameToCheck, password: ""}
    });
    return Boolean(res.data);
}

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

const forgetPassword = async (email) => {
    const res = await axiosInstance({
        method: "post",
        url: "/forget-password",
        data: email,
    });
    console.log("status", res.status);
    return res.status === 200;
};

const resetPassword = async (passwords, token) => {
    const res = await axiosInstance({
        method: "put",
        url: "/forget-password/reset" + token,
        data: passwords,
    });
    return res.status === 200;
};

export {
    login,
    register,
    usernameExists,
    confirmEmail,
    getUsers,
    forgetPassword,
    resetPassword,
};
