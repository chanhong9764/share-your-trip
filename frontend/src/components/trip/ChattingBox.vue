<script setup>
import { ref, watch, onMounted } from 'vue';
import { useUserInfoStore } from '../../stores/userLogin';
import { storeToRefs } from 'pinia';
import { useSocketStore } from '@/stores/useSocket';
import ChatLeftBox from './ChatLeftBox.vue';
import ChatRightBox from './ChatRightBox.vue';
import ChatEnterBox from './ChatEnterBox.vue';
import TripAPI from '@/api/trip';

const userStore = useUserInfoStore();
const socketStore = useSocketStore();
const { isUserInfo } = storeToRefs(userStore);
const { setChatting } = socketStore;
const { chatting, currentRoom, enterUser } = storeToRefs(socketStore);
const pgno = ref(2);
const pre_diffHeight = ref(0);
const bottom_flag = ref(true);
const component = ref(null);

watch(
    () => chatting.value,
    () => {
        if (bottom_flag.value) {
            setTimeout(() => {
                component.value.scrollTop = component.value.scrollHeight - component.value.clientHeight + 86;
            }, 200);
        }
    },
    {
        deep: true,
    }
);

watch(
    () => enterUser.value,
    () => {}
);

// const findWord = () => {
//     const word = '정인상';
//     const isFind = chatting.value.filter((element) => element.message == word);
//     console.log(isFind);
//     if (isFind.length > 0) {
//         handlerScroll(isFind[0].chattingId);
//     } else {
//         const data = {
//             roomId: currentRoom.value,
//             startId: chatting.value[chatting.value.length - 1].chattingId,
//             word: '',
//         };
//         TripAPI.getChattingListRange(data).then((response) => {
//             setChatting(response.data.result);
//             pgno.value = chatting.value.length / 10 + 1;
//             const chattingId = chatting.value.filter((element) => element.message == word);
//             console.log(chatting.value);
//             handlerScroll(chattingId);
//         });
//     }
// };

// const handlerScroll = (chattingId) => {
//     const find = document.getElementById(chattingId);
//     component.value.scrollTop = find.scrollTop;
// };

onMounted(() => {
    component.value = document.getElementById('chatroom');
});

const chat_on_scroll = (e) => {
    if (component.value.scrollTop == 0) {
        const data = {
            pgno: pgno.value,
            roomId: currentRoom.value,
        };
        TripAPI.getChattingList(data).then((response) => {
            setChatting(response.data.result);
            component.value.scrollTop = 20;
            pgno.value += 1;
        });
    }

    if (component.value.scrollTop + component.value.clientHeight == component.value.scrollHeight) {
        bottom_flag.value = true;
    }
    if (pre_diffHeight.value > component.value.scrollTop + component.value.clientHeight) {
        bottom_flag.value = false;
    }
    pre_diffHeight.value = component.value.scrollTop + component.value.clientHeight;
};
</script>

<template>
    <div id="chatroom" class="chat-list container-fluid d-flex flex-column" @scroll="chat_on_scroll">
        <div ref="chatBoxRef" v-for="chat in chatting" :key="chat.chattingId">
            <ChatLeftBox :id="chat.chattingId" v-if="chat.userId != isUserInfo.userId" :chatting-data="chat" />
            <ChatRightBox :id="chat.chattingId" v-else :chatting-data="chat" />
        </div>
    </div>
</template>

<style scoped>
.chat-list {
    height: 53vh !important;
    overflow-y: auto;
    -ms-overflow-style: none;
    scrollbar-width: none;
    -webkit-overflow-scrolling: touch;
}
.chat-list::-webkit-scrollbar {
    display: none;
}
</style>
