<script setup>
/* Το component συνδέεται με το κατάλληλο endpoint στο backend για να προσθέσει ένα νέο χρήστη.
   Σε περίπτωση επιτυχίας, εμφανίζει σχετικό μήνυμα και προσθέτει το νέο χρήστη,
   ενώ σε περίπτωση αποτυχίας απλώς εμφανίζει σχετικό μήνυμα. */
import { ref } from "vue";
import { useRouter } from 'vue-router';
import { useRemoteData } from "@/composables/useRemoteData.js";
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const router = useRouter(); // Access the router instance

const formDataRef = ref({
  "username": "",
  "password": "",
  "firstName": "",
  "lastName": "",
  "phone": "",
  "email": "",
  "address": ""
});

const urlRef = ref(backendEnvVar+'/api/user/new');
const authRef = ref(true);
const methodRef = ref("POST");

const { data, performRequest } = useRemoteData(urlRef, authRef, methodRef, formDataRef);

const onSubmit = async () => {
  try {
    await performRequest();
      alert(data.value);
      if (data.value === "User has been saved successfully!"){
          router.push({ name: 'users' });
      }
  } catch (error) {
    console.error('Error adding user:', error);
  }
};
</script>

<template>
  <!--Δημιουργία μιας φόρμας στην οποία ο admin δίνει ως input τα στοιχεία του καινούριου χρήστη.
    Επιπλέον, δημιουργία ενός κουμπιού για το confirmation της πρόσθεσης του νέου χρήστη.-->
  <div>
    <h2>Add New User</h2>
    <form @submit.prevent="onSubmit">
      <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" v-model="formDataRef.username" class="form-control" id="username" required>
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" v-model="formDataRef.password" class="form-control" id="password" required>
      </div>
      <div class="mb-3">
        <label for="address" class="form-label">Address</label>
        <input type="text" v-model="formDataRef.address" class="form-control" id="address" required>
      </div>
      <div class="mb-3">
        <label for="phone" class="form-label">Phone</label>
        <input type="text" v-model="formDataRef.phone" class="form-control" id="phone" required>
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" v-model="formDataRef.email" class="form-control" id="email" required>
      </div>
      <div class="mb-3">
        <label for="firstName" class="form-label">First Name</label>
        <input type="text" v-model="formDataRef.firstName" class="form-control" id="firstName" required>
      </div>
      <div class="mb-3">
        <label for="lastName" class="form-label">Last Name</label>
        <input type="text" v-model="formDataRef.lastName" class="form-control" id="lastName" required>
      </div>
      <button type="submit" class="btn btn-primary">Add User</button>
    </form>
  </div>
</template>
