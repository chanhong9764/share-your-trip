<script setup>
import { ref } from 'vue';
import EmailAPI from '@/api/email';
import UserAPI from '@/api/user';
import { useRouter } from 'vue-router';

const router = useRouter();

const params = ref({
    // 회원가입하는 유저의 객체 정보를 저장할 변수
    userId: '',
    userName: '',
    userPassword: '',
    email: '',
    emailVaild: '',
});

const goJoin = async () => {
    // 회원가입로 함수
    if (idDuplicated.value === 1 || params.value.userId.length == 0) {
        alert('아이디를 확인하세요.');
    } else if (vaildPwd_text.value == '비밀번호가 일치하지 않습니다.' || params.value.userPassword.length == 0) {
        alert('비밀번호를 확인하세요.');
    } else if (!isEmailCheck.value) {
        alert('이메일을 확인하세요.');
    } else {
        const response = await UserAPI.userJoin(params.value);
        alert('환영합니다. 회원가입이 되었습니다.');
        router.push('/');
        // if (response.data.resdata === 1) {
        // }
    }
};
const isCheckId = ref(false); // db와 통신해서 중복된 아이디인지 체크하는 변수. 중복이면 true
const duplicated = ref(''); // 아이디 사용가능 여부를 알려주는 text
const idDuplicated = ref(0); // 아이디가 중복인지 확인하는 변수 / 1이면 중복, 알림창 띄우는 용도

const checkId = async () => {
    const response = await UserAPI.userCheckId(params.value.userId);
    isCheckId.value = response.data.resdata;
    if (!response.data.resdata) {
        duplicated.value = '사용 가능한 아이디입니다.';
        idDuplicated.value = 0;
    } else if (response.data.resdata) {
        duplicated.value = '이미 사용중인 아이디입니다.';
        idDuplicated.value = 1;
    }
};

const vaildPwd = ref('');
const vaildPwd_text = ref('');
const checkPwd = () => {
    if (vaildPwd.value == params.value.userPassword) {
        vaildPwd_text.value = '비밀번호가 일치합니다.';
    } else {
        vaildPwd_text.value = '비밀번호가 일치하지 않습니다.';
    }
};

const emailNum = ref(0); // 이메일 인증번호를 저장할 변수
const emailBtn = ref(false);
const emailText = ref('인증');
const emailAuth = async () => {
    if (!params.value.email) {
        alert('이메일을 입력해 주세요.');
    } else {
        alert('인증번호가 전송되었습니다.');
        emailBtn.value = true;
        if (emailBtn.value) {
            emailText.value = '재전송';
        }
        const response = await EmailAPI.mailAuth(params.value.email);

        emailNum.value = response.data.resdata;
        console.log(emailNum.value);
    }
};

const isEmailCheck = ref(false); // 이메일 인증이 완료 되었는지 확인하는 변수
const emailCheck = () => {
    if (emailNum.value == params.value.emailVaild) {
        alert('인증되었습니다.');
        isEmailCheck.value = true;
    } else {
        alert('인증번호를 확인해 주세요.');
    }
};
</script>

<template>
    <div class="join-root">
        <div class="join-title">
            <h3 class="join-title1">JOIN</h3>
            <h3>Share Your Trip</h3>
        </div>
        <hr />
        <form class="container g-0 d-flex justify-content-center">
            <div class="form-floating">
                <input type="text" class="form-control form_input" placeholder="" v-model="params.userName" />
                <label class="changebg" for="name">이름</label>
            </div>
            <div class="form-floating joinEmail">
                <input type="text" class="form-control form_input" placeholder="" v-model="params.email" />
                <label for="name">이메일</label>
                <button class="email_Auth" @click.prevent="emailAuth">{{ emailText }}</button>
            </div>
            <div class="form-floating emailAuth">
                <input type="text" class="form-control form_input" placeholder="" v-model="params.emailVaild" />
                <label for="name">인증번호 입력</label>
                <button class="email_Auth" @click.prevent="emailCheck">확인</button>
            </div>

            <div class="form-floating joinId">
                <input
                    type="text"
                    class="form-control form_input"
                    placeholder=""
                    v-model="params.userId"
                    @keyup="checkId" />
                <label for="name">아이디</label>
                <p
                    v-if="params.userId.length != 0"
                    :class="!isCheckId ? 'checkColorBlue' : 'checkColorRed'"
                    class="id_filter">
                    {{ duplicated }}
                </p>
                <p v-else></p>
            </div>

            <div class="form-floating joinPwd">
                <input
                    type="password"
                    class="form-control form_input"
                    placeholder=""
                    v-model="params.userPassword"
                    autocomplete="on" />
                <label for="name">비밀번호</label>
                <p class="id_filter"></p>
            </div>
            <div class="form-floating joinPwdCheck">
                <input
                    type="password"
                    class="form-control form_input"
                    placeholder=""
                    v-model="vaildPwd"
                    autocomplete="on"
                    @keyup="checkPwd" />
                <label for="name">비밀번호 재입력</label>
                <p
                    v-if="vaildPwd.length != 0"
                    :class="params.userPassword == vaildPwd ? 'checkColorBlue' : 'checkColorRed'"
                    class="id_filter">
                    {{ vaildPwd_text }}
                </p>
                <p v-else></p>
            </div>
            <div class="join_joinBtnArea row mt-4">
                <div class="col text-center">
                    <button id="join_btnJoin" type="button" class="btn btn-dark" @click="goJoin">회원가입</button>
                </div>
            </div>
        </form>
    </div>
</template>

<style scoped>
.checkColorBlue {
    color: blue;
}

.checkColorRed {
    color: red;
}

.email_Auth {
    position: absolute;
    bottom: 11px;
    right: 40px;
    border-radius: 5px;
    border: none;
    background-color: #dde0e4;
    width: 60px;
    height: 35px;
}

.changebg::after {
    background-color: red;
}
.joinEmail {
    position: relative;
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
p {
    margin-bottom: 10px;
}
.id_filter {
    font-size: 14px;
    padding-left: 20px;
    padding-top: 6px;
}
.join-root {
    padding-top: 56px;
    padding-bottom: 56px;
    width: 100%;
    flex-shrink: 0;
    background-color: #fff;
    display: flex;
    justify-content: center;
    flex-direction: column;
}
.join-title {
    margin: 0 auto;
    width: 544px;
    height: 64px;
    /* outline: dashed 1px black; */
}
hr {
    width: 544px;
    margin: 0 auto;
    margin-top: 40px;
    margin-bottom: 40px;
    border: 0;
    height: 4px;
    background-color: black;
    opacity: 1;
}

.loginForm_infoWrap {
    position: relative;
    width: 544px;
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
.joinEmail,
.joinId {
    margin-top: 32px;
}
.emailAuth {
    margin-top: 6px;
}
.form_input {
    width: 544px;
    height: 50px;
    background-color: #f4f4f5;
    padding: 5px 15px;
    border: 0px;
}
.email_input {
    width: 100%;
    border: 1px solid #f4f4f5;
    background-color: #f4f4f5;
    font-size: 14px;
    outline: none;
    padding-top: 5px;
}
form {
    margin-top: 16px;
    /* padding: 0px; */
    font-size: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

#btn-join {
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
