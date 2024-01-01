<script setup>
import { ref, onUnmounted, watch, onMounted } from 'vue';
import SearchSVG from '../../assets/svg/Search.svg';
import SendChatSVG from '../../assets/svg/SendChat.svg';
import ChattingBox from './ChattingBox.vue';
import { useSocketStore } from '@/stores/useSocket';
import { useRoute } from 'vue-router';
import { useUserInfoStore } from '../../stores/userLogin';
import { storeToRefs } from 'pinia';
import EnterRoomEvent from '../common/EnterRoomEvent.vue';
import { Toast } from 'bootstrap';

const route = useRoute();

const userStore = useUserInfoStore();
const socketStore = useSocketStore();
const { isUserInfo } = storeToRefs(userStore);
const { send, leaveRoom } = socketStore;
const { enterUser } = storeToRefs(socketStore);
const inputValue = ref('');
const searchValue = ref('');
const chatRef = ref(null);
const enterUserId = ref('');

watch(
    () => inputValue.value,
    () => {
        inputValue.value = inputValue.value.replace(/\n/g, '');
        inputValue.value = inputValue.value.replace(/\r/g, '');
    }
);

watch(
    () => enterUser.value,
    () => {
        enterUserId.value = enterUser.value.userId;
        setTimeout(() => {
            let toastElList = [].slice.call(document.querySelectorAll('#enterroom'));
            if (toastElList.length > 0) {
                var toastList = new Toast(toastElList[toastElList.length - 1]);
                toastList.show();
                setTimeout(() => {
                    toastList.hide();
                }, 5000);
            }
        }, 500);
    }
);

const sendChat = () => {
    const userId = isUserInfo.value.userId;
    inputValue.value = inputValue.value.trim();
    if (inputValue.value != '' && userId) {
        const data = {
            userId: userId,
            message: inputValue.value,
            roomId: route.params.roomid,
            profile: isUserInfo.value.profile,
        };
        send(data);
    }
    inputValue.value = '';
};

onUnmounted(() => {
    leaveRoom();
});
</script>
<template>
    <div class="chat-wrapper g-0 d-felx flex-column">
        <div class="header d-flex align-items-center justify-content-center">
            <div class="notch">
                <div class="camera"></div>
            </div>
        </div>
        <EnterRoomEvent :enter-user="enterUserId" />
        <!-- <div class="search-wrapper container-fluid g-0">
            <input
                v-model="searchValue"
                @keyup.enter="search"
                class="search container-fluid mt-2"
                placeholder="검색할 키워드를 입력해주세요"
            />
            <SearchSVG class="search-icon" />
        </div> -->
        <ChattingBox />
        <div class="chatting-wrapper">
            <textarea
                v-model="inputValue"
                @keyup.enter="sendChat"
                class="chat-input"
                rows="5"
                placeholder="채팅을 입력해주세요"
            ></textarea>
            <SendChatSVG class="send-icon" @click="sendChat" />
        </div>
    </div>
</template>
<style scoped>
.chat-wrapper {
    min-height: 75vh;
    position: relative;
    border-radius: 30px;
    border: 2px solid black;
    margin: 5px;
}
.camera {
    background-color: #2a2a2a;
    width: 15px;
    height: 15px;
    border-radius: 50%;
    position: absolute;
    right: 30px;
    top: 3px;
}
.notch {
    background-color: #404040;
    width: 150px;
    height: 20px;
    border-radius: 30px;
    position: relative;
}
.chat-list {
    overflow-y: auto;
}
.header {
    border-bottom: 1.5px solid black;
    height: 80px;
    border-top-right-radius: 30px;
    border-top-left-radius: 30px;
}
.chatting-wrapper {
    margin-top: 15px;
    position: absolute;
    bottom: 0;
    height: 150px;
    width: 100%;
}
.title {
    font-size: 24px;
    margin: 0;
    font-weight: 700;
}

.search {
    padding: 10px 20px;
    border: 2px solid black;
    border-radius: 30px;
}

.search-icon {
    position: absolute;
    right: 35px;
    top: 20px;
}

.search-wrapper {
    position: relative;
    padding: 0 15px;
}

.chat-input {
    border: none;
    padding: 20px 30px;
    outline-style: none;
    resize: none;
    border-top: 1.5px solid black;
    width: 100%;
    border-bottom-right-radius: 30px;
    border-bottom-left-radius: 30px;
    height: 150px;
}

.send-icon {
    position: absolute;
    right: 30px;
    top: 50px;
}
.send-icon:hover {
    cursor: pointer;
}
</style>
