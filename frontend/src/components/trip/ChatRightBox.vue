<script setup>
import NoProfile from '../../assets/svg/NoProfile.svg';
const props = defineProps({
    chattingData: Object,
});
</script>

<template>
    <div class="d-flex align-items-start chatting-box justify-content-end">
        <div class="text-end">
            <span class="user-id">{{ props.chattingData.userId }}</span>
            <div class="text-box text-start mt-1">
                <span>{{ props.chattingData.message }}</span>
            </div>
        </div>
        <div class="ps-3">
            <NoProfile
                class="profile"
                v-if="props.chattingData.profile === null || props.chattingData.profile === ''"
            />
            <img v-else class="profile" :src="'/api/images' + props.chattingData.profile" alt="profile" />
        </div>
    </div>
</template>

<style scoped>
.profile {
    width: 30px;
    height: 30px;
    border-radius: 50%;
}

.user-id {
    font-weight: 700;
}

.chatting-box {
    padding: 10px 20px;
    max-width: 80%;
    margin-left: auto;
}
.text-box {
    background-color: #dfdede;
    padding: 10px 20px;
    border-radius: 10px;
    border-top-right-radius: 0px;
}
</style>
