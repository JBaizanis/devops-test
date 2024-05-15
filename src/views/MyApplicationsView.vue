<script setup>
import { ref, onMounted } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
import { useApplicationStore } from '@/stores/application.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const { userData } = useApplicationStore();
// const route = useRoute();
// const urlRef = computed(() => {
//   return backendEnvVar +'/api/application/user/' + `${userData.id}`;
// });
const urlRef = ref(backendEnvVar+`/api/application/user/${userData.id}`);
const authRef = ref(true);
const { data, performRequest } = useRemoteData(urlRef, authRef);

onMounted(() => {
    performRequest();
});
</script>

<template>
    <div class="bg-body-tertiary">
        <div class="container">
            <div class="row py-4 px-3">
                <div class="col-12">
                    <div class="mb-4">
                        <h1 class="fs-3">Applications</h1>
                    </div>
                    <div>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Application Id</th>
                                <th>Production Amount</th>
                                <th>Type of Production</th>
                                <th>Category of Production</th>
                                <th>Production Location</th>
                                <th>Damaged Production Amount</th>
                                <th>Decision</th>
                                <th>Longitude</th>
                                <th>Latitude</th>
                                <th>Compensation Amount</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody v-if="data && data.length > 0">
                            <tr v-for="application in data" :key="application.id">
                                <td>{{ application.id }}</td>
                                <td>{{ application.productionAmount }}</td>
                                <td>{{ application.typeOfProduction }}</td>
                                <td>{{ application.categoryOfProduction }}</td>
                                <td>{{ application.productionLocation }}</td>
                                <td>{{ application.damagedProductionAmount }}</td>
                                <td>{{ application.decision }}</td>
                                <td>{{ application.longitude }}</td>
                                <td>{{ application.latitude }}</td>
                                <td>{{ application.compensationAmount }}</td>
                                <td>
                                    <div class="btn-group">
                                        <router-link :to="{ name: 'delete-application', params: { id: application.id, decision: application.decision} }" class="btn btn-danger">Delete</router-link>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                            <tbody v-else>
                            <tr>
                                <td colspan="8">No applications available</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="mt-4">
                    <router-link :to="{ name: 'new-application', params: { id: userData.id } }" class="btn btn-success">Add New Application</router-link>
                </div>
            </div>
        </div>
    </div>
</template>