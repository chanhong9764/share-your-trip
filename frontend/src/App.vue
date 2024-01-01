<script setup>
import TripModal from './components/trip/TripModal.vue';
import UserLogin from './components/user/UserLogin.vue';
import TheHeader from './components/common/Header.vue';
import TheFooter from './components/common/Footer.vue';
import UserFindId from './components/user/UserFindId.vue';
import UserFindIdConfirm from './components/user/UserFindIdConfirm.vue';
import UserInfo from './components/user/UserInfo.vue';
</script>

<template>
    <div class="page-wrapper">
        <user-login></user-login>
        <user-find-id></user-find-id>
        <user-find-id-confirm></user-find-id-confirm>
        <user-info></user-info>
        <TripModal />
        <TheHeader />
        <!-- router view 설정 -->
        <router-view class="routerView" style="height: 100%"></router-view>
        <TheFooter />
    </div>
</template>

<style scoped>
.routerView {
    margin: 0px;
}
</style>
