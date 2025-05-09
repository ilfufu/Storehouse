<script>
  import DataUtils from "@/usercenter/DataUtils.vue";
  export default {
    name: "UserCenter",
    components: {DataUtils},
    data() {
      return {
        user: JSON.parse(sessionStorage.getItem("curUser")),
      }
    }
  }
</script>

<template>
  <div style="margin-left: 30px; margin-right: 30px">
    <h1 style="display: flex; align-items: center; justify-content: center; font-size: 40px;
    margin-top: 50px; margin-bottom: 53px">欢迎! {{user.name}}</h1>

    <el-descriptions :column="3" :size="30" border style="margin: 50px">
      <el-descriptions-item content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          账号
        </template>
        {{user.account}}
      </el-descriptions-item>

      <el-descriptions-item content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-info"></i>
          姓名
        </template>
        {{user.name}}
      </el-descriptions-item>

      <el-descriptions-item content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-tickets"></i>
          权限
        </template>
        <el-tag
            effect="plain"
            :type="user.roleId == 0 ? 'warning' : user.roleId == 1 ? '' : 'info'"
            disable-transitions>
          {{user.roleId == 0 ? '超级管理员' : user.roleId == 1 ? '管理员' : '普通用户'}}
        </el-tag>
      </el-descriptions-item>

      <el-descriptions-item content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-s-opportunity"></i>
          年龄
        </template>
        {{user.age}}
      </el-descriptions-item>

      <el-descriptions-item content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-phone"></i>
            电话
        </template>
        {{user.phone}}
      </el-descriptions-item>

      <el-descriptions-item content-class-name="my-content">
        <template slot="label">
          <i class="el-icon-fork-spoon"></i>
            性别
        </template>
        <el-tag
            effect="plain"
            :type="user.gender == 1 ? '' : user.gender == 0 ? 'success' : 'danger'"
            disable-transitions>
          <i :class=" user.gender == 1 ? 'el-icon-male' : user.gender == 0 ? 'el-icon-female' : 'el-icon-close'"></i>
          {{user.gender == 1 ? '男' : user.gender == 0 ? '女' : '无效性别'}}
        </el-tag>
      </el-descriptions-item>



    </el-descriptions>

    <DataUtils></DataUtils>
  </div>
</template>

<style scoped>
  .my-content {
    background: #ecf5ff;
    min-width: 90px;
  }
</style>