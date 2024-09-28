<template>
  <q-page padding class="q-pa-md">
    <q-form class="column full-width" :submit="onSubmit">
      <!-- Category Dropdown -->
      <q-select
        v-model="activity.category"
        :options="categories"
        option-value="id"
        option-label="name"
        label="Kategorie wählen"
        outlined
        dense
        filled
        clearable
        class="full-width"
        @update:model-value="categoryChanged"
      />

      <!-- Activity Dropdown -->
      <q-select
        v-model="activity.activity"
        :options="activities"
        option-value="id"
        option-label="name"
        label="Aktivität wählen"
        outlined
        dense
        filled
        clearable
        class="full-width"
        :disable="activity.category === null"
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
        color="accent"
        class="q-mt-md full-width"
        :disable="!canSubmit()"
      />
    </q-form>
  </q-page>
</template>

<script setup lang="ts">
import { onMounted, Ref, ref } from 'vue';

interface Category {
  id: string;
  name: string;
}

interface Categories {
  categories: Category[];
}

// Array of categories
const categories: Ref<Category[]> = ref([]);

async function fetchCategories() {
  try {
    const response = await fetch(
      'https://api.wasgehtmuenster.xyz:8080/api/category',
      {
        method: 'GET',
        headers: {
          Accept: '*/*', // Set the Accept header to allow all types
          mode: 'no-cors',
        },
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Get the response data
    const data: Categories = (await response.json()) as Categories;
    categories.value = data.categories;

    console.log('Success:', data);
  } catch (error) {
    console.error('Error fetching categories:', error);
  }
}

interface Activity {
  id: string;
  name: string;
  category: string;
  avatar: string;
}

interface Activities {
  activities: Activity[];
}

// Array of activities
const activities: Ref<Activity[]> = ref([]);

// Activity form data
const activity = ref({
  category: null as Category | null,
  activity: null as Activity | null,
  date: new Date().toISOString().split('T')[0], // Format: YYYY-MM-DD
  time: new Date().toTimeString().slice(0, 5), // Format: HH:MM
  minUsers: 1,
  maxUsers: 1,
  description: '',
});

function canSubmit() {
  const v = activity.value;

  return v.category !== null && v.activity !== null && v.maxUsers >= v.minUsers;
}

async function fetchActivities() {
  if (activity.value.category === null) return;

  try {
    const response = await fetch(
      `https://api.wasgehtmuenster.xyz:8080/api/activities?category=${activity.value.category.id}&page=1&size=100`,
      {
        method: 'GET',
        headers: {
          Accept: '*/*', // Set the Accept header to allow all types
          mode: 'no-cors',
        },
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Get the response data
    const data: Activities = (await response.json()) as Activities;
    activities.value = data.activities;

    console.log('Success:', data);
  } catch (error) {
    console.error('Error fetching categories:', error);
  }
}

async function categoryChanged() {
  if (activity.value.category === null) {
    activity.value.activity = null;
    return;
  }

  await fetchActivities();
}

// Form submit function
async function onSubmit() {
  //const sessionToken = ref<string | null>(localStorage.getItem('sessionToken')); // Get the session token from local storage

  try {
    const response = await fetch(
      'https://api.wasgehtmuenster.xyz:8080/api/activities',
      {
        method: 'POST',
        headers: {
          Accept: '*/*', // Set the Accept header to allow all types
          mode: 'no-cors',
        },
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Get the response data
    const data: Activities = (await response.json()) as Activities;
    activities.value = data.activities;

    console.log('Success:', data);
  } catch (error) {
    console.error('Error fetching categories:', error);
  }
}

onMounted(async () => {
  await fetchCategories();
});
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
