<script>
  export default {
    name: "Aside",

    methods: {
      collapse() {
        this.$emit('doCollapse');
      }
    },

    computed:{
      "menu":{
        get(){
          return this.$store.state.menu
        }
      },
    },

    props:{
      collapseIcon: String,
      isCollapse: Boolean
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

    <el-menu-item @click="collapse">
      <i :class="collapseIcon" style="font-size: 18px; line-height: 63px"></i>
      <span style="color: darkgrey; font-size: 15px">{{isCollapse ? "展开列表" : "收起列表"}}</span>
    </el-menu-item>

    <el-menu-item index="/Center">
      <i class="el-icon-s-home"></i>
      <span>个人中心</span>
    </el-menu-item>

    <el-menu-item :index="'/' + item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
      <span>{{item.menuname}}</span>
    </el-menu-item>

  </el-menu>
</template>

<style>
</style>