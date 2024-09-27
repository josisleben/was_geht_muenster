<template>
  <q-card class="q-my-md q-pa-sm shadow-2" style="width: 100%">
    <!-- Activity Name and Description -->
    <q-card-section class="row no-wrap items-center">
      <q-avatar square size="56px" class="q-mr-sm">
        <img src="/coolPicVolleyball.avif" alt="activity" />
      </q-avatar>
      <div>
        <div class="text-h6">{{ activity.name }}</div>
        <div class="text-subtitle2 text-grey-7">{{ activity.description }}</div>
      </div>
    </q-card-section>

    <!-- Date/Time -->
    <q-card-section>
      <div class="text-caption text-grey-6 q-mt-sm">
        <q-icon name="event" class="q-mr-xs" /> {{ activity.date }}
      </div>
      <div class="text-caption text-grey-6">
        <q-icon name="schedule" class="q-mr-xs" /> {{ activity.time }}
      </div>
    </q-card-section>

    <!-- Round Images of Users with Flex-Wrap -->
    <div class="text-caption text-grey-6">
      Users: {{ activity.currentUsers }}/{{ activity.maxUsers }}
    </div>
    <q-card-section class="q-my-md">
      <div class="row wrap">
        <q-avatar
          v-for="(user, index) in activity.users"
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
import { useRouter } from 'vue-router';

const router = useRouter();

const activity = {
  name: 'Volleyball',
  description:
    'Wir brauchen noch 3 Leute für Morgen Mittag! Wer Lust hat, kann gerne teilnehmen, egal wie erfahren!',
  currentUsers: 7, // Current number of users in the group
  maxUsers: 10, // Maximum number of allowed users
  date: '28.09.2024', // Date of the activity
  time: '14:00', // Time of the activity
  users: [
    // List of users with avatar images
    { avatar: 'https://randomuser.me/api/portraits/men/1.jpg' },
    { avatar: 'https://randomuser.me/api/portraits/women/2.jpg' },
    { avatar: 'https://randomuser.me/api/portraits/men/3.jpg' },
    { avatar: 'https://randomuser.me/api/portraits/women/4.jpg' },
    { avatar: 'https://randomuser.me/api/portraits/men/5.jpg' },
    { avatar: 'https://randomuser.me/api/portraits/women/6.jpg' },
    { avatar: 'https://randomuser.me/api/portraits/men/7.jpg' },
  ],
};

async function showActivity() {
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
