<script>
  export default {
    name: "Aside",

    data() {
      return {

      }
    },

    props:{
      isCollapse:Boolean
    }
  }
</script>

<template>
  <el-menu
      text-color="black"
      default-active="/home"
      :collapse="isCollapse"
      :collapse-transition="false"
      style="height: 100%">

    <el-menu-item index="/home">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item index="/one">
      <i class="el-icon-s-promotion"></i>
      <span slot="title">导航一</span>
    </el-menu-item>

    <el-menu-item index="/two">
      <i class="el-icon-s-promotion"></i>
      <span slot="title">导航二</span>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>
</style>