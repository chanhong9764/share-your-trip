import API from './axios';

const BoardAPI = {
    getBoardList(data) {
        return API.get('/boards', {
            params: data,
        });
    },
    getBoardPost(data) {
        return API.get('/boards/' + data.articleNo, {
            params: {
                userId: data.userId,
            },
        });
    },
    writePost(data) {
        return API.post('/boards', data, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    },
    removePost(articleNo) {
        return API.delete('/boards/' + articleNo);
    },
    modifyPost(data) {
        return API.put('/boards', data, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    },
    setRecommend(data) {
        return API.post('/boards/recommends', data);
    },
    delRecommend(data) {
        return API.delete('/boards/recommends', {
            params: data,
        });
    },
    getHotHasgTag() {
        return API.get('/boards/hothashtags');
    },
};

export default BoardAPI;
