import axiosInstance from '../utils/AxiosInstance';
import AsyncStorage from '@react-native-async-storage/async-storage';

const SignIn = async (user) => {
    const res = await axiosInstance({
        method: 'post',
        url: '/api/v1/login',
        data: user,
        headers: { 'content-Type' : 'multipart/form-data'},
    });

    let isLoggedIn = false;

    if(res.status === 200 && res.data.access_token){
        await AsyncStorage.setItem('token', res.data.access_token);
        await AsyncStorage.setItem('name', user.username);
        isLoggedIn = true;
    }
    return isLoggedIn;
}

export {SignIn};