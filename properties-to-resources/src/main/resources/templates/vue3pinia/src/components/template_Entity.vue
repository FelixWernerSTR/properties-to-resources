<template>
  <div class="hello">
    <h1>{{ [=mavenproject.entityName?lower_case].name }}</h1>
    <h2>[=mavenproject.entityName]: {{ [=mavenproject.entityName?lower_case].id }}</h2>
    <input type="text" name="[=mavenproject.entityName?lower_case]Id" id="[=mavenproject.entityName?lower_case]Id" data-cy="[=mavenproject.entityName?lower_case]Id" v-model="[=mavenproject.entityName?lower_case]Id" />
    <button v-on:click="get[=mavenproject.entityName]ForId()">Get [=mavenproject.entityName] for Id</button>
    <br />
    <input
      type="text"
      name="[=mavenproject.entityName?lower_case]Name"
      id="[=mavenproject.entityName?lower_case]Name"
      data-cy="[=mavenproject.entityName?lower_case]Name"
      v-model="[=mavenproject.entityName?lower_case]Name"
    />
    <button v-on:click="save()">Save</button>
    <br />
  </div>
</template>

<script lang="ts" src="./[=mavenproject.entityName].ts"></script>