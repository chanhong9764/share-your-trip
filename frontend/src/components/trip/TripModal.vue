<script setup>
import UserAPI from '@/api/user';
import { ref, computed, watch } from 'vue';
import NoProfileSVG from '../../assets/svg/NoProfile.svg';
import NoProfileSizeSVG from '../../assets/svg/NoProfileSize.svg';
import { useSocketStore } from '@/stores/useSocket';
import { storeToRefs } from 'pinia';

const socketStore = useSocketStore();
const { userId } = storeToRefs(socketStore);
const { createRoom, getTripList } = socketStore;

const groupName = ref('');
const groupInfo = ref('');
const inputUserId = ref('');

const searchUser = ref([]);
const selectedUser = ref([]);

watch(
    () => inputUserId.value,
    () => {
        if (inputUserId.value != '') {
            UserAPI.searchUser(inputUserId.value).then((response) => {
                let data = response.data.result;
                if (data) {
                    searchUser.value = [...data];
                    console.log(data);
                } else {
                    searchUser.value = [];
                }
            });
        }
    }
);

const searchUsers = computed(() => {
    if (inputUserId.value === '') {
        return [];
    }

    let matches = 0;
    return searchUser.value.filter((user) => {
        if (user.userId.toLowerCase().includes(inputUserId.value.toLowerCase()) && matches < 10) {
            matches++;
            return user;
        }
    });
});

const removeUser = (userId) => {
    selectedUser.value = selectedUser.value.filter((element) => element.userId != userId);
};

const selectUser = (user) => {
    if (selectedUser.value.length < 4 && user.userId != userId.value) {
        let isValid = true;
        if (selectedUser.value.length > 0) {
            selectedUser.value.forEach((element) => {
                if (element.userId === user.userId) {
                    isValid = false;
                    return;
                }
            });
        }
        if (isValid) {
            selectedUser.value.push(user);
        }
    }

    inputUserId.value = '';
};

const isValidInput = computed(() => {
    if (groupName.value != '' && groupInfo.value != '' && selectedUser.value.length > 0) {
        return 'black';
    } else {
        return '#e9e9e9';
    }
});

const isValidInputColor = computed(() => {
    if (groupName.value != '' && groupInfo.value != '' && selectedUser.value.length > 0) {
        return false;
    } else {
        return true;
    }
});

const invite = async () => {
    const identifier = userId.value + ',' + selectedUser.value.map((element) => element.userId).join(',');
    const data = {
        identifier: identifier,
        roomName: groupName.value,
        content: groupInfo.value,
    };
    await createRoom(data);
    await getTripList(userId.value);
    init();
};

const init = () => {
    selectedUser.value = [];
    groupName.value = '';
    groupInfo.value = '';
    inputUserId.value = '';
};
</script>

<template>
    <div class="modal modal-lg fade" id="tripModal" tabindex="-1" aria-labelledby="tripModal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header pt-4 pb-4">
                    <p class="modal-title w-100 text-center ps-5">여행 그룹 만들기!</p>
                    <button
                        type="button"
                        class="btn-close pe-5"
                        data-bs-dismiss="modal"
                        @click="init"
                        aria-label="Close"
                    ></button>
                </div>
                <div class="modal-body px-5 mt-2">
                    <div class="input-wrapper">
                        <input
                            type="text"
                            class="auto-complete container"
                            v-model="inputUserId"
                            placeholder="초대할 유저의 아이디를 입력해주세요"
                        />
                        <ul
                            v-if="searchUsers.length > 0"
                            class="container p-0 list mt-1 w-full rounded bg-white border border-gray-300 py-2 space-y-1 absolute z-10"
                        >
                            <li
                                class="cursor-pointer hover:bg-gray-100 list-element"
                                v-for="user in searchUsers"
                                :key="user"
                                @click="selectUser(user)"
                            >
                                <img
                                    class="search-profile"
                                    v-if="user.profile != null"
                                    :src="'/api/images' + user.profile"
                                    alt="profile"
                                />
                                <NoProfileSVG v-else class="search-profile" />
                                <span class="search-user-text">{{ user.userId }}</span>
                            </li>
                        </ul>
                    </div>
                    <div class="mb-4 mt-4 d-flex justify-content-end">
                        <div
                            class="d-flex flex-column justify-content-center align-items-center select-user"
                            v-for="user in selectedUser"
                            :key="user.userId"
                            @click="removeUser(user.userId)"
                        >
                            <img
                                class="input-profile"
                                v-if="user.profile != null"
                                :src="'/api/images' + user.profile"
                                alt="profile"
                            />
                            <NoProfileSizeSVG v-else class="input-profile" />
                            <span class="text-center mt-2 user-id">{{ user.userId }}</span>
                        </div>
                    </div>
                    <div class="mb-4">
                        <p class="group-text">그룹명</p>
                        <input
                            class="container search-input"
                            placeholder="생성할 그룹명을 입력해주세요!"
                            v-model="groupName"
                        />
                    </div>
                    <div class="mb-2">
                        <p class="group-text">그룹 소개</p>
                        <textarea
                            class="container intro-input"
                            rows="7"
                            placeholder="그룹에 대해 소개해주세요!"
                            v-model="groupInfo"
                        ></textarea>
                    </div>
                    <div class="modal-footer justify-content-center">
                        <button
                            type="button"
                            class="btn invite-button"
                            :disabled="isValidInputColor"
                            data-bs-dismiss="modal"
                            @click="invite"
                        >
                            초대하기
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.input-profile {
    width: 52px;
    height: 50px;
    border-radius: 50%;
}

.user-id {
    font-weight: 600;
}

.select-user {
    width: 70px;
    margin-right: 5px;
    display: flex;
    flex-direction: column;
}
.auto-complete {
    background-color: #e9e9e9;
    border-radius: 30px;
    height: 56px;
    padding: 8px 20px;
    border: none;
    outline-style: none;
}
.list-element {
    border-bottom: 1px solid #e9e9e9;
    padding: 0 20px;
    margin: 5px 0;
}
.search-profile {
    width: 25px;
    height: 25px;
    border-radius: 50%;
}
.input-wrapper {
    position: relative;
}
.search-user-text {
    font-weight: 600;
    font-size: 15px;
    margin-left: 6px;
}
.list {
    position: absolute;
    top: 60px;
    list-style: none;
    max-height: 150px;
    overflow-y: scroll;
}

.selector {
    margin-top: 10px;
}
.profile {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin: 0 10px;
}
.invite-button {
    background-color: black;
    color: white;
    font-weight: 700;
    height: 56px;
    width: 40%;
    border-radius: 30px;
}
.modal-footer {
    border: none;
}
.modal-header {
    text-align: center !important;
}
.modal-title {
    font-size: 28px;
    font-weight: 700;
}
.group-text {
    font-size: 24px;
    font-weight: 700;
    padding-left: 10px;
}

.search-input {
    background-color: #e9e9e9;
    border: none;
    height: 56px;
    border-radius: 30px;
    padding: 10px 30px;
    outline-style: none;
}

.intro-input {
    background-color: #e9e9e9;
    border: none;
    border-radius: 30px;
    padding: 20px 30px;
    outline-style: none;
    resize: none;
}
</style>
