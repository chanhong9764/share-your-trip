<script setup>
import { ref, computed } from 'vue';
import EmailAPI from '@/api/email';
import UserAPI from '@/api/user';

const find_info = ref({
    // 아이디 찾기일때 사용할 객체
    user_name: '',
    user_id: '',
    user_email: '',
    email_check: '',
    change_pwd: '', // 바꿀 비번
    pwd_vaild: '', // 비번 확인
});

const resultId = ref('못바꿨지롱'); //찾은 아이디를 담는 변수

const handleBtn = computed(() => {
    if (change_btn.value == 1) {
        return '아이디 확인';
    } else if (change_btn.value == 2) {
        return '비밀번호 재설정';
    } else if (change_btn.value == 3) {
        return '로그인하러가기';
    } else if (change_btn.value == 4) {
        return '비밀번호 재설정';
    }
});

const findIdAPI = async () => {
    // 아이디 찾기 API
    const response = await UserAPI.findId(find_info.value.user_email);
    resultId.value = response.data.resdata;
};

const changePwdAPI = async () => {
    // 비번 변경 API
    await UserAPI.userModify(find_info.value);
};

const change_btn = ref(1);
const change = (num) => {
    change_btn.value = num;
};

const handleLogin = () => {
    // 아이디 찾기
    if (change_btn) {
    } else {
        // 패스워드 찾기
    }
};
const checkFunc = () => {
    if (!find_info.value.user_name) {
        alert('이름 입력해라');
    } else if (!find_info.value.user_email) {
        alert('이메일 입력해라');
    } else if (!find_info.value.email_check) {
        alert('인증번호 입력해라');
    }
};
const handleComp = (num) => {
    if (num == 1) {
        if (isEmailCheck.value) {
            findIdAPI();
            change_btn.value = 3;
            // 아이디 확인이 끝나면 모든 값을 초기화 해줘야돼 알겠냐
        } else {
            checkFunc();
        }
    } else if (num == 2) {
        if (isEmailCheck.value) {
            change_btn.value = 4;
        }
    } else if (num == 3) {
        change_btn.value = 1;
    } else {
        if (find_info.value.change_pwd == find_info.value.pwd_vaild) {
            changePwdAPI();
            change_btn.value = 1;
            // 아이디 확인이 끝나면 모든 값을 초기화 해줘야돼 알겠냐
        } else {
            checkFunc();
        }
    }
};
const emailNum = ref(0); // 이메일 인증번호를 저장할 변수
const emailBtn = ref(false);
const emailText = ref('인증');
const emailAuth = async () => {
    if (!find_info.value.user_email) {
        alert('이메일을 입력해 주세요.');
    } else {
        alert('인증번호가 전송되었습니다.');
        emailBtn.value = true;
        if (emailBtn.value) {
            emailText.value = '재전송';
        }
        const response = await EmailAPI.mailAuth(find_info.value.user_email);
        emailNum.value = response.data.resdata;
    }
};

const isEmailCheck = ref(false); // 이메일 인증이 완료 되었는지 확인하는 변수
const emailCheck = () => {
    if (find_info.value.email_check != 0 && emailNum.value == find_info.value.email_check) {
        alert('인증되었습니다.');
        isEmailCheck.value = true;
    } else if (find_info.value.email_check.length == 0) {
        alert('인증번호를 입력해주세요.');
        isEmailCheck.value = false;
    } else {
        alert('인증번호를 확인해 주세요.');
        isEmailCheck.value = false;
    }
};
</script>

