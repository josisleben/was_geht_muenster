<template>
  <q-page class="col">
    <div v-for="i in 50" :key="i" class="row">
      <ActivityComponent v-for="j in 2" :key="j" class="col-6 q-pa-sm" />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import ActivityComponent from 'src/components/ActivityComponent.vue';
</script>
