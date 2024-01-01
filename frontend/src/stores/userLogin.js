import { ref, computed } from 'vue';
import { defineStore } from 'pinia';

export const useUserInfoStore = defineStore('userinfo', () => {
    // store
    const isLogined = ref(false);
    const isUserInfo = ref({
        userId: '',
        userName: '',
        userPwd: '',
        emailId: '',
        profile: '',
    });
    const changeProfile = (url) => {
        isUserInfo.value.profile = url;
    };
    // actions
    function login(params) {
        isLogined.value = true;
        isUserInfo.value.userId = params.userId;
        isUserInfo.value.userName = params.userName;
        isUserInfo.value.userPwd = params.userPassword;
        isUserInfo.value.emailId = params.email;
        isUserInfo.value.profile = params.profile;
    }
    function logout() {
        isLogined.value = false;
        isUserInfo.value.userId = '';
        isUserInfo.value.userName = '';
        isUserInfo.value.userPwd = '';
        isUserInfo.value.emailId = '';
        isUserInfo.value.profile = '';
    }

    return { isLogined, isUserInfo, login, logout, changeProfile };
});
