<script setup>
import { ref } from 'vue';
import { useUserInfoStore } from '@/stores/userLogin';
import { useSocketStore } from '../../stores/useSocket';
import { useRouter } from 'vue-router';
import UserAPI from '../../api/user';
import TripAPI from '@/api/trip';

const router = useRouter();
const socketStore = useSocketStore();
const store = useUserInfoStore();
const { connectSocket, initUserId, setInvitations, getTripList } = socketStore;
const { login, logout } = store;

const params = ref({
    userId: '',
    userPassword: '',
});

const goLogin = async () => {
    const data = await UserAPI.userLogin(params.value);
    if (data.data.result) {
        // data에 값이 있으면 로그인 함수 호출
        data.data.result.userPassword = params.value.userPassword;
        login(data.data.result);
        // 찬홍 추가
        initUserId(data.data.result.userId);
        connectSocket();
        getTripList(data.data.result.userId);
        TripAPI.getInvitations(data.data.result.userId).then((response) => {
            setInvitations(response.data.result);
        });
    } else {
        logout();
        alert('아이디 또는 비밀번호를 확인해주세요.');
    }
};

const moveJoin = () => {
    router.push('/join');
};
</script>

<template>
    <div>
        <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="loginModal-layer">
                        <p class="modalTitle">Share Your Trip</p>
                        <p class="modalTitle2">추천 여행지 및 코스를 알려드려요</p>
                        <p class="modalTitle3">여행지를 공유하세요</p>

                        <form>
                            <div class="loginForm_infoWrap">
                                <div class="labelInput_inputWrap loginForm_input">
                                    <label class="labelInput_label">
                                        <input
                                            name="id"
                                            type="text"
                                            class="login_input"
                                            placeholder="아이디"
                                            v-model="params.userId" />
                                    </label>
                                </div>
                            </div>
                            <div class="loginForm_infoWrap loginPwd">
                                <div class="labelInput_inputWrap loginForm_input">
                                    <label class="labelInput_label">
                                        <input
                                            name="password"
                                            type="password"
                                            class="login_input"
                                            autocomplete="on"
                                            minlength="1"
                                            maxlength="16"
                                            placeholder="비밀번호"
                                            v-model="params.userPassword"
                                            @keyup.enter="goLogin"
                                    /></label>
                                </div>
                            </div>
                            <div class="loginForm_optionArea">
                                <button
                                    class="loginForm_btnFindIdPassword__HAC0B"
                                    type="button"
                                    data-bs-toggle="modal"
                                    data-bs-target="#FindModal">
                                    아이디·비밀번호 찾기
                                </button>
                            </div>
                            <div class="row">
                                <div class="col text-center">
                                    <button
                                        id="btn-login"
                                        type="button"
                                        class="btn btn-dark"
                                        @click="goLogin"
                                        data-bs-dismiss="modal">
                                        로그인
                                    </button>
                                </div>
                            </div>
                        </form>
                        <div class="join_joinBtnArea">
                            <span class="join_text">처음이시라면</span
                            ><button
                                to="/join"
                                class="join_btnJoin"
                                role="button"
                                @click="moveJoin"
                                data-bs-dismiss="modal">
                                회원가입
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
a {
    text-decoration: none;
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
.join_text {
    -webkit-text-size-adjust: none;
    font-family: Pretendard, sans-serif;
    letter-spacing: -0.01em;
    overflow-wrap: break-word;
    text-underline-position: under;
    box-sizing: border-box;
    position: absolute;
    top: -1px;
    left: 50%;
    padding: 0 4px;
    background-color: #fff;
    font-size: 11px;
    line-height: 14px;
    color: #9fa4ab;
    transform: translate(-50%, -50%);
}
.join_joinBtnArea {
    -webkit-text-size-adjust: none;
    font-family: Pretendard, sans-serif;
    font-size: 12px;
    line-height: 1.4;
    letter-spacing: -0.01em;
    color: #121619;
    overflow-wrap: break-word;
    text-underline-position: under;
    box-sizing: border-box;
    position: relative;
    width: 100%;
    margin-top: 30px;
    padding-top: 22px;
    border-top: 1px solid #e7e8ea;
}
.socialLogin_btnText {
    height: 100%;
    color: #44474b;
    font-size: 14px;
    font-weight: 500;
    line-height: 18px;
}
.socialLogin {
    /* 소셜로그인 div */
    -webkit-text-size-adjust: none;
    font-family: Pretendard, sans-serif;
    font-size: 12px;
    line-height: 1.4;
    letter-spacing: -0.01em;
    color: #121619;
    overflow-wrap: break-word;
    text-underline-position: under;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    margin-top: 16px;
}
.loginForm_btnFindIdPassword__HAC0B {
    /*아이디 비밀번호 찾기 */
    margin: 0;
    padding: 0;
    border: 0;
    background: transparent;
    text-align: center;
    cursor: pointer;
    color: #44474b;
    line-height: 18px;
    letter-spacing: -0.0866px;
    font-size: 14px;
}

.loginForm_optionArea {
    /* 자동 로그인 , 아이디 비밀번호 찾기 div*/
    -webkit-text-size-adjust: none;
    font-family: Pretendard, sans-serif;
    line-height: 1.4;
    letter-spacing: -0.01em;
    color: #121619;
    overflow-wrap: break-word;
    text-underline-position: under;
    font-size: 100%;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 16px;
}

.modalTitle {
    /* display: flex;
    justify-content: center; */
    text-align: center;
    color: #000;
    text-align: center;
    font-size: 30px;
    font-weight: 700;
    line-height: normal;
}
.modalTitle2 {
    /* display: flex;
    justify-content: center; */
    margin-top: 40px;
    text-align: center;
    color: #000;
    text-align: center;
    font-size: 20px;
    font-weight: 700;
    line-height: normal;
}
.modalTitle3 {
    /* display: flex;
    justify-content: center; */
    text-align: center;
    color: #000;
    text-align: center;
    font-size: 25px;
    font-weight: 700;
    line-height: normal;
}

.loginModal-layer {
    position: relative;
    width: 416px;
    padding: 0px 48px 48px 40px;
    border-radius: 16px;
    background-color: #fff;
    box-shadow: 0 16px 25px rgba(0, 0, 0, 0.1), 0 8px 10px rgba(0, 0, 0, 0.04);
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
.loginForm_infoWrap .loginForm_input {
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
.loginPwd {
    margin-top: 10px;
}
.login_input {
    width: 100%;
    border: 1px solid #f4f4f5;
    background-color: #f4f4f5;
    font-size: 14px;
    outline: none;
}
form {
    margin-top: 32px;
    /* padding: 0px; */
    font-size: 100%;
}
.loginId {
    margin: 10px;
}

.modal-header {
    border: 0px;
}
.modal-content {
    width: 416px;
}
#btn-login {
    text-underline-position: under;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    cursor: pointer;
    appearance: none;
    border: 0;
    background: transparent;
    font: inherit;
    outline: none;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 46px;
    margin-top: 30px;
    border-radius: 4px;
    background-color: #121619;
    color: #fff;
    font-size: 16px;
    font-weight: 700;
    line-height: 20px;
}
</style>
