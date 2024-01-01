<script setup>
import SendSVG from '../../assets/svg/Send.svg';
import ModifySVG from '../../assets/svg/Modify.svg';
import PlusSVG from '../../assets/svg/Plus.svg';
import DeleteImgSVG from '../../assets/svg/DeleteImg.svg';
import HashtagRemoveSVG from '../../assets/svg/HashtagRemove.svg';
import { useRoute, useRouter } from 'vue-router';
import { ref, onMounted, computed } from 'vue';
import BoardAPI from '../../api/board';

import { useUserInfoStore } from '../../stores/userLogin';
import { storeToRefs } from 'pinia';

const userStore = useUserInfoStore();
const { isUserInfo } = storeToRefs(userStore);

const route = useRoute();
const router = useRouter();

const { articleno } = route.params;
const hashTag = ref('');

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

const modifyData = ref({
    addHashtag: [],
    removeHashtag: [],
    addImages: [],
    removeImages: [],
});

const imageURL = ref([]);
const encodingImages = ref([]);

const renderImages = computed(() => encodingImages.value.slice().reverse());

onMounted(() => {
    // 수정
    if (articleno) {
        const data = {
            articleNo: articleno,
            userId: isUserInfo.value.userId,
        };
        BoardAPI.getBoardPost(data).then((response) => {
            article.value = response.data.result;
            article.value.imageURL.forEach((image) => {
                imageURL.value.push('/api/images' + image);
            });
            article.value.hashtag = article.value.hashtag.map((tag) => tag.name);
        });
    }
});

const writePost = () => {
    let data = new FormData();
    data.append('userId', isUserInfo.value.userId);
    data.append('content', article.value.content);
    article.value.hashtag.forEach((ele) => {
        data.append('hashtag', ele);
    });
    article.value.imageURL.forEach((ele) => {
        data.append('images', ele);
    });
    BoardAPI.writePost(data).then(() => {
        moveList();
    });
};

const updatePost = () => {
    const data = new FormData();
    modifyData.value.addHashtag.forEach((ele) => {
        data.append('addHashtag', ele);
    });
    modifyData.value.removeHashtag.forEach((ele) => {
        data.append('removeHashtag', ele);
    });
    modifyData.value.addImages.forEach((ele) => {
        data.append('addImages', ele);
    });
    modifyData.value.removeImages.forEach((ele) => {
        data.append('removeImages', ele);
    });
    data.append('userId', isUserInfo.value.userId);
    data.append('content', article.value.content);
    data.append('articleNo', articleno);
    BoardAPI.modifyPost(data).then((response) => {
        router.push({ name: 'board-view', params: { articleno: articleno } });
    });
};

const moveList = () => {
    router.push({ name: 'board-list' });
};

const enterHashtag = () => {
    if (hashTag.value !== '') {
        if (!article.value.hashtag.includes(hashTag.value) && article.value.hashtag.length < 10) {
            if (articleno) {
                if (modifyData.value.removeHashtag.includes(hashTag.value)) {
                    const modifyIdx = modifyData.value.removeHashtag.indexOf(hashTag.value);
                    modifyData.value.removeHashtag.splice(modifyIdx, 1);
                } else {
                    modifyData.value.addHashtag.push(hashTag.value);
                }
            }
            article.value.hashtag.push(hashTag.value);
        }
        hashTag.value = '';
    }
};

const removeHashtag = (param) => {
    const idx = article.value.hashtag.indexOf(param);
    article.value.hashtag.splice(idx, 1);
    // 수정일 때, 만약 기존의 데이터라면
    const modifyIdx = modifyData.value.addHashtag.indexOf(param);
    if (idx != -1 && articleno && modifyIdx == -1) {
        modifyData.value.removeHashtag.push(param);
    }
    if (modifyIdx != -1) {
        modifyData.value.addHashtag.splice(modifyIdx, 1);
    }
};

const handleImages = (e) => {
    const image = e.target.files[0];
    if (image != null) {
        if (articleno) {
            modifyData.value.addImages.push(image);
        } else {
            article.value.imageURL.push(image);
        }
        // 타입 체크
        if (image.type.indexOf('image') != -1) {
            const preview = new FileReader();
            preview.onload = (e) => {
                encodingImages.value.push(e.target.result);
            };
            preview.readAsDataURL(image);
        }
    }
};

