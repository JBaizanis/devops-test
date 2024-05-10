import { createRouter, createWebHistory } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';

// Δηλωνουμε τα paths και τα αντιστοιχα components (views) στα οποία αντιστοιχούν
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/logout',
      name: 'logout',
      component: () => import('../views/LogoutView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/users',
      name: 'users',
      component: () => import('../views/UsersView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/edit/:id/:phone/:email/:address',
      name: 'edit-user',
      component: () => import('../views/UserEditView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/delete/:id',
      name: 'delete-user',
      component: () => import('../views/DeleteUserView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/new-user',
      name: 'new-user',
      component: () => import('../views/AddNewUserView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/role-to-user/:id/:roleId',
      name: 'role-to-user',
      component: () => import('../views/AssignRoleView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/applications',
      name: 'applications',
      component: () => import('../views/ApplicationsView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/new-application',
      name: 'new-application',
      component: () => import('../views/AddNewApplicationView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/delete-application/:id',
      name: 'delete-application',
      component: () => import('../views/DeleteApplicationView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/make-decision/:id',
      name: 'make-decision',
      component: () => import('../views/MakeDecisionView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/determine-compensation/:id',
      name: 'determine-compensation',
      component: () => import('../views/DetermineCompensationView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/my-applications',
      name: 'my-applications',
      component: () => import('../views/MyApplicationsView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/my-profile',
      name: 'my-profile',
      component: () => import('../views/ProfileView.vue'),
      meta: { requiresAuth: true }
    }

  ]
});

router.beforeEach((to, from, next) => {
  const { isAuthenticated } = useApplicationStore();
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

  if (requiresAuth && !isAuthenticated) {
    console.log('user not authenticated. redirecting to /login');
    next('/login');
  } else {
    next();
  }
});

export default router;