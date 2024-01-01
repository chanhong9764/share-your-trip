<script setup>
defineProps({
    bannerInfo: Object,
});
</script>

<template>
    <div id="banner" class="carousel slide" data-bs-ride="carousel">
        <!-- Indicators/dots -->
        <div class="carousel-indicators">
            <button
                type="button"
                data-bs-target="#banner"
                :data-bs-slide-to="index"
                :class="{ active: index === 0 }"
                v-for="(banner, index) in bannerInfo"
                :key="banner.id"
            ></button>
        </div>

        <div class="carousel-inner" role="listbox">
            <div
                v-for="(banner, index) in bannerInfo"
                :key="banner.id"
                :class="{ 'carousel-item': true, active: index === 0 }"
                data-bs-interval="2000"
            >
                <img :src="banner.img" alt="banner_img" />
                <div class="carousel-caption">
                    <div class="title-wrapper">
                        <p class="sub-title">{{ banner.subTitle }}</p>
                        <h1 class="title">
                            {{ banner.mainTitle }}
                        </h1>
                        <router-link to="" class="link"><span class="link-text">지금 바로 떠나기!</span></router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.carousel-inner > .carousel-item > img {
    width: 50%;
    height: 800px;
    object-fit: cover;
    border-radius: 15px;
}

.carousel-item .carousel-caption {
    color: black !important;
    text-shadow: none !important;
    right: 0 !important;
    left: 50% !important;
    top: 15%;
    width: 50%;
}

.carousel-caption {
    display: flex;
    justify-content: center;
}

.carousel-indicators > button {
    background-color: #888888;
    width: 25px;
    height: 10px;
}

.carousel-indicators > .active {
    background-color: black;
}

.carousel-indicators {
    width: 50%;
    margin-right: 0 !important;
    margin-left: 0 !important;
    left: 50% !important;
    justify-content: center;
    bottom: 5% !important;
}

.title-wrapper {
    text-align: left;
    width: 50%;
}

.sub-title {
    width: 200px;
    height: 40px;
    background-color: black;
    color: white;
    font-weight: 600;
    border-radius: 20px;
    justify-content: center;
    display: flex;
    align-items: center;
    margin-bottom: 50px;
}

.title {
    color: black;
    font-size: 46px;
    text-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    font-weight: 600;
    line-height: normal;
    margin-bottom: 50px;
}

.link-text {
    color: rgba(0, 0, 0, 0.4);
    font-size: 20px;
    font-style: normal;
    font-weight: 400;
    line-height: normal;
}
.link {
    text-decoration: none;
    border-bottom: 1px solid rgba(0, 0, 0, 0.4);
    padding-bottom: 5px;
}

.carousel-inner:before {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 72%;
    content: '';
    display: block;
    background-color: #fff;
    z-index: 2;
}
</style>
