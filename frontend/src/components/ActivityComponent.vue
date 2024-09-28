<template>
  <q-card class="q-my-md q-pa-sm shadow-2" style="width: 100%">
    <!-- Activity Name and Description -->
    <q-card-section class="row no-wrap items-center">
      <q-avatar square size="56px" class="q-mr-sm">
        <img :src="image" alt="activity" />
        <!-- Bind the src to the image prop -->
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
      </div>
      <div class="text-caption text-grey-6">
        <q-icon name="schedule" class="q-mr-xs" /> {{ formattedStartTime }} -
        {{ formattedEndTime }}
      </div>
    </q-card-section>

    <!-- Round Images of Users with Flex-Wrap -->
    <div class="text-caption text-grey-6">
      Users: {{ users.length }}/{{ maxPerson }}
    </div>
    <q-card-section class="q-my-md">
      <div class="row wrap">
        <q-avatar
          v-for="(user, index) in users"
          :key="index"
          size="40px"
          rounded
          class="q-mb-sm"
        >
          <img :src="user.avatar" alt="user avatar" />
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
    default: 0, // Assuming end is a UNIX timestamp
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

// Navigate to activity page
async function showActivity() {
  const userId = props.id; //id is the id from th activity @Josefine
  console.log(userId);

  await router.push({ name: 'activityPage' });
}
</script>

<style scoped>
/* Flex wrap for responsive layout */
.row.wrap {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

/* Mobile specific adjustments */
@media (max-width: 600px) {
  .q-card {
    width: 100%;
    /* Ensures card takes full width */
  }

  .row.wrap {
    justify-content: space-evenly;
  }
}
</style>
