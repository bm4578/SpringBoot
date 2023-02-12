<template>
  <div class="login">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>登录</span>
        </div>
      </template>
      <!--表单-->
      <el-form
          ref="formRef"
          :model="UserForm"
          :rules="rules"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-form-item
            label="用户名"
            prop="username"
        >
          <el-input
              v-model="UserForm.username"
          />
        </el-form-item>

        <el-form-item
            label="密码"
            prop="password"
        >
          <el-input
              type="password"
              v-model="UserForm.password"
          />
        </el-form-item>

<!--        验证码-->
        <el-form-item
            label="验证码"
            prop="imgCode"
        >
          <div class="imgCode">
          <el-input
              style="width: 120px"
              v-model="UserForm.imgCode"
          />
            <img :src="img.imgUrl" alt="" style="height: 40px;width: 90px;margin-left: 2px"/>
            <el-button
                link
                v-if="!img.isCode"
                @click="sendCode"
            >重新获取验证码</el-button>

            <el-button
                style="margin-left: 30px"
                link
                v-if="img.isCode"
            >{{ img.codeTime }}s</el-button>
        </div>
        </el-form-item>

        <!--登录-->
        <el-form-item>
          <el-button type="primary" @click="login(formRef)">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import {ref, reactive} from 'vue'
import {user_login} from '../../api/user.js'
import { ElMessage } from 'element-plus'
import {userStore} from "../../store/user/user.js";
import router from "../../router";
//持久化存储
const store = userStore()
//定义接收用户名密码对象
const UserForm = reactive({
  username: '2109961182',
  password: '2109961182',
  imgCode: ''
})
//定义表单数据
const formRef = ref()
//初始时间，和倒计时模块是否显示
const img = reactive({
  imgUrl : '/api/user/imgCode',
  codeTime: 60,
  isCode: false
})
let clear = null
//异步函数
const sendCode = async () => {
  img.isCode = true
  img.imgUrl = '/api/user/imgCode?time=' +new Date()
  clear= setInterval(() => {
    // 每次时间 -1
    img.codeTime--;
    // 时间=0时 清除定时器
    if (img.codeTime === 0) {
      //清除定时器
      clearInterval(clear);
      // 还原 倒计时60s
      img.codeTime = 60;
      img.isCode = false
    }
  }, 1000);
}

//数据校验
const rules = reactive({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 16, message: '长度在3-16之间', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 3, max: 16, message: '长度在3-16之间', trigger: 'blur'},
  ],
  imgCode:[
    {required: true, message: '请输入验证码', trigger: 'blur'},
    {min: 4, max: 4, message: '长度4位', trigger: 'blur'},
  ]
})
//登录逻辑
const login = (formRef) => {
  formRef.validate((valid) => {
    if (valid) {
      user_login({
        code:UserForm.imgCode,
        username: UserForm.username,
        password:UserForm.password
      }).then(res => {
        if (res.code === 200){
          ElMessage({
            message: '登录成功 ！！！'+'欢迎您' +UserForm.username +'。' ,
            type: 'success',
          })
          store.setToken(res.msg)
          router.go(-1)
        }else {
          ElMessage({
            message: res.result,
            type: 'error',
          })
        }
      })
    } else {
      console.log('error submit!')
    }
  })
}

</script>

<style>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
}
.imgCode{
  display: flex;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.box-card {
  width: 480px;
}
</style>
