<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import dayjs from 'dayjs';
import SettingSVG from '../../assets/svg/Setting.svg';
import HeartSVG from '../../assets/svg/Heart.svg';
import FillHeartSVG from '../../assets/svg/FillHeart.svg';
import ReplySVG from '../../assets/svg/Reply.svg';
import NoProfile from '../../assets/svg/NoProfile.svg';
import ModifyButtonSVG from '../../assets/svg/ModifyButton.svg';
import DeleteButtonSVG from '../../assets/svg/DeleteButton.svg';
import { Modal } from 'bootstrap';

import TheBoardComment from './BoardComment.vue';
import BoardAPI from '../../api/board';
import CommentAPI from '../../api/comment';

import { useUserInfoStore } from '@/stores/userLogin';
import { storeToRefs } from 'pinia';

const store = useUserInfoStore();
const { isUserInfo } = storeToRefs(store);

const route = useRoute();
const router = useRouter();

const { articleno } = route.params;

const article = ref({
    articleNo: 0,
    userId: '',
    profile: '',
    imageURL: [],
    content: '',
    hashtag: [],
    recommendCnt: 0,
    recommend: false,
    createdAt: '',
});

const inputComment = ref('');

const comments = ref([]);
const imageURL = ref([]);

onMounted(() => {
    getArticle();
    getComments();
});

const changeComments = (data) => {
    CommentAPI.deleteComment(data).then((response) => {
        comments.value = response.data.result;
    });
};

const getComments = () => {
    CommentAPI.getComment(articleno).then((response) => {
        comments.value = response.data.result;
    });
};

const getArticle = () => {
    const data = {
        articleNo: articleno,
        userId: isUserInfo.value.userId,
    };
    BoardAPI.getBoardPost(data).then((response) => {
        article.value = response.data.result;
        article.value.imageURL.forEach((image) => {
            imageURL.value.push('/api/images' + image);
        });
    });
};

const writeComment = () => {
    if (inputComment.value != '') {
        const data = {
            articleNo: articleno,
            content: inputComment.value,
            userId: isUserInfo.value.userId,
        };
        CommentAPI.writeComment(data).then((response) => {
            comments.value = response.data.result;
            inputComment.value = '';
        });
    }
};

const moveModify = () => {
    router.push({ name: 'board-modify', params: { articleno } });
};

const onDeleteArticle = () => {
    BoardAPI.removePost(article.value.articleNo).then((response) => {
        router.push({ name: 'board-list' });
    });
};

const handleRecommend = () => {
    const userId = isUserInfo.value.userId;
    if (userId) {
        const data = {
            userId: userId,
            articleNo: article.value.articleNo,
        };
        if (article.value.recommend) {
            BoardAPI.delRecommend(data).then((response) => {
                article.value.recommend = false;
                article.value.recommendCnt -= 1;
            });
        } else {
            BoardAPI.setRecommend(data).then((response) => {
                article.value.recommend = true;
                article.value.recommendCnt += 1;
            });
        }
    } else {
        const modalInstance = new Modal(document.querySelector('#loginModal'));
        modalInstance.show();
    }
};
</script>

