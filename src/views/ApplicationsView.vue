<script setup>
// Το component εμφανίζει το σύνολο όλων των applications, αφότου επικοινωνήσει με το backend.
import { ref, onMounted } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

const urlRef = ref(backendEnvVar+'/api/application');
const authRef = ref(true);
const { data, performRequest } = useRemoteData(urlRef, authRef);

onMounted(() => {
    performRequest();
});
</script>

<template>
    <!--Δημιουργία ενός table που εμφανίζει τα στοιχεία όλων των applications
      και προσφέρει επιλογές για την λήψη απόφασης ("MakeDecision" view) και για τον καθορισμό αποζημίωσης ("DetermineCompensation" view).
      Σε περίπτωση που δεν υπάρχουν application προς έλεγχο, εμφανίζεται ενημερωτικό μήνυμα.-->
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
                                    <div class="mt-2">
                                        <router-link :to="{ name: 'make-decision', params: { id: application.id } }" class="btn btn-outline-primary btn-sm">Make Decision</router-link>
                                        <router-link :to="{ name: 'determine-compensation', params: { id: application.id, compensationAmount: application.compensationAmount } }" class="btn btn-outline-info btn-sm">Determine Compensation</router-link>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                            <tbody v-else>
                            <tr>
                                <td colspan="8">No unchecked applications available</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>