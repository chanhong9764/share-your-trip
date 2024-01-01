<script setup>
import UserSVG from '../../assets/svg/User.svg';
import { useUserInfoStore } from '@/stores/userLogin';
import { useSocketStore } from '@/stores/useSocket';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import UserAPI from '@/api/user';

const router = useRouter();
const store = useUserInfoStore();
const socketStore = useSocketStore();
const { logout, changeProfile } = store;
const { isUserInfo } = storeToRefs(store);
const { initTripList } = socketStore;

const pwdBtn = ref(false);
const btnChange = () => {
    if (pwdBtn.value) {
        setTimeout(() => {
            pwdBtn.value = false;
        }, 1000);
    } else {
        pwdBtn.value = true;
    }
};

const handleImages = (e) => {
    const image = e.target.files[0];
    if (image != null) {
        const data = new FormData();
        data.append('images', image);
        data.append('userId', isUserInfo.value.userId);
        UserAPI.changeProfile(data).then((response) => {
            changeProfile(response.data.result);
        });
    }
};

const initInfo = () => {
    logout();
    initTripList();
    router.push({ name: 'main' });
};
</script>

<template>
    <div>
        <div class="modal fade" id="userInfoModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div id="modal-frame" class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="findModal-layer">
                        <div>
                            <p class="modalTitle">Share Your Trip</p>
                        </div>
                        <!-- pwdBtn이 false면 보여줄 회원정보 컴포넌트 -->
                        <template v-if="!pwdBtn">
                            <div class="userInfoTitle mt-4">회원 정보</div>
                            <div class="userInfo d-flex flex-column">
                                <label for="file">
                                    <UserSVG
                                        v-if="isUserInfo.profile == '' || isUserInfo.profile == null"
                                        class="icon mt-4"
                                    ></UserSVG>
                                    <img
                                        class="profile"
                                        v-else
                                        :src="'/api/images' + isUserInfo.profile"
                                        alt="profile"
                                    />
                                </label>

                                <div class="userInfo pt-3">
                                    <label class="input-box" for="file">
                                        프로필 사진 변경
                                        <input type="file" id="file" @change="handleImages" />
                                    </label>
                                </div>

                                <div class="id_email container">
                                    <p class="userInfo">ID : {{ isUserInfo.userId }}</p>
                                    <p class="userInfo">Email : {{ isUserInfo.emailId }}</p>
                                </div>
                            </div>

                            <div class="info mt-4">
                                <button class="infoBtn" role="button" @click="btnChange">비밀번호 변경</button>
                            </div>
                            <div class="info">
                                <button class="infoBtn" role="button" data-bs-dismiss="modal" @click="initInfo">
                                    로그아웃
                                </button>
                            </div>
                        </template>
                        <template v-else>
                            <div class="userInfoTitle mt-4">비밀번호 변경</div>
                            <div class="findForm_info mt-4">
                                <div class="labelInput_inputWrap loginForm_input">
                                    <label class="labelInput_label">
                                        <input type="password" class="changePwd" placeholder="현재 비밀번호" />
                                    </label>
                                </div>
                            </div>

                            <div class="findForm_info mt-3">
                                <div class="labelInput_inputWrap loginForm_input">
                                    <label class="labelInput_label">
                                        <input type="password" class="changePwd" placeholder="변경할 비밀번호" />
                                    </label>
                                </div>
                            </div>
                            <div class="findForm_info mt-3">
                                <div class="labelInput_inputWrap loginForm_input">
                                    <label class="labelInput_label">
                                        <input class="changePwd" type="password" placeholder="비밀번호 확인"
                                    /></label>
                                </div>
                            </div>
                            <div class="join_joinBtnArea">
                                <button class="join_btnJoin" role="button" @click="btnChange" data-bs-dismiss="modal">
                                    비밀번호 재설정
                                </button>
                            </div>
                        </template>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.id_email {
    padding-top: 40px;
    /* font-size: 16px;
    font-weight: 700;
    line-height: 20px; */
}
.profile {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-top: 20px;
}
.userInfo {
    justify-content: center;
    align-items: center;
    font-size: 16px;
    font-weight: 700;
    line-height: 20px;
}
.userInfoTitle {
    text-align: center;
    color: #000;
    text-align: center;
    font-size: 26px;
    font-weight: 0;
    line-height: normal;
    padding: 10px 0px 16px 0px;
    border-bottom: 2px solid;
}
.emailAuth {
    position: absolute;
    right: 20px;
    bottom: 7px;
    border-radius: 5px;
    border: none;
    background-color: #dde0e4;
    width: 50px;
    height: 30px;
}
.findIdResult {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f4f4f5;
    width: 100%;
    height: 80px;
    font-size: 30px;
    border-radius: 16px;
}
.findIdText {
    text-align: center;
    font-size: 16px;
}

