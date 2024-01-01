<script setup>
import { ref, onMounted, watch } from 'vue';
import { useTripSearchStore } from '@/stores/tripInfo';
import { useSocketStore } from '@/stores/useSocket';
import { storeToRefs } from 'pinia';
import { instance2 } from '@/api/axios';
import DefaultImg from '../../assets/img/marker.png';
import MarkerImg1 from '../../assets/img/marker1.png';
import MarkerImg2 from '../../assets/img/marker2.png';
import MarkerImg3 from '../../assets/img/marker3.png';
import MarkerImg4 from '../../assets/img/marker4.png';
import MarkerImg5 from '../../assets/img/marker5.png';
import MarkerImg6 from '../../assets/img/marker6.png';
import MarkerImg7 from '../../assets/img/marker7.png';
import MarkerImg8 from '../../assets/img/marker8.png';
import MarkerImg9 from '../../assets/img/marker9.png';
import MarkerImg10 from '../../assets/img/marker10.png';
import MarkerImg11 from '../../assets/img/marker11.png';
import MarkerImg12 from '../../assets/img/marker12.png';
import MarkerImg13 from '../../assets/img/marker13.png';
import MarkerImg14 from '../../assets/img/marker14.png';
import MarkerImg15 from '../../assets/img/marker15.png';

const store = useTripSearchStore();
const socketStore = useSocketStore();
const { search } = storeToRefs(store);
const { findTrip, currentRoomName } = storeToRefs(socketStore);

let map;
var ps;

const markerNum = ref(0);
const markers = ref([]);
const polyline = ref([]);
const markerArr = [
    MarkerImg1,
    MarkerImg2,
    MarkerImg3,
    MarkerImg4,
    MarkerImg5,
    MarkerImg6,
    MarkerImg7,
    MarkerImg8,
    MarkerImg9,
    MarkerImg10,
    MarkerImg11,
    MarkerImg12,
    MarkerImg13,
    MarkerImg14,
    MarkerImg15,
]; // 마커를 담는 배열

onMounted(() => {
    search.value = '';
    if (window.kakao && window.kakao.maps) {
        initMap();
    } else {
        const script = document.createElement('script');
        /* global kakao */
        script.onload = () => kakao.maps.load(initMap);
        script.src =
            '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=eb5cfa50315fe2e75930b057c29f2371&libraries=services,clusterer';
        document.head.appendChild(script);
    }
});

const initMap = () => {
    const container = document.getElementById('map');
    const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
    };

    map = new kakao.maps.Map(container, options);
    ps = new kakao.maps.services.Places();
};

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    deleteMarkers();
    //initMap();
    if (status === kakao.maps.services.Status.OK) {
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();
        // console.log(data);
        for (var i = 0; i < data.length; i++) {
            displayMarker(data[i]);
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    }
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    const { updateTrip } = socketStore;
    // markerNum.value = 0;
    // 마커를 생성하고 지도에 표시합니다
    let markerImage;
    if (markerNum.value == -1) {
        markerImage = new kakao.maps.MarkerImage(DefaultImg, new kakao.maps.Size(24, 35));
    } else {
        markerImage = new kakao.maps.MarkerImage(markerArr[markerNum.value], new kakao.maps.Size(24, 35));
    }

    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
        image: markerImage,
    });
    markers.value.push(marker);

    var customOverlay = new kakao.maps.CustomOverlay({
        position: new kakao.maps.LatLng(place.y, place.x),
        xAnchor: 0.5,
        yAnchor: 1.05,
    });

    var content = document.createElement('div');
    content.style.paddingTop = '20px';
    content.style.height = '220px';
    content.style.display = 'flex';
    content.style.justifyContent = 'center';
    content.style.flexDirection = 'column';
    content.style.alignItems = 'center';
    content.style.borderRadius = '20px';
    content.style.boxShadow = '0px 4px 4px 0px rgba(0, 0, 0, 0.25)';
    content.style.backgroundColor = 'white';

    var titleWrapper = document.createElement('div');
    titleWrapper.style.width = '100%';
    titleWrapper.style.height = '100px';
    titleWrapper.style.display = 'flex';
    titleWrapper.style.justifyContent = 'center';
    titleWrapper.style.alignItems = 'end';
    titleWrapper.style.paddingLeft = '20px';
    titleWrapper.style.paddingRight = '20px';

    var title = document.createElement('span');
    title.style.fontSize = '22px';
    title.style.fontWeight = '700';
    title.appendChild(document.createTextNode(place.place_name));

    var category = document.createElement('span');
    category.style.fontSize = '11px';
    category.style.paddingBottom = '5px';
    category.style.marginLeft = '3px';
    category.appendChild(document.createTextNode(place.category_group_name));

    titleWrapper.appendChild(title);
    titleWrapper.appendChild(category);
    content.appendChild(titleWrapper);

    var infoAddr = document.createElement('span');
    infoAddr.style.marginTop = '10px';
    infoAddr.style.paddingLeft = '10px';
    infoAddr.style.paddingRight = '10px';
    infoAddr.appendChild(document.createTextNode(place.road_address_name));
    var infoTel = document.createElement('span');
    infoTel.style.marginTop = '10px';
    infoTel.appendChild(document.createTextNode(place.phone));
    content.appendChild(infoAddr);
    content.appendChild(infoTel);

    var buttonWrapper = document.createElement('div');
    buttonWrapper.style.display = 'flex';
    buttonWrapper.style.justifyContent = 'center';
    buttonWrapper.style.width = '100%';

    var leftButton = document.createElement('button');
    leftButton.style.borderBottomLeftRadius = '20px';
    leftButton.style.display = 'flex';
    leftButton.style.justifyContent = 'center';
    leftButton.style.alignItems = 'center';
    leftButton.style.height = '70px';
    leftButton.style.width = '100%';
    leftButton.style.marginTop = '30px';
    leftButton.style.backgroundColor = 'white';
    leftButton.style.border = 'none';
    leftButton.style.borderTop = '1px solid #d9d9d9';
    leftButton.style.borderRight = '1px solid #d9d9d9';
    leftButton.style.padding = '0px';
    leftButton.style.fontWeight = '700';
    leftButton.appendChild(document.createTextNode('취소'));
    leftButton.onclick = () => {
        customOverlay.setMap(null);
    };

    var rightButton = document.createElement('button');
    rightButton.style.borderBottomRightRadius = '20px';
    rightButton.style.display = 'flex';
    rightButton.style.justifyContent = 'center';
    rightButton.style.alignItems = 'center';
    rightButton.style.height = '70px';
    rightButton.style.width = '100%';
    rightButton.style.marginTop = '30px';
    rightButton.style.backgroundColor = 'white';
    rightButton.style.border = 'none';
    rightButton.style.borderTop = '1px solid #d9d9d9';
    rightButton.style.padding = '0px';
    rightButton.style.fontWeight = '700';
    rightButton.appendChild(document.createTextNode('선택'));
    rightButton.onclick = () => {
        updateTrip(place);
        customOverlay.setMap(null);
    };

    buttonWrapper.appendChild(leftButton);
    buttonWrapper.appendChild(rightButton);
    content.appendChild(buttonWrapper);

    // // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function () {
        // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
        customOverlay.setMap(map);
    });
    customOverlay.setContent(content);
}

