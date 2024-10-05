<template>
  <q-page padding class="q-pa-md">
    <q-form class="column full-width" :submit="onSubmit">
      <!-- Category Dropdown -->

      <!-- Description -->
      <q-input
        v-model="activity.name"
        label="Überschrift"
        outlined
        dense
        filled
        type="textarea"
        autogrow
        class="full-width"
      />

      <q-select
        v-model="selectedCategory"
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
        :disable="selectedCategory === null"
      />

      <!-- Time Picker -->
      <q-input
        v-model="activity.start"
        label="Treff-Uhrzeit"
        outlined
        dense
        filled
        type="time"
        class="full-width"
      />

      <q-input
        v-model="activity.end"
        label="geplante-Endzeit"
        outlined
        dense
        filled
        type="time"
        class="full-width"
      />

      <!-- Minimum Users -->
      <q-input
        v-model.number="activity.minPerson"
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
        v-model.number="activity.maxPerson"
        label="Maximale Anzahl an Leuten"
        outlined
        dense
        filled
        type="number"
        :min="activity.minPerson"
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
      />
    </q-form>
  </q-page>
</template>

<script setup lang="ts">
import axios from 'axios';
import { onMounted, Ref, ref } from 'vue';

interface Category {
  id: string;
  name: string;
}

interface Categories {
  categories: Category[];
}

const selectedCategory = ref<Category>();

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

const apiHost: string = process.env.VUE_APP_API_HOST;

// Array of activities
const activities: Ref<Activity[]> = ref([]);

// Activity form data
const activity = ref({
  name: '',
  description: '',
  start: new Date().toTimeString().slice(0, 5), // Format: HH:MM
  end: new Date().toTimeString().slice(0, 5), // Format: HH:MM
  activity: null as Activity | null,
  minPerson: 1,
  maxPerson: 1,
  tags: ['test', 'data'],
});

async function fetchActivities() {
  try {
    const response = await fetch(
      `https://api.wasgehtmuenster.xyz:8080/api/activities?category=${selectedCategory.value?.id}&page=1&size=100`,
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
  if (selectedCategory.value === undefined) {
    activity.value.activity = null;
    return;
  }

  await fetchActivities();
}

async function onSubmit() {
  const sessionToken = localStorage.getItem('sessionToken'); // Get the session token from local storage

  if (sessionToken === null) {
    return;
  }

  const dataToSend = {
    name: activity.value.name,
    description: activity.value.description,
    start: createUnixTimestampInMillis(activity.value.start),
    end: createUnixTimestampInMillis(activity.value.end),
    activityId: activity.value.activity?.id,
    minPerson: activity.value.minPerson,
    maxPerson: activity.value.maxPerson,
    tags: ['test', 'abc'],
  };

  console.log(dataToSend);

  const url = apiHost + '/api/sessions';

  try {
    const response = await axios.post(url, dataToSend, {
      headers: {
        Authorization: sessionToken, // Accessing the string directly
        'Content-Type': 'application/json',
      },
    });

    console.log('Success:', response.data);
  } catch (error) {
    console.error('Error:', error);
  }
}

function createUnixTimestampInMillis(time: string): number {
  // Split the time string into hours and minutes
  const [hours, minutes] = time.split(':').map(Number);

  // Get the current date
  const now = new Date();

  // Create a new Date object with the current date and the specified time
  const combinedDate = new Date(
    now.getFullYear(),
    now.getMonth(),
    now.getDate(),
    hours,
    minutes
  );

  // Get the Unix timestamp in milliseconds
  const unixTimestampInMillis = combinedDate.getTime(); // Returns milliseconds

  return unixTimestampInMillis;
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
