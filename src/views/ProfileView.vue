<script setup>
// Προσπέλαση των δεδομένων του χρήστη που έκανε Login.
import { useApplicationStore } from '@/stores/application.js';
const { userData } = useApplicationStore();
</script>

<template>
  <!--Δημιουργία ενοτήτων για το username, το id, το email και των ρόλων του χρήστη στο template.-->
  <div class="bg-body-tertiary">
    <div class="container">
      <div class="row py-4 px-3">
        <div class="col-12">
          <div class="mb-4">
            <h1 class="fs-3">Profile</h1>
          </div>
          <div>
            <p>
              Logged in as: <strong>{{ userData.username }}</strong>
            </p>
            <p>
              User Id: <strong>{{ userData.id }}</strong>
            </p>
            <p>
              User Email: <strong>{{ userData.email }}</strong>
            </p>
            <p>
              User Role: <strong>{{ userData.roles }}</strong>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>