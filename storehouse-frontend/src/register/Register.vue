<script>
export default{
  name:'Register',

  data(){
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
      confirm_disabled: false,
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
    confirm(){
      this.confirm_disabled = true;
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
            if(res.code == 200){
              this.$message({
                message: '新增用户成功，自动跳转到登录页面',
                type: 'success'
              });

              this.form.id = '';
              this.form.account = '';
              this.form.name = '';
              this.form.password = '';
              this.form.age = '';
              this.form.gender = '';
              this.form.phone = '';
              this.form.roleId = '2';

              this.$router.replace('/')
            }
            else{
              this.confirm_disabled = false;
              alert('新增用户失败，请检查后端');
              return false;
            }
          })
        }
        else {
          this.confirm_disabled = false;
          alert('新增用户失败，请检查输入');
          return false;
        }
      });
    },

    jumpLogin(){
      this.$router.replace('/');
    }

  }
}

</script>

<template>
  <div class="loginbody">
    <div class="logindiv">
      <div class="logincontent">
        <h1 class="logintitle">普通用户注册</h1>

        <el-form ref="form" :rules="rules" :model="form" label-width="100px">
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
          <el-form-item>
            <el-button style="width: 225px" type="primary" @click="confirm" :disabled="confirm_disabled">确 定</el-button>
          </el-form-item>
        </el-form>

        <p @click="jumpLogin" class="mylink">已有账号？点此回到登录页面</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .loginbody{
    top: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #ecf5ff;
  }

  .logindiv{
    position: absolute;
    top: 34%;
    left: 53%;
    margin-top: -200px;
    margin-left: -250px;
    width: 450px;
    height: 600px;
    background-color: #fff;
    border-radius: 5%;
  }

  .logincontent{
    position: absolute;
    width: 400px;
    height: 250px;
    top: 25px;
    left: 25px;
  }

  .logintitle{
    margin: 20px 0;
    text-align: center;
  }

  .mylink{
    margin-left: 99px;
    font-size: 17.5px;
  }

  .mylink:hover{
    color: blue;
    cursor: pointer;
  }
</style>