<script setup>
/* Το component συνδέεται με το κατάλληλο endpoint στο backend για να προσπελάσει το application που ο farmer επέλεξε να διαγράψει
   και να το διαγράψει αφότου δωθεί η κατάλληλη έγκριση. Αν δεν δωθεί, επιστρέφει τον χρήστη στο "MyApplications" view. */
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const applicationId = useRoute().params.id;
const router = useRouter(); // Access the router instance

const urlRef = ref(backendEnvVar+`/api/application/${applicationId}`);
const authRef = ref(true);
const methodRef = ref("DELETE");


const { data, performRequest } = useRemoteData(urlRef, authRef, methodRef);

const deleteApplication = async () => {
    try {
        await performRequest();
        router.push({ name: 'my-applications' }); // Redirect to the "users" view
        alert(data.value);
    } catch (error) {
        console.error('Error deleting application:', error);
    }
};

</script>


<template>
    <!--Δημιουργία ενός prompt ερώτησης για την διαγραφή του application και δύο κουμπιών για την διαγραφή ή την επιστροφή αντίστοιχα. -->
    <div>
        <h2>Delete Application</h2>
        <p>Are you sure you want to delete this application?</p>
        <button class="btn btn-danger" @click="deleteApplication">Delete</button>
        <router-link :to="{ name: 'my-applications' }" class="btn btn-secondary">Cancel</router-link>
    </div>
</template>