const deleteImages = (encodeImage) => {
    const idx = encodingImages.value.indexOf(encodeImage);
    if (!articleno) {
        article.value.imageURL.splice(idx, 1);
        encodingImages.value.splice(idx, 1);
    } else {
        if (idx != -1) {
            encodingImages.value.splice(idx, 1);
            modifyData.value.addImages.splice(idx, 1);
        } else {
            const existImageIdx = imageURL.value.indexOf(encodeImage);
            imageURL.value.splice(existImageIdx, 1);
            const splitData = encodeImage.split('/');
            modifyData.value.removeImages.push(splitData[splitData.length - 1]);
        }
    }
};
</script>

<template>
    <div class="container">
        <div class="mt-3 mb-3 container justify-content-end d-flex">
            <SendSVG class="wm-button" v-if="!route.params.articleno" @click="writePost" />
            <ModifySVG class="wm-button" v-else @Click="updatePost" />
        </div>
        <div>
            <textarea
                placeholder="내용을 입력해주세요"
                class="content container"
                v-model="article.content"
                rows="20"
            ></textarea>
        </div>
        <div class="container hashtag-box">
            <div>
                <input
                    @keyup.enter="enterHashtag"
                    v-model="hashTag"
                    class="hashtag-input"
                    placeholder="해시태그를 입력해주세요"
                />
            </div>
            <div class="hashtag-list">
                <div class="hashtag-button d-flex align-items-center" v-for="hashtag in article.hashtag" :key="hashtag">
                    <span class="hashtag-link">#{{ hashtag }}&nbsp;</span>
                    <HashtagRemoveSVG @click="removeHashtag(hashtag)" class="wm-button" />
                </div>
            </div>
        </div>
        <div class="container g-0 image-wrapper d-flex">
            <div>
                <label class="input-box">
                    <PlusSVG class="plus-svg" />
                    <input type="file" id="file" @change="handleImages" />
                </label>
            </div>
            <div class="d-flex">
                <template v-if="!articleno">
                    <div class="image-box" v-for="(image, index) in renderImages" :key="index">
                        <img class="input-images" :src="image" alt="images" />
                        <DeleteImgSVG class="delete-image" @click="deleteImages(image)" />
                    </div>
                </template>
                <template v-else>
                    <div class="image-box" v-for="(image, index) in renderImages" :key="index">
                        <img class="input-images" :src="image" alt="images" />
                        <DeleteImgSVG class="delete-image" @click="deleteImages(image)" />
                    </div>
                    <div class="image-box" v-for="(image, index) in imageURL" :key="index">
                        <img class="input-images" :src="image" alt="images" />
                        <DeleteImgSVG class="delete-image" @click="deleteImages(image)" />
                    </div>
                </template>
            </div>
        </div>
    </div>
</template>

<style scoped>
@media (min-width: 992px) {
    .container {
        width: 768px !important;
    }
}

.delete-image {
    position: absolute;
    top: 10px;
    right: 20px;
}

.delete-image:hover {
    cursor: pointer;
}

.image-box {
    position: relative;
}

.input-images {
    width: 200px;
    height: 200px;
    margin: 0 10px;
    border-radius: 10px;
    border: 1px solid black;
}

input[type='file'] {
    width: 0;
    height: 0;
}

.hashtag-button {
    border: none;
    border-radius: 30px;
    padding: 5px 10px;
    margin-right: 10px;
    margin-top: 10px;
    background-color: rgba(217, 217, 217, 0.4);
}
.hashtag-list {
    display: flex;
    flex-wrap: wrap;
}
.hashtag-link {
    color: black;
    font-weight: 700;
    margin-right: 5px;
}

.wm-button {
    cursor: pointer;
}
.hashtag-box {
    padding: 20px 0;
    border-bottom: 1px solid rgba(0, 0, 0, 0.3);
}
.hashtag-input {
    border: none;
    background-color: rgba(217, 217, 217, 0.4);
    border-radius: 30px;
    padding-left: 20px;
    margin-bottom: 10px;
    height: 35px;
    outline-style: none;
}

.content {
    border: none;
    border-top: 1px solid rgba(0, 0, 0, 0.3);
    border-bottom: 1px solid rgba(0, 0, 0, 0.3);
    outline-style: none;
    resize: none;
    padding: 10px 10px;
}

.input-box {
    width: 200px;
    height: 200px;
    border: 1px solid black;
    border-radius: 10px;
    position: relative;
    cursor: pointer;
}

.image-wrapper {
    flex-wrap: nowrap;
    overflow-x: auto;
    padding: 20px 0;
    border-bottom: 1px solid #d9d9d9;
    -ms-overflow-style: none;
    scrollbar-width: none;
    -webkit-overflow-scrolling: touch;
}
.image-wrapper::-webkit-scrollbar {
    display: none;
}

.plus-svg {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>
