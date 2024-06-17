<script setup>
// Το component με το κλικ του κουμπιού αναλαμβάνει να αποσυνδέσει τον τρέχον χρήστη και να τον επιστρέψει στην οθόνη της σύνδεσης (Login).
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';

const router = useRouter();
const { clearUserData } = useApplicationStore();

const loading = ref(false);

const onFormSubmit = () => {
  loading.value = true;
  clearUserData();
  setTimeout(function () {}, 2000);
  router.push({ name: 'login' });
};
</script>

<template>
  <!--Δημιουργία ενός κουμπιού για την αποσύνδεση του χρήστη-->
  <div class="bg-body-tertiary">
    <div class="container">
      <div class="row py-4 px-3">
        <div class="col-4">
          <div class="mb-4">
            <h1 class="fs-3">Logout</h1>
          </div>
          <div class="spinner-border" role="status" v-if="loading">
            <span class="visually-hidden">Loading...</span>
          </div>
          <form v-else>
            <button @click="onFormSubmit" type="submit" class="btn btn-primary">
              Logout
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
