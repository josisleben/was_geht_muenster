<template>
  <q-toolbar class="bg-primary text-white justify-center items-center">
    <!-- Logo Image in the Center -->
    <q-btn flat round class="q-mr-sm">
      <img src="/public/Logo_WasGeht.svg" alt="Logo" style="width: 60px" />
    </q-btn>
  </q-toolbar>
  <div>
    <!-- Footer Section -->
    <q-footer class="bg-primary text-white fixed-bottom" height="56px">
      <div class="row justify-around items-center full-width">
        <!-- Filter Button -->
        <q-btn
          flat
          round
          icon="filter_list"
          aria-label="Filter"
          class="text-white"
          @click="showFilterDialog = true"
        />

        <!-- Home Button -->
        <q-btn
          flat
          icon="home"
          aria-label="HOME"
          class="text-white"
          @click="addActivity"
        />
        <!-- Plus Button -->
        <q-btn
          flat
          round
          icon="add_circle"
          aria-label="Add"
          class="text-white"
          @click="addActivity"
        />
      </div>
    </q-footer>

    <!-- Filter Dialog Pop-Up -->
    <q-dialog v-model="showFilterDialog" persistent>
      <q-card class="q-pa-md q-mt-auto filter-popup">
        <q-card-section>
          <div class="text-h6">Filter Options</div>
        </q-card-section>

        <q-card-section>
          <!-- Filter: Type of Activity -->
          <q-select
            v-model="selectedActivityType"
            :options="activityTypes"
            label="Art der Aktivität"
            filled
            dense
            class="q-mb-sm"
          />

          <!-- Filter: Day -->
          <q-select
            v-model="selectedDay"
            :options="days"
            label="Tag"
            filled
            dense
            class="q-mb-sm"
          />

          <!-- Filter: Age Group -->
          <q-select
            v-model="selectedAge"
            :options="ageGroups"
            label="Altergruppe"
            filled
            dense
            class="q-mb-sm"
          />
        </q-card-section>

        <!-- Close Filter Button -->
        <q-card-actions align="center">
          <q-btn
            label="filtern"
            color="primary"
            @click="showFilterDialog = false"
          />
        </q-card-actions>

        <!-- Close Filter Button -->
        <q-card-actions align="center">
          <q-btn
            label="schließen"
            color="secondary"
            @click="showFilterDialog = false"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// State for showing/hiding the filter dialog
const showFilterDialog = ref(false);

// Activity options for the filters
const activityTypes = ref([
  'Volleyball',
  'Speckbrett',
  'Tennis',
  'Yoga',
  'Basketball',
]);
const days = ref([
  'Heute',
  'Morgen',
  'Diese Woche',
  'Dieses Wochenende',
  'Nächste Woche',
]);
const ageGroups = ref(['Alle Alter', 'jung', 'mittel', 'älter']);

// Selected filter values
const selectedActivityType = ref('');
const selectedDay = ref('');
const selectedAge = ref('');

// Placeholder for adding an activity
function addActivity() {
  console.log('Add new activity');
}
</script>

<style scoped>
/* Sticky Footer */
.fixed-bottom {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}

/* Filter Pop-Up Transition */
.filter-popup {
  max-width: 100%;
  border-radius: 16px;
  animation: slide-up 0.4s ease-in-out;
}

/* Smooth slide-up animation */
@keyframes slide-up {
  from {
    transform: translateY(100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}
.q-btn {
  padding: 0;
}
</style>