<template>
    <div>
        <div class="modal fade" id="FindModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div id="modal-frame" class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="findModal-layer">
                        <div>
                            <p class="modalTitle">Share Your Trip</p>
                        </div>
                        <div class="find_findArea__HQ7YF mt-3">
                            <div class="find_findTabArea__R5BvJ">
                                <a
                                    class="find_tab"
                                    v-bind:class="{
                                        find_true: change_btn == 1 || change_btn == 3,
                                        find_false: change_btn == 2 || change_btn == 4,
                                    }"
                                    role="button"
                                    @click="change(1)"
                                    >아이디 찾기</a
                                ><a
                                    class="find_tab"
                                    v-bind:class="{
                                        find_true: change_btn == 2 || change_btn == 4,
                                        find_false: change_btn == 1 || change_btn == 3,
                                    }"
                                    role="button"
                                    @click="change(2)"
                                    >비밀번호 찾기</a
                                >
                            </div>

                            <div class="find_selectArea__p9R52 find_id__i7EGC"></div>
                            <a class="find_btnClose__dNdlV" role="button"></a>
                        </div>
                        <form class="findForm">
                            <template v-if="change_btn == 1 || change_btn == 2">
                                <div class="findForm_info">
                                    <div class="labelInput_inputWrap loginForm_input">
                                        <label class="labelInput_label">
                                            <input
                                                v-if="change_btn == 1"
                                                name="find-name"
                                                type="text"
                                                class="find_input"
                                                placeholder="이름"
                                                v-model="find_info.user_name"
                                            />
                                            <input
                                                v-else
                                                type="test"
                                                class="find_input"
                                                placeholder="아이디"
                                                v-model="find_info.user_id"
                                            />
                                        </label>
                                    </div>
                                </div>
                                <div class="findForm_info findEmail">
                                    <div class="labelInput_inputWrap loginForm_input">
                                        <label class="labelInput_label">
                                            <input
                                                name="find-email"
                                                class="find_input"
                                                placeholder="이메일"
                                                v-model="find_info.user_email"
                                        /></label>
                                        <button class="emailAuth" @click.prevent="emailAuth">{{ emailText }}</button>
                                    </div>
                                </div>
                                <div class="findForm_info findEmail">
                                    <div class="labelInput_inputWrap loginForm_input">
                                        <label class="labelInput_label">
                                            <input
                                                name="find-emailauth"
                                                type="text"
                                                class="find_input"
                                                placeholder="인증번호"
                                                v-model="find_info.email_check"
                                            />
                                        </label>
                                        <button class="emailAuth" @click.prevent="emailCheck">확인</button>
                                    </div>
                                </div>
                            </template>
                            <template v-if="change_btn == 3">
                                <p class="findIdText mt-3">찾은 아이디는 다음과 같습니다.</p>
                                <div class="findIdResult mt-4">{{ resultId }}</div>
                            </template>
                            <template v-if="change_btn == 4">
                                <div class="findForm_info">
                                    <div class="labelInput_inputWrap loginForm_input">
                                        <label class="labelInput_label">
                                            <input
                                                v-model="find_info.change_pwd"
                                                type="password"
                                                class="find_input"
                                                placeholder="변경할 비밀번호"
                                            />
                                        </label>
                                    </div>
                                </div>
                                <div class="findForm_info findEmail">
                                    <div class="labelInput_inputWrap loginForm_input">
                                        <label class="labelInput_label">
                                            <input
                                                type="password"
                                                class="find_input"
                                                placeholder="비밀번호 확인"
                                                v-model="find_info.pwd_vaild"
                                        /></label>
                                    </div>
                                </div>
                            </template>
                        </form>
                        <div v-if="change_btn != 3 && change_btn != 4" class="join_joinBtnArea">
                            <button class="join_btnJoin" role="button" @click.prevent="handleComp(change_btn)">
                                {{ handleBtn }}
                            </button>
                        </div>
                        <div v-else class="join_joinBtnArea">
                            <button
                                class="join_btnJoin"
                                role="button"
                                @click.prevent="handleComp(change_btn)"
                                data-bs-toggle="modal"
                                data-bs-target="#loginModal"
                            >
                                {{ handleBtn }}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.emailAuth {
    position: absolute;
    right: 20px;
    bottom: 7px;
    border-radius: 5px;
    border: none;
    background-color: #dde0e4;
    width: 60px;
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
.findEmail {
    padding-top: 20px;
    display: flex;
    justify-content: flex-end;
}
.findForm {
    padding-top: 30px;
}
.find_input {
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
