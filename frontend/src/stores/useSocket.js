import { ref } from 'vue';
import { defineStore } from 'pinia';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';
import { Toast } from 'bootstrap';
import TripAPI from '@/api/trip';

export const useSocketStore = defineStore('socket', () => {
    const stompClient = ref(null);
    const userId = ref('');
    // 초대장 리스트
    const invitationList = ref([]);
    // 채팅방 입장한 유저 정보
    const enterUser = ref('');
    // 채팅방 채팅 정보
    const chatting = ref([]);
    // 현재 입장한 방의 roomId
    const currentRoom = ref(-1);
    const currentRoomName = ref('');

    // 선택한 여행지
    const selectedTrip = ref([]);

    // subscribe
    const enterRoomRef = ref(null);
    const chatRef = ref(null);
    const userRef = ref(null);
    const updateRef = ref(null);
    const seletedEventRef = ref(null);
    const deleteEventRef = ref(null);
    const updateSelected = ref(null);

    const tripList = ref([]);
    const findTrip = ref([]);

    function findRoad() {
        let temp = [];
        selectedTrip.value.forEach((data) => {
            temp.push(data);
        });
        findTrip.value = temp;
    }

    const getTripList = (userId) => {
        TripAPI.getTripList(userId).then((response) => {
            tripList.value = response.data.result;
        });
    };

    const deleteTripList = (info) => {
        const data = {
            userId: info.userId,
            roomId: info.roomId,
        };
        TripAPI.deleteChattingRoom(data).then((response) => {
            tripList.value = tripList.value.filter((element) => element.roomId != info.roomId);
        });
    };

    const initTripList = () => {
        tripList.value = [];
    };

    const initUserId = (data) => {
        userId.value = data;
    };

    const setChatting = (data) => {
        chatting.value = [...data, ...chatting.value];
    };

    const send = (data) => {
        if (stompClient.value && stompClient.value.connected) {
            stompClient.value.send('/pub/chat/message', JSON.stringify(data), {});
        }
    };

    const createRoom = (data) => {
        if (stompClient.value && stompClient.value.connected) {
            stompClient.value.send('/pub/createRoom', JSON.stringify(data), {});
        }
    };

    const setInvitations = (data) => {
        invitationList.value = data;
    };

    const sequenceUpdateRequest = (data) => {
        if (stompClient.value && stompClient.value.connected) {
            stompClient.value.send('/pub/updateSequence', JSON.stringify(data), {});
        }
    };

    const enterRoom = (data) => {
        currentRoomName.value = data.roomName;
        if (stompClient.value && stompClient.value.connected) {
            enterRoomRef.value = stompClient.value.subscribe('/sub/trip/' + data.roomId, (response) => {
                const data = JSON.parse(response.body);
                if (data.userId != userId.value) {
                    enterUser.value = data;
                }
            });
            // 유저 입장 리스너
            stompClient.value.send('/pub/enterRoom', JSON.stringify(data), {});

            chatRef.value = stompClient.value.subscribe('/sub/chat/' + data.roomId, (response) => {
                const data = JSON.parse(response.body);
                chatting.value.push(data);
            });
            // 여행지 선택 리스너
            seletedEventRef.value = stompClient.value.subscribe('/sub/select/' + data.roomId, (response) => {
                const data = JSON.parse(response.body);
                selectedTrip.value.push(data);
            });

            // 여행지 제거 리스너
            deleteEventRef.value = stompClient.value.subscribe('/sub/remove/' + data.roomId, (response) => {
                const data = JSON.parse(response.body);
                selectedTrip.value = selectedTrip.value.filter((element) => element.trip_info_id != data.trip_info_id);
                for (let i = 0; i < selectedTrip.value.length; i++) {
                    selectedTrip.value[i].sequense = i;
                }
                sequenceUpdateRequest(selectedTrip.value);
            });

            // 여행지 순서 변화 리스너
            updateSelected.value = stompClient.value.subscribe('/sub/sequenceUpdate/' + data.roomId, (response) => {
                const data = JSON.parse(response.body);
                selectedTrip.value = data;
            });

            currentRoom.value = data.roomId;

            // 현재 그룹에 저장된 여행지 리스트 Load
            TripAPI.getTrip(currentRoom.value).then((response) => {
                const data = response.data.result;
                selectedTrip.value = data;
            });
        }
    };

    const updateTrip = (data) => {
        if (stompClient.value && stompClient.value.connected) {
            const requestData = {
                y: data.y,
                x: data.x,
                road_address_name: data.road_address_name,
                sequense: selectedTrip.value.length,
                category: data.category_group_name,
                roomId: currentRoom.value,
                place_name: data.place_name,
                place_url: data.place_url,
                phone: data.phone,
            };

            stompClient.value.send('/pub/selectTrip', JSON.stringify(requestData), {});
        }
    };

    const removeTrip = (data) => {
        if (stompClient.value && stompClient.value.connected) {
            stompClient.value.send('/pub/deleteTrip', JSON.stringify(data), {});
        }
    };

    const leaveRoom = () => {
        if (stompClient.value && stompClient.value.connected) {
            enterRoomRef.value.unsubscribe();
            chatRef.value.unsubscribe();
            seletedEventRef.value.unsubscribe();
            deleteEventRef.value.unsubscribe();
            updateSelected.value.unsubscribe();
            currentRoom.value = -1;
            chatting.value = [];
            selectedTrip.value = [];
        }
    };

    const leaveTrip = () => {
        userRef.value.unsubscribe();
        updateRef.value.unsubscribe();
        invitationList.value = [];
        userId.value = '';
        chatting.value = [];
        currentRoom.value = -1;
    };

    const connectSocket = () => {
        const serverURL = 'http://192.168.0.53:8080/';
        // const serverURL = 'http://172.30.1.34:8080';
        // const serverURL = 'http://192.168.0.53:8080';
        //const serverURL = 'http://192.168.205.75:8080'; // 인상 자리
        const socket = new SockJS(serverURL, null, { transports: ['websocket', 'xhr-streaming', 'xhr-polling'] });
        stompClient.value = Stomp.over(socket);
        stompClient.value.connect(
            {},
            (frame) => {
                stompClient.value.connected = true;
                userRef.value = stompClient.value.subscribe('/sub/' + userId.value, (response) => {
                    const res = JSON.parse(response.body);
                    invitationList.value.push(res);
                    setTimeout(() => {
                        let toastElList = [].slice.call(document.querySelectorAll('#toast'));
                        if (toastElList.length > 0) {
                            var toastList = new Toast(toastElList[toastElList.length - 1]);
                            toastList.show();
                            setTimeout(() => {
                                toastList.hide();
                            }, 5000);
                        }
                    }, 500);
                });
                updateRef.value = stompClient.value.subscribe('/sub/update/' + userId.value, (response) => {
                    getTripList(userId.value);
                });
            },
            (error) => {
                console.log('소켓 연결 실패', error);
                stompClient.value.connected = false;
            }
        );
    };
    return {
        stompClient,
        chatting,
        connectSocket,
        send,
        createRoom,
        initUserId,
        userId,
        enterRoom,
        leaveRoom,
        currentRoom,
        enterUser,
        setChatting,
        invitationList,
        setInvitations,
        leaveTrip,
        tripList,
        getTripList,
        deleteTripList,
        initTripList,
        selectedTrip,
        updateTrip,
        removeTrip,
        findRoad,
        sequenceUpdateRequest,
        findTrip,
        currentRoomName,
    };
});
