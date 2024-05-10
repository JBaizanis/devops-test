import { ref } from 'vue';
import { useApplicationStore } from '@/stores/application.js';

const store = useApplicationStore();

export function useRemoteData(urlRef, authRef, methodRef = ref("GET"), bodyRef = ref(null)) {
    const data = ref(null);
    const error = ref(null);
    const loading = ref(false);

    const performRequest = () => {
        let contentType;
        const headers = {
            'Content-Type': 'application/json'
        };

        if (authRef.value === true) {
            headers['Authorization'] = 'Bearer ' + store.userData.accessToken;
        }

        const config = {
            method: methodRef.value,
            headers: headers,
        };

        if (bodyRef.value !== null) {
            config.body = JSON.stringify(bodyRef.value);
        }

        loading.value = true;

        return fetch(urlRef.value, config)
            .then((response) => {
                if (response.ok) {
                    // Handle different methods accordingly
                    switch (methodRef.value) {
                        case 'GET':
                        case 'POST':
                        case 'PUT':
                        case 'DELETE':

                            contentType = response.headers.get('Content-Type');

                            // Αν υπάρχει στο header το application/json τότε χειριζόμαστε το response που δεχόμαστε από το backend σαν json, αλλιώς σαν text.
                            if (contentType && contentType.includes('application/json')) {
                                return response.json().then((responseData) => {
                                    data.value = responseData;
                                });
                            } else {
                                return response.text().then((responseData) => {
                                    if (responseData != null) {
                                        data.value = responseData;
                                    }
                                });
                            }

                        default:
                            break;
                    }
                } else {
                    throw new Error(`Request failed with status: ${response.status}`);
                }
            })
            .catch((err) => {
                error.value = err;
            })
            .finally(() => {
                loading.value = false;
            });
    };

    return { data, error, loading, performRequest };
}
