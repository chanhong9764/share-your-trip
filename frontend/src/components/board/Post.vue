<script setup>
import HeartSVG from '../../assets/svg/Heart.svg';
import NoProfile from '../../assets/svg/NoProfile.svg';
import FillHeart from '../../assets/svg/FillHeart.svg';
import { ref, onMounted } from 'vue';
const prorps = defineProps({
    postInfo: Object,
});

const imageURL = ref('');

onMounted(() => {
    imageURL.value = '/api/images' + prorps.postInfo.imageURL[0];
});
</script>

<template>
    <router-link style="text-decoration: none" :to="{ name: 'board-view', params: { articleno: postInfo.articleNo } }">
        <div class="card">
            <img class="card-img-top post-img" :src="imageURL" alt="post_img" />
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div class="d-flex">
                        <NoProfile class="profile-img" v-if="postInfo.profile == null" />
                        <img v-else class="profile-img" :src="'/api/images' + postInfo.profile" alt="profile" />
                        <span class="profile-id">{{ postInfo.userId }}</span>
                    </div>
                    <div class="d-flex">
                        <HeartSVG v-if="!postInfo.recommend" />
                        <FillHeart v-else />
                        <span class="heart-cnt">{{ postInfo.recommendCnt }}</span>
                    </div>
                </div>
                <div class="container-fluid g-0 mt-3">
                    <p class="content">
                        {{ postInfo.content }}
                    </p>
                    <div class="hashtag-box">
                        <span class="hashtag" v-for="hashtag in postInfo.hashtag" :key="hashtag.hashtagId">
                            #{{ hashtag.name }}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </router-link>
</template>

<style scoped>
.profile-img {
    width: 25px;
    height: 25px;
    border-radius: 25px;
}
.card-body {
    height: 130px;
}
.post-img {
    height: 300px;
    object-fit: fill;
}
.content {
    font-weight: 500;
    font-size: 13px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    padding-right: 50px;
}

.hashtag-box {
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    padding-right: 20px;
}

.profile-id {
    font-size: 15px;
    justify-content: center;
    align-items: center;
    display: flex;
    margin-left: 7px;
    font-weight: 600;
}
.hashtag {
    color: #297dcb;
}

.heart-cnt {
    font-size: 14px;
    font-weight: 00;
    justify-content: center;
    align-items: center;
    display: flex;
    margin-bottom: 3px;
    margin-left: 5px;
    color: #888888;
}
.card {
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}
</style>
