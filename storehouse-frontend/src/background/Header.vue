<script>
  export default {
    name: "Header",

    data(){
      return {
        user: JSON.parse(sessionStorage.getItem("curUser")),
      }
    },

    methods: {
      logout() {
        this.$confirm('您确定要退出登录吗？', '下线提示', {
          confirmButtonText: '',
          type: 'warning',
          center: true,
          showClose: false,
          closeOnClickModal: false
        })
            .then(() => {
              this.$message({
                type: 'success',
                message:'退出成功'
              })

              this.$router.replace("/")
              sessionStorage.removeItem("curUser")
            })

            .catch(() => {
              this.$message({
                type: 'info',
                message:'取消退出'
              })
            })
      },

    },

    created () {
      this.$router.push("/Center")
    }
  }
</script>

<template>
  <div id="header" style="display: flex; line-height: 60px">
    <div>
      <i class="el-icon-loading" style="font-size: 19px"></i>
    </div>

    <div style="flex: 1; text-align: center; font-size: 25px;">
      <span>后台管理系统</span>
    </div>

    <div>
      <span style="font-size: 15px">{{user.name}}</span>
      <el-dropdown>
        <i class="el-icon-arrow-down" style="margin-left: 10px"></i>
        <el-dropdown-menu>
          <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

  </div>
</template>

<style>
</style>