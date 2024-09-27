<template>
  <q-page class="row items-center justify-evenly">
    <test></test>
    <!-- This is your custom component -->
  </q-page>
</template>

<script setup lang="ts">
import test from 'components/testPage.vue';

defineOptions({
  name: 'TestPage',
});
</script>
