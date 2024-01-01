<script setup>
import CloseTripSVG from '../../assets/svg/CloseTrip.svg';
import NoProfileSizeSVG from '../../assets/svg/NoProfileSize.svg';
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import TripAPI from '@/api/trip';
import { useUserInfoStore } from '../../stores/userLogin';
import { useSocketStore } from '@/stores/useSocket';
import { storeToRefs } from 'pinia';

const socketStore = useSocketStore();
const store = useUserInfoStore();
const { enterRoom, setChatting, getTripList, deleteTripList } = socketStore;
const { isUserInfo } = storeToRefs(store);
const { tripList } = storeToRefs(socketStore);
const realList = ref([]);
const router = useRouter();

onMounted(() => {
    const userId = isUserInfo.value.userId;
    if (userId) {
        getTripList(userId);
    }
});

watch(
    () => tripList.value,
    () => {
        realList.value = tripList.value;
    },
    {
        deep: true,
    }
);

const removeTrip = (roomId) => {
    const data = {
        userId: isUserInfo.value.userId,
        roomId: roomId,
    };
    deleteTripList(data);
};

const routerRoom = (trip) => {
    const data = {
        userId: isUserInfo.value.userId,
        roomId: trip.roomId,
        roomName: trip.roomName,
    };

    const getData = {
        roomId: trip.roomId,
        pgno: 1,
    };
    TripAPI.getChattingList(getData).then((response) => {
        setChatting(response.data.result);
    });
    enterRoom(data);
    router.push({ name: 'trip-room', params: { roomid: trip.roomId } });
};
</script>

<template>
    <div class="container-fluid g-0 trip-wrapper">
        <h3 class="title">채팅방 리스트</h3>
        <div class="container d-flex justify-content-center">
            <button class="trip-button" data-bs-toggle="modal" data-bs-target="#tripModal">여행 그룹 만들기</button>
        </div>
        <div class="container trip-list d-flex flex-column justify-content-center align-items-center">
            <span v-if="realList.length <= 0" class="no-list">참여한 채팅방이 존재하지 않습니다!</span>
            <div class="container list-wrapper" v-for="trip in realList" :key="trip.roomId">
                <button
                    class="container d-flex trip-box align-items-center justify-content-between"
                    @click="routerRoom(trip)"
                >
                    <div class="d-flex flex-column">
                        <p class="subject">
                            {{ trip.roomName }}
                        </p>
                        <span class="content">
                            {{ trip.content }}
                        </span>
                    </div>
                    <div class="image-box me-5">
                        <template v-for="(profile, index) in trip.profiles" :key="index">
                            <img v-if="profile != null" class="image" :src="'/api/images' + profile" alt="profile" />
                            <NoProfileSizeSVG v-else class="input-profile image" />
                        </template>
                    </div>
                </button>
                <CloseTripSVG class="close-button" @click="removeTrip(trip.roomId)" />
            </div>
        </div>
    </div>
</template>

<style scoped>
.list-wrapper {
    position: relative;
}
.image {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
}

.close-button {
    position: absolute;
    top: 20px;
    right: 30px;
}
.close-button:hover {
    cursor: pointer;
}
.subject {
    font-size: 24px;
    font-weight: 700;
    padding: 0px;
    margin: 0;
    text-align: left;
}

.content {
    font-size: 20px;
    margin-top: 5px;
    font-weight: 500;
    text-align: left;
}
.trip-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.trip-box {
    background-color: #ebebeb;
    border-radius: 30px;
    padding: 30px;
    margin-bottom: 40px;
    border: none;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.no-list {
    font-size: 18px;
    font-weight: 700;
}

.title {
    text-align: center;
    padding: 50px 0;
    margin-bottom: 20px;
    font-size: 26px;
    font-weight: 700;
    border-bottom: 1px solid #e9e9e9;
}

.trip-button {
    width: 350px;
    height: 50px;
    color: white;
    background-color: black;
    border-radius: 30px;
    border: none;
    margin-bottom: 10px;
    font-weight: 600;
    font-size: 18px;
    margin-top: 30px;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.trip-list {
    min-height: 700px;
    border-radius: 30px;
    padding: 30px;
    margin-top: 20px;
    position: relative;
}

@media (max-width: 1400px) {
    .trip-box {
        flex-direction: column;
        align-items: flex-start !important;
    }
    .image-box {
        margin-top: 10px;
    }
}
</style>
