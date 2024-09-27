<template>
  <q-page class="flex flex-column items-center q-px-md q-py-lg">
    <q-card class="my-card shadow-2 rounded-borders">
      <q-card-section>
        <!-- Titel der Aktivität -->
        <div class="text-h4 q-mb-md">🏐 Aktivität: Volleyball</div>

        <!-- Freie Plätze und Mitmachen Button -->
        <q-card-actions class="q-pa-none q-mb-sm row items-center">
          <q-item-label class="text-h6">Freie Plätze: <strong>{{ activity.free }}</strong></q-item-label>
          <q-btn class="q-mb-md" color="primary" icon="person_add">
            Mitmachen
          </q-btn>
        </q-card-actions>

        <!-- Teilnehmer Liste -->
        <q-item-label class="text-subtitle2 q-mb-xs">👥 Teilnehmer:</q-item-label>
        <q-list dense class="q-mb-md">
          <q-item v-for="(participant, index) in activity.participants" :key="index" class="q-pb-xs">
            <q-item-section avatar>
              <q-avatar rounded size="56px">
                <q-img :src="participant.image" />
              </q-avatar>
            </q-item-section>
            <q-item-section>
              <q-item-label class="text-body1">{{ participant.name }}</q-item-label>
            </q-item-section>
          </q-item>
        </q-list>

        <!-- Ort, Zeit, Beschreibung -->
        <div class="details q-mt-md">
          <q-item-label class="text-body2 q-mb-sm">
            📍 <strong>Ort:</strong> {{ activity.location }}
          </q-item-label>
          <q-item-label class="text-body2 q-mb-sm">
            🕰️ <strong>Zeit:</strong> {{ activity.time }}
          </q-item-label>
          <q-item-label class="text-body2">
            🖌️ <strong>Beschreibung:</strong> {{ activity.description }}
          </q-item-label>
        </div>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
const activity = {
  title: 'Volleyball',
  free: '4 / 8',
  participants: [
    { name: 'Anel Imamovic', image: '../src/assets/profil.jpeg' },
    { name: 'Enrico Koschel', image: '../src/assets/profil.jpeg' },
    { name: 'Andrej Benedicjus', image: '../src/assets/profil.jpeg' },
    { name: 'Nic Markfort', image: '../src/assets/profil.jpeg' }
  ],
  location: 'Stadtpark Münster',
  time: 'Sonntag, 29.09.2024 15:00 Uhr',
  description: 'Wir suchen für Sonntag noch vier Leute, die Bock haben Volleyball zu spielen. Jeder ist erwünscht :))'
};
</script>

<style scoped>
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
</style>
