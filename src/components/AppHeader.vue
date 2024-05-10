<script setup>
import { RouterLink } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const applicationStore = useApplicationStore();
const { userData } = useApplicationStore();
</script>

<!--Δηλώνουμε τα tabs στο menu και ανάλογα με τον ρόλο εμφανίζονται τα αντίστοιχα tabs-->
<template>
  <header class="text-bg-dark">
    <div class="container">
      <div class="d-flex flex-wrap justify-content-center py-2 px-3">
        <a
            href="/"
            class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none"
        >
          <span class="fs-5 fw-bolder text-white">Farmer Compensation System</span>
        </a>
        <ul class="nav nav-pills">
          <li class="nav-item" v-if="applicationStore.isAuthenticated === true">
            <router-link :to="{ name: 'home' }" class="nav-link text-white"
            >Home</router-link
            >
          </li>
          <li class="nav-item" v-if="applicationStore.isAuthenticated === true && userData.roles == 'ROLE_ADMIN'">
            <router-link :to="{ name: 'users' }" class="nav-link text-white">Users</router-link>
          </li>
          <li class="nav-item" v-if="applicationStore.isAuthenticated === true && userData.roles == 'ROLE_INSPECTOR'">
            <router-link :to="{ name: 'applications' }" class="nav-link text-white"
            >Applications</router-link
            >
          </li>
          <li class="nav-item" v-if="applicationStore.isAuthenticated === true && userData.roles == 'ROLE_FARMER'">
                <router-link :to="{ name: 'my-applications' }" class="nav-link text-white"
                >MyApplications</router-link
                >
            </li>
          <li class="nav-item" v-if="applicationStore.isAuthenticated === true">
            <router-link :to="{ name: 'my-profile' }" class="nav-link text-white"
            >MyProfile</router-link
            >
          </li>
          <li class="nav-item" v-if="applicationStore.isAuthenticated === false">
            <router-link :to="{ name: 'login' }" class="nav-link text-white"
            >Login</router-link
            >
          </li>
          <li class="nav-item" v-if="applicationStore.isAuthenticated === true">
            <router-link :to="{ name: 'logout' }" class="nav-link text-white"
            >Logout</router-link
            >
          </li>
        </ul>
      </div>
    </div>
  </header>
</template>