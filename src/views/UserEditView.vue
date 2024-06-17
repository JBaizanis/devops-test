<script setup>
/* Το component συνδέεται με το κατάλληλο endpoint στο backend για να προσπελάσει τα στοιχεία του εκάστοτε χρήστη και να πραγματοποιήσει
   συγκρίσεις με τα στοιχεία που εισάχθηκαν, ούτως ώστε είτε να τα ενημερώσει, είτε να εμφανίσει μήνυμα σφάλματος ενημέρωσης. */
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const formData = ref({
  address: "",
  phone: "",
  email: ""
});

const userId = useRoute().params.id;
const initialPhone = useRoute().params.phone;
const initialEmail = useRoute().params.email;
const initialAddress = useRoute().params.address;
const router = useRouter(); // Access the router instance

const urlRef = ref(backendEnvVar+`/api/user/${userId}`);
const authRef = ref(true);
const methodRef = ref("PUT");

const { data, performRequest } = useRemoteData(urlRef, authRef, methodRef, formData);

const onSubmit = async () => {
  // Check if each field is changed and update formData
  if (formData.value.address === "") {
    formData.value.address = initialAddress;
  }
  if (formData.value.phone === "") {
    formData.value.phone = initialPhone;
  }
  if (formData.value.email === "") {
    formData.value.email = initialEmail;
  }

  try {
    await performRequest();
      alert(data.value);
      if(data.value === "User updated successfully"){
        router.push({ name: 'users' }); // Redirect to the "users" view after successful edit
      }
  } catch (error) {
    console.error('Error editing user:', error);
  }
};
</script>

<template>
  <!--Δημιουργία ενός table με τα κατάλληλα πεδία εισαγωγής για την ενημέρωση στοιχείων του επιλεγμένου χρήστη.-->
  <div>
    <h2>Edit User</h2>
    <form @submit.prevent="onSubmit">
      <table class="table">
        <tbody>
        <tr>
          <td><label for="address" class="form-label">Address</label></td>
          <td><input type="text" v-model="formData.address" class="form-control" id="address"></td>
        </tr>
        <tr>
          <td><label for="phone" class="form-label">Phone</label></td>
          <td><input type="text" v-model="formData.phone" class="form-control" id="phone"></td>
        </tr>
        <tr>
          <td><label for="email" class="form-label">Email</label></td>
          <td><input type="email" v-model="formData.email" class="form-control" id="email"></td>
        </tr>
        </tbody>
      </table>
      <button class="btn btn-primary" type="submit">Save Changes</button>
    </form>
  </div>
</template>
