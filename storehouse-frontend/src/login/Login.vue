<script>
  export default{
    name:'Login',

    data(){
      return {
        confirm_disabled: false,
        loginForm:{
          account:'',
          password:''
        },
        rules:{
          account:[
            {required: true, message: '请输入账号', trigger: 'blur'}
          ],
          password:[
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        }
      }
    },

    methods: {
      confirm(){
        this.confirm_disabled = true;
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.$axios.post(this.$httpUrl+'/user/login',this.loginForm).then(res=>res.data).then(res=>{
              if(res.code == 200){
                sessionStorage.setItem("curUser",JSON.stringify(res.data.user));
                this.$store.commit("setMenu",res.data.menu);
                this.$router.replace('/Center')
              }
              else{
                this.confirm_disabled = false;
                alert('账号或密码错误！');
                return false;
              }
            })
          }
          else {
            this.confirm_disabled = false;
            return false;
          }
        });
      },

    }
  }

</script>

<template>
  <div class="loginbody">
    <div class="logindiv">
      <div class="logincontent">
        <h1 class="logintitle">登录到系统</h1>
        <el-form :model="loginForm" label-width="100px"
                 :rules="rules" ref="loginForm">
          <el-form-item label="账号" prop="account" style="margin-left: 5px">
            <el-input style="width: 200px" type="text" v-model="loginForm.account"
                      autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" style="margin-left: 5px">
            <el-input style="width: 200px" type="password" v-model="loginForm.password"
                      show-password autocomplete="off" size="small"
                      @keyup.enter.native="confirm"></el-input>
          </el-form-item>
          <el-form-item style="margin-left: 62px">
            <el-button type="primary" @click="confirm" :disabled="confirm_disabled">确 定</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style>
  .loginbody{
    top: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #eee;
  }

  .logindiv{
    position: absolute;
    top: 50%;
    left: 53%;
    margin-top: -200px;
    margin-left: -250px;
    width: 450px;
    height: 330px;
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
</style>