watch(
    () => search.value,
    () => {
        if (search.value) {
            if (polyline != null) {
                polyline.value.forEach((ele) => {
                    ele.setMap(null);
                });
            }
            markerNum.value = -1;
            ps.keywordSearch(search.value, placesSearchCB);
        }
    }
);
watch(
    () => findTrip.value,
    () => {
        deleteMarkers();
        if (polyline != null) {
            polyline.value.forEach((ele) => {
                ele.setMap(null);
            });
        }
        //initMap();
        var bounds = new kakao.maps.LatLngBounds();
        markerNum.value = 0;
        for (var i = 0; i < findTrip.value.length; i++) {
            displayMarker(findTrip.value[i]);
            markerNum.value += 1;
            bounds.extend(new kakao.maps.LatLng(findTrip.value[i].y, findTrip.value[i].x));
        }

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
        const temp = [];

        for (let index = 1; index < findTrip.value.length - 1; index++) {
            temp.push({
                x: findTrip.value[index].x,
                y: findTrip.value[index].y,
            });
        }
        instance2
            .post('/v1/waypoints/directions', {
                origin: {
                    x: findTrip.value[0].x,
                    y: findTrip.value[0].y,
                },
                destination: {
                    x: findTrip.value[findTrip.value.length - 1].x,
                    y: findTrip.value[findTrip.value.length - 1].y,
                },
                waypoints: temp,
            })
            .then((resposnes) => {
                var linePath = [];

                resposnes.data.routes.forEach((router) => {
                    router.sections.forEach((section) => {
                        section.roads.forEach((road) => {
                            road.vertexes.forEach((vertex, index) => {
                                if (index % 2 === 0) {
                                    linePath.push(
                                        new kakao.maps.LatLng(road.vertexes[index + 1], road.vertexes[index])
                                    );
                                }
                            });
                        });
                        // 지도에 표시할 선을 생성
                        var color = Math.random() * 0xffffff;
                        color = parseInt(color);
                        color = color.toString(16);
                        var colorCode = '#' + color;

                        const poly = new kakao.maps.Polyline({
                            path: linePath, // 선을 구성하는 좌표배열
                            strokeWeight: 8, // 선 두께
                            strokeColor: colorCode, // 선 색깔
                            strokeOpacity: 0.85, // 선 불투명도 1 ~ 0 사이의 값
                            strokeStyle: 'solid', // 선 스타일
                        });
                        polyline.value.push(poly);
                        // 지도에 선을 표시
                        poly.setMap(map);
                        linePath = [];
                    });
                });
            });
    }
);

const deleteMarkers = () => {
    if (markers.value.length > 0) {
        markers.value.forEach((marker) => marker.setMap(null));
    }
};
</script>

<template>
    <div class="container map-box">
        <h1 class="mt-3 mb-4 text-center">{{ currentRoomName }}</h1>
        <div id="map" style="width: 100%; height: 87%"></div>
    </div>
</template>

<style scoped>
#map {
    border-radius: 15px;
    border: 2px solid black;
}
</style>
