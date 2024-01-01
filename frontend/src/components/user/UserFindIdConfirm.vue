<script setup></script>

<template>
    <div>
        <div class="modal fade" id="FindIdModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div id="modal-frame" class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="loginModal-layer">
                        <div>
                            <p class="modalTitle">Share Your Trip</p>
                        </div>
                        <div class="find_findArea__HQ7YF mt-3">
                            <div class="find_findTabArea__R5BvJ">
                                <a class="find_tab find_id" role="button">아이디 찾기</a
                                ><a
                                    class="find_tab find_pwd"
                                    role="button"
                                    data-bs-toggle="modal"
                                    data-bs-target="#FindPasswordModal"
                                    >비밀번호 찾기</a
                                >
                            </div>
                            <div class="find_selectArea__p9R52 find_id__i7EGC"></div>
                            <a class="find_btnClose__dNdlV" role="button"></a>
                        </div>
                        <form class="findForm">
                            <div class="findForm_info">
                                <div class="labelInput_inputWrap loginForm_input">
                                    <label class="labelInput_label">
                                        <input name="find-id" type="text" class="find_input" placeholder="이름" />
                                    </label>
                                </div>
                            </div>
                            <div class="findForm_info findEmail">
                                <div class="labelInput_inputWrap loginForm_input">
                                    <label class="labelInput_label">
                                        <input name="find-email" class="find_input" placeholder="이메일"
                                    /></label>
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
                                        />
                                    </label>
                                </div>
                            </div>
                            <div class="join_joinBtnArea">
                                <button class="join_btnJoin" role="button" data-bs-dismiss="modal">아이디 확인</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
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
.loginModal-layer {
    position: relative;
    width: 100%;
    padding: 48px 48px 40px;
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
.find_id {
    border-bottom: 2px solid;
}
.find_pwd {
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
