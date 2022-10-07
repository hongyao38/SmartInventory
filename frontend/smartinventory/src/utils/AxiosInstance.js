import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/v1",
    headers: { "Content-Type": "application/json" },
    withCredentials: false,
});

axiosInstance.interceptors.request.use(
    async (config) => {
        const jwt = sessionStorage.getItem("jwt");
        console.log("Instance jwt:" , jwt);
        if (
            !(
                config.url.includes("/login") ||
                config.url.includes("/registration") 
            )
        ) {
            if (jwt) {
                config.headers.Authorization = `Bearer ${jwt}`;
            }
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
