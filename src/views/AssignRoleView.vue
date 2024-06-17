<script setup>
/* Το component συνδέεται με το κατάλληλο endpoint στο backend για να προσπελάσει τον ρόλο του εκάστοτε χρήστη και να του δώσει
   τον ρόλο ο οποίος επιλέχθηκε από το "users" view. */
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const route = useRoute();
const userId = ref(route.params.id);
const roleId = ref(route.params.roleId);

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
  <!--Δημιουργία ενός alert επιτυχίας για την αλλαγή του ρόλου του χρήστη και ενός κουμπιού για επιστροφή στο "users" view.-->
  <div>
    <div class="alert alert-success" role="alert">
      <h4 class="alert-heading">Success!</h4>
      <p class="mb-0">{{ data }}!</p>
    </div>
    <router-link :to="{ name: 'users' }" class="btn btn-primary">Back to Users</router-link>
  </div>
</template>
