<template>
  <q-page class="flex flex-column items-center q-px-md q-py-lg">
    <q-card v-if="sessionData" class="my-card shadow-2 rounded-borders">
      <q-card-section>
        <div class="row items-center justify-between q-mb-md">
          <div class="text-h4 q-mb-md">{{ sessionData.name }}</div>
          <q-btn
            class="q-ml-auto"
            color="primary"
            icon="person_add"
            @click="joinActivity"
          >
            Mitmachen
          </q-btn>
        </div>
        <q-item-label class="text-body1 q-mb-md">
          <strong>Beschreibung:</strong> {{ sessionData.description }}
        </q-item-label>

        <q-card-actions class="q-pa-none q-mb-sm row items-center">
          <q-item-label class="text-h6">
            Plätze: <strong>{{ userData.length }}</strong> /
            <strong>{{ sessionData.maxPerson }}</strong>
          </q-item-label>
        </q-card-actions>

        <q-item-label class="text-body2 q-mb-md">
          <q-icon name="people" class="q-mr-xs" />
          <strong>Teilnehmer:</strong>
        </q-item-label>

        <!-- Member List with Reduced Padding -->
        <q-list dense class="q-mb-md">
          <q-item
            v-for="(user, index) in userData"
            :key="index"
            class="q-pb-xs"
          >
            <q-item-section avatar>
              <q-avatar rounded size="56px">
                <q-img :src="user.avatar" />
              </q-avatar>
            </q-item-section>
            <q-item-section>
              <q-item-label class="text-body1">{{
                user.username
              }}</q-item-label>
            </q-item-section>
          </q-item>
        </q-list>

        <!-- Dates Section with No Padding or Margin -->
        <q-card-section class="no-padding q-mb-lg">
          <div class="text-caption text-grey-6 no-margin-padding">
            <q-icon name="event" class="q-mr-xs" /> {{ formattedStartDate }}
            <br />
            <q-icon name="schedule" class="q-mr-xs" />
            {{ formattedStartTime }} - {{ formattedEndTime }}
          </div>
        </q-card-section>

        <q-item-label class="text-body2 q-mb-md">
          <strong>Mindestteilnehmer:</strong> {{ sessionData.minPerson }}
        </q-item-label>
        <q-item-label class="text-body2 q-mb-md">
          <strong>Tags: </strong>
          <span>{{ formattedTags }}</span>
        </q-item-label>
      </q-card-section>

      <q-card-section>
        <q-dialog v-model="showRegisterDialog">
          <RegisterCard
            @registration-success-emit="handleRegistrationSuccess"
          />
        </q-dialog>
      </q-card-section>
    </q-card>

    <div v-else class="loading-message">
      <q-spinner color="primary" size="50px" />
      <p>Loading session data...</p>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router'; // Import useRoute to access route parameters
import RegisterCard from 'src/components/RegisterCard.vue';

const showRegisterDialog = ref(false);
const sessionToken = ref<string | null>(localStorage.getItem('sessionToken')); // Get the session token from local storage
const isLoading = ref(false); // New loading state

async function joinActivity() {
  sessionToken.value = localStorage.getItem('sessionToken');
  if (sessionToken.value === null) {
    console.log('You will be redirected!');
    showRegisterDialog.value = true; // Show dialog if sessionToken exists
  } else {
    await addUserToSession(); // Call function if no sessionToken exists
  }
}

async function addUserToSession() {
  const url = apiHost + '/api/sessions/' + sessionData.value?.id + '/member';

  if (sessionToken.value === null) {
    return;
  }

  //We send the SessionToken to insert the user. I hate that there is no login.

  const options: RequestInit = {
    method: 'POST',
    headers: {
      Authorization: sessionToken.value, // The Authorization token
      'Content-Type': 'application/json', // Adjust content type as needed
    },
    body: JSON.stringify({}), // Replace {} with the body content if needed
  };

  try {
    const response = await fetch(url, options);
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    if (response.status === 200) {
      location.reload(); // Reload page after registration
    }
  } catch (error) {
    console.error('Error:', error);
  }
}

function handleRegistrationSuccess(registrationSuccess: boolean) {
  console.log(registrationSuccess);
  showRegisterDialog.value = false;
  isLoading.value = false; // End loading
  location.reload(); // Reload page after registration
}

const apiHost: string = process.env.VUE_APP_API_HOST;

// Access route parameters
const route = useRoute();
const sessionId: string = route.params.id as string; // Fetch the id from the URL parameters

// Define the SessionProps interface
interface SessionProps {
  id: string; // Unique identifier for the session
  name: string; // Name of the session
  description: string; // Description of the session
  start: number; // Start time as a UNIX timestamp (milliseconds)
  end: number; // End time as a UNIX timestamp (milliseconds)
  activityId: string; // Identifier for the associated activity
  minPerson: number; // Minimum number of participants required
  maxPerson: number; // Maximum number of participants allowed
  tags: string[]; // Array of tags related to the session
  creator: string; // ID of the user who created the session
  member?: string[]; // Array of IDs of members participating in the session
}

interface UserData {
  id: string;
  username: string;
  avatar: string;
  birthDate: string;
}

// State to hold session data
const sessionData = ref<SessionProps | null>(null);
const userData = ref<UserData[]>([]); // Initialize as an empty array

const formattedStartTime = computed(() => {
  return sessionData.value
    ? new Date(sessionData.value.start).toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit',
      })
    : '';
});

const formattedStartDate = computed(() => {
  return sessionData.value
    ? new Date(sessionData.value.start).toLocaleDateString()
    : '';
});

const formattedEndTime = computed(() => {
  return sessionData.value
    ? new Date(sessionData.value.end).toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit',
      })
    : '';
});

const formattedTags = computed(() => {
  return sessionData.value ? sessionData.value.tags.join(', ') : '';
});

// Function to fetch session data
async function fetchSessionData() {
  try {
    const response = await fetch(apiHost + '/api/sessions/' + sessionId); // Replace with your API endpoint
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const data: SessionProps = (await response.json()) as SessionProps;
    sessionData.value = data; // Store the fetched data in sessionData
  } catch (error) {
    console.error('Failed to fetch session data:', error);
  }
}

// Function to fetch user data for each member
async function fetchUserData() {
  if (sessionData.value === null) {
    return;
  }

  if (
    sessionData.value.member === undefined ||
    sessionData.value.member.length === 0
  ) {
    console.log('No members in the session.');
    return;
  }

  try {
    // Use Promise.all to await all fetch requests
    await Promise.all(
      sessionData.value.member.map(async (member) => {
        const response = await fetch(apiHost + '/api/v1/user/' + member);

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data: UserData = (await response.json()) as UserData;
        userData.value.push(data); // Push each user's data into the array
      })
    );
  } catch (error) {
    console.error('Failed to fetch user data:', error);
  }
}

onMounted(async () => {
  await fetchSessionData();
  await fetchUserData();
});
</script>

<style scoped>
.avatar-img {
  width: 40px; /* Adjust as needed */
  height: 40px; /* Adjust as needed */
  border-radius: 50%; /* Optional: for circular images */
}
.my-card {
  width: 100%;
  max-width: 500px;
  margin: 20px;
}

.rounded-borders {
  border-radius: 12px;
}

.q-mb-sm {
  margin-bottom: 8px;
}

.q-mb-md {
  margin-bottom: 16px;
}

.q-mb-xs {
  margin-bottom: 4px;
}

.q-px-md {
  padding-left: 16px;
  padding-right: 16px;
}

.q-py-lg {
  padding-top: 32px;
  padding-bottom: 32px;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}
</style>
