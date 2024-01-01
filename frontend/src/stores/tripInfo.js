import { ref, computed } from 'vue';
import { defineStore } from 'pinia';

export const useTripSearchStore = defineStore('tripsearch', () => {
    // store
    const search = ref('');

    return { search };
});
