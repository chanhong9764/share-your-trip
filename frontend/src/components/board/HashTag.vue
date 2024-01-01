<script setup>
const props = defineProps({
    hashTag: Object,
    index: Number,
});

const hashImg = [
    { id: 1, img: require('@/assets/img/hashtag_1.jpg') },
    { id: 2, img: require('@/assets/img/hashtag_2.jpg') },
    { id: 3, img: require('@/assets/img/hashtag_3.jpg') },
    { id: 4, img: require('@/assets/img/hashtag_4.jpg') },
    { id: 5, img: require('@/assets/img/hashtag_5.jpg') },
    { id: 6, img: require('@/assets/img/hashtag_6.jpg') },
    { id: 7, img: require('@/assets/img/hashtag_7.jpg') },
    { id: 8, img: require('@/assets/img/hashtag_8.jpg') },
    { id: 9, img: require('@/assets/img/hashtag_9.jpg') },
];
</script>

<template>
    <router-link :to="{ name: 'board-list', query: { hashtag: hashTag.name } }" class="hash-wrapper">
        <img class="hashtag-img" :src="hashImg[props.index].img" alt="hash_tag" />
        <span class="content">#{{ props.hashTag.name }}</span>
    </router-link>
</template>

<style scoped>
.hash-wrapper {
    display: flex;
    flex-direction: column;
    width: 80px;
    margin: 0 20px;
    text-decoration: none;
}
.hash-wrapper:hover {
    cursor: pointer;
}

.hashtag-img {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.content {
    font-size: 14px;
    font-weight: 600;
    margin-top: 10px;
    text-align: center;
    color: black;
}
</style>
