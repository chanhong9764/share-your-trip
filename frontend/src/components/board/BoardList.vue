<script setup>
import { ref, onMounted, watch, onUnmounted } from 'vue';
import HashTag from '../../components/board/HashTag.vue';
import Post from '../../components/board/Post.vue';
import CloseFilterSVG from '../../assets/svg/CloseFilter.svg';
import { useRoute, useRouter } from 'vue-router';
import BoardAPI from '../../api/board';

import { useUserInfoStore } from '@/stores/userLogin';
import { storeToRefs } from 'pinia';

const store = useUserInfoStore();
const { isLogined, isUserInfo } = storeToRefs(store);

const route = useRoute();
const router = useRouter();

const postArray = ref([]);
const pageno = ref(1);

const hashArray = ref([]);
const type = ref(0);
const isFetchData = ref(false);

const isValid = ref(true);
const scrollComponent = ref(null);
const loadData = () => {
    if (pageno.value > 1) {
        const data = {
            pgno: pageno.value,
            hashtag: route.query.hashtag,
            type: type.value,
            userId: isUserInfo.value.userId,
        };
        isFetchData.value = true;
        BoardAPI.getBoardList(data).then((response) => {
            if (response.data.result.length < 10) {
                isValid.value = false;
            }
            isFetchData.value = false;
            postArray.value = [...postArray.value, ...response.data.result];
        });
    }
    pageno.value += 1;
};

watch(
    () => route.query.hashtag,
    () => {
        initParams(0, 1);
        fetchData();
    }
);

onMounted(() => {
    initParams(0, 1);
    fetchData();
    window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
    window.removeEventListener('scroll', handleScroll);
});

const handleScroll = (e) => {
    let element = scrollComponent.value;
    if (element.getBoundingClientRect().bottom < window.innerHeight) {
        if (isValid.value && !isFetchData.value) {
            console.log('hihi');
            loadData();
        }
    }
};

const moveWrite = () => {
    router.push({ name: 'board-write' });
};

const removeFilter = () => {
    router.push({ name: 'board-list' });
};

const fetchData = () => {
    const data = {
        pgno: pageno.value,
        hashtag: route.query.hashtag,
        type: type.value,
        userId: isUserInfo.value.userId,
    };
    BoardAPI.getBoardList(data).then((response) => {
        postArray.value = response.data.result;
        if (postArray.value.length < 10) {
            isValid.value = false;
        }
    });
    BoardAPI.getHotHasgTag().then((response) => {
        hashArray.value = response.data.result;
    });
};

const initParams = (initValue, pageNumber) => {
    type.value = initValue;
    pageno.value = pageNumber;
    isValid.value = true;
};

const getNewList = () => {
    initParams(0, 1);
    fetchData();
    pageno.value = 2;
};

const getHotList = () => {
    initParams(1, 1);
    fetchData();
    pageno.value = 2;
};
</script>

<template>
    <div class="container-fluid g-0 board-wrapper">
        <h3 class="title">여행지 공유</h3>
        <div class="container post-container d-flex justify-content-center">
            <div class="container g-0 hashtag-wrapper d-flex pb-4 pt-4">
                <HashTag v-for="(hashTag, index) in hashArray" :key="hashTag.id" :hash-tag="hashTag" :index="index" />
            </div>
            <div class="filter d-flex px-4 justify-content-between align-items-center">
                <div class="d-flex align-items-center">
                    <button v-if="isLogined" class="share-button" @click="moveWrite">여행지 공유하기!</button>
                    <div class="hashtag-filter" v-if="route.query.hashtag != null && route.query.hashtag != ''">
                        <button class="hashtag-button"># {{ route.query.hashtag }}</button>
                        <CloseFilterSVG class="close-filter" @click="removeFilter" />
                    </div>
                </div>

                <div class="mt-3 mb-3">
                    <span class="filter-button" :style="{ fontWeight: type == 0 ? 700 : 500 }" @click="getNewList"
                        >최신순</span
                    ><span class="mx-1" style="color: #bababa">|</span
                    ><span class="filter-button" :style="{ fontWeight: type == 1 ? 700 : 500 }" @click="getHotList"
                        >인기순</span
                    >
                </div>
            </div>
            <div class="container g-0 row" ref="scrollComponent">
                <Post
                    class="col-lg-4 ps-3 pe-3 pb-5 post"
                    v-for="post in postArray"
                    :key="post.articleNo"
                    :post-info="post"
                />
            </div>
        </div>
    </div>
</template>

<style scoped>
.share-button {
    width: 150px;
    height: 30px;
    border-radius: 30px;
    border: 0;
    font-weight: 600;
    background-color: black;
    color: white;
    line-height: 12px;
}

.loading {
    height: 50px;
}

.hashtag-filter {
    position: relative;
    padding-left: 10px;
    padding-right: 10px;
    height: 30px;
}

.hashtag-filter:hover {
    cursor: pointer;
}

.close-filter {
    position: absolute;
    right: 20px;
    top: 10px;
}
.hashtag-button {
    padding-left: 15px;
    height: 30px;
    border-radius: 30px;
    padding-right: 30px;
    border: 0;
    font-weight: 600;
    color: white;
    background-color: rgba(0, 0, 0, 0.9);
}

.title {
    text-align: center;
    padding: 50px 0;
    font-size: 26px;
    font-weight: 700;
    border-bottom: 1px solid #bfbfbf;
}

.board-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.hashtag-wrapper {
    flex-wrap: nowrap;
    overflow-x: auto;
    padding: 10px 0;
    border-bottom: 1px solid #d9d9d9;
    -ms-overflow-style: none;
    scrollbar-width: none;
    -webkit-overflow-scrolling: touch;
}

.hashtag-wrapper::-webkit-scrollbar {
    display: none;
}

.post-container {
    display: flex;
    flex-direction: column;
}

.filter-button {
    text-decoration: none;
    color: black;
    font-size: 16px;
}
.filter-button:hover {
    cursor: pointer;
}

.filter {
    padding: 10px 0;
}

@media (min-width: 1201px) {
    .container {
        justify-content: center !important;
    }
}
@media (max-width: 1200px) {
    .container {
        justify-content: start !important;
    }
}

.container {
    display: flex !important;
}
</style>
