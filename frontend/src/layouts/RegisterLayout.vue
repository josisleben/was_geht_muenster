<template>
  <q-layout view="lHh Lpr lFf">
    <q-header>
      <MainToolbar />
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import MainToolbar from 'src/components/MainToolbar.vue';
</script>