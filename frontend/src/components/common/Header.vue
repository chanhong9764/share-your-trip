<script setup>
import UserSVG from '../../assets/svg/User.svg';
import AlarmSVG from '../../assets/svg/Alarm.svg';
import { useUserInfoStore } from '../../stores/userLogin';
import { useSocketStore } from '@/stores/useSocket';
import { storeToRefs } from 'pinia';
import InvitationAlarm from './InvitationAlarm.vue';
import InvitationEvent from './InvitationEvent.vue';
import { Toast } from 'bootstrap';
import { ref, watch } from 'vue';

const store = useUserInfoStore();
const socketStore = useSocketStore();
const { isLogined } = storeToRefs(store);
const { invitationList } = storeToRefs(socketStore);
const isOpen = ref(false);

const handleAlarm = () => {
    var toastElList = [].slice.call(document.querySelectorAll('#toast'));
    var toastList = toastElList.map(function (toastEl) {
        return new Toast(toastEl);
    });
    if (isOpen.value) {
        toastList.forEach((toast) => toast.hide());
        isOpen.value = false;
    } else {
        toastList.forEach((toast) => toast.hide());
        toastList.forEach((toast) => toast.show());
        isOpen.value = true;
    }
};
</script>

<template>
    <nav class="navbar navbar-expand-lg navbar-light" id="header">
        <div class="container-fluid">
            <router-link to="/" class="nav-link">
                <img id="logo" src="../../assets/img/logo.jpg" alt="logo" />
            </router-link>
            <button
                class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent"
                aria-expanded="false"
                aria-label="Toggle navigation"
                @click="onClickToggle"
            >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link to="/board" class="nav-link"><span class="link">여행지 공유</span></router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/trip" class="nav-link"><span class="link">여행 일정</span></router-link>
                    </li>
                </ul>
                <template v-if="isLogined">
                    <div class="d-flex alarm">
                        <div class="alarm-box">
                            <AlarmSVG class="icon" @click="handleAlarm" />
                            <div class="count">
                                <span class="count-text">{{ invitationList.length }}</span>
                            </div>
                        </div>
                        <InvitationEvent class="invitaiton" />
                        <InvitationAlarm class="invitation" />
                    </div>
                    <div class="d-flex" data-bs-toggle="modal" data-bs-target="#userInfoModal">
                        <UserSVG class="icon" />
                    </div>
                </template>
                <div v-else class="d-flex" data-bs-toggle="modal" data-bs-target="#loginModal">
                    <UserSVG class="icon" />
                </div>
            </div>
        </div>
    </nav>
</template>

<style scoped>
#header {
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.alarm-box {
    position: relative;
}
.alarm {
    position: relative;
    min-width: 450px;
}

@media (min-width: 992px) {
    .alarm {
        justify-content: flex-end;
    }
    .invitation {
        position: absolute;
        right: 30px;
        top: 40px;
    }
}
@media (max-width: 991px) {
    .invitation {
        position: absolute;
        left: 20px;
        right: 0px;
        top: 30px;
    }
}

.count {
    position: absolute;
    right: 20px;
    top: -10px;
    background-color: red;
    width: 30px;
    text-align: center;
    border-radius: 30px;
}

.count-text {
    color: white;
    font-size: 18px;
    font-weight: 700;
}

#logo {
    width: 190px;
    height: 95px;
    margin-left: 30px;
    margin-right: 30px;
}

.icon {
    margin-right: 30px;
    margin-bottom: 10px;
}

.icon:hover {
    cursor: pointer;
}

.link {
    font-style: normal;
    font-size: 20px;
    font-weight: 700;
    margin-right: 30px;
}

.navbar-toggler {
    margin-right: 20px;
}
.navbar-toggler:focus {
    box-shadow: none;
}
</style>
