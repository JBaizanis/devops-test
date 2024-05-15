<script setup>
import { ref } from "vue";
import { useRemoteData } from "@/composables/useRemoteData.js";
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const router = useRouter(); // Access the router instance

const formDataRef = ref({
  "productionAmount": "",
  "typeOfProduction": "",
  "categoryOfProduction": "",
  "productionLocation": "",
  "damagedProductionAmount": "",
  "longitude": "",
  "latitude": ""
});
const { userData } = useApplicationStore();
// const urlRef = computed(() => {
//   return backendEnvVar + '/api/application/new/' + `${userData.id}`;
// });
const urlRef = ref(backendEnvVar+`/api/application/new/${userData.id}`);
const authRef = ref(true);
const methodRef = ref("POST");


const { data, performRequest } = useRemoteData(urlRef, authRef, methodRef, formDataRef);


const onSubmit = async () => {
    try {
        await performRequest();
        alert(data.value);
        if (data.value === "Application has been successfully saved!"){
            router.push({ name: 'my-applications' });
        }

    } catch (error) {
        console.error('Error adding application:', error);
    }
};
</script>


<template>
    <div>
        <h2>Add New Application</h2>
        <form @submit.prevent="onSubmit">
            <div class="mb-3">
                <label for="productionAmount">Production Amount</label>
                <input type="number" v-model="formDataRef.productionAmount" class="form-control" id="productionAmount" required>
            </div>
            <div class="mb-3">
                <label for="typeOfProduction">Type Of Production</label>
                <input type="text" v-model="formDataRef.typeOfProduction" class="form-control" id="typeOfProduction" required>
            </div>
            <div class="mb-3">
                <label for="categoryOfProduction">Category Of Production</label>
                <input type="text" v-model="formDataRef.categoryOfProduction" class="form-control" id="categoryOfProduction" required>
            </div>
            <div class="mb-3">
                <label for="productionLocation">Production Location</label>
                <input type="text" v-model="formDataRef.productionLocation" class="form-control" id="productionLocation" required>
            </div>
            <div class="mb-3">
                <label for="damagedProductionAmount">Damaged Production Amount</label>
                <input type="number" v-model="formDataRef.damagedProductionAmount" class="form-control" id="damagedProductionAmount" required>
            </div>
            <div class="mb-3">
                <label for="longitude">Longitude</label>
                <input type="number" v-model="formDataRef.longitude" class="form-control" id="longitude" required>
            </div>
            <div class="mb-3">
                <label for="latitude">Latitude</label>
                <input type="number" v-model="formDataRef.latitude" class="form-control" id="latitude" required>
            </div>
            <button type="submit" class="btn btn-primary">Add Application</button>
        </form>
    </div>
</template>
