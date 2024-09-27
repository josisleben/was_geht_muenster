<template>
  <q-page class="flex flex-column items-center">
    <q-card class="my-card">
      <q-card-section>
        <div class="text-h4 q-mb-md">Profil</div>
        
        <!-- Flex container for avatar and details -->
        <div class="row items-center q-mb-sm">
          <q-item-section avatar class="q-mr-md">
            <q-avatar rounded size="56px">
              <q-img :src="participant.image" />
            </q-avatar>
          </q-item-section>
          
          <!-- Name and birthday stacked vertically -->
          <div>
            <q-item-label class="text-body2 q-mb-sm">
              <strong>{{ participant.name }}</strong> 
            </q-item-label>
            <q-item-label class="text-body2">
              <strong>{{ participant.birthday }}</strong> 
            </q-item-label>
          </div>
        </div>
        
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup lang="ts">
const participant = {
  name: 'Profi Volleyballer',
  image: '../src/assets/profil.jpeg',
  birthday: '01.02.2003'
};
</script>

<style scoped>
.my-card {
  width: 100%;
  max-width: 400px;
  margin: 20px;
}
</style>

