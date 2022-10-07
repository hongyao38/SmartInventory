import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/v1",
    headers: { "Content-Type": "application/json" },
    withCredentials: false,
});

axiosInstance.interceptors.request.use(
    async (config) => {
        const token = sessionStorage.getItem("token");
        console.log("Instance token" , token);
        if (
            !(
                config.url.includes("/login") ||
                config.url.includes("/registration") 
            )
        ) {
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
