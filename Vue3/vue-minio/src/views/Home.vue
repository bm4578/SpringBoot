<template>
  <div>
    <Header></Header>

    <div class="app">
      <button @click="test()"> test</button>
    </div>
  </div>
</template>

<script setup>
import Header from '../components/Header.vue'
import {user_info} from "../api/user";
const test = ()=>{
  console.log("tttt")
  user_info().then(res=>{
    console.log(res.result)
  })
}
</script>