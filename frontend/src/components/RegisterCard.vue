<template>
  <q-card v-if="!registrationSuccess" class="my-card">
    <q-card-section>
      <div class="text-h4 q-mb-md">Registrieren</div>

      <!-- First Name -->
      <q-input
        v-model="form.username"
        label="Username"
        filled
        class="q-mb-md"
      />

      <!-- Birthday -->
      <q-input
        v-model="form.birthDate"
        label="Geburtstag"
        type="date"
        filled
        class="q-mb-md"
      />

      <!-- Image Uploader -->
      <q-uploader
        label="Profilbild"
        url="http://localhost:4444/upload"
        color="teal"
        flat
        bordered
        class="q-mb-md full-width"
        @added="onImageUpload"
      />

      <!-- Submit Button -->
      <q-btn color="green" @click="submitForm">Registrieren</q-btn>
    </q-card-section>
  </q-card>
</template>

<script setup lang="ts">
import { ref } from 'vue';

interface FormData {
  birthDate: string; // Changed to string to match the input type
  username: string;
  avatar: string | null; // Now stores base64 string
}

const form = ref<FormData>({
  username: '',
  birthDate: '', // Initialize as an empty string
  avatar: null, // Will hold the base64 string of the image
});

interface RegisterResponse {
  id: string;
  sessionToken: string;
}

const emit =
  defineEmits<
    (event: 'registration-success-emit', registrationSuccess: boolean) => void
  >();

const registrationSuccess = ref(false); // State variable to track registration success

// Handle image upload event
function onImageUpload(images: readonly File[]): void {
  const file = images[0];

  console.log(file);

  // Use FileReader to read the file as a base64 string
  const reader = new FileReader();
  reader.onload = (event) => {
    const base64String = event.target?.result as string;

    // Remove the prefix and store the raw base64 string
    form.value.avatar = base64String.split(',')[1]; // Get only the base64 part
  };
  reader.readAsDataURL(file); // Convert file to base64 string
}

// Define a function to handle form submission
async function submitForm(): Promise<void> {
  // Convert birthDate from string to Unix timestamp
  const birthDateTimestamp = new Date(form.value.birthDate).getTime() / 1000; // Convert to seconds

  const formData = {
    username: form.value.username,
    birthDate: birthDateTimestamp, // Now it's a Unix timestamp
    avatar: form.value.avatar, // Image is already a base64 string
  };

  try {
    const response: Response = await fetch(
      'http://localhost:8080/api/v1/user/auth',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      }
    );

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`);
    }

    const result: RegisterResponse =
      (await response.json()) as RegisterResponse; // Get the JSON directly
    console.log('Registration successful!', result);

    // Store the session token
    storeSessionToken(result);

    // Set registration success to true
    registrationSuccess.value = true;
    //push to parent to close the Component after successull registration
    emit('registration-success-emit', true);
  } catch (error: unknown) {
    if (error instanceof Error) {
      console.error('Form submission error:', error.message);
      console.log(formData);
    } else {
      console.error('Unknown error occurred during form submission.');
    }
  }
}

// Store the session token from the response
function storeSessionToken(response: RegisterResponse): void {
  // Store the session token in local storage
  localStorage.setItem('sessionToken', response.sessionToken);
  console.log('Session Token stored in local storage:', response.sessionToken);
}
</script>

<style scoped>
.my-card {
  width: 100%;
  max-width: 400px;
  margin: 20px;
}

.success-message {
  text-align: center;
  margin-top: 20px;
}
</style>
