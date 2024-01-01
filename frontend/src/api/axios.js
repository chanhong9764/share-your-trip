import axios from 'axios';

const instance = axios.create({
    // baseURL: 'http://192.168.0.53:8080/',
    baseURL: '/api',
    headers: {
        'Content-Type': 'application/json',
    },
    // cors 에러 처리
    //withCredentials: true,
});

export const instance2 = axios.create({
    baseURL: 'https://apis-navi.kakaomobility.com',
    headers: {
        'Content-Type': 'application/json',
    },
});

instance2.interceptors.request.use(function (config) {
    return {
        ...config,
        headers: {
            ...config.headers,
            Authorization: 'KakaoAK f4182680fbb196a8ab235737a5ecae3c',
        },
    };
});

export default instance;
