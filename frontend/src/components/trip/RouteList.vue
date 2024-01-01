<script setup>
import { ref, onMounted, watch } from 'vue';
import SearchSVG from '../../assets/svg/Search.svg';
import DeleteTripSVG from '../../assets/svg/DeleteTrip.svg';
import CloseTrip from '../../assets/svg/CloseTrip.svg';
import { VueDraggableNext } from 'vue-draggable-next';
import { useTripSearchStore } from '@/stores/tripInfo';
import { useSocketStore } from '@/stores/useSocket';
import { storeToRefs } from 'pinia';
import TripAPI from '../../api/trip';

const store = useTripSearchStore();
const socketStore = useSocketStore();
const { search } = storeToRefs(store);
const { selectedTrip } = storeToRefs(socketStore);
const { removeTrip, findRoad, sequenceUpdateRequest } = socketStore;
const component = ref(null);
const pre_diffHeight = ref(0);
const bottom_flag = ref(true);

const searchInput = ref('');

watch(
    () => selectedTrip.value,
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

const updateSequense = () => {
    for (let i = 0; i < selectedTrip.value.length; i++) {
        selectedTrip.value[i].sequense = i;
    }
    sequenceUpdateRequest(selectedTrip.value);
};

const addKeyword = () => {
    search.value = searchInput.value;
};

onMounted(() => {
    component.value = document.getElementById('scroll-list');
});

const trip_on_scroll = (e) => {
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
    <div class="chat-wrapper g-0 container d-felx flex-column">
        <div class="title-wrapper d-flex align-items-center justify-content-center">
            <p class="title">여행 경로</p>
        </div>
        <div class="search-wrapper container-fluid g-0">
            <input
                v-model="searchInput"
                class="search container-fluid mt-2"
                placeholder="검색할 경로를 입력해주세요."
                @keyup.enter="addKeyword"
            />
            <SearchSVG class="search-icon" @click="addKeyword" />
        </div>
        <div class="container-fluid d-flex flex-column g-0 p-2">
            <VueDraggableNext
                class="seleted-list"
                id="scroll-list"
                :list="selectedTrip"
                @change="updateSequense"
                @scroll="trip_on_scroll"
            >
                <div class="seleted-box" v-for="item in selectedTrip" :key="item.trip_info_id">
                    <a class="trip-link" :href="item.place_url" target="_blank">
                        <div class="selected-link">
                            <div>
                                <span class="seleted-title">
                                    {{ item.place_name }}
                                </span>
                                <span class="seleted-category">
                                    {{ item.category }}
                                </span>
                            </div>
                            <p class="road-addr">
                                {{ item.road_address_name }}
                            </p>
                            <p class="phone">
                                {{ item.phone }}
                            </p>
                        </div>
                    </a>
                    <div class="order">
                        {{
                            item.sequense == 0
                                ? '출발지'
                                : item.sequense == selectedTrip.length - 1
                                ? '도착지'
                                : '경유지'
                        }}
                    </div>
                    <DeleteTripSVG class="close-button" @click.stop="removeTrip(item)" />
                </div>
                <div>
                    <button v-if="selectedTrip.length >= 2" class="search-road container-fluid" @click="findRoad">
                        경로 찾기
                    </button>
                </div>
            </VueDraggableNext>
        </div>
    </div>
</template>
<style scoped>
.chat-wrapper {
    min-height: 75vh;
    margin: 5px 5px 5px 5px;
    position: relative;
    border: 2px solid black;
    border-radius: 15px;
    margin-left: 20px;
}

.search-road {
    height: 50px;
    background: white;
    border-radius: 30px;
    font-weight: 700;
}
.trip-link {
    text-decoration: none;
}

.order {
    position: absolute;
    top: -5px;
    left: -10px;
    width: 50px;
    height: 30px;
    background-color: #dfdede;
    color: black;
    font-weight: 700;
    display: flex;
    justify-content: center;
    align-content: center;
    line-height: 30px;
    border-radius: 10px;
    font-size: 12px;
}

.road-addr {
    margin-top: 10px;
    margin-bottom: 0px;
}
.phone {
    margin-top: 5px;
}
.selected-link {
    text-decoration: none;
    color: black;
}
.seleted-box {
    color: black;
    border: 1px solid rgba(0, 0, 0, 0.2);
    border-radius: 20px;
    padding: 20px;
    padding-top: 30px;
    margin-bottom: 10px;
    position: relative;
    width: 95%;
    overflow: visible;
    margin: 10px;
}

.seleted-title {
    font-size: 22px;
    font-weight: 700;
}
.seleted-category {
    font-size: 12px;
}
.close-button {
    position: absolute;
    right: 15px;
    top: 15px;
}
.close-button:hover {
    cursor: pointer;
}

.seleted-list {
    height: 60vh;
    overflow-y: auto;
    position: relative;
    -ms-overflow-style: none;
    scrollbar-width: none;
    -webkit-overflow-scrolling: touch;
    margin-bottom: 15px;
}
.seleted-list::-webkit-scrollbar {
    display: none;
}
.title-wrapper {
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
    height: 80px;
}
.chatting-wrapper {
    padding: 0 15px;
    margin-top: 15px;
    position: absolute;
    bottom: 0;
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

.search-icon:hover {
    cursor: pointer;
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
    border: 1px solid rgba(0, 0, 0, 0.2);
    border-radius: 15px;
}

.send-icon {
    position: absolute;
    right: 50px;
    top: 50px;
}
.send-icon:hover {
    cursor: pointer;
}
</style>
