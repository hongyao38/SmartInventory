import axiosInstance from '../utils/AxiosInstance';

const SignIn = async (user) => {
    const res = await axiosInstance({
        method: 'post',
        url: '/api/v1/login',
        data: user,
        headers: { 'content-Type' : 'multipart/form-data'},
    });

    let isLoggedIn = false;

    if(res.status === 200 && res.data.access_token){
        sessionStorage.setItem('token', res.data.access_token);
        sessionStorage.setItem('name', user.username);
        isLoggedIn = true;
    }
    return isLoggedIn;
}

export {SignIn};