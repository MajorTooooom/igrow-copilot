import Vue from 'vue';
import Router from 'vue-router';
import HomePage from '../views/HomePage.vue';
import ConnMenuPage from '../views/ConnMenuPage.vue';
import TableMenuPage from '../views/TableMenuPage.vue';
import GenMenuPage from '../views/GenMenuPage.vue';
import AboutPage from '../views/AboutPage.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {path: '/', name: 'HomePage', component: HomePage},
        {path: '/conn-menu', name: 'ConnMenuPage', component: ConnMenuPage},
        {path: '/table-menu', name: 'TableMenuPage', component: TableMenuPage},
        {path: '/gen-menu', name: 'GenMenuPage', component: GenMenuPage},
        {path: '/about', name: 'AboutPage', component: AboutPage}
    ]
});
