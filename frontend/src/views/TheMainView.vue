<script setup>
import ThePost from '../components/board/Post.vue';
import TheBanner from '../components/main/Banner.vue';
import { ref, onMounted } from 'vue';
import BoardAPI from '../api/board';
import { useUserInfoStore } from '@/stores/userLogin';
import { storeToRefs } from 'pinia';

const store = useUserInfoStore();
const { isUserInfo } = storeToRefs(store);

onMounted(() => {
    const data = {
        key: '인기글',
        userId: isUserInfo.value.userId,
    };
    BoardAPI.getBoardList(data).then((response) => {
        tempPost.value = response.data.result;
    });
});
const tempPost = ref([]);

const Banner = ref([
    {
        id: 1,
        subTitle: 'MZ세대 추천 여행지!',
        mainTitle: '가을 햇살 따라 달리는 유성 여행🚀',
        img: require('@/assets/img/banner.jpg'),
    },
    {
        id: 2,
        subTitle: '서퍼들의 천국!',
        mainTitle: '젊음의 향기가 느껴지는 양양 서퍼비치!',
        img: require('@/assets/img/banner2.png'),
    },
    {
        id: 3,
        subTitle: '제주도 억새 명소',
        mainTitle: '은빛 물결 가득한 제주도의 가을',
        img: require('@/assets/img/post.jpg'),
    },
]);
</script>

<template>
    <main class="container-fluid">
        <div class="container-fluid g-0 banner-wrapper">
            <TheBanner :banner-info="Banner" />
        </div>
        <div class="container col post-wrapper">
            <p class="post-title">지금 뜨는 여행지!</p>
            <div class="row g-0">
                <ThePost class="col-lg-4 px-2" v-for="item in tempPost" :key="item.id" :post-info="item" />
            </div>
        </div>
    </main>
</template>

<style scoped>
.post-title {
    font-size: 26px;
    font-weight: 700;
    text-align: center;
    margin-bottom: 100px;
}

.banner-wrapper {
    margin-top: 100px;
    margin-bottom: 150px;
    padding-left: 30px;
}

.post-wrapper {
    margin-bottom: 100px;
}
</style>
