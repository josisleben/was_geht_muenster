<template>
  <q-page class="flex flex-column items-center q-px-md q-py-lg">
    <!-- Loading Screen -->
    <div v-if="isLoading" class="loading-overlay">
      <q-spinner color="primary" size="50px" />
    </div>

    <!-- Always show the card -->
    <q-card v-if="!isLoading" class="my-card shadow-2 rounded-borders">
      <q-card-section>
        <!-- Titel der Aktivität -->
        <div class="text-h4 q-mb-md">Aktivität: {{ activity.title }}</div>

        <!-- Freie Plätze und Mitmachen Button -->
        <q-card-actions class="q-pa-none q-mb-sm row items-center">
          <q-item-label class="text-h6">
            Freie Plätze: <strong>{{ activity.free }}</strong>
          </q-item-label>
          <q-btn
            class="q-mb-md"
            color="primary"
            icon="person_add"
            @click="joinActivity"
          >
            Mitmachen
          </q-btn>
        </q-card-actions>

        <!-- Teilnehmer Liste -->
        <q-item-label class="text-subtitle2 q-mb-xs">
          👥 Teilnehmer:
        </q-item-label>
        <q-list dense class="q-mb-md">
          <q-item
            v-for="(participant, index) in activity.participants"
            :key="index"
            class="q-pb-xs"
          >
            <q-item-section avatar>
              <q-avatar rounded size="56px">
                <q-img :src="participant.image" />
              </q-avatar>
            </q-item-section>
            <q-item-section>
              <q-item-label class="text-body1">{{
                participant.name
              }}</q-item-label>
            </q-item-section>
          </q-item>
        </q-list>

        <!-- Ort, Zeit, Beschreibung -->
        <div class="details q-mt-md">
          <q-item-label class="text-body2 q-mb-sm">
            📍 <strong>Ort:</strong> {{ activity.location }}
          </q-item-label>
          <q-item-label class="text-body2 q-mb-sm">
            🕰️ <strong>Zeit:</strong> {{ activity.time }}
          </q-item-label>
          <q-item-label class="text-body2">
            🖌️ <strong>Beschreibung:</strong> {{ activity.description }}
          </q-item-label>
        </div>
      </q-card-section>
    </q-card>

    <q-dialog v-model="showRegisterDialog">
      <RegisterCard @registration-success-emit="handleRegistrationSuccess" />
    </q-dialog>
  </q-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import RegisterCard from 'src/components/RegisterCard.vue';

const activity = {
  title: 'Volleyball 🏐',
  free: '7 / 10',
  participants: [
    { name: 'Anel Imamovic', image: '../src/assets/profil.jpeg' },
    { name: 'Enrico Koschel', image: '../src/assets/profil.jpeg' },
    { name: 'Andrej Benedicjus', image: '../src/assets/profil.jpeg' },
    { name: 'Nic Markfort', image: '../src/assets/profil.jpeg' },
    { name: 'Josefine Zwach', image: '../src/assets/profil.jpeg' },
    { name: 'Profi Volleyballer', image: '../src/assets/profil.jpeg' },
    { name: 'Nina Müller', image: '../src/assets/profil.jpeg' },
  ],
  location: 'Stadtpark Münster',
  time: 'Sonntag, 29.09.2024 15:00 Uhr',
  description:
    'Wir suchen für Sonntag noch vier Leute, die Bock haben Volleyball zu spielen. Jeder ist erwünscht :))',
};

const showRegisterDialog = ref(false);
const sessionToken = ref<string | null>(localStorage.getItem('sessionToken')); // Get the session token from local storage
const isLoading = ref(false); // New loading state

function joinActivity() {
  sessionToken.value = localStorage.getItem('sessionToken');
  if (sessionToken.value === null) {
    console.log('You will be redirected!');
    showRegisterDialog.value = true; // Show dialog if sessionToken exists
  } else {
    addUserToSession(); // Call function if no sessionToken exists
  }
}

function addUserToSession() {
  console.log('hello world!'); // Placeholder action for users without a sessionToken
}

function handleRegistrationSuccess(registrationSuccess: boolean) {
  isLoading.value = true; // Start loading
}
</script>

<style scoped>
.my-card {
  width: 100%;
  max-width: 500px;
  margin: 20px;
}

.rounded-borders {
  border-radius: 12px;
}

.q-mb-sm {
  margin-bottom: 8px;
}

.q-mb-md {
  margin-bottom: 16px;
}

.q-mb-xs {
  margin-bottom: 4px;
}

.q-px-md {
  padding-left: 16px;
  padding-right: 16px;
}

.q-py-lg {
  padding-top: 32px;
  padding-bottom: 32px;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999; /* Ensure it appears above other content */
}
</style>
