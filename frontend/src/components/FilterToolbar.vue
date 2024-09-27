<template>
  <q-toolbar class="row">
    <q-select v-model="selectedActivity" filled :options="activityTypes" label="Aktivität" class="col-6" />
  </q-toolbar>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const activityTypes = [
  'Speckbrett',
  'Tennis'
];

const selectedActivity = ref('');
</script>
