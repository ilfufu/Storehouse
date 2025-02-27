<script>
  export default {
    name: "Aside",

    data() {
      return {
        menu: [
          {
            menuClick: 'Admin',
            menuName: '管理员管理',
            menuIcon: 'el-icon-user-solid',
          },
          {
            menuClick: 'User',
            menuName: '用户管理',
            menuIcon: 'el-icon-user',
          }
        ],
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
      default-active="/Center"
      :collapse="isCollapse"
      :collapse-transition="false"
      router
      style="height: 100%">

    <el-menu-item index="/Center">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item :index="'/' + item.menuClick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuIcon"></i>
      <span slot="title">{{item.menuName}}</span>
    </el-menu-item>

  </el-menu>
</template>

<style scoped>
</style>