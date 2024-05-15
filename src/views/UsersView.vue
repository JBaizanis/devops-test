<script setup>
import { ref, onMounted } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND; 

// const urlRef = computed(() => {
//   return backendEnvVar +'/api/user';
// });
const urlRef = ref(backendEnvVar+'/api/user');
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
            <h1 class="fs-3">Users</h1>
          </div>
          <div>
            <table class="table">
              <thead>
              <tr>
                <th>Id</th>
                <th>Username</th>
                <th>Password</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Address</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody v-if="data && data.length > 0">
              <tr v-for="user in data" :key="user.id">
                <td>{{ user.id }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.password }}</td>
                <td>{{ user.firstName }}</td>
                <td>{{ user.lastName }}</td>
                <td>{{ user.phone }}</td>
                <td>{{ user.email }}</td>
                <td>{{ user.address }}</td>
                <td>
                  <div class="btn-group">
                    <router-link :to="{ name: 'edit-user', params: { id: user.id, phone: user.phone, email: user.email, address: user.address, data: data } }" class="btn btn-primary">Edit</router-link>
                    <router-link :to="{ name: 'delete-user', params: { id: user.id } }" class="btn btn-danger">Delete</router-link>
                  </div>
                  <div class="mt-2">
                    <router-link :to="{ name: 'role-to-user', params: { id: user.id, roleId: 1 } }" class="btn btn-outline-primary btn-sm">Assign Farmer Role</router-link>
                    <router-link :to="{ name: 'role-to-user', params: { id: user.id, roleId: 2 } }" class="btn btn-outline-info btn-sm">Assign Inspector Role</router-link>
                  </div>
                </td>
              </tr>
              </tbody>
              <tbody v-else>
              <tr>
                <td colspan="8">No users available</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="mt-4">
          <!-- Button to navigate to the new user creation view -->
          <router-link :to="{ name: 'new-user' }" class="btn btn-success">Add New User</router-link>
        </div>
      </div>
    </div>
  </div>
</template>
