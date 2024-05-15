<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const route = useRoute();
const userId = ref(route.params.id);
const roleId = ref(route.params.roleId);
// const urlRef = computed(() => {
//   return backendEnvVar +'/api/user/role/' + `${userId.value}/` + `${roleId.value}`;
// });
const urlRef = ref(backendEnvVar+`/api/user/role/${userId.value}/${roleId.value}`);
const authRef = ref(true);
const methodRef = ref("POST");

console.log('userId:', userId.value); 

const { data, performRequest } = useRemoteData(urlRef, authRef, methodRef);

onMounted(() => {
  performRequest();
});
</script>

<template>
  <div>
    <div class="alert alert-success" role="alert">
      <h4 class="alert-heading">Success!</h4>
      <p class="mb-0">{{ data }}!</p>
    </div>
    <router-link :to="{ name: 'users' }" class="btn btn-primary">Back to Users</router-link>
  </div>
</template>
