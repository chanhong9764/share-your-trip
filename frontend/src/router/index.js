import { createRouter, createWebHistory } from 'vue-router';
import TheMainView from '@/views/TheMainView.vue';
import TheUserJoinView from '@/views/TheUserJoinView.vue';
import { storeToRefs } from 'pinia';
import { useUserInfoStore } from '@/stores/userLogin';
import { Modal } from 'bootstrap';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'main',
            component: TheMainView,
        },
        {
            path: '/board',
            name: 'board',
            component: () => import('../views/TheBoardView.vue'),
            redirect: { name: 'board-list' },
            children: [
                {
                    path: 'list',
                    name: 'board-list',
                    component: () => import('@/components/board/BoardList.vue'),
                },
                {
                    path: 'view/:articleno',
                    name: 'board-view',
                    component: () => import('@/components/board/BoardDetail.vue'),
                },
                {
                    path: 'write',
                    name: 'board-write',
                    component: () => import('@/components/board/BoardWrtieModify.vue'),
                    beforeEnter: (to, from, next) => {
                        const userStore = useUserInfoStore();
                        const { isLogined } = storeToRefs(userStore);
                        if (isLogined.value) {
                            next();
                        } else {
                            next(from);
                            const modalInstance = new Modal(document.querySelector('#loginModal'));
                            modalInstance.show();
                        }
                    },
                },
                {
                    path: 'modify/:articleno',
                    name: 'board-modify',
                    component: () => import('@/components/board/BoardWrtieModify.vue'),
                    beforeEnter: (to, from, next) => {
                        const userStore = useUserInfoStore();
                        const { isLogined } = storeToRefs(userStore);
                        if (isLogined.value) {
                            next();
                        } else {
                            next(from);
                            const modalInstance = new Modal(document.querySelector('#loginModal'));
                            modalInstance.show();
                        }
                    },
                },
            ],
        },
        {
            path: '/join',
            name: 'join',
            component: TheUserJoinView,
        },
        {
            path: '/trip',
            name: 'trip',
            component: () => import('@/views/TheTripListView.vue'),
            redirect: { name: 'trip-list' },
            children: [
                {
                    path: 'list',
                    name: 'trip-list',
                    component: () => import('@/components/trip/TripList.vue'),
                    beforeEnter: (to, from, next) => {
                        const userStore = useUserInfoStore();
                        const { isLogined } = storeToRefs(userStore);
                        if (isLogined.value) {
                            next();
                        } else {
                            next(from);
                            const modalInstance = new Modal(document.querySelector('#loginModal'));
                            modalInstance.show();
                        }
                    },
                },
                {
                    path: 'room/:roomid',
                    name: 'trip-room',
                    component: () => import('@/views/TheTripView.vue'),
                    beforeEnter: (to, from, next) => {
                        const userStore = useUserInfoStore();
                        const { isLogined } = storeToRefs(userStore);

                        if (isLogined.value) {
                            next();
                        } else {
                            next(from);
                            const modalInstance = new Modal(document.querySelector('#loginModal'));
                            modalInstance.show();
                        }
                    },
                },
            ],
        },
    ],
});

export default router;
