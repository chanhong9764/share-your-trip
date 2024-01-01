<script setup>
import TripAPI from '@/api/trip';
import { useSocketStore } from '@/stores/useSocket';
import { storeToRefs } from 'pinia';
import { useUserInfoStore } from '../../stores/userLogin';
import { watch, ref } from 'vue';

const store = useUserInfoStore();
const socketStore = useSocketStore();

const { isUserInfo } = storeToRefs(store);
const { invitationList } = storeToRefs(socketStore);
const { setInvitations, getTripList } = socketStore;
const invitation = ref(null);

watch(
    () => invitationList.value,
    () => {
        invitation.value = invitationList.value[invitationList.length - 1];
    },
    {
        deep: true,
    }
);

const accepted = async (invi) => {
    const data = {
        participantId: invi.participantId,
    };
    await TripAPI.updateChatRoom(data).then((response) => {
        const data = invitationList.value.filter((element) => element.participantId != invitation.participantId);
        setInvitations(data);
    });
    await getTripList(isUserInfo.value.userId);
};

const declined = (invi) => {
    const data = {
        participantId: invi.participantId,
    };
    TripAPI.deleteChatRoom(data).then((response) => {
        const data = invitationList.value.filter((element) => element.participantId != invitation.participantId);
        setInvitations(data);
    });
};
</script>

<template>
    <div class="mt-3 toast-container" v-if="invitation != null">
        <div class="toast" id="toast">
            <div class="toast-header d-flex justify-content-center">
                {{ invitation.identifier.split(',')[0] }}님으로부터 여행 초대가 도착했습니다!
            </div>
            <div class="toast-body d-flex justify-content-center">
                <button class="accept" @click="accepted(invitation)">수락</button>
                <button class="decline" @click="declined(invitation)">거절</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.toast-container {
    width: 400px;
    height: 250px;
    overflow-y: scroll;
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
}
.toast-container::-webkit-scrollbar {
    display: none;
}
.toast-header {
    background-color: rgba(0, 0, 0, 0.65);
    border: none;
    color: white;
    font-weight: 700;
    border-top-right-radius: 30px;
    border-top-left-radius: 30px;
    width: 400px;
}
.accept {
    width: 150px;
    height: 30px;
    border-radius: 30px;
    border: none;
    margin-right: 10px;
    background-color: #3f97e8;
    color: white;
    font-weight: 700;
}

.decline {
    width: 150px;
    height: 30px;
    border-radius: 30px;
    border: none;
    margin-left: 10px;
    background-color: #ff5c5c;
    color: white;
    font-weight: 700;
}

.toast-body {
    background-color: rgba(0, 0, 0, 0.65);
    border-bottom-left-radius: 30px;
    border-bottom-right-radius: 30px;
    border: none;
    width: 400px;
}

.toast {
    width: 400px;
    border-radius: 50px;
    border: none;
}
</style>
