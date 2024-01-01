import API from './axios';

const EmailAPI = {
    mailAuth(mail) {
        return API.get('/email', {
            params: {
                receiver: mail,
            },
        });
    },
};

export default EmailAPI;
