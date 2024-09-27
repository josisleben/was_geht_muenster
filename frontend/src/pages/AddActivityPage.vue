<template>
  <q-page padding class="q-pa-md">
    <q-form class="column full-width" :submit="onSubmit">
      <!-- Category Dropdown -->
      <q-select
        v-model="activity.category"
        :options="categories"
        label="Kategorie wählen"
        outlined
        dense
        filled
        clearable
        class="full-width"
      />

      <!-- Date Picker -->
      <q-input
        v-model="activity.date"
        label="Tag"
        outlined
        dense
        filled
        type="date"
        class="full-width"
      />

      <!-- Time Picker -->
      <q-input
        v-model="activity.time"
        label="Treff-Uhrzeit"
        outlined
        dense
        filled
        type="time"
        class="full-width"
      />

      <!-- Minimum Users -->
      <q-input
        v-model.number="activity.minUsers"
        label="Mindest Anzahl an Leuten"
        outlined
        dense
        filled
        type="number"
        :min="1"
        class="full-width"
      />

      <!-- Maximum Users -->
      <q-input
        v-model.number="activity.maxUsers"
        label="Maximale Anzahl an Leuten"
        outlined
        dense
        filled
        type="number"
        :min="activity.minUsers"
        class="full-width"
      />

      <!-- Description -->
      <q-input
        v-model="activity.description"
        label="Beschreibung (optional)"
        outlined
        dense
        filled
        type="textarea"
        autogrow
        class="full-width"
      />

      <!-- Submit Button -->
      <q-btn
        type="submit"
        label="Create Activity"
        color="primary"
        class="q-mt-md full-width"
      />
    </q-form>
  </q-page>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// Array of categories
const categories = ['Volleyball', 'Basketball', 'Football', 'Tennis'];

// Activity form data
const activity = ref({
  category: '',
  date: '',
  time: '',
  minUsers: 1,
  maxUsers: 1,
  description: '',
});

// Form submit function
function onSubmit() {
  if (activity.value.maxUsers >= activity.value.minUsers) {
    console.log('Activity Created', activity.value);
    // Handle form submission (e.g., API call or navigation)
  } else {
    console.log('Error: Max users cannot be less than Min users');
  }
}
</script>

<style scoped>
/* Full-width adjustments */
.q-page {
  width: 100%;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh; /* Ensures it takes up the full height */
}

.full-width {
  width: 100%;
}
</style>
