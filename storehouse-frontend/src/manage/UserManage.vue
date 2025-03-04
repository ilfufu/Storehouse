<script>
export default {
  name: "UserManage",

  data() {
    let checkAge = (rule, value, callback) => {
      if(value>150) {
        callback(new Error('年龄太大'));
      }
      else{
        callback();
      }
    }

    let checkAccount = (rule, value, callback) => {
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl + "/user/findByAccount?account=" + this.form.account).then(res => res.data).then(res => {
        if(res.code != 200){
          callback();
        }
        else {
          callback(new Error('账号已存在'));
        }
      })
    }

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
      centerDialogVisible: false,
      form:{
        id: '',
        account:'',
        name:'',
        password:'',
        age:'',
        gender:'',
        phone:'',
        roleId: '2'
      },
      rules: {
        account: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 1, max: 12, message: '长度在 1 到 12 个字符', trigger: 'blur'},
          {validator: checkAccount, trigger: 'blur'}
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 1, max: 12, message: '长度在 1 到 12 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 位', trigger: 'blur' },
          { pattern: /^([1-9][0-9]*){1,3}$/, message: '请输入正确的年龄', trigger: 'blur' },
          { validator:checkAge, trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的电话', trigger: 'blur' }
        ]
      },

    }
  },

  methods: {
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

    resetForm() {
      this.$refs['form'].resetFields();
    },

    addDialog(){
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm()
      })
    },
    doAdd(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl+"/user/save", this.form).then(res => res.data).then(res => {
            if(res.code==200){
              this.$message({
                message: '新增用户成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            }
            else{
              this.$message.error('新增用户失败，请检查后端');
            }
          });
        }
        else {
          this.$message.error('新增用户失败，请检查输入');
          return false;
        }
      });
    },
    doMod(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.put(this.$httpUrl+"/user/mod", this.form).then(res => res.data).then(res => {
            if(res.code==200){
              this.$message({
                message: '修改用户成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            }
            else{
              this.$message.error('修改用户失败，请检查后端');
            }
          });
        }
        else {
          this.$message.error('修改用户失败，请检查输入');
          return false;
        }
      });
    },
    addOrMod(){
      if(this.form.id){
        this.doMod();
      }
      else{
        this.doAdd();
      }
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

    modUser(row){
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form.id = row.id+'';
        this.form.account = row.account;
        this.form.name = row.name;
        this.form.password = '';
        this.form.age = row.age+'';
        this.form.gender = row.gender+'';
        this.form.phone = row.phone;
        this.form.roleId = row.roleId+'';
      })
    },

    deleteUser(id){
      this.$axios.delete(this.$httpUrl+"/user/delete?id="+id).then(res => res.data).then(res => {
        if(res.code==200){
          this.$message({
            message: '删除用户成功',
            type: 'success'
          });
          if(this.tableData.length===1&&this.pageNum>1){
            this.pageNum -= 1
          }
          this.loadPost();
        }
        else{
          this.$message.error('删除用户失败，请检查后端');
        }
      })
    }

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
                style="width: 250px; margin-left: 200px" @keyup.enter.native="loadPost"></el-input>
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
      <el-button type="warning" plain style="margin-left: 5px" @click="addDialog">新增</el-button>
    </div>





    <el-table :data="tableData"
              :header-cell-style="{background: '#ecf5ff', color: '#555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="110">
      </el-table-column>
      <el-table-column prop="account" label="账号" width="110">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="110">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="110">
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="110">
        <template slot-scope="scope">
          <el-tag
              effect="plain"
              :type="scope.row.gender == 1 ? '' : scope.row.gender == 0 ? 'success' : 'danger'"
              disable-transitions>{{scope.row.gender == 1 ? '男' : scope.row.gender == 0 ? '女' : '无效性别'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="110">
      </el-table-column>
      <el-table-column prop="roleId" label="权限" width="110">
        <template slot-scope="scope">
          <el-tag
              effect="plain"
              :type="scope.row.roleId == 0 ? 'warning' : scope.row.roleId == 1 ? '' : 'info'"
              disable-transitions>{{scope.row.roleId == 0 ? '超级管理员' : scope.row.roleId == 1 ? '管理员' : '普通用户'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isvalid" label="账号有效性" width="110">
        <template slot-scope="scope">
          <el-tag
              effect="plain"
              :type="scope.row.isvalid !== 'Y' ? 'danger' : ''"
              disable-transitions>{{scope.row.isvalid === 'Y' ? '有效' : '无效'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" plain @click="modUser(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除此账号？"
              @confirm="deleteUser(scope.row.id)"
          >
            <el-button slot="reference" type="danger" plain
                       style="margin-left: 5px">删除</el-button>
          </el-popconfirm>
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





    <el-dialog
        title="新增或更改用户"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="账号：" prop="account">
          <el-col :span="18">
            <el-input v-model="form.account"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="名字：" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-col :span="18">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄：" prop="age">
          <el-col :span="18">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别：" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话：" prop="phone">
          <el-col :span="18">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrMod">确 定</el-button>
      </span>
    </el-dialog>



  </div>
</template>

<style>
</style>