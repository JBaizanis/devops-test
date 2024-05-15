<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const applicationId = useRoute().params.id;
const router = useRouter(); // Access the router instance
// const urlRef = computed(() => {
//   return backendEnvVar +'/api/application/' + `${applicationId}`;
// });
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
    <div>
        <h2>Delete Application</h2>
        <p>Are you sure you want to delete this application?</p>
        <button class="btn btn-danger" @click="deleteApplication">Delete</button>
        <router-link :to="{ name: 'my-applications' }" class="btn btn-secondary">Cancel</router-link>
    </div>
</template>