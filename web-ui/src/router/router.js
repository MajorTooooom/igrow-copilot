import Vue from 'vue';
import Router from 'vue-router';
import HomePage from '../views/HomePage.vue';
import AboutPage from '../views/AboutPage.vue';

Vue.use(Router);

export default new Router({
    routes: [
        {path: '/', name: 'HomePage', component: HomePage},
        {path: '/about', name: 'AboutPage', component: AboutPage}
    ]
});
