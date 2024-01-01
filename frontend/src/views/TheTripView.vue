<script setup>
import TheKakaoMap from '../components/trip/TheKakaoMap.vue';
import ChatContainer from '@/components/trip/ChatContainer.vue';
import RouteList from '@/components/trip/RouteList.vue';
</script>

<template>
    <div class="map-wrapper container-fluid row">
        <div class="container g-0 d-flex col-3">
            <RouteList />
        </div>
        <TheKakaoMap class="kakaoMap col-6" />
        <div class="container g-0 d-flex col-3">
            <ChatContainer class="container" />
        </div>
    </div>
</template>

<style scoped>
.map-wrapper {
    padding: 10px 0px 0px 0px;
    display: flex;
}
</style>
