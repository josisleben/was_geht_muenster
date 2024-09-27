<template>
  <q-layout view="lHh Lpr lFf">
    <q-header>
      <MainToolbar />
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>

    <q-footer>
      <BottomToolbar />
    </q-footer>
  </q-layout>
</template>

<script setup lang="ts">
import BottomToolbar from 'src/components/BottomToolbar.vue';
import MainToolbar from 'src/components/MainToolbar.vue';
</script>
