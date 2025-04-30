<script>
import SelectUser from "@/helper/SelectUser.vue";
export default {
  name: "GoodsManage",
  components: {SelectUser},

  data() {
    let checkNum = (rule, value, callback) => {
      if(value>9999) {
        callback(new Error('数字太大'));
      }
      else{
        callback();
      }
    }

    return {
      user: JSON.parse(sessionStorage.getItem("curUser")),
      storageData: [],
      goodstypeData: [],
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: '',
      storage: '',
      goodstype: '',
      centerDialogVisible: false,
      inDialogVisible: false,
      innerVisible: false,
      currentRow: {},
      tempUser: {},
      form:{
        id: '',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },
      form1:{
        goods:'',
        goodsname:'',
        count:'',
        remark:'',
        userid:'',
        username:'',
        adminId:'',
        action:''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 1, max: 12, message: '长度在 1 到 12 个字符', trigger: 'blur' }
        ],
        storage: [
          {required: true, message: '请选择仓库', trigger: 'blur'}
        ],
        goodstype: [
          {required: true, message: '请选择类型', trigger: 'blur'}
        ],
        count: [
          { required: true, message: '请输入数量', trigger: 'blur' },
          { min: 1, max: 4, message: '长度在 1 到 4 位', trigger: 'blur' },
          { pattern: /^([1-9][0-9]*){1,4}$/, message: '请输入正确的数量', trigger: 'blur' },
          { validator:checkNum, trigger: 'blur' }
        ]
      },
      rules1:{

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
          this.$axios.post(this.$httpUrl+"/goods/save", this.form).then(res => res.data).then(res => {
            if(res.code==200){
              this.$message({
                message: '新增物品成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            }
            else{
              this.$message.error('新增物品失败，请检查后端');
            }
          });
        }
        else {
          this.$message.error('新增物品失败，请检查输入');
          return false;
        }
      });
    },
    doMod(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.put(this.$httpUrl+"/goods/mod", this.form).then(res => res.data).then(res => {
            if(res.code==200){
              this.$message({
                message: '修改物品成功',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            }
            else{
              this.$message.error('修改物品失败，请检查后端');
            }
          });
        }
        else {
          this.$message.error('修改物品失败，请检查输入');
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



    modUser(row){
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form.id = row.id+'';
        this.form.name = row.name;
        this.form.storage = row.storage;
        this.form.goodstype = row.goodstype;
        this.form.count = row.count+'';
        this.form.remark = row.remark;
      })
    },
    deleteUser(id){
      this.$axios.delete(this.$httpUrl+"/goods/delete?id="+id).then(res => res.data).then(res => {
        if(res.code==200){
          this.$message({
            message: '删除物品成功',
            type: 'success'
          });
          if(this.tableData.length===1&&this.pageNum>1){
            this.pageNum -= 1
          }
          this.loadPost();
        }
        else{
          this.$message.error('删除物品失败，请检查后端');
        }
      })
    },



    resetInForm() {
      this.$refs['form1'].resetFields();
    },
    inGoods(){
      if(!this.currentRow.id){
        alert('请选择物品')
        return
      }

      this.inDialogVisible = true
      this.form1.goods = this.currentRow.id;
      this.form1.goodsname = this.currentRow.name;
      this.form1.adminId = this.user.id;
      this.form1.action = '1';

      this.$nextTick(() => {
        this.resetInForm()
      })
    },
    outGoods(){
      if(!this.currentRow.id){
        alert('请选择记录')
        return
      }

      this.inDialogVisible = true
      this.form1.goods = this.currentRow.id;
      this.form1.goodsname = this.currentRow.name;
      this.form1.adminId = this.user.id;
      this.form1.action = '2';

      this.$nextTick(() => {
        this.resetInForm()
      })
    },
    doGoods(){
      this.$refs.form1.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl+"/record/save", this.form1).then(res => res.data).then(res => {
            if(res.code==200){
              this.$message({
                message: '操作成功',
                type: 'success'
              });
              this.inDialogVisible = false;
              this.loadPost();
            }
            else{
              this.$message.error('操作失败，请检查后端');
            }
          });
        }
        else {
          this.$message.error('操作失败，请检查输入');
          return false;
        }
      });
    },



    resetParam(){
      this.name=''
      this.storage=''
      this.goodstype=''
      this.currentRow = {}
      this.loadPost()
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+"/goods/search", {
        pagenum: this.pageNum,
        pagesize: this.pageSize,
        param: {
          name: this.name,
          goodstype: this.goodstype+'',
          storage: this.storage+'',
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
    loadStorage(){
      this.$axios.get(this.$httpUrl+"/storage/findAll").then(res => res.data).then(res => {
        if(res.code==200){
          this.storageData = res.data
        }
        else{
          alert('获取数据失败')
        }
      })
    },
    loadGoodstype(){
      this.$axios.get(this.$httpUrl+"/goodstype/findAll").then(res => res.data).then(res => {
        if(res.code==200){
          this.goodstypeData = res.data
        }
        else{
          alert('获取数据失败')
        }
      })
    },



    formatStorage(row){
      let temp = this.storageData.find(item => {
        return item.id == row.storage
      })
      return temp && temp.name;
    },
    formatGoodstype(row){
      let temp = this.goodstypeData.find(item => {
        return item.id == row.goodstype
      })
      return temp && temp.name;
    },




    selectUser(){
      this.innerVisible = true;
    },
    doSelectUser(val){
      this.tempUser = val;
    },
    confirmUser(){
      this.form1.username = this.tempUser.name;
      this.form1.userid = this.tempUser.id;
      this.innerVisible = false;
    },



    selectCurrentChange(val){
      this.currentRow = val;
    },

  },

  beforeMount() {
    this.loadPost();
    this.loadStorage();
    this.loadGoodstype();
  }
}
</script>

<template>
  <div>





    <div style="margin-bottom:5px; margin-top:4px; margin-left: 5px">
      <el-input v-model="name" placeholder="请输入物品名称" suffix-icon="el-icon-search"
                :style="{width: '250px', marginLeft: this.user.roleId == 2 ? '220px' : '100px'}"  @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="storage" placeholder="请选择仓库"
                 style="width: 120px; margin-left: 5px">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="请选择类型"
                 style="width: 120px; margin-left: 5px">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button type="primary" plain style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" plain style="margin-left: 5px" @click="resetParam">重置</el-button>
      <el-button type="warning" plain style="margin-left: 5px" @click="addDialog" v-if="user.roleId!=2">新增</el-button>
      <el-button type="primary" plain style="margin-left: 5px" @click="inGoods" v-if="user.roleId!=2">入库</el-button>
      <el-button type="danger" plain style="margin-left: 5px" @click="outGoods" v-if="user.roleId!=2">出库</el-button>
    </div>





    <el-table :data="tableData"
              :header-cell-style="{background: '#ecf5ff', color: '#555'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" :width="user.roleId!=2 ? '150' : '180'">
      </el-table-column>
      <el-table-column prop="name" label="物品" :width="user.roleId!=2 ? '150' : '180'">
      </el-table-column>
      <el-table-column prop="storage" label="仓库" :width="user.roleId!=2 ? '150' : '180'" :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="goodstype" label="物品类型" :width="user.roleId!=2 ? '150' : '180'" :formatter="formatGoodstype">
      </el-table-column>
      <el-table-column prop="count" label="数量" :width="user.roleId!=2 ? '150' : '180'">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>
      <el-table-column prop="operate" label="操作" v-if="user.roleId!=2">
        <template slot-scope="scope">
          <el-button type="primary" plain @click="modUser(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除此物品？"
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
        :title="this.form.id ? '修改物品' : '新增物品'"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="100px">
        <el-form-item label="物品：" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库：" prop="storage">
          <el-col :span="18">
            <el-select v-model="form.storage">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="物品类型：" prop="goodstype">
          <el-col :span="18">
            <el-select v-model="form.goodstype">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="数量：" prop="count">
          <el-col :span="18">
            <el-input v-model="form.count"></el-input>
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





    <el-dialog
        :title="this.form1.action == '1' ? '入库物品' : '出库物品'"
        :visible.sync="inDialogVisible"
        width="30%"
        center>
      <el-dialog
          width="80%"
          title="选择申请人"
          :visible.sync="innerVisible"
          append-to-body>
        <SelectUser @doSelectUser="doSelectUser"></SelectUser>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmUser">确 定</el-button>
        </span>
      </el-dialog>
      <el-form ref="form1" :rules="rules1" :model="form1" label-width="100px">
        <el-form-item label="物品：" prop="goodsname">
          <el-col :span="18">
            <el-input v-model="form1.goodsname" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人：" prop="username">
          <el-col :span="18">
            <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量：" prop="count">
          <el-col :span="18">
            <el-input v-model="form1.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-col :span="18">
            <el-input type="textarea" v-model="form1.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="inDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doGoods">确 定</el-button>
      </span>
    </el-dialog>





  </div>
</template>

<style>
</style>