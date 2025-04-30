<script>
export default {
  name: "SelectUser",

  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: '',
      gender: '',
      genders: [
        {
          value: '1',
          label: '男'
        },
        {
          value: '0',
          label: '女'
        }
      ],
    }
  },

  methods: {
    selectCurrentChange(val){
      this.$emit("doSelectUser", val);
    },



    handleSizeChange(val) {
      this.pageNum = 1;
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadPost();
    },



    resetParam(){
      this.name=''
      this.gender=''
      this.loadPost()
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+"/user/search", {
        pagenum: this.pageNum,
        pagesize: this.pageSize,
        param: {
          name: this.name,
          gender: this.gender,
          roleId: '2'
        }
      }).then(res => res.data).then(res => {
        if(res.code==200){
          this.tableData = res.data
          this.total = res.total
        }
        else{
          alert('获取数据失败')
        }
      })
    },

  },

  beforeMount() {
    this.loadPost();
  }
}
</script>

<template>
  <div>





    <div style="margin-bottom:5px; margin-top:4px; margin-left: 5px">
      <el-input v-model="name" placeholder="请输入姓名" suffix-icon="el-icon-search"
                style="width: 250px; margin-left: 220px" @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="gender" filterable placeholder="请选择性别" style="width: 150px; margin-left: 5px">
        <el-option
            v-for="item in genders"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" plain style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" plain style="margin-left: 5px" @click="resetParam">重置</el-button>
    </div>





    <el-table :data="tableData"
              :header-cell-style="{background: '#ecf5ff', color: '#555'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" width="130">
      </el-table-column>
      <el-table-column prop="account" label="账号" width="130">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="130">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="130">
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="130">
        <template slot-scope="scope">
          <el-tag
              effect="plain"
              :type="scope.row.gender == 1 ? '' : scope.row.gender == 0 ? 'success' : 'danger'"
              disable-transitions>{{scope.row.gender == 1 ? '男' : scope.row.gender == 0 ? '女' : '无效性别'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="130">
      </el-table-column>
      <el-table-column prop="roleId" label="权限" width="130">
        <template slot-scope="scope">
          <el-tag
              effect="plain"
              :type="scope.row.roleId == 0 ? 'warning' : scope.row.roleId == 1 ? '' : 'info'"
              disable-transitions>{{scope.row.roleId == 0 ? '超级管理员' : scope.row.roleId == 1 ? '管理员' : '普通用户'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isvalid" label="账号有效性">
        <template slot-scope="scope">
          <el-tag
              effect="plain"
              :type="scope.row.isvalid !== 'Y' ? 'danger' : ''"
              disable-transitions>{{scope.row.isvalid === 'Y' ? '有效' : '无效'}}</el-tag>
        </template>
      </el-table-column>
    </el-table>





    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>





  </div>
</template>

<style>
</style>