input[type='file'] {
    width: 0;
    height: 0;
}
.labelInput_inputWrap .labelInput_label {
    position: relative;
    vertical-align: baseline;
    display: flex;
    flex: 1 0;
    height: 100%;
    min-width: 0;
    border: 0;
    padding: 0;
    margin: 0;
    background-color: #fff;
}
.findForm {
    padding-top: 30px;
}
.changePwd {
    width: 100%;
    border: 1px solid #f4f4f5;
    background-color: #f4f4f5;
    font-size: 14px;
    outline: none;
}
.findForm_info .loginForm_input {
    position: relative;
    width: 100%;
    height: 46px;
    padding: 7px 16px;
    border: 1px solid #f4f4f5;
    border-radius: 4px;
    background-color: #f4f4f5;
    color: #25282b;
    font-size: 14px;
    font-weight: 500;
    line-height: 18px;
    outline: none;
}
.findModal-layer {
    position: relative;
    width: 100%;
    padding: 0px 48px 40px;
    border-radius: 16px;
    background-color: #fff;
    box-shadow: 0 16px 25px rgba(0, 0, 0, 0.1), 0 8px 10px rgba(0, 0, 0, 0.04);
}
.modal-header {
    border-bottom: 0px;
}
.modalTitle {
    text-align: center;
    color: #000;
    text-align: center;
    font-size: 30px;
    font-weight: 700;
    line-height: normal;
}

.find_findTabArea__R5BvJ {
    display: flex;
    /* border-bottom: 2px solid #e7e8ea; */
}
.find_true {
    border-bottom: 2px solid;
}
.find_false {
    border-bottom: 2px solid #e7e8ea;
}
.find_findTabArea__R5BvJ .find_tab .find_id {
    color: #121619;
    font-size: 16px;
    font-weight: 700;
    line-height: 20px;
    -webkit-text-size-adjust: none;
    /* font-size: 12px; */
    line-height: 1.4;
    letter-spacing: -0.01em;
    color: #121619;
    overflow-wrap: break-word;
    text-underline-position: under;
    box-sizing: border-box;
    display: flex;
    border-bottom: 2px solid #e7e8ea;
}
.find_tab {
    -webkit-text-size-adjust: none;
    font-family: Pretendard, sans-serif;
    letter-spacing: -0.01em;
    overflow-wrap: break-word;
    text-underline-position: under;
    box-sizing: border-box;
    text-decoration: none;
    cursor: pointer;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 50%;
    height: 52px;
    text-align: center;
    color: #121619;
    font-size: 16px;
    font-weight: 700;
    line-height: 20px;
}

.modal-content {
    width: 416px;
}

.info {
    width: 100%;
    padding-top: 8px;
}

.infoBtn {
    -webkit-text-size-adjust: none;
    /* font-family: Pretendard, sans-serif; */
    letter-spacing: -0.01em;
    overflow-wrap: break-word;
    text-underline-position: under;
    box-sizing: border-box;
    text-decoration: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 46px;
    border: 1px solid #25282b;
    border-radius: 4px;
    background-color: #fff;
    color: #25282b;
    font-size: 16px;
    font-weight: 700;
    line-height: 20px;
}
.join_joinBtnArea {
    width: 100%;
    margin-top: 30px;
    padding-top: 22px;
}

.join_btnJoin {
    -webkit-text-size-adjust: none;
    font-family: Pretendard, sans-serif;
    letter-spacing: -0.01em;
    overflow-wrap: break-word;
    text-underline-position: under;
    box-sizing: border-box;
    text-decoration: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 46px;
    border: 1px solid #25282b;
    border-radius: 4px;
    background-color: #fff;
    color: #25282b;
    font-size: 16px;
    font-weight: 700;
    line-height: 20px;
}
</style>
