<template>
  <q-card class="q-my-md q-pa-sm shadow-2" style="width: 100%">
    <!-- Activity Name and Description -->
    <q-card-section class="row no-wrap items-center">
      <q-avatar square size="56px" class="q-mr-sm">
        <img :src="image" alt="activity" />
      </q-avatar>
      <div>
        <div class="text-h6">{{ name }}</div>
        <div class="text-subtitle2 text-grey-7">{{ description }}</div>
      </div>
    </q-card-section>

    <!-- Date/Time -->
    <q-card-section>
      <div class="text-caption text-grey-6 q-mt-sm">
        <q-icon name="event" class="q-mr-xs" /> {{ formattedStartDate }}
        <q-icon name="schedule" class="q-mr-xs" /> {{ formattedStartTime }} -
        {{ formattedEndTime }}
      </div>
    </q-card-section>

    <!-- Aktueller Status der Nutzer -->
    <q-card-section class="text-caption text-grey-6 q-my-md flex items-center">
      <q-icon name="people" class="q-mr-xs text-grey-8" />
      <p class="font-semibold text-lg">
        Gerade angemeldet:
        <span class="text-accent">{{ users.length }}</span> /
        <span class="text-grey-8">{{ maxPerson }}</span>
      </p>
    </q-card-section>

    <!-- Testimonial Avatars View (X direction stacking) -->
    <q-card-section class="testimonial-avatars q-my-md">
      <div class="row wrap">
        <q-avatar
          v-for="(user, index) in userimages"
          :key="index"
          size="40px"
          rounded
          class="q-mr-xs q-mb-xs"
        >
          <img :src="userimages[index]" alt="user avatar" />
        </q-avatar>
      </div>
    </q-card-section>

    <!-- Open Button -->
    <q-card-actions align="center">
      <q-btn
        color="accent"
        label="Anzeigen"
        class="full-width q-my-xs"
        @click="showActivity"
      />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps({
  name: {
    type: String,
    default: 'Unnamed Activity',
  },
  description: {
    type: String,
    default: 'No description available.',
  },
  start: {
    type: Number,
    default: 0, // Assuming start is a UNIX timestamp
  },
  end: {
    type: Number,
    default: 0,
  },
  minPerson: {
    type: Number,
    default: 1,
  },
  maxPerson: {
    type: Number,
    default: 10,
  },
  users: {
    type: Array,
    default: () => [],
  },
  image: {
    type: String,
    default: () => 'no pic here',
  },
  id: {
    type: String,
    default: () => 'no id to be found',
  },
  userimages: {
    type: Array,
    default: () => [],
  },
});

// Router for navigation
const router = useRouter();

// Computed properties for formatted date/time
const formattedStartDate = computed(() =>
  new Date(props.start).toLocaleDateString()
);
const formattedStartTime = computed(() =>
  new Date(props.start).toLocaleTimeString()
);

const formattedEndTime = computed(() =>
  new Date(props.end).toLocaleTimeString()
);

async function showActivity() {
  const userId = props.id; // id is the id from the activity
  await router.push({
    name: 'activityPage',
    params: { id: userId }, // Append userId as a route parameter
  });
}
</script>

<style scoped>
/* Flex wrap for responsive layout */
.row.wrap {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start; /* Ensures they align to the left */
}

/* Stacking avatars in the X direction (horizontal alignment) */
.row.horizontal-avatars {
  display: flex;
  flex-wrap: nowrap;
  justify-content: flex-start; /* Ensures they align to the left */
  overflow-x: auto; /* Scroll if there are too many avatars */
}

.testimonial-avatars {
  padding-left: 24px; /* Align the testimonial avatars with other sections */
}

/* Mobile specific adjustments */
@media (max-width: 600px) {
  .q-card {
    width: 100%; /* Ensures card takes full width */
  }
}
</style>
