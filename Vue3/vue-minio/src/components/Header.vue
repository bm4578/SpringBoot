<template>
  <header>
    <div class='header-content'>
      <h1 class='content-logo'>
        <img src="../../assets/img/logo.png">
      </h1>
      <div class='content-nav'>
        <ul>
          <li>首页</li>
          <li>课程</li>
          <li>会员</li>
        </ul>
      </div>
      <div class='search-buy-login'>
        <div class='content-search'>
          <input type="" placeholder="请输入要搜索的课程">
          <el-icon color='#808080' :size='22'>
            <search style="width: 22px; height:22px;"/>
          </el-icon>
        </div>
        <div class='content-shopping'>
          <el-icon color='#808080' :size='24'><shopping-cart style="width: 24px; height:24px;"/></el-icon>
        </div>
        <div class='content-login'>
          <router-link to="/login">登录 / 注册</router-link>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { Search , ShoppingCart} from "@element-plus/icons-vue";
</script>

<style scoped>
header{
  display: flex;
  justify-content: center;
  align-items: center;
  width:100%;
  height: 100px;
  background: white;
  box-shadow: 0px 5px 6px rgba(0, 0, 0, 0.16);
  opacity: 1;
}
.header-content{
  display: flex;
  justify-content: space-around;
  width: 1200px;
}
.content-logo{
  width: 160px;
  height: 55px;
  margin: 10px 0;
  cursor: pointer;
}
.content-logo img{
  height: 100%;
}
.content-nav{
  width: 300px;
  height: 75px;
}
.content-nav ul{
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 75px;
}
.content-nav ul li{
  font-size: 18px;
  color: #808080;
}
.search-buy-login{
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 650px;
}
.content-search{
  display: flex;
  align-items: center;
  padding: 5px 10px;
  width: 350px;
  height: 35px;
  border-radius: 8px;
  background: #f0f2f4;
}
.content-search input{
  padding: 0 10px;
  width: 430px;
  height: 40px;
  border: 0;
  border-radius: 8px;
  color: #808080;
  background: #f0f2f4;
  font-size: 16px;
  outline: none;
}
.content-login a{
  font-size: 18px;
  color: #808080;
  text-align: center;
  cursor: pointer;
  text-decoration: none;
}
</style>