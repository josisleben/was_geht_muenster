<template>
  <q-page class="col">
    <div v-for="i in 50" :key="i" class="row">
      <ActivityComponent />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import ActivityComponent from 'src/components/ActivityComponent.vue';
</script>
