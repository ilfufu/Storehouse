<script>
import Aside from "@/background/Aside.vue";
import Header from "@/background/Header.vue";

export default {
  name: "Index",

  components: {Aside, Header},

  data() {
    return {
      isCollapse: false,
      asideWidth: '220px',
      collapseIcon: 'el-icon-d-arrow-left',
    }
  },

  methods: {
    doCollapse(){
      console.log("change collapse");
      this.isCollapse = !this.isCollapse;
      if(!this.isCollapse){
        this.asideWidth = '220px';
        this.collapseIcon = 'el-icon-d-arrow-left';
      }
      else{
        this.asideWidth = '64.5px';
        this.collapseIcon = 'el-icon-d-arrow-right';
      }
    }
  }
};
</script>

<template>
  <el-container style="border: 1px solid #eee; min-height: 100vh;">
    <el-aside :width="asideWidth">
      <Aside :isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container>
      <el-header style="border: 1px solid #eee">
        <Header @doCollapse="doCollapse" :collapseIcon="collapseIcon"></Header>
      </el-header>

      <el-main style="padding: 1px; overflow: hidden">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
</style>