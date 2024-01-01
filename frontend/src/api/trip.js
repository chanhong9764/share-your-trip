import API from './axios';

const TripAPI = {
    getTripList(userId) {
        return API.get('/chats/' + userId);
    },
    getChattingList(data) {
        return API.get('/chats', {
            params: data,
        });
    },
    deleteChattingRoom(data) {
        return API.delete('/chats', {
            params: data,
        });
    },
    getInvitations(data) {
        return API.get('/chats/invitations', {
            params: {
                userId: data,
            },
        });
    },
    updateChatRoom(data) {
        return API.patch('/chats', data);
    },
    deleteChatRoom(data) {
        return API.delete('/chats/invitations', {
            params: data,
        });
    },
    getChattingListRange(data) {
        return API.get('/chats/ranges', {
            params: data,
        });
    },
    getTrip(data) {
        return API.get('/trips/' + data);
    },
};

export default TripAPI;