<template>
    <div class="container">
        <div class="container d-flex justify-content-between title-wrapper align-items-center">
            <div class="d-flex justify-cotent-center">
                <NoProfile v-if="article.profile == null || article.profile == ''" class="profile-img me-2" />
                <img v-else class="profile-img" :src="'/api/images' + article.profile" alt="profile" />
                <span class="user-id">{{ article.userId }}</span>
            </div>
            <button
                type="button"
                class="btn btn-secondary dropdown-toggle"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                v-if="article.userId == isUserInfo.userId"
            >
                <SettingSVG class="setting" />
            </button>
            <ul class="dropdown-menu">
                <li class="dropdown-item">
                    <span class="setting-button" @click="moveModify">수정하기</span>
                </li>
                <li class="dropdown-item">
                    <span class="setting-button" @click="onDeleteArticle">삭제하기</span>
                </li>
            </ul>
        </div>
        <div id="carouselExampleControls" class="container g-0 carousel slide mb-2 me-0" data-bs-ride="carousel">
            <div class="carousel-inner" role="listbox">
                <div
                    v-for="(image, index) in imageURL"
                    :key="index"
                    :class="{ active: index === 0 }"
                    class="carousel-item"
                >
                    <img :src="image" alt="main-img" />
                </div>
            </div>
            <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExampleControls"
                data-bs-slide="prev"
            >
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            </button>
            <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselExampleControls"
                data-bs-slide="next"
            >
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
            </button>
        </div>
        <div class="info container">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <HeartSVG v-if="!article.recommend" class="button" @click="handleRecommend" />
                    <FillHeartSVG v-else class="button" @click="handleRecommend" />
                    <span class="button-text">{{ article.recommendCnt }}</span>
                    <ReplySVG class="button" />
                    <span class="button-text">{{ comments.length }}</span>
                </div>
                <span class="button-text">{{ dayjs(article.createdAt).format('YYYY년 MM월 DD일') }}</span>
            </div>
            <div class="content">{{ article.content }}</div>
            <div class="hash-box">
                <router-link
                    class="hashtag-link"
                    :to="{ name: 'board-list', query: { hashtag: hashtag.name } }"
                    v-for="hashtag in article.hashtag"
                    :key="hashtag.hashtagId"
                    >#{{ hashtag.name }}&nbsp;
                </router-link>
            </div>
        </div>
        <div v-if="isUserInfo.userId" class="d-flex align-items-center container input-wrapper">
            <textarea
                v-model="inputComment"
                rows="1"
                placeholder="댓글을 작성해주세요..."
                @keyup.enter="writeComment"
                class="input"
            />
            <span class="input-button" @click="writeComment">작성하기</span>
        </div>
        <div class="mt-3 mb-5 container me-0">
            <TheBoardComment
                @changeComments="changeComments"
                :comment-info="comment"
                v-for="comment in comments"
                :key="comment.commentId"
            />
        </div>
    </div>
</template>

<style scoped>
.content {
    word-wrap: break-word;
    padding-right: 15px;
    margin-bottom: 30px;
}
.hash-box {
    word-wrap: break-word;
    padding-right: 15px;
}
.input-wrapper {
    position: relative;
    margin-top: 15px;
}
.dropdown-toggle::after {
    border: none;
}

.dropdown-item {
    text-align: center !important;
    width: 90px !important;
}

.dropdown-item:hover {
    cursor: pointer;
}

.dropdown-item:active {
    background-color: white;
    color: black;
}
.dropdown-menu {
    width: 100px !important;
    min-width: 0 !important;
}

.setting-button {
    margin-right: 10px;
}

.btn-secondary {
    background-color: white;
    border: none;
}
.input {
    background-color: #e9e9e9;
    border: none;
    outline-style: none;
    resize: none;
    padding: 10px 20px;
    border-radius: 30px;
    width: 100%;
}

.profile-img {
    width: 27px;
    height: 27px;
    border-radius: 50%;
}
.input-button {
    position: absolute;
    right: 30px;
    font-weight: 700;
}

.input-button:hover {
    cursor: pointer;
}
.info {
    margin-top: 20px;
    padding-bottom: 30px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
    margin-right: 0px;
}
.hashtag-link {
    text-decoration: none;
    color: #297dcb;
    font-weight: 700;
}
.info > p {
    margin-top: 15px;
    font-weight: 500;
}

.button-text {
    color: #d9d9d9;
    margin: 0 7px;
}
.button:hover {
    cursor: pointer;
}
.title-wrapper {
    height: 85px;
    padding-left: 20px;
    padding-right: 15px;
}

.carousel-inner > .carousel-item > img {
    width: 100%;
    object-fit: cover;
    border-radius: 15px;
    padding: 0 15px;
}

.profile {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
}

.user-id {
    font-weight: 600;
    font-size: 18px;
    margin-left: 10px;
}

.setting:hover {
    cursor: pointer;
}

@media (min-width: 992px) {
    .container {
        width: 768px !important;
    }
}
</style>
