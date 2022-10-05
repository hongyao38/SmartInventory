import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {'Content-Type' : 'application/json'},
    withCredentials: true,
});

axiosInstance.interceptors.request.use(
    async (config) =>{
        const token = sessionStorage.getItem('token');
        if(!(
            config.url.includes('/api/v1/login') || 
            config.url.includes('/api/v1/registration')
        )){
            if(token){
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