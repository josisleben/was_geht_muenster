<template>
  <q-page class="col">
    <div v-for="(session, index) in sessions" :key="session.id" class="row">
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
        :userimages="userImages[index]"
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
  image: string;
}

// Define the interface for the response containing sessions
interface SessionsResponseDto {
  sessions: SessionResponseDto[]; // Array of SessionResponseDto
}

// Create a ref to hold the array of sessions
const sessions = ref<SessionResponseDto[]>([]);
const userImages = ref<string[][]>([]); // Change to an array of arrays

// Fetch the session data
async function fetchSessions() {
  try {
    const response = await fetch(
      'https://api.wasgehtmuenster.xyz:8080/api/sessions',
      {
        method: 'GET',
        headers: {
          Accept: '*/*', // Set the Accept header to allow all types
          mode: 'no-cors', // Set mode to 'no-cors'
        },
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Get the response data
    const data: SessionsResponseDto =
      (await response.json()) as SessionsResponseDto;

    // Assign only the sessions array to sessions.value
    sessions.value = data.sessions; // Correct assignment

    setActivitesImage();
    getUserImages(); // Now gets user images for each session

    console.log('Success:', data);
  } catch (error) {
    console.error('Error fetching sessions:', error);
  }
}

function setActivitesImage() {
  for (const session of sessions.value) {
    session.image =
      'https://api.wasgehtmuenster.xyz:8080/api/activities/' +
      session.id +
      '/avatar';
  }
}

function getUserImages() {
  // Clear previous images
  userImages.value = [];

  // Loop through each session using for-of
  for (const session of sessions.value) {
    const imagesForSession: string[] = []; // Array to hold user images for this session

    // Check if session.member is an array and is not empty
    if (Array.isArray(session.member) && session.member.length > 0) {
      // Loop through each member in the current session
      for (const memberId of session.member) {
        console.log(memberId);

        imagesForSession.push(
          'https://api.wasgehtmuenster.xyz:8080/api/v1/user/' +
            memberId +
            '/avatar'
        );
      }
    } else {
      console.warn(
        `'member' is either null or empty for session ${session.id}:`,
        session.member
      );
    }

    userImages.value.push(imagesForSession); // Add the images for this session to the main array
  }
}

// Call the function when the component is mounted
onMounted(async () => {
  await fetchSessions();
});
</script>
