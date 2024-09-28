<template>
  <q-page class="col">
    <div v-for="session in sessions" :key="session.id" class="row">
      <ActivityComponent
        :id="session.id"
        :name="session.name"
        :description="session.description"
        :start="session.start"
        :end="session.end"
        :min-person="session.minPerson"
        :max-person="session.maxPerson"
        :users="session.member"
        :image="session.image"
      />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import ActivityComponent from 'src/components/ActivityComponent.vue';

// Define the interface for a session
interface SessionResponseDto {
  id: string;
  name: string;
  description: string;
  start: number;
  end: number;
  activityId: string;
  minPerson: number;
  maxPerson: number;
  tags: string[];
  creator: string;
  member: string[];
  image?: string; // Optional field for the image URL
}

// Define the interface for the response containing sessions
interface SessionsResponseDto {
  sessions: SessionResponseDto[]; // Array of SessionResponseDto
}

// Create a ref to hold the array of sessions
const sessions = ref<SessionResponseDto[]>([]);

// Fetch the session data
async function fetchSessions() {
  try {
    const response = await fetch('http://45.142.107.241:8080/api/sessions', {
      method: 'GET',
      headers: {
        Accept: '*/*', // Set the Accept header to allow all types
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Get the response data
    const data: SessionsResponseDto =
      (await response.json()) as SessionsResponseDto;

    // Assign only the sessions array to sessions.value
    sessions.value = data.sessions; // Correct assignment
    setActivityImage();
  } catch (error) {
    console.error('Error fetching sessions:', error);
  }
}

// New function to fetch images
function setActivityImage() {
  try {
    // Loop through each session and fetch its image
    for (const session of sessions.value) {
      session.image =
        'http://45.142.107.241:8080/api/activities/' + session.id + '/avatar';
    }
  } catch (e) {
    console.log('error');
  }
}

// Call the function when the component is mounted
onMounted(async () => {
  await fetchSessions();
});
</script>
