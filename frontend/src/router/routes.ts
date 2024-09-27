import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        path: '',
        name: 'mainPage',
        component: () => import('pages/IndexPage.vue'),
      },
    ],
  },
  {
    path: '/activity',

    component: () => import('layouts/ActivityLayout.vue'),
    children: [
      {
        path: '',
        name: 'activityPage',
        component: () => import('pages/ActivityPage.vue'),
      },
    ],
  },
  {
    path: '/register',

    component: () => import('layouts/RegisterLayout.vue'),
    children: [
      {
        path: '',
        name: 'registerPage',
        component: () => import('pages/RegisterPage.vue'),
      },
    ],
  },
  {
    path: '/profile',

    component: () => import('layouts/ProfilLayout.vue'),
    children: [
      {
        path: '',
        name: 'profilePage',
        component: () => import('pages/ProfilPage.vue'),
      },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
