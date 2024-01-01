import { createApp } from 'vue';
import { createPinia } from 'pinia';
import MasonryWall from '@yeger/vue-masonry-wall';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

//import './App.css';
import './App.css';

import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(MasonryWall);
app.use(router);
app.use(createPinia());
app.mount('#app');
