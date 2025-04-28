<script>
export default {
  name: "StorageManage",

  data() {
    let checkName = (rule, value, callback) => {
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl + "/storage/findByName?name=" + this.form.name).then(res => res.data).then(res => {
        if(res.code != 200){
          callback();
        }
        else {
          callback(new Error('仓库已存在'));
        }
      })
    }

    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: '',
      centerDialogVisible: false,
      form:{
        id: '',
        name:'',
        remark:''
      },
      rules: {
        name: [
          {required: true, message: '请输入仓库名', trigger: 'blur'},
          {min: 1, max: 12, message: '长度在 1 到 12 个字符', trigger: 'blur'},
          {validator: checkName, trigger: 'blur'}
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
      this.loadPost()
    },

    resetForm() {
      this.$refs['form'].resetFields();
    },

    addDialog(){
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm()
        this.form.id = ''
      })
    },
    doAdd(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl+"/storage/save", this.form).then(res => res.data).then(res => {
            if(res.code==200){
              this.$message({
                message: '新增仓库成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            }
            else{
              this.$message.error('新增仓库失败，请检查后端');
            }
          });
        }
        else {
          this.$message.error('新增仓库失败，请检查输入');
          return false;
        }
      });
    },
    doMod(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.put(this.$httpUrl+"/storage/mod", this.form).then(res => res.data).then(res => {
            if(res.code==200){
              this.$message({
                message: '修改仓库成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            }
            else{
              this.$message.error('修改仓库失败，请检查后端');
            }
          });
        }
        else {
          this.$message.error('修改仓库失败，请检查输入');
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
      this.$axios.post(this.$httpUrl+"/storage/search", {
        pagenum: this.pageNum,
        pagesize: this.pageSize,
        param: {
          name: this.name
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
        this.form.remark = row.remark;
        this.form.name = row.name;
      })
    },

    deleteUser(id){
      this.$axios.delete(this.$httpUrl+"/storage/delete?id="+id).then(res => res.data).then(res => {
        if(res.code==200){
          this.$message({
            message: '删除仓库成功',
            type: 'success'
          });
          if(this.tableData.length===1&&this.pageNum>1){
            this.pageNum -= 1
          }
          this.loadPost();
        }
        else{
          this.$message.error('删除仓库失败，请检查后端');
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



    <div style="margin-bottom:5px; margin-top:4px; margin-left: 90px">
      <el-input v-model="name" placeholder="请输入仓库名" suffix-icon="el-icon-search"
                style="width: 250px; margin-left: 200px" @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" plain style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" plain style="margin-left: 5px" @click="resetParam">重置</el-button>
      <el-button type="warning" plain style="margin-left: 5px" @click="addDialog">新增</el-button>
    </div>





    <el-table :data="tableData"
              :header-cell-style="{background: '#ecf5ff', color: '#555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="270">
      </el-table-column>
      <el-table-column prop="name" label="仓库名" width="270">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="270">
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" plain @click="modUser(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除此仓库？"
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
        title="新增或更改仓库"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="仓库名：" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-col :span="18">
            <el-input type="textarea" v-model="form.remark"></el-input>
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