<template>
  <div class="hello">
    <h2>Name:{{ [=mavenproject.entityName?lower_case].name }}</h2>
    <h2>[=mavenproject.entityName]: {{ [=mavenproject.entityName?lower_case].id }}</h2>
    <input type="text" name="[=mavenproject.entityName?lower_case]Id" id="[=mavenproject.entityName?lower_case]Id" data-cy="[=mavenproject.entityName?lower_case]Id"/>
    <button v-on:click="get[=mavenproject.entityName]ForId()">Get [=mavenproject.entityName] for Id</button>
    <br />
    <input
      type="text"
      name="[=mavenproject.entityName?lower_case]Name"
      id="[=mavenproject.entityName?lower_case]Name"
      data-cy="[=mavenproject.entityName?lower_case]Name"
      v-model="[=mavenproject.entityName?lower_case].name"
    />
    <div v-if="v$.name.$anyDirty && v$.name.$invalid">
      <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
    </div>
    <button v-on:click="save()">Save</button>
    <br />
  </div>
</template>

<script lang="ts" src="./[=mavenproject.entityName].ts"></script>