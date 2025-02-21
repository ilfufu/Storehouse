<script>
  export default {
    name: "Header",

    props: {
      collapseIcon: String,
    },

    methods: {
      toUser() {
        console.log("to_user");
      },

      logout() {
        console.log("logout");
      },

      collapse() {
        this.$emit('doCollapse');
      }
    }
  }
</script>

<template>
  <div id="header" style="display: flex; line-height: 60px">
    <div>
      <i :class="collapseIcon" style="font-size: 18px; line-height: 63px" @click="collapse"></i>
    </div>

    <div style="flex: 1; text-align: center; font-size: 25px;">
      <span>欢迎来到仓库系统</span>
    </div>

    <div>
      <span style="font-size: 15px">当前用户名</span>
      <el-dropdown>
        <i class="el-icon-arrow-down" style="margin-left: 10px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

  </div>
</template>

<style scoped>
</style>