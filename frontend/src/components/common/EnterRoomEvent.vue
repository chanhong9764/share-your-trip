<script setup>
const props = defineProps({
    enterUser: String,
});
</script>

<template>
    <div class="mt-3 toast-container" v-if="props.enterUser != ''">
        <div class="toast container-fluid" id="enterroom">
            <div class="toast-header d-flex justify-content-center">{{ props.enterUser }}님이 입장했습니다.</div>
        </div>
    </div>
</template>

<style scoped>
.toast-container {
    width: 100% !important;
}
</style>
