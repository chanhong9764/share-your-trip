<script setup></script>

<template>
    <div id="footer" class="container-fluid row footer g-0">
        <router-link to="/" class="pt-3 pb-3 mx-auto col-xxl-1 col-12 d-flex align-items-center">
            <img id="logo" src="../../assets/img/logo.jpg" alt="logo" />
        </router-link>
        <div class="mt-3 ps-4 col-xxl-4 col-12">
            <p class="content mb-2">Share Your Trip</p>
            <p class="content mb-2">상호 : Share Your Trip | 공동대표 : 정인상, 박찬홍</p>
            <p class="content mb-2">사업자등록번호 : 010-97-64-9784 | 이메일 : trip@google.com</p>
            <p class="content mb-2">주소 : 대전 유성구 동서대로 98-39 (삼성화재연수원)</p>
            <p class="copyright mb-2">Copyright ⓒ Share Your Trip All rights reserved.</p>
        </div>
        <div class="mt-3 mb-4 ps-4 col-xxl-6 col-12">
            <p class="content mb-2">이용 약관</p>
            <p class="privacy mb-2">개인정보처리방침</p>
        </div>
    </div>
</template>

<style scoped>
#logo {
    width: 190px;
    height: 95px;
}

.footer {
    background-color: #e9e9e9;
}

.content {
    font-weight: 500;
}
.copyright {
    font-weight: 700;
}
.privacy {
    font-weight: 700;
}
</style>
