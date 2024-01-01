<script setup>
const prorps = defineProps({
    userId: String,
});
</script>

<template>
    <div class="container-fluid enter-box">{{ userId }}님이 입장했습니다.</div>
</template>

<style scoped>
.enter-box {
    height: 30px;
}
</style>
