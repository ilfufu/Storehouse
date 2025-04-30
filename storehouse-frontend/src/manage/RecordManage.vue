<script>
export default {
  name: "RecordManage",

  data() {
    return {
      storageData: [],
      goodstypeData: [],
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: '',
      storage: '',
      goodstype: ''
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
      this.storage=''
      this.goodstype=''
      this.loadPost()
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+"/record/search", {
        pagenum: this.pageNum,
        pagesize: this.pageSize,
        param: {
          name: this.name,
          goodstype: this.goodstype+'',
          storage: this.storage+''
        }
      }).then(res => res.data).then(res => {
        if(res.code==200){
          console.log(res.data);
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
                style="width: 250px; margin-left: 220px" @keyup.enter.native="loadPost"></el-input>
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
    </div>





    <el-table :data="tableData"
              :header-cell-style="{background: '#ecf5ff', color: '#555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="110">
      </el-table-column>
      <el-table-column prop="goodsname" label="物品" width="110">
      </el-table-column>
      <el-table-column prop="storagename" label="仓库" width="110">
      </el-table-column>
      <el-table-column prop="goodstypename" label="物品类型" width="110">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" width="110">
      </el-table-column>
      <el-table-column prop="username" label="申请人" width="110">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="110">
      </el-table-column>
      <el-table-column prop="createtime" label="记录时间" width="170">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
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