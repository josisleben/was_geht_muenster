<template>
  <q-card class="my-card">
    <q-card-section>
      <div class="text-h4 q-mb-md">Registrieren</div>

      <q-input
        v-model="form.firstName"
        label="Vorname"
        filled
        class="q-mb-md"
      />
      <q-input
        v-model="form.lastName"
        label="Nachname"
        filled
        class="q-mb-md"
      />
      <q-input
        v-model="form.birthday"
        label="Geburtstag"
        type="date"
        filled
        class="q-mb-md"
      />
      <q-uploader
        label="Profilbild"
        url="http://localhost:4444/upload"
        color="teal"
        flat
        bordered
        class="q-mb-md full-width"
      />
      <q-btn color="green" @click="submitForm">Registrieren</q-btn>
    </q-card-section>
  </q-card>
</template>

<script setup lang="ts">
const form = {
  firstName: '',
  lastName: '',
  birthday: '',
  image: null,
};

function submitForm() {
  console.log('Formularinhalt:', form);
}
</script>

<style scoped>
.my-card {
  width: 100%;
  max-width: 400px;
  margin: 20px;
}
</style>
