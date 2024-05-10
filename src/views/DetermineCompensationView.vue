<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';

const route = useRoute();
const applicationId = ref(route.params.id);
const urlRef = ref(`http://localhost:8080/api/application/determineCompensation/${applicationId.value}`);
const authRef = ref(true);
const methodRef = ref("POST");

console.log('applicationId:', applicationId.value);

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
        <router-link :to="{ name: 'applications' }" class="btn btn-primary">Back to Applications</router-link>
    </div>
</template>