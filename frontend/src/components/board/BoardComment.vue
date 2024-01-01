<script setup>
import CommentSettingSVG from '../../assets/svg/CommentSetting.svg';
import dayjs from 'dayjs';
import NoProfile from '../../assets/svg/NoProfile.svg';

import { useUserInfoStore } from '@/stores/userLogin';
import { storeToRefs } from 'pinia';
import BoardAPI from '../../api/board';
import CommentAPI from '../../api/comment';

const props = defineProps({
    commentInfo: Object,
});
const emit = defineEmits(['changeComments']);

const store = useUserInfoStore();
const { isUserInfo } = storeToRefs(store);

const { commentInfo } = props;

const deleteComment = () => {
    const data = {
        commentId: commentInfo.commentId,
        articleNo: commentInfo.articleNo,
    };
    emit('changeComments', data);
};
</script>

<template>
    <div class="comment-wrapper mt-2">
        <div class="d-flex align-items-start">
            <div class="pe-3">
                <NoProfile class="profile" v-if="commentInfo.profile == null" />
                <img v-else class="profile" :src="'/api/images' + commentInfo.profile" alt="profile" />
            </div>
            <div>
                <span class="comment-id pe-3">{{ commentInfo.userId }}</span>
                <span class="">{{ commentInfo.content }}</span>
                <div class="mt-1">
                    <span class="me-3 comment-date">{{ dayjs(commentInfo.createdAt).format('YYYY년 MM월 DD일') }}</span>
                </div>
            </div>
        </div>
        <div class="d-flex align-items-center ms-4">
            <button
                type="button"
                class="btn btn-secondary dropdown-toggle"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                v-if="commentInfo.userId == isUserInfo.userId"
            >
                <CommentSettingSVG class="comment-setting" />
            </button>
            <ul class="dropdown-menu">
                <li class="dropdown-item">
                    <span class="setting-button" @click="deleteComment">삭제하기</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<style scoped>
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

.btn-secondary {
    background-color: white;
    border: none;
}
.comment-setting {
    cursor: pointer;
}
.comment-wrapper {
    display: flex;
    justify-content: space-between;
}
.profile {
    width: 27px;
    height: 27px;
    border-radius: 50%;
}

.comment-id {
    font-weight: 700;
    display: inline-flex;
}

.comment-date {
    color: #d9d9d9;
    font-size: 11px;
}
.reply-button {
    color: #d9d9d9;
    font-weight: 700;
    font-size: 11px;
}
</style>
