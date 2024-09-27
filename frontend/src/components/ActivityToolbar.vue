<template>
  <!-- Footer Section -->
  <q-footer class="bg-primary text-white fixed-bottom" height="56px">
    <div class="row full-width">
      <!-- Home Button -->
      <q-btn
        flat
        icon="home"
        aria-label="HOME"
        class="col text-white full-width"
        @click="goHome"
      />
    </div>
  </q-footer>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

async function goHome() {
  await router.push({ name: 'mainPage' });
}
</script>

<style scoped lang="scss">
/* Sticky Footer */
.fixed-bottom {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}
</style>
