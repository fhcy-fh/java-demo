// 请求拦截器
axios.interceptors.request.use(
    config => {
        // 从本地存储中获取token，携带在header中
        const token = sessionStorage.access_token;
        token && (config.headers.Authorization = 'Bearer' + ' ' + token);
        return config;
    },
    error => {
        return Promise.error(error);
    }
)
