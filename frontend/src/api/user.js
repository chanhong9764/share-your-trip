import API from './axios';

const UserAPI = {
    userLogin(params) {
        return API.post('/users/login', params);
    },
    userJoin(params) {
        return API.post('/users', params);
    },
    userDelete(params) {
        return API.delete('/users/' + params);
    },
    userModify(params) {
        return API.put('/users', params);
    },
    userCheckId(params) {
        return API.get('/users/check/' + params);
    },
    getUserInfo(userId) {
        return API.get('/users/' + userId);
    },

    searchUser(userId) {
        return API.get('/users/search/' + userId);
    },
    findId(email) {
        return API.get('/users/findId/' + email);
    },
    changeProfile(data) {
        return API.post('/users/changeProfile', data, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    },
};

export default UserAPI;
