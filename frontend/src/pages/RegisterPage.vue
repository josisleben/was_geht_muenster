<template>
  <q-page class="flex flex-column items-center">
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
          v-model="form.password" 
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
  </q-page>
</template>

<script lang="ts">
export default {
  data() {
    return {
      form: {
        firstName: '',
        lastName: '',
        password: '',
        image: null,
      },
    };
  },
  methods: {

    submitForm() {
      console.log('Formularinhalt:', this.form);
    },
  },
};
</script>

<style scoped>
.my-card {
  width: 100%;
  max-width: 400px;
  margin: 20px;
}
</style>
