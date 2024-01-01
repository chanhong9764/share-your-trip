import API from './axios';

const CommentAPI = {
    writeComment(data) {
        return API.post('/comments', data);
    },
    getComment(articleNo) {
        return API.get('/comments/' + articleNo);
    },
    deleteComment(data) {
        return API.delete('/comments', {
            params: data,
        });
    },
};

export default CommentAPI;
